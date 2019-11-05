package com.apps.webnmobileapps;

public class Todo {
    private Integer id;
    private String text;
    private Boolean isCompleted;
    private Integer id_project;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setIdTodo(Integer id) {
        this.id = id;
    }

    public Integer getIdTodo() {
        return id;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Integer getId_project() {
        return id_project;
    }

    public void setId_project(Integer id_project) {
        this.id_project = id_project;
    }
}
