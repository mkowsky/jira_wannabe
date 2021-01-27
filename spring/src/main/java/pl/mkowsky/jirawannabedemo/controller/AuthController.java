package pl.mkowsky.jirawannabedemo.controller;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.mkowsky.jirawannabedemo.*;
import pl.mkowsky.jirawannabedemo.dictionary.ERole;
import pl.mkowsky.jirawannabedemo.model.Role;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.model.VerificationToken;
import pl.mkowsky.jirawannabedemo.repository.RoleRepository;
import pl.mkowsky.jirawannabedemo.repository.UserRepository;
import pl.mkowsky.jirawannabedemo.security.jwt.JwtUtils;
import pl.mkowsky.jirawannabedemo.security.services.UserDetailsImpl;
import pl.mkowsky.jirawannabedemo.services.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
            System.out.println(loginRequest.getEmail());

        if(!(userRepository.existsByEmail(loginRequest.getEmail()))){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email is invalid."));

        } else {User user = userRepository.findUserByEmail(loginRequest.getEmail());
            System.out.println(user.getEmail());
            if(!(encoder.matches(loginRequest.getPassword(), user.getPassword()))){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Bad password."));
            }
            else {

                if(user.isActivated()){
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = jwtUtils.generateJwtToken(authentication);

                    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                    List<String> roles = userDetails.getAuthorities().stream()
                            .map(item -> item.getAuthority())
                            .collect(Collectors.toList());

                    System.out.println(userDetails.getUsername());
                    System.out.println(userDetails.getEmail());
                    System.out.println(userDetails.getPassword());
                    return ResponseEntity.ok(new JwtResponse(jwt,
                            userDetails.getId(),
                            userDetails.getUsername(),
                            userDetails.getEmail(),
                            roles,
                            user.getFirstName(),
                            user.getLastName(),
                            user.getPictureURL()));
                } else {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Account is not active."));
                }

            }
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest,  WebRequest request) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email is already in use!"));
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName());
//
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_PROJECT_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationSuccessEvent(user, request.getLocale(), appUrl));
        } catch(Exception exc) {
            exc.printStackTrace();
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @GetMapping("/confirmRegistration")
    public void confirmRegistration(HttpServletResponse httpServletResponse, @RequestParam("token") String token) throws IOException {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if(verificationToken == null) {
            httpServletResponse.sendRedirect("http://localhost:8081/token-confirmation/isValid-false");
        } else {
            User user = verificationToken.getUser();
            Calendar calendar = Calendar.getInstance();
            if((verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {
                httpServletResponse.sendRedirect("http://localhost:8081/token-confirmation/isValid-false");
            } else {
                user.setActivated(true);
                userService.save(user);
                httpServletResponse.sendRedirect("http://localhost:8081/token-confirmation/isValid-true");
            }

        }

    }
}
