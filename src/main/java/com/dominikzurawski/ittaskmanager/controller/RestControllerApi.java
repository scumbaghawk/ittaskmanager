package com.dominikzurawski.ittaskmanager.controller;

import com.dominikzurawski.ittaskmanager.dto.TaskDto;
import com.dominikzurawski.ittaskmanager.dto.UserDto;
import com.dominikzurawski.ittaskmanager.repository.TaskRepo;
import com.dominikzurawski.ittaskmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RestControllerApi {

    private TaskRepo taskRepo;
    private UserRepo userRepo;

    @Autowired
    public RestControllerApi(TaskRepo taskRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegister(){
        return "register";
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
