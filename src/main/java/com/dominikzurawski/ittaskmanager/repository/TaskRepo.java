package com.dominikzurawski.ittaskmanager.repository;

import com.dominikzurawski.ittaskmanager.dto.TaskDto;
import com.dominikzurawski.ittaskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    //wyciaga wszystko do obiektu w rest api, potem mozna uzywac metod zadeklarowanych w TaskDto do wyciągania poszczególnych wartości
    @Query(value = "SELECT * FROM Task", nativeQuery = true)
    List<TaskDto> getTask();
}
