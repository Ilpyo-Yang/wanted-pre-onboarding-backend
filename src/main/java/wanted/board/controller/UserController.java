package wanted.board.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import wanted.board.dto.UserDto;
import wanted.board.dto.form.LoginForm;
import wanted.board.entity.User;
import wanted.board.repository.UserRepository;
import wanted.board.security.TokenProvider;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;

    ModelMapper modelMapper = new ModelMapper();


    // 과제1. 사용자 회원가입 엔드포인트
    // 이메일과 비밀번호로 회원가입할 수 있는 엔드포인트를 구현해 주세요.
    // 이메일과 비밀번호에 대한 유효성 검사를 구현해 주세요.
    // 이메일 조건: @ 포함
    // 비밀번호 조건: 8자 이상
    // 비밀번호는 반드시 암호화하여 저장해 주세요.
    // 이메일과 비밀번호의 유효성 검사는 위의 조건만으로 진행해 주세요. 추가적인 유효성 검사 조건은 포함하지 마세요.
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDto dto, Errors error){
        if(vaildationCheck(dto.getEmail(), dto.getPassword())){
            return ResponseEntity.badRequest().body(error);
        }
        User user = modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(tokenProvider.generateToken(user));
    }


    // 과제2. 사용자 로그인 엔드포인트
    // 사용자가 올바른 이메일과 비밀번호를 제공하면, 사용자 인증을 거친 후에 JWT(JSON Web Token)를 생성하여 사용자에게 반환하도록 해주세요.
    // 과제 1과 마찬가지로 회원가입 엔드포인트에 이메일과 비밀번호의 유효성 검사기능을 구현해주세요.
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm form, Errors error){
        if(vaildationCheck(form.getEmail(), form.getPassword())){
            return ResponseEntity.badRequest().body(error);
        }

//        Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword())
//        );

        User user = userRepository.findByEmail(form.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(!passwordEncoder.matches(form.getPassword(), user.getPassword()))
            return ResponseEntity.badRequest().body(error);

//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(tokenProvider.generateToken(user));
    }


    // 유효성 체크
    public boolean vaildationCheck(String email, String password){
        if(email.contains("@") || password.length()<8){
            return false;
        }
        return true;
    }
}