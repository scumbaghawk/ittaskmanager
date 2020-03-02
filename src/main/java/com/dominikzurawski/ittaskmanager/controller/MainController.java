package com.dominikzurawski.ittaskmanager.controller;

import com.dominikzurawski.ittaskmanager.model.Role;
import com.dominikzurawski.ittaskmanager.model.Task;
import com.dominikzurawski.ittaskmanager.model.User;
import com.dominikzurawski.ittaskmanager.repository.TaskRepository;
import com.dominikzurawski.ittaskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegister(Model model){

        User user = new User();
        model.addAttribute("user", user);

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute(value = "user") User user){

        // basic form validation
        if (user.getUsername() == "") return "redirect:/register";
        else if (user.getPassword() == "") return "redirect:/register";
        else if (user.getRole() == null) return "redirect:/register";
        else if (user.getExperience() == null) return "redirect:/register";

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

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

    @GetMapping("/tasks")
    public String getTasks(Model model){

        //pobiera OBIEKTY TASK i potem w thymeleafie mozna je wyciagac poszeczegolnym metodami z TaskDto
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    @GetMapping("/users")
    public String getUsers(Model model){

        //pobiera OBIEKTY TASK i potem w thymeleafie mozna je wyciagac poszeczegolnym metodami z TaskDto
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "users";
    }

}
