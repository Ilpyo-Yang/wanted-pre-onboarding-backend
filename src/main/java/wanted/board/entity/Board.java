package wanted.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String uuid;
  private String category;
  private String subject;
  private String contents;
}
