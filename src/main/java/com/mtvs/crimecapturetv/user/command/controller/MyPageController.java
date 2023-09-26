package com.mtvs.crimecapturetv.user.command.controller;

import com.mtvs.crimecapturetv.configuration.login.UserDetail;
import com.mtvs.crimecapturetv.exception.AppException;
import com.mtvs.crimecapturetv.exception.ErrorCode;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.UserDto;
import com.mtvs.crimecapturetv.user.command.aggregate.dto.request.CommandMyPageEditRequest;
import com.mtvs.crimecapturetv.user.command.service.CommandMyPageService;
import com.mtvs.crimecapturetv.user.command.service.CommandUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final CommandUserService userService;
    private final CommandMyPageService myPageService;

    @GetMapping("")
    public String mypage(@AuthenticationPrincipal UserDetail userDetail, Model model) {

        UserDto userDto = userService.findUser(userDetail.getId());
        model.addAttribute("user", userDto);
        return "users/myPage";
    }

    @ResponseBody
    @GetMapping("/{userNo}/check-password")
    public String checkPassword(@PathVariable Long userNo, @RequestParam String password) {

        try {
            UserDto userDto = userService.findUserByNo(userNo);
            userService.checkPassword(userDto.getId(), password);
        } catch (AppException e) {
            //user가 찾아지지 않을 때
            if(e.getErrorCode().equals(ErrorCode.USER_NOT_FOUNDED)) {
                return "1";
            }
            // 비밀번호 오류
            else if(e.getErrorCode().equals(ErrorCode.INVALID_PASSWORD)) {
                return "2";
            }
        }
        //성공
        return "3";
    }

    @GetMapping("/{userNo}/edit")
    public String editPage(Model model, @PathVariable Long userNo, @AuthenticationPrincipal UserDetail userDetail) {

        UserDto user = userService.findUserByNo(userNo);

        // 다른 사람의 정보 수정 페이지에 진입했을 경우
        if (!userDetail.getId(). equals(user.getId())) {
            model.addAttribute("msg", "내 정보만 수정 가능합니다.");
            model.addAttribute("nextPage", "/users/mypage");
            return "/index";
        }

        model.addAttribute("user", user);
        model.addAttribute("myPageEditRequest", new CommandMyPageEditRequest());

        return "users/myPageEdit";
    }

    @ResponseBody
    @PostMapping("/{userNo}/edit")
    public String editInfo(@PathVariable Long userNo, @RequestPart(value = "request") CommandMyPageEditRequest req,
                           @AuthenticationPrincipal UserDetail userDetail) {
        try{
            userService.editUserInfo(req.getPassword(), req.getPhoneNumber(), userDetail.getId());
        } catch (Exception e) {
            return "로그인 정보가 유효하지 않습니다. 다시 로그인 해주세요.*/login";
        }

        return "변경이 완료되었습니다.*/mypage";
    }



}
