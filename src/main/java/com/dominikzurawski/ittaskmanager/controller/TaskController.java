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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

        SortService sortService = new SortService();
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("sortService", sortService);
        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    @PostMapping("/tasks")
    public String getSortedTasks(Model model, @ModelAttribute SortService sortService){

        System.out.println(sortService.getSortBy());;

        if (sortService.getSortBy().equals("")){
            System.out.println("SORTED BY DEFAULT");
            List<Task> tasks = taskRepository.findAll();
            model.addAttribute("tasks", tasks);
        } else if (sortService.getSortBy().equals("id")){
            System.out.println("SORTED BY ID");
            List<Task> tasks = taskRepository.findAll();
            model.addAttribute("tasks", tasks);
        } else if (sortService.getSortBy().equals("name")){
           // sort by name
            System.out.println("SORTED BY NAME");
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

    @GetMapping("/task/toggle/{id}")
    public String toggleTaskCompleted(@PathVariable Long id){

        Optional<Task> taskToToggle = taskRepository.findById(id);

        if (taskToToggle.isPresent()){

            Task task = taskToToggle.get();

//            System.out.println("BEFORE TOGGLE");
//            System.out.println("ID: " + task.getId() + ": " + task.getCompleted());

            if (task.getCompleted() == false){
                task.setCompleted(true);
            } else if (task.getCompleted() == true) {
                task.setCompleted(false);
            }

//            System.out.println("AFTER TOGGLE");
//            System.out.println("ID: " + task.getId() + ": " + task.getCompleted());

            taskRepository.save(task);
        }

        taskToToggle = null;

        return "redirect:/tasks";
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
//            System.out.println("Task name: *" + taskToAssign.get().getName() + "has no owner now.");
            System.out.println("You can't assign task to not existing user.");
            return "tasks";
        }

        if (userToAddTask.isPresent() && taskToAssign.isPresent()){

            User foundUser = userToAddTask.get();
            Task foundTask = taskToAssign.get();
            foundUser.addTask(foundTask);
            userRepository.save(foundUser);

            System.out.println("Task name: *" + foundTask.getName() + "* was assigned to " + foundUser.getUsername());
        }

        return "redirect:/tasks";
    }

}
