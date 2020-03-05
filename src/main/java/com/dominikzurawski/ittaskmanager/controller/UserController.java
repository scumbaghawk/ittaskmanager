package com.dominikzurawski.ittaskmanager.controller;

import com.dominikzurawski.ittaskmanager.model.Task;
import com.dominikzurawski.ittaskmanager.model.User;
import com.dominikzurawski.ittaskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

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

        return "redirect:/users";
    }
}
