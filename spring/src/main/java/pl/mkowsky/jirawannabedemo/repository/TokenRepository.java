package pl.mkowsky.jirawannabedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.model.VerificationToken;

@Repository
@Transactional
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {


    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);

}
