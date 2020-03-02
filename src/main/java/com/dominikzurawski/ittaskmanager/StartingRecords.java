package com.dominikzurawski.ittaskmanager;

import com.dominikzurawski.ittaskmanager.model.Experience;
import com.dominikzurawski.ittaskmanager.model.Role;
import com.dominikzurawski.ittaskmanager.model.Task;
import com.dominikzurawski.ittaskmanager.model.User;
import com.dominikzurawski.ittaskmanager.repository.TaskRepository;
import com.dominikzurawski.ittaskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

// THIS IS RESPONSIBLE FOR INJECTING STARTING RECORDS!!!!

@Component
public class StartingRecords {

    private UserRepository userRepository;
    private TaskRepository taskRepository;

    @Autowired
    public StartingRecords(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;

        Task task1 = new Task();
        task1.setName("Delete system32");
        task1.setShortDesc("You need to go to C:/ and delete system32");
        task1.setDesc("Deleting system32 is very important for our software to work properly. Do it and do not ask anyone. It is safe.");
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setName("Repair coffee machine");
        task2.setShortDesc("Repairing coffee machine is key");
        task2.setDesc("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec in massa blandit erat lacinia vestibulum non ac est.");
        task2.setCompleted(false);

        Task task3 = new Task();
        task3.setName("Upgrade website");
        task3.setShortDesc("We need better graphics for our website");
        task3.setDesc("Email Mike the designer to request visuals for the website. Customers' opinion is our graphics are really bad.");
        task3.setCompleted(true);

        Task task4 = new Task();
        task4.setName("Testing database");
        task4.setShortDesc("Check if h2-console is avilable");
        task4.setDesc("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed quis blandit dolor, ac sagittis nisl. Duis fringilla non odio vitae.");
        task4.setCompleted(false);

        User employee = new User();
        employee.setUsername("Dominik");
        employee.setPassword("$2a$10$.xxq7E6VTLDAclIArzvQhe8VqrutoRfTFQl2gYVhkB8LiN3nKuG8."); // password is "haslo"
        employee.setRole(Role.EMPLOYEE);
        employee.setExperience(Experience.JUNIOR);
        employee.setTasks(Stream.of(task1, task2, task3).collect(Collectors.toSet()));

        User manager = new User();
        manager.setUsername("Tom");
        manager.setPassword("$2a$10$.xxq7E6VTLDAclIArzvQhe8VqrutoRfTFQl2gYVhkB8LiN3nKuG8."); // password is "haslo"
        manager.setRole(Role.MANAGER);
        manager.setExperience(Experience.MID);
        manager.setTasks(Stream.of(task4).collect(Collectors.toSet()));

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("$2a$10$FZaaWDGFH0NfzWB9Bc/5w.70uLhHJPkA.M38mBhWXosH6BpDdDMji"); // password is "admin"
        admin.setRole(Role.ADMIN);
        admin.setExperience(Experience.SENIOR);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);

        userRepository.save(employee);
        userRepository.save(manager);
        userRepository.save(admin);
    }
}
