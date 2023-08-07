package wanted.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String uuid;
  private String category;
  private String subject;
  private String contents;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_uuid")
  private User user;

  public static Board create(String category, String subject, String contents){
    Board board = new Board();
    board.setCategory(category);
    board.setSubject(subject);
    board.setContents(contents);
    return board;
  }
}
