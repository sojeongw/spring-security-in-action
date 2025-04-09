package security.springsecurityinaction.repositories;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;
import security.springsecurityinaction.model.Token;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    @Autowired
    private JpaTokenRepository jpaTokenRepository;

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
        // 클라이언트 ID로 데이터베이스에서 토큰을 얻는다.
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = jpaTokenRepository.findByIdentifier(identifier);

        // ID가 존재하면 새로 생성된 값으로 토큰 값을 업데이트 한다.
        if(existingToken.isPresent()) {
            Token token = existingToken.get();
            token.setToken(csrfToken.getToken());
        } else {
            // 존재하지 않으면 생성한 CSRF 토큰과 ID로 새 데이터를 생성한다.
            Token token = new Token();
            token.setToken(csrfToken.getToken());
            token.setIdentifier(identifier);
            jpaTokenRepository.save(token);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String identifier = request.getHeader("X-IDENTIFIER");
        Optional<Token> existingToken = jpaTokenRepository.findByIdentifier(identifier);

        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
        }
        return null;
    }
}
