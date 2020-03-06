package com.dominikzurawski.ittaskmanager.service;

import com.dominikzurawski.ittaskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class TaskComparatorByNameService implements Comparator<Task> {
    @Override
    public int compare(Task t1, Task t2) {
        return t1.getName().compareTo(t2.getName());
    }
}
