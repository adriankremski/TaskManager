package pl.adriankremski.taskmanager;

import java.util.Date;

import io.realm.RealmObject;

public class Task extends RealmObject{

    private boolean completed;
    private String text;
    private long createdAt;

    public Task() {
    }

    public Task(String text) {
        this.text = text;
        this.createdAt = new Date().getTime();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
