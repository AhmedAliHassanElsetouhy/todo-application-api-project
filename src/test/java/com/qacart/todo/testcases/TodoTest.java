package com.qacart.todo.testcases;
import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.hamcrest.Description;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Feature("Todo Feature")
public class TodoTest {

    @Story("Should Be Able To Add Todo")
    @Test(description = "Should Be Able To Add Todo")
    public void shouldBeAbleToAddTodo() {
        // Get user token
        String token = UserSteps.getUserToken();

        // Generate a new Todo
        Todo todo = TodoSteps.generateTodoTask();

        // Add the Todo to the database
        Response response = TodoApi.addTodo(todo, token);

        // Deserialize the response body into a Todo object
        Todo returnedTodo = response.body().as(Todo.class);

        // Verify that the response status code is 201 (Created)
        assertThat(response.statusCode(), equalTo(201));

        // Verify that the properties of the returned Todo object are equal to the properties of the original Todo object
        assertThat(returnedTodo.getISCompleted(), equalTo(todo.getISCompleted()));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
    }

    @Story("Should Not Be Able To Add Todo When If IsCompleted Is Missing")
    @Test(description = "Should Not Be Able To Add Todo When If IsCompleted Is Missing")
    public void shouldNotBeAbleToAddTodoWhenIfIsCompletedIsMissing() {
        // Get user token
        String token = UserSteps.getUserToken();

        // Create a new Todo object without the isCompleted property
        Todo todo = TodoSteps.addTodoTaskWithoutIsCompleted();

        // Attempt to add the Todo to the database
        Response response = TodoApi.addTodo(todo, token);

        // Deserialize the response body into an Error object
        Error returnedError = response.body().as(Error.class);

        // Verify that the response status code is 400 (Bad Request)
        assertThat(response.statusCode(), equalTo(400));

        // Verify that the error message indicates that the isCompleted property is required
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.IsCOMPLETED_IS_REQUIRED));
    }

    @Story("Should Be Able To Get All Todo Tasks")
    @Test(description = "Should Be Able To Get All Todo Tasks")
    public void shouldBeAbleToGetAllTodoTasks() {
        // Generate a new Todo task and add it to the database
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        TodoApi.addTodo(todo, token);

        // Retrieve the ID of the new task and the ID of the user who created it
        String taskId = TodoSteps.getTodoTaskID(todo, token);
        String userId = TodoSteps.getUserId(todo, token);

        // Get the list of all Todo tasks and verify that it contains at least one task
        Response response = TodoApi.getAllTodoTasks(token);
        Todo todoList = response.body().as(Todo.class);
        List<Todo> todos = todoList.getTasks();
        assertThat(todos.size(), greaterThanOrEqualTo(1));

        // Get the first task in the list and verify its properties
        Todo firstTodo = todos.get(0);
        Response taskResponse = TodoApi.getSpecificTodoTask(token, firstTodo.getId());
        Todo returnedTodo = taskResponse.body().as(Todo.class);
        assertThat(taskResponse.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(firstTodo.getItem()));
        assertThat(returnedTodo.getISCompleted(), equalTo(firstTodo.getISCompleted()));
        assertThat(returnedTodo.getId(), equalTo(firstTodo.getId()));
        assertThat(returnedTodo.getUserID(), equalTo(firstTodo.getUserID()));
        assertThat(returnedTodo.getCreatedAt(), notNullValue());
        assertThat(returnedTodo.getV(), equalTo(firstTodo.getV()));
    }

    @Story("Should Be Able To Get A Todo By ID")
    @Test(description = "Should Be Able To Get A Todo By ID")
    public void shouldBeAbleToGetATodoByID(){
        // Generate a new Todo task and add it to the database
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        String taskId = TodoSteps.getTodoTaskID(todo, token);
        String userId = TodoSteps.getUserId(todo, token);

        // Retrieve the specific Todo task by ID and verify its properties
        Response response = TodoApi.getSpecificTodoTask(token, taskId);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getISCompleted(), equalTo(false));
        assertThat(returnedTodo.getId(), equalTo(taskId));
        assertThat(returnedTodo.getUserID(), equalTo(userId));
        assertThat(returnedTodo.getCreatedAt(), notNullValue());
        assertThat(returnedTodo.getV(), equalTo("0"));
    }

    @Story("Should Be Able To Update Todo Task")
    @Test(description = "Should Be Able To Update Todo Task")
    public void shouldBeAbleToUpdateTodoTask(){
        // Update an existing Todo task in the database
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.updateTodoTask();
        String taskId = TodoSteps.getTodoTaskID(todo, token);
        String userId = TodoSteps.getUserId(todo, token);
        Response response = TodoApi.updateTodoTask(todo,token, taskId);

        // Verify that the updated Todo task has the correct properties
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getISCompleted(), equalTo(todo.getISCompleted()));
        assertThat(returnedTodo.getId(), equalTo(taskId));
        assertThat(returnedTodo.getUserID(), equalTo(userId));
        assertThat(returnedTodo.getCreatedAt(), notNullValue());
        assertThat(returnedTodo.getV(), equalTo("0"));
    }

    @Story("Should Be Able To Delete A Todo Task")
    @Test(description = "Should Be Able To Delete A Todo Task")
    public void shouldBeAbleToDeleteATodoTask(){
        // Generate a new Todo task and add it to the database
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        String taskId = TodoSteps.getTodoTaskID(todo,token);

        // Delete the Todo task from the database and verify that the response status code is 200
        Response response = TodoApi.deleteTodoTask(token, taskId);
        assertThat(response.statusCode(),equalTo(200));
    }

    @Story("Should Be Able To Ensure That Task Is Delete")
    @Test(description = "Should Be Able To Ensure That Task Is Delete")
    public void shouldBeAbleToEnsureThatTaskIsDelete(){
        // Generate a new Todo task and add it to the database
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        String taskId = TodoSteps.getTodoTaskID(todo, token);
        Response response = TodoApi.deleteTodoTask(token, taskId);

        // Verify that the response status code is 200 and that the returned Todo task has the correct ID
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getId(),equalTo(taskId));
    }
}