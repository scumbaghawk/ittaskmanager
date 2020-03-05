package com.dominikzurawski.ittaskmanager.controller;

import com.dominikzurawski.ittaskmanager.model.Task;
import com.dominikzurawski.ittaskmanager.model.User;
import com.dominikzurawski.ittaskmanager.repository.UserRepository;
import com.dominikzurawski.ittaskmanager.auth.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public String getUsers(Model model){

        //pobiera OBIEKTY TASK i potem w thymeleafie mozna je wyciagac poszeczegolnym metodami z TaskDto
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping("/user/edit/{id}")
    public String getUserEdit(Model model, @PathVariable Long id){

        Optional<User> userToEdit = userRepository.findById(id);

        if (userToEdit.isPresent()){
            model.addAttribute("user", userToEdit);
            return "useredit";
        }

        return "redirect:/";
    }

    @PostMapping("/user/edit/{id}")
    public String saveUserEdit(@ModelAttribute(value = "user") User user, Model model, @PathVariable Long id){

        Optional<User> userToEdit = userRepository.findById(id);

        if (userToEdit.isPresent()) model.addAttribute("user", userToEdit);

        if (user.getUsername().equals("")){
            model.addAttribute("usernameEmpty", "Username can't be empty");
            return "useredit";
        } else if (user.getRole().equals("")){
            model.addAttribute("roleIsNull", "Please ");
            return "useredit";
        } else if (user.getExperience().equals("")){
            model.addAttribute("experienceIsNull", "Description can't be empty");
            return "useredit";
        }

        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteTask(@PathVariable Long id) {

        Optional<User> userToDelete = userRepository.findById(id);

        if (userToDelete.isPresent()){
            User user = userToDelete.get();
            userRepository.delete(user);
        }

        userToDelete = null;

        return "redirect:/";
    }

    @GetMapping("/myprofile")
    public String getMyProfile(Model model){

        // lines below are to identify currently logged user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof CustomUserDetails) {
            username = ((CustomUserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        // lines above are to identify currently logged user

        User myUser = userRepository.findByUsername(username);
        List<Task> myTasks = userRepository.findByUsername(username).getTasks();

        int numberOfTasks = myTasks.size();
        int numberOfCompletedTasks = 0;

        for (Task task : myTasks){
            if (task.getCompleted() == true) numberOfCompletedTasks++;
        }

        int numberOfIncompletedTasks = numberOfTasks - numberOfCompletedTasks;

        model.addAttribute("userId", myUser.getId());
        model.addAttribute("username", username);
        model.addAttribute("completedTasks", numberOfCompletedTasks);
        model.addAttribute("incompletedTasks", numberOfIncompletedTasks);

        return "myprofile";
    }

    @GetMapping("/myprofile/changepassword/{id}")
    public String getChangePassword(Model model, @PathVariable Long id){

        Optional<User> userToEdit = userRepository.findById(id);

        if (userToEdit.isPresent()){
            model.addAttribute("user", userToEdit);
            return "changepassword";
        }

        return "redirect:/myprofile";
    }

    @PostMapping("/myprofile/changepassword/{id}")
    public String setChangePassword(@ModelAttribute(value = "user") User user, Model model, @PathVariable Long id){

        Optional<User> userToChangePassword = userRepository.findById(id);

        if (user.getPassword() == "") {
            model.addAttribute("passwordEmpty", "Password can't be empty.");
            return "redirect:/myprofile/changepassword/" + id;
        }

        if (userToChangePassword.isPresent()){
            List<Task> tasks = userToChangePassword.get().getTasks();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setTasks(tasks);
            System.out.println(user.getTasks());
            userRepository.save(user);
        }

        return "redirect:/myprofile";
    }
}
