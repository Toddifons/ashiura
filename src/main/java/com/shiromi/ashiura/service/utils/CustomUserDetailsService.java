package com.shiromi.ashiura.service.utils;

import com.shiromi.ashiura.domain.entity.UserEntity;
import com.shiromi.ashiura.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("{" + username + "} is not found"));
    }

    //해당하는 user의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(UserEntity user) {
        return UserEntity.builder()
                .userName(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .rating(user.getRating())
                .build();
    }
}
