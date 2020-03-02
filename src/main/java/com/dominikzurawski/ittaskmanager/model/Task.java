package com.dominikzurawski.ittaskmanager.model;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "short_desc")
    private String shortDesc;

    private String desc;

    private boolean completed; // automatyczny getter ustawia sie na isCompleted ale do H2 Hibernate musi byc getCompleted

    //    getters and setters...

    public long getId() {
        return id;
    }

    public void setId(long task_id) {
        this.id = task_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String short_desc) {
        this.shortDesc = short_desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
