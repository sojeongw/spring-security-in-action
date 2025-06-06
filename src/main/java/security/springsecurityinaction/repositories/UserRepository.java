package security.springsecurityinaction.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import security.springsecurityinaction.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);
}
