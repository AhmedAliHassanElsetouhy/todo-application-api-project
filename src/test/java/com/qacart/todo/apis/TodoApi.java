package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {
    public static Response addTodo(Todo todo, String token){
        return given()
                .spec(Specs.getRequestSpec())
                .body(todo)
                .auth().oauth2(token)
                .when()
                .post(Route.TODOTASKS_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response getAllTodoTasks(String token){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.TODOTASKS_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response getSpecificTodoTask(String token, String id){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.TODOTASKS_ROUTE+id)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response updateTodoTask(Todo todo, String token, String id){
        return given()
                .spec(Specs.getRequestSpec())
                .body(todo)
                .auth().oauth2(token)
                .when()
                .put(Route.TODOTASKS_ROUTE+id)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteTodoTask(String token, String taskId){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .delete(Route.TODOTASKS_ROUTE+taskId)
                .then()
                .log().all()
                .extract().response();
    }
}