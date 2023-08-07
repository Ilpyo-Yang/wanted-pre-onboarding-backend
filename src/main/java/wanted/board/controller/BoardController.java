package wanted.board.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import wanted.board.dto.BoardDto;
import wanted.board.dto.form.BoardEditForm;
import wanted.board.entity.Board;
import wanted.board.repository.BoardRepository;
import wanted.board.repository.UserRepository;

@Controller
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    ModelMapper modelMapper = new ModelMapper();


    // 과제 3. 새로운 게시글을 생성하는 엔드포인트
    @PostMapping
    public ResponseEntity<Object> setPost(@RequestBody BoardDto dto, Principal principal, Errors error){
        if(principal==null)
            return ResponseEntity.badRequest().body("로그인이 필요한 요청입니다.");

        Board board = Board.create(dto.getCategory(), dto.getSubject(), dto.getContents());
        board.setUser(userRepository.findById(dto.getUserUuid()).get());
        boardRepository.save(board);
        if(board.getUuid()==null)
            return ResponseEntity.badRequest().body(error);

        Map<String, String> response = new HashMap<>();
        response.put("board", board.getUuid());
        return ResponseEntity.ok(response);
    }

    // 과제 4. 게시글 목록을 조회하는 엔드포인트
    // 반드시 Pagination 기능을 구현해 주세요.
    @GetMapping
    public ResponseEntity<Object> getBoardList(Pageable pageable){
        Page<Board> list = boardRepository.findAll(pageable);
        return ResponseEntity.ok(list);
    }


    // 과제 5. 특정 게시글을 조회하는 엔드포인트
    // 게시글의 ID를 받아 해당 게시글을 조회하는 엔드포인트를 구현해 주세요.
    @GetMapping("/detail")
    public ResponseEntity<Object> getBoard(@RequestBody String uuid, Errors error){
        if(uuid==null)
            return ResponseEntity.badRequest().body(error);
        Board board = boardRepository.findById(uuid)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        BoardDto dto = modelMapper.map(board, BoardDto.class);
        return ResponseEntity.ok(dto);
    }


    // 과제 6. 특정 게시글을 수정하는 엔드포인트
    // 게시글의 ID와 수정 내용을 받아 해당 게시글을 수정하는 엔드포인트를 구현해 주세요.
    // 게시글을 수정할 수 있는 사용자는 게시글 작성자만이어야 합니다.
    @PostMapping("/edit")
    public ResponseEntity<Object> editPost(@RequestBody BoardEditForm form, Principal principal, Errors error){
        if(principal==null)
            return ResponseEntity.badRequest().body("로그인이 필요한 요청입니다.");

        Board board = boardRepository.findById(form.getUuid())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(board.getUser().getEmail().equals(principal.getName())){
            board.setCategory(board.getCategory());
            board.setSubject(board.getSubject());
            board.setContents(board.getContents());
            board.setUser(userRepository.findByEmail(principal.getName()).get());
            boardRepository.save(board);
            
            Map<String, String> response = new HashMap<>();
            response.put("board", board.getUuid());
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.badRequest().body(error);
        }
    }


    // 과제 7. 특정 게시글을 삭제하는 엔드포인트
    // 게시글의 ID를 받아 해당 게시글을 삭제하는 엔드포인트를 구현해 주세요.
    // 게시글을 삭제할 수 있는 사용자는 게시글 작성자만이어야 합니다.
    @PostMapping("/delete")
    public ResponseEntity<Object> editPost(@RequestBody String uuid, Principal principal, Errors error){
        if(principal==null)
            return ResponseEntity.badRequest().body("로그인이 필요한 요청입니다.");

        Board board = boardRepository.findById(uuid)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(board.getUser().getEmail().equals(principal.getName())){
            boardRepository.delete(board);
            return ResponseEntity.ok("success");
        }else{
            return ResponseEntity.badRequest().body(error);
        }
    }
}