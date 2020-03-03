package com.dominikzurawski.ittaskmanager.controller;

import com.dominikzurawski.ittaskmanager.model.Task;
import com.dominikzurawski.ittaskmanager.model.User;
import com.dominikzurawski.ittaskmanager.repository.TaskRepository;
import com.dominikzurawski.ittaskmanager.repository.UserRepository;
import com.dominikzurawski.ittaskmanager.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/tasks")
    public String getAllTasks(Model model){

        //pobiera OBIEKTY TASK i potem w thymeleafie mozna je wyciagac poszeczegolnym metodami z TaskDto
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    @GetMapping("/mytasks")
    public String getMyTasks(Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof CustomUserDetails) {
            username = ((CustomUserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        System.out.println(username);

        List<Task> myTasks = userRepository.findByUsername(username).getTasks();
        model.addAttribute("myTasks", myTasks);

        return "mytasks";
    }

    @GetMapping("/newtask")
    public String addNewTask(Model model){

        Task task = new Task();
        model.addAttribute("task", task);

        return "newtask";
    }

    @PostMapping("/newtask")
    public String saveNewTask(@ModelAttribute(value = "task") Task task, Model model){

        if (task.getName() == ""){
            model.addAttribute("nameEmpty", "Name can't be empty");
            return "newtask";
        } else if (task.getShortDesc() == ""){
            model.addAttribute("shortDescEmpty", "Short description can't be empty");
            return "newtask";
        } else if (task.getDesc() == ""){
            model.addAttribute("descEmpty", "Description can't be empty");
            return "newtask";
        }

        task.setCompleted(false);
        taskRepository.save(task);

        return "redirect:/";
    }
}
