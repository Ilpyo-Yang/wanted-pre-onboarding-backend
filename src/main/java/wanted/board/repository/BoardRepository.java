package wanted.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, String>{
  Page<Board> findAll(Pageable pageable);
}
