package com.dominikzurawski.ittaskmanager.repository;

import com.dominikzurawski.ittaskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //wyciaga wszystko do obiektu w kontrolerze, potem mozna uzywac metod zadeklarowanych w UserDto do wyciągania poszczególnych wartości
    List<User> findAll();

    User findByUsername(String username);
}
