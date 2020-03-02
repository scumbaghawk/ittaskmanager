package com.dominikzurawski.ittaskmanager.dto;

public interface TaskDTO {

    // gettery musza byc takie same jak w klasie Task
    long getId();
    String getName();
    String getShort_desc();
    String getDesc();
    boolean getCompleted();

}