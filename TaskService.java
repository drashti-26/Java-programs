package service;

import java.util.ArrayList;
import model.Task;

public class TaskService {
    private ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be empty");
        }
        tasks.add(new Task(name));
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
