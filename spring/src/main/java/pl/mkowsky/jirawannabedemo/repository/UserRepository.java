package pl.mkowsky.jirawannabedemo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mkowsky.jirawannabedemo.model.User;

import java.util.Optional;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(int userID);
    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);


}
