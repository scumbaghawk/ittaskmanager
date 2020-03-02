package com.dominikzurawski.ittaskmanager.controller;

import com.dominikzurawski.ittaskmanager.model.User;
import com.dominikzurawski.ittaskmanager.repository.TaskRepository;
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

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/register")
    public String getRegister(Model model){

        User user = new User();
        model.addAttribute("user", user);

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute(value = "user") User user, BindingResult result, Model model){

        // check if user already exists
        User userExisting = userRepository.findByUsername(user.getUsername());

        // basic form validation
        if (user.getUsername() == ""){
            model.addAttribute("usernameEmpty", "Username can't be empty.");
            return "register";
        } else if (userExisting != null){
            model.addAttribute("usernameTaken", "Username is already taken.");
            return "register";
        } else if (user.getPassword() == ""){
            model.addAttribute("passwordEmpty", "Password can't be empty.");
            return "register";
        } else if (user.getRole() == null){
            model.addAttribute("roleIsNull", "Please choose your role");
            return "register";
        } else if (user.getExperience() == null){
            model.addAttribute("experienceIsNull", "Please choose your experience");
            return "register";
        }

        // testing purposes
        System.out.println("New user registered:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Role: " + user.getRole());
        System.out.println("Experience: " + user.getExperience());
        System.out.println("=======================");

        // bcrypt encoding
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "redirect:/";
    }
}
