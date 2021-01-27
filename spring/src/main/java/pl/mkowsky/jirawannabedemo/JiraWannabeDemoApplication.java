package pl.mkowsky.jirawannabedemo;


import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mkowsky.jirawannabedemo.dictionary.ERole;
import pl.mkowsky.jirawannabedemo.model.Role;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.repository.RoleRepository;
import pl.mkowsky.jirawannabedemo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Configuration
public class JiraWannabeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiraWannabeDemoApplication.class, args);
    }
//    @Bean
//    ApplicationRunner applicationRunner(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder){
//        return args -> {
//            roleRepository.save(new Role(ERole.ROLE_USER));
//            roleRepository.save(new Role(ERole.ROLE_ADMIN));
//            roleRepository.save(new Role(ERole.ROLE_PROJECT_MANAGER));
//
//            Set<Role> userRoles = new HashSet<>();
//            Set<Role> modRoles = new HashSet<>();
//            Set<Role> adminRoles = new HashSet<>();
//
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            userRoles.add(userRole);
//            modRoles.add(userRole);
//            adminRoles.add(userRole);
//            Role modRole = roleRepository.findByName(ERole.ROLE_PROJECT_MANAGER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            modRoles.add(modRole);
//            adminRoles.add(modRole);
//            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            adminRoles.add(adminRole);
//
//            //team1
//            userRepository.save(new User("nowak-j@jira-wannabe.com", encoder.encode("user123"), userRoles, "Jan", "Nowak", true, "https://i.pinimg.com/originals/50/9c/26/509c2694309791e3175a928242b31266.jpg"));
//            userRepository.save(new User("ostrowska-k@jira-wannabe.com", encoder.encode("user123"), userRoles, "Katarzyna", "Ostrowska", true, "https://upload.wikimedia.org/wikipedia/commons/3/3e/KeiraKnightleyByAndreaRaffin2011.jpg"));
//            userRepository.save(new User("marciniak-m@jira-wannabe.com", encoder.encode("user123"), userRoles, "Malwina", "Marciniak", true, "https://www.vogue.pl/uploads/repository/julia_roberts/gettyimages-1407922.jpg"));
//            userRepository.save(new User("piotrowski-d@jira-wannabe.com", encoder.encode("user123"), userRoles, "Dawid", "Piotrowski", true, "https://yt3.ggpht.com/ytc/AAUvwnhD3sWBrjPYzkkADZrOIbgwHVt5XG8CGexjgptdJg=s900-c-k-c0x00ffffff-no-rj"));
//            //team2
//            userRepository.save(new User("adamska-l@jira-wannabe.com", encoder.encode("user123"), userRoles, "Luzia", "Adamska", true, "https://i.iplsc.com/sandra-bullock-adoptowala-dwuletniego-psa/00092XQOB03H6EG4-C122-F4.jpg"));
//            userRepository.save(new User("mazur-a@jira-wannabe.com", encoder.encode("user123"), userRoles, "Anita", "Mazur", true, "https://i.iplsc.com/watson-to-nie-tylko-utalentowana-aktorka-ale-rowniez-zadekla/0006NKHNT0JQ10VG-C122-F4.jpg"));
//            userRepository.save(new User("szewczyk-a@jira-wannabe.com", encoder.encode("user123"), userRoles, "Adam", "Szewczyk", true, "https://www.elle.pl/uploads/media/default/0003/28/piec-twarzy-leonardo-dicaprio-tych-najpiekniejszych.jpeg"));
//            userRepository.save(new User("kalinowski-k@jira-wannabe.com", encoder.encode("user123"), userRoles, "Kamil", "Kalinowski", true, "https://www.vogue.pl/images/blog/entry/facebook-brad-pitt-powraca-na-maly-ekran-34-big-6827.jpg"));
//            userRepository.save(new User("baranowski-d@jira-wannabe.com", encoder.encode("user123"), userRoles, "Damian", "Baranowski", true, "https://www.vogue.pl/images/blog/gallery/523/hugh-grant1_1600.jpg"));
//            userRepository.save(new User("andrzejewski-i@jira-wannabe.com", encoder.encode("user123"), userRoles, "Igor", "Andrzejewski", true, "https://m.media-amazon.com/images/M/MV5BMTQyMTExNTMxOF5BMl5BanBnXkFtZTcwNDg1NzkzNw@@._V1_.jpg"));
//            //team 3
//            userRepository.save(new User("sikorska-r@jira-wannabe.com", encoder.encode("user123"), userRoles, "Róża", "Sikorska", true, "https://bloximages.chicago2.vip.townnews.com/auburnpub.com/content/tncms/assets/v3/editorial/7/d8/7d86aa1a-3df6-11e8-b23b-0f79c7c48f51/5acec027d2170.image.jpg?resize=1200%2C675"));
//            userRepository.save(new User("borkowska-k@jira-wannabe.com", encoder.encode("user123"), userRoles, "Kinga", "Borkowska", true, "https://www.thetimes.co.uk/imageserver/image/%2Fmethode%2Ftimes%2Fprod%2Fweb%2Fbin%2F95671a8c-2165-11e8-8ccc-a83211a65142.jpg?crop=3000%2C2000%2C0%2C0"));
//            userRepository.save(new User("wasilewska-e@jira-wannabe.com", encoder.encode("user123"), userRoles, "Emilia", "Wasilewska", true, "https://upload.wikimedia.org/wikipedia/commons/a/a7/Premios_Goya_2018_-_Pen%C3%A9lope_Cruz.jpg"));
//            userRepository.save(new User("pietrzak-p@jira-wannabe.com", encoder.encode("user123"), userRoles, "Piotr", "Pietrzak", true, "https://rytmy.pl/wp-content/uploads/bfi_thumb/que-1-olchnzutho9oniz3vt1uiuj069eyibdbzn4ahrrkhc.jpg"));
//            userRepository.save(new User("sobczak-n@jira-wannabe.com", encoder.encode("user123"), userRoles, "Norbert", "Sobczak", true, "https://www.tuwroclaw.com/htimg/display/galbig3/?relativePath=news/43520/taco2_5b84037c41cab7_26170003.jpg"));
//            //pms
//            userRepository.save(new User("czerniewski-j@jira-wannabe.com", encoder.encode("admin123"), modRoles, "Jakub", "Czerniewski", true, "https://moja.wywrota.pl/storage/media/16177/sarius.jpg"));
//            userRepository.save(new User("sadowska-i@jira-wannabe.com", encoder.encode("admin123"), modRoles, "Iga", "Sadowska", true, "https://www.vogue.pl/uploads/repository/billie_ellish/re-mtacampaign-billieselect181217-billieeilish-album-pkg-04741-2.jpg"));
//            //admin
//            userRepository.save(new User("wojciechowski-p@jira-wannabe.com", encoder.encode("admin"), adminRoles, "Piotr", "Wojciechowski", true, "https://r-scale-c0.dcs.redcdn.pl/scale/o2/tvn/web-content/m/p1/i/88ef51f0bf911e452e8dbb1d807a81ab/ce07756e-940f-4c91-8da9-4b3f052c7978.jpg?type=1&srcmode=3&srcx=0/1&srcy=0/1&srcw=1920&srch=1080&dstw=1920&dsth=1080&quality=80"));
//
//
//        };
//    }


}
