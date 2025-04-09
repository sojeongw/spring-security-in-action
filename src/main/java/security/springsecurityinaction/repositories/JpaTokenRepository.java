package security.springsecurityinaction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import security.springsecurityinaction.model.Token;

import java.util.Optional;

public interface JpaTokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByIdentifier(String identifier);
}
