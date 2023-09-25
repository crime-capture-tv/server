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

import javax.transaction.Transactional;

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

    public boolean checkId(String id) {
        return userRepository.existsById(id);
    }

    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // 비밀번호 변경
    public void changePassword (String id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        String encodedPassword = encoder.encode(newPassword);
        user.changePassword(encodedPassword);
        userRepository.save(user);
    }

    // 이메일로 아이디 찾기
    public String findIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
        return user.getId();
    }

    // 아이디 + 이메일로 비밀번호 찾기
    public boolean findPassword(String id, String email) {
        return userRepository.existsByIdAndEmail(id, email);
    }

    // 비밀번호 확인
    public void checkPassword(String id, String password) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        if (!encoder.matches(password, user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public UserDto findUserByNo(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
        return UserDto.of(user);
    }

    // 유저 정보 수정
    @Transactional
    public void editUserInfo(String password, String phoneNumber, String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));

        String changedPassword = user.getPassword();
        String changedPhoneNumber = user.getPhoneNumber();

        if (!password.equals("")) {
            changedPassword = encoder.encode(password);
        }

        if (!phoneNumber.equals("")) {
            changedPhoneNumber = phoneNumber;
        }

        user.updateUser(changedPassword, changedPhoneNumber);
        userRepository.save(user);
    }

}
