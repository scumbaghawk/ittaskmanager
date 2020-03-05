package com.dominikzurawski.ittaskmanager.controller;

import com.dominikzurawski.ittaskmanager.model.Task;
import com.dominikzurawski.ittaskmanager.repository.TaskRepository;
import com.dominikzurawski.ittaskmanager.repository.UserRepository;
import com.dominikzurawski.ittaskmanager.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for:
 * displaying all tasks
 * displaying tasks for particular users (who have tasks assigned to them)
 * creating new tasks
 * updating existing tasks
 * deleting tasks
 **/

@Controller
public class TaskController {

    final TaskRepository taskRepository;
    final UserRepository userRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/tasks")
    public String getAllTasks(Model model){

        //pobiera OBIEKTY TASK i potem w thymeleafie mozna je wyciagac poszeczegolnym metodami z TaskDto
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    @GetMapping("/mytasks")
    public String getMyTasks(Model model){

        // lines below are to identify currently logged user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof CustomUserDetails) {
            username = ((CustomUserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        // lines above are to identify currently logged user


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

        if (task.getName().equals("")){
            model.addAttribute("nameEmpty", "Name can't be empty");
            return "newtask";
        } else if (task.getShortDesc().equals("")){
            model.addAttribute("shortDescEmpty", "Short description can't be empty");
            return "newtask";
        } else if (task.getDesc().equals("")){
            model.addAttribute("descEmpty", "Description can't be empty");
            return "newtask";
        }

        task.setCompleted(false);
        taskRepository.save(task);

        return "redirect:/tasks";
    }

    @GetMapping("/task/edit/{id}")
    public String getTaskEdit(Model model, @PathVariable Long id){

        Optional<Task> taskToEdit = taskRepository.findById(id);

        if (taskToEdit.isPresent()){
            model.addAttribute("task", taskToEdit);
            return "taskedit";
        }

        return "redirect:/";
    }

    @PostMapping("/task/edit/{id}")
    public String saveTaskEdit(@ModelAttribute(value = "task") Task task, Model model, @PathVariable Long id){

        Optional<Task> taskToEdit = taskRepository.findById(id);

        if (taskToEdit.isPresent()) model.addAttribute("task", taskToEdit);

        if (task.getName().equals("")){
            model.addAttribute("nameEmpty", "Name can't be empty");
            return "taskedit";
        } else if (task.getShortDesc().equals("")){
            model.addAttribute("shortDescEmpty", "Short description can't be empty");
            return "taskedit";
        } else if (task.getDesc().equals("")){
            model.addAttribute("descEmpty", "Description can't be empty");
            return "taskedit";
        }

        taskRepository.save(task);

        return "redirect:/tasks";
    }


    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {

        Optional<Task> taskToDelete = taskRepository.findById(id);

        if (taskToDelete.isPresent()){
            Task task = taskToDelete.get();
            taskRepository.delete(task);
        }

        taskToDelete = null;

        return "redirect:/tasks";
    }

}
