package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.models.Todo;
import io.restassured.response.Response;

public class TodoSteps {
    public static Todo generateTodoTask(){
        Faker faker = new Faker();
        boolean isCompleted = false;
        String item = faker.educator().course();
        return new Todo(isCompleted, item);
    }

    public static Todo addTodoTaskWithoutIsCompleted(){
        Faker faker = new Faker();
        String item = faker.educator().course();
        return new Todo(item);
    }
    public static Response addTodo(String token){
        Todo todo = TodoSteps.generateTodoTask();
        Response response = TodoApi.addTodo(todo, token);
        return response;
    }

    public static String getTodoTaskID(Todo todo, String token){
        Response response = TodoApi.addTodo(todo, token);
        return response.body().path("_id");
    }

    public static String getUserId(Todo todo, String token){
//        Response response = TodoApi.addTodo(todo, token);
//        return response.body().path("userID");
        Response response = TodoApi.addTodo(todo, token);
        Todo addedTodo = response.body().as(Todo.class);
        return addedTodo.getUserID();
    }

    public static void getAllTodo(Todo todo){
        TodoSteps.generateTodoTask();
        TodoSteps.addTodoTaskWithoutIsCompleted();

    }

    public static Todo updateTodoTask(){
        Faker faker = new Faker();
        boolean isCompleted = true;
        String item = faker.educator().course();
        return new Todo(isCompleted,item);
    }
}
