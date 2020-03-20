package com.dominikzurawski.ittaskmanager.controller;

import com.dominikzurawski.ittaskmanager.model.Task;
import com.dominikzurawski.ittaskmanager.model.User;
import com.dominikzurawski.ittaskmanager.repository.TaskRepository;
import com.dominikzurawski.ittaskmanager.repository.UserRepository;
import com.dominikzurawski.ittaskmanager.auth.CustomUserDetails;
import com.dominikzurawski.ittaskmanager.service.SortService;
import com.dominikzurawski.ittaskmanager.service.TaskComparatorByCompletedService;
import com.dominikzurawski.ittaskmanager.service.TaskComparatorByNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        // initialize sort object and add to the model
        SortService sortService = new SortService();
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("sortService", sortService);
        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    @PostMapping("/tasks")
    public String getSortedTasks(Model model, @ModelAttribute SortService sortService){

        // get sorting option from thymeleaf and sort tasks
        if (sortService.getSortBy().equals("")){
            List<Task> tasks = taskRepository.findAll();
            model.addAttribute("tasks", tasks);
        } else if (sortService.getSortBy().equals("id")){
            List<Task> tasks = taskRepository.findAll();
            model.addAttribute("tasks", tasks);
        } else if (sortService.getSortBy().equals("name")){
            List<Task> tasks = taskRepository.findAll();
            Collections.sort(tasks, new TaskComparatorByNameService());
            model.addAttribute("tasks", tasks);
        } else if (sortService.getSortBy().equals("completed")){
            List<Task> tasks = taskRepository.findAll();
            Collections.sort(tasks, new TaskComparatorByCompletedService());
            model.addAttribute("tasks", tasks);
        }

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

        // validate new task form
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

        // automatically set every new task incompleted
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

        // optional - task may not be found
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

        // optional - task may not be found
        Optional<Task> taskToDelete = taskRepository.findById(id);

        // if it is found then delete
        if (taskToDelete.isPresent()){
            Task task = taskToDelete.get();
            taskRepository.delete(task);
        }

        taskToDelete = null;

        return "redirect:/tasks";
    }

    @GetMapping("/task/toggle/{id}")
    public String toggleTaskCompleted(@PathVariable Long id, HttpServletRequest request){

        Optional<Task> taskToToggle = taskRepository.findById(id);

        // if task is found then toggle it
        if (taskToToggle.isPresent()){

            Task task = taskToToggle.get();

            if (task.getCompleted() == false){
                task.setCompleted(true);
            } else if (task.getCompleted() == true) {
                task.setCompleted(false);
            }

            taskRepository.save(task);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/task/assign/{id}")
    String getTaskAssign(@PathVariable Long id, @ModelAttribute(value = "user") User user, Model model){

        Optional<Task> taskToAssign = taskRepository.findById(id);
        Long taskId = taskToAssign.get().getId();
        String taskName = taskToAssign.get().getName();
        List<User> users = userRepository.findAll();

        if (taskToAssign.isPresent()){
            model.addAttribute("taskid", taskId);
            model.addAttribute("taskname", taskName);
            model.addAttribute("users", users);
            return "assigntask";
        }

        return "assigntask";
    }

    @PostMapping("/task/assign/{id}")
    String saveTaskAssign(@PathVariable Long id, @ModelAttribute(value = "user") User user){

        Optional<User> userToAddTask = userRepository.findById(user.getId());
        Optional<Task> taskToAssign = taskRepository.findById(id);

        if (user.getId() == -1){
            return "tasks";
        }

        if (userToAddTask.isPresent() && taskToAssign.isPresent()){

            User foundUser = userToAddTask.get();
            Task foundTask = taskToAssign.get();
            foundUser.addTask(foundTask);
            userRepository.save(foundUser);
        }

        return "redirect:/tasks";
    }

}
