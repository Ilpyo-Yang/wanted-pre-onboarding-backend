package wanted.board.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import wanted.board.dto.BoardDto;
import wanted.board.dto.form.BoardEditForm;
import wanted.board.repository.BoardRepository;
import wanted.board.repository.UserRepository;
import wanted.board.security.TokenProvider;

@AutoConfigureMockMvc
@SpringBootTest
class BoardControllerTest {
  @Resource
  MockMvc mockMvc;
  @Resource
  ObjectMapper objectMapper;

  @Autowired
  UserRepository userRepository;
  @Autowired
  BoardRepository boardRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  TokenProvider tokenProvider;

  @Test
  @DisplayName("과제 3. 새로운 게시글을 생성하는 엔드포인트")
  @Rollback(value = false)
  void setPost() throws Exception {
    String url = "/api/board";
    BoardDto dto = BoardDto.builder()
        .category("Notice")
        .subject("게시판 테스트를 진행합니다.")
        .contents("posting 통합테스트")
        .userUuid("1da3da6b-bd8f-4835-be2a-bfb1d6d2da09")
        .createdDate(LocalDateTime.now())
        .build();

    String token =
        "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJzdWIiOiJURVNUQGdtYWlsLmNvbSIsImlhdCI6MTY5MTQwMzUyOSwiZXhwIjoxNjkxNDAzNjE2fQ.BBjmf89ZKGR0ZUUpIQjFd8WoSwr-Qhvzg8gSMkLZKWs";

    ResultActions resultActions = mockMvc.perform(
        post(url)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
    );

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.board").isNotEmpty())
        .andDo(print());
  }


  @Test
  @DisplayName("과제 4. 게시글 목록을 조회하는 엔드포인트")
  void getBoardList() throws Exception {
    String url = "/api/board";

    ResultActions resultActions = mockMvc.perform(
        get(url)
            .contentType(MediaType.APPLICATION_JSON)
    );

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.pageable").isNotEmpty())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    String jsonResponse = mvcResult.getResponse().getContentAsString();
    JSONObject jsonObject = new JSONObject(jsonResponse);
    JSONArray boardArray = jsonObject.getJSONArray("content");
    int expectedBoardCount = boardRepository.findAll().size();
    int actualBoardCount = boardArray.length();
    assertEquals(expectedBoardCount, actualBoardCount, "게시판 수가 일치하지 않습니다.");
  }


  @Test
  @DisplayName("과제 5. 특정 게시글을 조회하는 엔드포인트")
  void getBoard() throws Exception {
    String url = "/api/board/detail";
    String uuid = "03e90924-d53b-4f76-8b6e-e7a1a58d09e2";

    ResultActions resultActions = mockMvc.perform(
        get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(uuid)
    );

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isNotEmpty())
        .andDo(print());
  }


  @Test
  @DisplayName("과제 6. 특정 게시글을 수정하는 엔드포인트")
  @Rollback(value = false)
  void editPost() throws Exception {
    String url = "/api/board/edit";
    BoardEditForm form = BoardEditForm.builder()
        .uuid("5babdf80-0be4-4a0f-8b27-7b8e473dd7e1")
        .category("Notice")
        .subject("게시판 테스트를 진행합니다.")
        .contents("editing 통합테스트")
        .createdBy("2af67b39-bc83-4cbe-9421-ff632e3839f8")
        .build();

    String token =
      "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJzdWIiOiJ3YW50ZWRAZ21haWwuY29tIiwiaWF0IjoxNjkxMjI5NTIwLCJleHAiOjE2OTEyMjk2MDZ9.V9TQF-hQfVrGdDyMuDUbnDCWKJSISSZ3DOpznCB-DrM";
    ResultActions resultActions = mockMvc.perform(
        post(url)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(form))
    );

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.board").isNotEmpty())
        .andDo(print());
  }


  @Test
  @DisplayName("과제 7. 특정 게시글을 삭제하는 엔드포인트")
  @Rollback(value = false)
  void deletePost() throws Exception {
    String url = "/api/board/delete";
    String uuid = "5babdf80-0be4-4a0f-8b27-7b8e473dd7e1";

    String token =
        "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dLCJzdWIiOiJ3YW50ZWRAZ21haWwuY29tIiwiaWF0IjoxNjkxMjMwMjcwLCJleHAiOjE2OTEyMzAzNTZ9.4mg8mrvNc6QIF1liTunkpkBSlXMjdBP2Ee3WKqNsGUg";
    ResultActions resultActions = mockMvc.perform(
        post(url)
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(uuid)
    );

    resultActions
        .andExpect(status().isOk())
        .andDo(print());

    MvcResult mvcResult = resultActions.andReturn();
    String jsonResponse = mvcResult.getResponse().getContentAsString();
    JSONObject jsonObject = new JSONObject(jsonResponse);
    assertEquals(jsonObject.toString(), "success", "게시글 삭제에 실패했습니다.");
  }
}