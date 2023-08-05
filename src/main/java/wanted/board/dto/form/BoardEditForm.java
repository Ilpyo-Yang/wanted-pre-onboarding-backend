package wanted.board.dto.form;

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
public class BoardEditForm {
  private String uuid;
  private String category;
  private String subject;
  private String contents;
  private String createdBy;
}
