package com.shiromi.ashiura.controller.auth;

import com.shiromi.ashiura.domain.dto.TokenInfo;
import com.shiromi.ashiura.domain.dto.UserDomain;
import com.shiromi.ashiura.domain.dto.request.UserLoginRequest;
import com.shiromi.ashiura.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {
    private final UserService userService;

    @Value("${url.api}")
    private String urlApi;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequestJson) {
        log.info("Post: {}", urlApi + "/auth/login");
        log.info("data: {}", userLoginRequestJson);
        TokenInfo tokenInfo = userService.userLogin(userLoginRequestJson);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/loginForm")
    public ResponseEntity<?> loginForm(UserLoginRequest userLoginRequestXML) {
        log.info("Post: {}", urlApi + "/auth/loginForm");
        log.info("data: {}", userLoginRequestXML.toString());
        TokenInfo tokenInfo = userService.userLogin(userLoginRequestXML);
        return ResponseEntity
                .created(URI.create(urlApi + "/view/info"))
                .header("Authorization",
                        tokenInfo.getGrantType() +" "+ tokenInfo.getAccessToken())
                .build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> save(@RequestBody UserDomain userDomain) {
        log.info("post: {}", urlApi + "/auth/signup");
        log.info("UsDo: {}", userDomain.toString());
        log.info("save: {}", userService.userSave(userDomain));

        return ResponseEntity.status(HttpStatus.OK)
                .body(userDomain.toString());
    }

    @PostMapping("/signupForm")
    public ResponseEntity<?> saveForm(UserDomain userDomain) {
        log.info("post: {}", urlApi + "/auth/signupForm");
        log.info("UsDo: {}", userDomain.toString());
        log.info("save: {}", userService.userSave(userDomain));

        return ResponseEntity.status(HttpStatus.OK)
                .body(userDomain.toString());
    }



//    @PostMapping("/signup")
//    public ResponseEntity<?> save(Model model) {
//        log.info("post: {}", urlCli + "/signup");
//        log.info("modD: {}", model.toString());
//        log.info("save: {}", userService.userSave(model));
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(model.toString());
//    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> save(
//            @RequestParam("userName") String userName,
//            @RequestParam("password") String password,
//            @RequestParam("phoneNumber") String phoneNumber
//    ) {
//        log.info("post: {}", urlCli + "/signup");
//        log.info("modD: {}", userName +"~"+ password +"~"+ phoneNumber);
//        log.info("save: {}", userService.userSave(userName, password, phoneNumber));
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(userName +"~"+ password +"~"+ phoneNumber);
//    }




//    @PostMapping("/signup")
//    public ResponseEntity<?> save (
//            @RequestBody Map<String, Object> resultMap
//    ){
//        log.info((String) resultMap.get("id"));
////        String join = userService.join(userDomain);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("");
//    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> save(
//            @RequestParam("id") String user_id,
//            @RequestParam("password") String password,
//            @RequestParam("name") String name,
//            @RequestParam("phone") String phone) {
//        UserDomain userDomain = UserDomain
//                .builder()
//                .userId(user_id)
//                .password(password)
//                .username(name)
//                .phoneNumber(phone)
//                .rating("N")
//                .build();
//
//
//        String join = userService.join(userDomain);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(join);
//    }



//    @GetMapping("/User/view")
//    public String view(Model model) {
//
//        List<UserEntity> users = UserRepository.findAll();
//        model.addAttribute("all",users);
//        return "view";
//    }
}
