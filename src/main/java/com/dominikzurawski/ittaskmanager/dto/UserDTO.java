package com.dominikzurawski.ittaskmanager.dto;

import com.dominikzurawski.ittaskmanager.model.Experience;
import com.dominikzurawski.ittaskmanager.model.Role;
import com.dominikzurawski.ittaskmanager.model.Task;

import java.util.Set;

public interface UserDTO {

    public long getId();
    public String getUsername();
    public String getPassword();
    public Role getRole();
    public Experience getExperience();
    public Set<Task> getTasks();

}
