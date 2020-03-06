package com.dominikzurawski.ittaskmanager.service;

import com.dominikzurawski.ittaskmanager.model.Task;
import org.aspectj.weaver.ast.Test;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class TaskComparatorByCompletedService implements Comparator<Task> {
    @Override
    public int compare(Task t1, Task t2) {
        return Boolean.compare(t2.getCompleted(),t1.getCompleted());
    }
}
