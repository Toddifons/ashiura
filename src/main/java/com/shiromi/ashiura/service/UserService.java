package com.shiromi.ashiura.service;

import com.shiromi.ashiura.config.jwt.JwtProvider;
import com.shiromi.ashiura.domain.dto.TokenInfo;
import com.shiromi.ashiura.domain.dto.request.UserSignupRequestDTO;
import com.shiromi.ashiura.domain.dto.request.UserUpdateRequestDTO;
import com.shiromi.ashiura.repository.UserRepository;
import com.shiromi.ashiura.domain.dto.UserDomain;
import com.shiromi.ashiura.domain.dto.request.UserLoginRequestDTO;
import com.shiromi.ashiura.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public TokenInfo userLogin(UserLoginRequestDTO userLogin) {
        //1. Login ID/PW 를 기반으로 Authentication 객체 생성
        //이때 authentication는 인증 여부를 확인하는 authenticated 값이 false
        //UsernamePasswordAuthenticationToken(Object principal, Object credentials)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword());
        //2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        //aeuthenticate 메서드가 실행될 때 CustomUserDetailsService에서 오버라이드한 loadUserByUsername 메서드가 실행
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtProvider.generateToken(authentication);

        return tokenInfo;
    }

    //유저를 DB에 저장
    public String userSave(UserSignupRequestDTO userSignupRequestDTO) {
        if (userSignupRequestDTO.getRating() == null) {
            userSignupRequestDTO.setRating("USER");
        }// Rating이 설정되지 않았을 경우 디폴트값을 주듯이 "N"으로 set
        String encodePassword = encoder.encode(userSignupRequestDTO.getPassword());
        userSignupRequestDTO.setPassword(encodePassword);
        UserEntity savedUser = userRepository.save(userSignupRequestDTO.toEntity());
        return savedUser.toString();
    }

    public String userPwUpdate(UserDomain userDto, String OrgPw, String NewPw) {
        if (bCryptPasswordEncoder.matches(userDto.getPassword(),OrgPw) ) {
            return "3"; //비밀번호 불일치
        } else {
            String encodePw = encoder.encode(NewPw);
            userRepository.updatePassword(userDto.getIdx(),encodePw);
        }
        return "2"; //성공
    }

    public String userUpdate(UserUpdateRequestDTO userDto){
        log.info("{},{},{}",userDto.getIdx(),userDto.getName(),userDto.getPhoneNumber());
        userRepository.updateNameAndPhoneNumber(userDto.getIdx(),userDto.getName(),userDto.getPhoneNumber());
        return "2";
    }

    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(IllegalAccessError::new);
    }

    public UserEntity findByIdx(Long idx) {
        return userRepository.findByIdx(idx)
                .orElseThrow(IllegalAccessError::new);
    }

}
