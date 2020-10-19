package pl.mkowsky.jirawannabedemo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mkowsky.jirawannabedemo.model.User;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(int userID);
    User findUserByUsername(String username);
}
