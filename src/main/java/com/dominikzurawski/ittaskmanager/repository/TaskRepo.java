package com.dominikzurawski.ittaskmanager.repository;

import com.dominikzurawski.ittaskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
}
