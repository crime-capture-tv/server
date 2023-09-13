package com.mtvs.crimecapturetv.configuration.login;

import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
// POST/login에 대한 요청을 security가 가로채서 로그인 진행
// 로그인 성공 시 Security Session을 생성
public class UserDetail implements UserDetails {
    // 인증 관련 사용자 정보를 담은 DTO

    private Long no;
    private String id;  // 로그인에 사용할 ID
    private String password;  // 비밀번호
    private String role;      // 권한

    // 권한 부여
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {   // GrantedAuthority : 현재 사용자(Principal)가 가지고 있는 권한
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));  // SimpleGrantedAuthority : GrantedAuthority 를 implement 한 클래스
        return authorities;
    }

    // 비밀번호
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }

    /*
    * 계정 만료 여부
    * true : 만료 안됨
    * false : 만료
    * */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     * */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
     * 계정 사용 가능 여부
     * true : 활성화
     * false : 비활성화
     * */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetail of(User user) {
        return UserDetail.builder()
                .no(user.getNo())
                .id(user.getId())
                .password(user.getPassword())
                .role(user.getRole().name())
                .build();
    }

}
