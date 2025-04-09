package security.springsecurityinaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identifier;

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getToken() {
        return this.token;
    }
}
