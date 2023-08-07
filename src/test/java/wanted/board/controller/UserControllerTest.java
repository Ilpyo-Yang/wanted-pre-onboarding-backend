package wanted.board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import wanted.board.dto.UserDto;
import wanted.board.dto.form.LoginForm;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
  @Resource
  MockMvc mockMvc;
  @Resource
  ObjectMapper objectMapper;

  @Test
  @DisplayName("과제 1. 사용자 회원가입 엔드포인트")
  @Rollback(value = false)
  void register() throws Exception {
    String url = "/api/user/register";
    UserDto dto = UserDto.builder()
        .email("wantedd@gmail.com")
        .password("12345678")
        .name("회원가입")
        .role("USER")
        .createdDate(LocalDateTime.now())
        .build();

    ResultActions resultActions = mockMvc.perform(
        post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
    );

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isNotEmpty())
        .andDo(print());
  }


  @Test
  @DisplayName("과제 2. 사용자 로그인 엔드포인트")
  void login() throws Exception {
    String url = "/api/user/login";
    LoginForm form = LoginForm.builder()
        .email("TEST@gmail.com")
        .password("12345678")
        .build();

    ResultActions resultActions = mockMvc.perform(
        post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(form))
    );

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isNotEmpty())
        .andDo(print());
  }
}