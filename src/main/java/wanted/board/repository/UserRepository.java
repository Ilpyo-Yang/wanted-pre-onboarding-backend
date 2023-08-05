package wanted.board.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.board.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

  Optional<User> findByEmail(String email);
}
