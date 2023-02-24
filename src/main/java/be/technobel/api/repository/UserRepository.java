package be.technobel.api.repository;

import be.technobel.api.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("select u from User u where u.login = ?1")
    Optional<User> findByLogin(String login);

}
