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
        task1.setTask_id(1);
        task1.setName("Zrobic cos");
        task1.setShort_desc("Wejdz tu albo tam");
        task1.setDesc("To bardzo dlugi opis jakiegos skomplikowanego zadania");
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setTask_id(2);
        task2.setName("Zrobic cos 2");
        task2.setShort_desc("asdafdfsd");
        task2.setDesc("To bardzo dlugi opis jakiegos skomplikowanego zadania");
        task2.setCompleted(false);

        Task task3 = new Task();
        task3.setTask_id(3);
        task3.setName("Zrobic cos 3");
        task3.setShort_desc("Wejdz tu i tu i zrob tamto");
        task3.setDesc("To bardzo dlugi opis jakiegos skomplikowanego zadania");
        task3.setCompleted(true);

        Task task4 = new Task();
        task4.setTask_id(4);
        task4.setName("Zrobic cos 4");
        task4.setShort_desc("Wejdz tu i tu i zrob tamto");
        task4.setDesc("To bardzo dlugi opis jakiegos skomplikowanego zadania");
        task4.setCompleted(false);

        User user1 = new User();
        user1.setUser_id(1);
        user1.setUsername("Dominik");
        user1.setPassword("haslo");
        user1.setRole(Role.EMPLOYEE);
        user1.setExperience(Experience.JUNIOR);
        user1.setTasks(Stream.of(task1, task2, task3).collect(Collectors.toSet()));


        User user2 = new User();
        user2.setUser_id(2);
        user2.setUsername("Tomek");
        user2.setPassword("haslo");
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
