package com.todo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String action;
    private String description;
    private Boolean completed;

    public Long getId() { return id; }

    public String getAction() { return action; }

    public void setAction(String action) { this.action = action; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Boolean getCompleted() { return completed; }

    public void toggleCompleted() { this.completed = !this.completed; }

    public Todo(String action, String description) {
        this.action = action;
        this.description = description;
        this.completed = false;
    }

    Todo(){}
}
