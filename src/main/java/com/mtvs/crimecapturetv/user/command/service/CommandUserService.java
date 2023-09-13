package com.mtvs.crimecapturetv.user.command.service;

import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.UserDto;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.request.CommandUserJoinRequest;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.repository.CommandUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandUserService {

    private final CommandUserRepository userRepository;
    private final BCryptPasswordEncoder encoder;



    public UserDto join(CommandUserJoinRequest userJoinRequest){

        //user id 중복 체크
        if (userRepository.existsById(userJoinRequest.getId())) {
            throw new AppException(ErrorCode.DUPLICATED_USER_ID);
        }

        String encodedPassword = encoder.encode(userJoinRequest.getPassword());
        User user = userRepository.save(userJoinRequest.toEntity(encodedPassword));
        return UserDto.of(user);
    }

    public UserDto findUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        return UserDto.of(user);
    }



}
