package com.shiromi.ashiura.domain.entity;

import com.shiromi.ashiura.domain.dto.UserDomain;
import com.shiromi.ashiura.domain.dto.request.UserUpdateRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @Column(name= "user_id", unique = true, nullable = false, length = 100)
    private String userName;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name= "username", nullable = false)
    private String name;

    @Column(name= "phoneNumber", nullable = false, length = 50)
    private String phoneNumber;

    @Column(name = "role",nullable = false)
    private String rating;

    @Column
    private String provider;

    @Column
    private String providerId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(String rating : rating.split(",")){
            authorities.add(new SimpleGrantedAuthority(rating));
        }
        return authorities;
        // rating을 ","로 구분해서 리스트로 정렬후 권한확인

    }

    @Builder
    public UserEntity(long idx, String userName, String name, String password, String phoneNumber,
                      String rating, String provider, String providerId) {
        this.idx = idx;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.provider = provider;
        this.providerId = providerId;
    }


    public UserDomain toDomain() {
        return UserDomain.builder()
                .idx(idx)
                .userName(userName)
                .name(name)
                .password(password)
                .phoneNumber(phoneNumber)
                .rating(rating)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


