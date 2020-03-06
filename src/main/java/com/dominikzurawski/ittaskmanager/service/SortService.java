package com.dominikzurawski.ittaskmanager.service;

import com.dominikzurawski.ittaskmanager.model.Task;
import org.springframework.stereotype.Service;

@Service
public class SortService {

    private String sortBy;

    public SortService(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortService() {
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }

}
