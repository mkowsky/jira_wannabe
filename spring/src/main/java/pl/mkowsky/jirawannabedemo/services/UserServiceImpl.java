package pl.mkowsky.jirawannabedemo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllusers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User getUserById(Long userID) {
        return userRepository.findUserById(userID);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
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
}
