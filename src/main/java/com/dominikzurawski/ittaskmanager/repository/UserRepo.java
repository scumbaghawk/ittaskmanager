package com.dominikzurawski.ittaskmanager.repository;

import com.dominikzurawski.ittaskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
