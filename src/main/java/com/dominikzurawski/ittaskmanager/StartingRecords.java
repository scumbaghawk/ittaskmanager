package com.dominikzurawski.ittaskmanager;

import com.dominikzurawski.ittaskmanager.model.Experience;
import com.dominikzurawski.ittaskmanager.model.Role;
import com.dominikzurawski.ittaskmanager.model.Task;
import com.dominikzurawski.ittaskmanager.model.User;
import com.dominikzurawski.ittaskmanager.repository.TaskRepo;
import com.dominikzurawski.ittaskmanager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

// THIS IS RESPONSIBLE FOR INJECTING STARTING RECORDS!!!!

@Component
public class StartingRecords {

    private UserRepo userRepo;
    private TaskRepo taskRepo;

    @Autowired
    public StartingRecords(TaskRepo taskRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;

        Task task1 = new Task();
        task1.setName("Delete system32");
        task1.setShort_desc("You need to go to C:/ and delete system32");
        task1.setDesc("Deleting system32 is very important for our software to work properly. Do it and do not ask anyone. It is safe.");
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setName("Repair coffee machine");
        task2.setShort_desc("Repairing coffee machine is key");
        task2.setDesc("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec in massa blandit erat lacinia vestibulum non ac est.");
        task2.setCompleted(false);

        Task task3 = new Task();
        task3.setName("Upgrade website");
        task3.setShort_desc("We need better graphics for our website");
        task3.setDesc("Email Mike the designer to request visuals for the website. Customers' opinion is our graphics are really bad.");
        task3.setCompleted(true);

        Task task4 = new Task();
        task4.setName("Testing database");
        task4.setShort_desc("Check if h2-console is avilable");
        task4.setDesc("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed quis blandit dolor, ac sagittis nisl. Duis fringilla non odio vitae.");
        task4.setCompleted(false);

        User user1 = new User();
        user1.setUsername("Dominik");
        user1.setPassword("password1");
        user1.setRole(Role.EMPLOYEE);
        user1.setExperience(Experience.JUNIOR);
        user1.setTasks(Stream.of(task1, task2, task3).collect(Collectors.toSet()));

        User user2 = new User();
        user2.setUsername("Tom");
        user2.setPassword("ilovemyboss");
        user2.setRole(Role.EMPLOYEE);
        user2.setExperience(Experience.MID);
        user2.setTasks(Stream.of(task4).collect(Collectors.toSet()));

        taskRepo.save(task1);
        taskRepo.save(task2);
        taskRepo.save(task3);
        taskRepo.save(task4);

        userRepo.save(user1);
        userRepo.save(user2);
    }
}
