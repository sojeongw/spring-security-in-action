package security.springsecurityinaction.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import security.springsecurityinaction.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
