package com.dominikzurawski.ittaskmanager.dto;

public interface TaskDto {

    // gettery musza byc takie same jak w klasie Task
    long getTask_id();
    String getName();
    String getShort_desc();
    String getDesc();
    boolean getCompleted();

}