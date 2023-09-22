package com.mtvs.crimecapturetv.user.command.controller;

import com.mtvs.crimecapturetv.configuration.login.UserDetail;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.User;
import com.mtvs.crimecapturetv.user.command.repository.CommandUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletResponse;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class homeController {

    private final CommandUserRepository userRepository;

    @GetMapping(value = {"", "/"})
    public String home(Model model, @AuthenticationPrincipal UserDetail userDetail) {
//        userDetail.get

        model.addAttribute("userDetail", userDetail);

        return "index";
    }

    @GetMapping("/store")
    public String store() {
        return "stores/store";
    }

//    @GetMapping("/users/login")
//    public String usersLogin() {
//        return "users/login";
//    }

//    @GetMapping("/users/join")
//    public String usersJoin() {
//        return "users/register";
//    }
}
