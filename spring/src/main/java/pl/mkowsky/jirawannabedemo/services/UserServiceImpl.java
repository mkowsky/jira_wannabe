package pl.mkowsky.jirawannabedemo.services;


import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mkowsky.jirawannabedemo.dto.PersonalDataDTO;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.model.VerificationToken;
import pl.mkowsky.jirawannabedemo.repository.TokenRepository;
import pl.mkowsky.jirawannabedemo.repository.UserRepository;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private EntityManager entityManager;
    private TokenRepository tokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public List<User> getAllusers() {
        return userRepository.findAll();
    }



    @Override
    public User getUserById(Long userID) {
        return userRepository.findUserById(userID);
    }



    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<PersonalDataDTO> getOnlyUsersPersonalData() {
        String searchQuery = "select id,  first_name as firstName, last_name as lastName, CONCAT( first_name, \" \", last_name ) AS fullName  from user_roles join user on user_roles.user_id = user.id where role_id != 2";
        Query query = this.entityManager.createNativeQuery(searchQuery).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(PersonalDataDTO.class));
        List<PersonalDataDTO> result = (List<PersonalDataDTO>) query.list();
        return result;
    }

    @Override
    public String generateRandomName() {
        String[] names = {"Mark", "Josh", "Marry", "Joshua", "David", "Jonny", "Abraham", "Nathan", "Martin", "Samuel", "King",
                "Terry", "Rob", "Jack", "Anny", "Linda", "Danny", "Stephan", "Roger", "George", "Gerrard", "Juliet", "Ola",
                "Alice", "Victoria", "Rick"};
        int random = (int) (Math.random() * (25 - 0)) + 0;
        return names[random];
    }


    @Override
    public String generateRandomSurname() {
        String[] surnames = {
                "Smith", "Groben", "Daniels", "Bark", "Ferry", "Cage",
                "Lincoln", "Abramov", "Luther", "Davis", "Jackson", "Roberts",
                "Taylor", "Kuba", "Shultz", "Nord", "Marks", "Michael", "Nate"
        };
        int random = (int) (Math.random() * (18 - 0)) + 0;
        return surnames[random];

    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken newUserToken = new VerificationToken(token, user);
        tokenRepository.save(newUserToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }
}
