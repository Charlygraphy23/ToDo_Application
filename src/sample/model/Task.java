package sample.model;

import java.sql.Timestamp;

public class Task {
    private int taskid;
    private String task,description;
    private Timestamp datecreated;

    public Task() {
    }

    public Task(String task, String description, Timestamp datecreated) {
        this.task = task;
        this.description = description;
        this.datecreated = datecreated;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Timestamp datecreated) {
        this.datecreated = datecreated;
    }
}
