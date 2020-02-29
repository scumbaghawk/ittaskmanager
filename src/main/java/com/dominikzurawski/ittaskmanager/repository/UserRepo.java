package com.dominikzurawski.ittaskmanager.repository;

import com.dominikzurawski.ittaskmanager.dto.UserDto;
import com.dominikzurawski.ittaskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    //wyciaga wszystko do obiektu w rest api, potem mozna uzywac metod zadeklarowanych w UserDTO do wyciągania poszczególnych wartości
    @Query(value = "SELECT * FROM User ", nativeQuery = true)
    List<UserDto> getUser();
}
