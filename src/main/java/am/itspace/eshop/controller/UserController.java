package am.itspace.eshop.controller;

import am.itspace.eshop.entity.User;
import am.itspace.eshop.entity.UserType;
import am.itspace.eshop.security.SpringUser;
import am.itspace.eshop.service.CategoryService;
import am.itspace.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final CategoryService categoryService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user/register")
    public String userRegisterPage(@RequestParam(value = "msg", required = false) String msg, ModelMap modelMap) {
        if (msg != null && !msg.isEmpty()) {
            modelMap.addAttribute("msg", msg);
        }
        return "register";
    }

    @PostMapping("/user/register")
    public String userRegister(@ModelAttribute User user) {
        User byEmail = userService.findByEmail(user.getEmail());
        if (byEmail == null) {
            user.setUserType(UserType.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            log.info("User with {} email registered successfully", user.getEmail());
            return "redirect:/user/register?msg=User Registered";
        } else {
            log.info("User with {} email already registered", user.getEmail());
            return "redirect:/user/register?msg=Email already in use";
        }
    }

    @GetMapping("/loginPage")
    public String loginPage(@AuthenticationPrincipal SpringUser springUser, ModelMap modelMap) {
        if (springUser == null) {
            modelMap.addAttribute("categories", categoryService.findAll());
            return "loginPage";
        }
        return "redirect:/";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal SpringUser springUser) {
        User user = springUser.getUser();
        if (user.getUserType() == UserType.ADMIN) {
            log.info("user {} logged in", user.getEmail());
            return "redirect:/admin/home";
        } else {
            return "redirect:/";
        }
    }
}
