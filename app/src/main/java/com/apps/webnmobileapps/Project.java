package com.apps.webnmobileapps;

import java.util.ArrayList;

public class Project {
    String title;
    ArrayList<Todo> todos = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Todo> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<Todo> todos) {
        this.todos = todos;
    }
}
