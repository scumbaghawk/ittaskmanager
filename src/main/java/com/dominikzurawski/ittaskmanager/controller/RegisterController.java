package com.dominikzurawski.ittaskmanager.controller;

import com.dominikzurawski.ittaskmanager.service.PasswordConfirmation;
import com.dominikzurawski.ittaskmanager.model.User;
import com.dominikzurawski.ittaskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/register")
    public String getRegister(Model model){

        User user = new User();
        // initialize extra object to verify password in form
        PasswordConfirmation passwordConfirmation = new PasswordConfirmation();
        model.addAttribute("user", user);
        model.addAttribute("passwordToConfirm", passwordConfirmation);

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute(value = "user") User user, PasswordConfirmation passwordConfirmation, BindingResult result, Model model){

        // check if user already exists
        User userExisting = userRepository.findByUsername(user.getUsername());

        // form validation
        if (user.getUsername().equals("")){
            model.addAttribute("usernameEmpty", "Username can't be empty.");
            return "register";
        } else if (userExisting != null){
            model.addAttribute("usernameTaken", "Username is already taken.");
            return "register";
        } else if (user.getPassword().equals("")) {
            model.addAttribute("passwordEmpty", "Password can't be empty.");
            return "register";
        } else if (passwordConfirmation.getPasswordToConfirm().equals("")) {
            model.addAttribute("passwordconfirmationfailed", "Password confirmation can't be empty.");
            return "register";
        } else if (!passwordConfirmation.getPasswordToConfirm().equals(user.getPassword())) {
            System.out.println("password: " + user.getPassword());
            System.out.println("password confirmation: " + passwordConfirmation.getPasswordToConfirm());
            model.addAttribute("passwordmissmatch", "Passwords don't match");
            return "register";
        } else if (user.getRole() == null){
            model.addAttribute("roleIsNull", "Please choose your role");
            return "register";
        } else if (user.getExperience() == null){
            model.addAttribute("experienceIsNull", "Please choose your experience");
            return "register";
        }

        // bcrypt encoding
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // add new user to database
        userRepository.save(user);

        return "redirect:/";
    }
}
