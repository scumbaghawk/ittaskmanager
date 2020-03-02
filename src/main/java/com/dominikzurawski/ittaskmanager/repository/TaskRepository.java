package com.dominikzurawski.ittaskmanager.repository;

import com.dominikzurawski.ittaskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    //wyciaga wszystko do obiektu w kontrolerze, potem mozna uzywac metod zadeklarowanych w TaskDto do wyciągania poszczególnych wartości
    List<Task> findAll();
}
