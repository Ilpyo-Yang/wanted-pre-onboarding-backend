package wanted.board.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDto {
  private String uuid;
  private String category;
  private String subject;
  private String contents;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
  private String userUuid;
}
