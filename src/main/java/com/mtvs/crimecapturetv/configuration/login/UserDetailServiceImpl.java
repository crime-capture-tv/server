package com.mtvs.crimecapturetv.configuration.login;

import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.repository.CommandUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 인증을 위해 아이디로 유저 디테일 찾는 과정
// Security는 User 객체가 아닌 UserDetail이 필요하기 때문에 따로 설정 필요
// => Security에서 사용할 UserDetail return
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final CommandUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("유저 ID를 찾을 수 없습니다."));
        return UserDetail.of(user);
    }
}
