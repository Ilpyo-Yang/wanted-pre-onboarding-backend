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
public class UserDto {
  private String uuid;
  private String name;
  private String email;
  private String password;
  private String role;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
}
