package com.dominikzurawski.ittaskmanager.controller;

import com.dominikzurawski.ittaskmanager.dto.TaskDto;
import com.dominikzurawski.ittaskmanager.dto.UserDto;
import com.dominikzurawski.ittaskmanager.model.User;
import com.dominikzurawski.ittaskmanager.repository.TaskRepo;
import com.dominikzurawski.ittaskmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private UserRepo userRepo;

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
        if (user.getUsername() == "") {
            return "redirect:/register";
        } else if (user.getPassword() == "") {
            return "redirect:/register";
        } else if (user.getRole() == null) {
            return "redirect:/register";
        } else if (user.getExperience() == null){
            return "redirect:/register";
        }

        System.out.println("New user registered:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Role: " + user.getRole());
        System.out.println("Experience: " + user.getExperience());
        System.out.println("=======================");

        userRepo.save(user);
        return "redirect:/";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

    @GetMapping("/tasks")
    public String getTasks(Model model){

        //pobiera OBIEKTY TASK i potem w thymeleafie mozna je wyciagac poszeczegolnym metodami z TaskDto
        List<TaskDto> tasks = taskRepo.getTask();
        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    @GetMapping("/users")
    public String getUsers(Model model){

        //pobiera OBIEKTY TASK i potem w thymeleafie mozna je wyciagac poszeczegolnym metodami z TaskDto
        List<UserDto> users = userRepo.getUser();
        model.addAttribute("users", users);

        return "users";
    }
}
