package com.example.SpringSecurityTodoApp.Dto;

public class TodoRequest {
	
	private int todoId;
	private String todoTitle;
	private String todoDescription;
	private String completed;
	public int getTodoId() {
		return todoId;
	}
	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}
	public String getTodoTitle() {
		return todoTitle;
	}
	public void setTodoTitle(String todoTitle) {
		this.todoTitle = todoTitle;
	}
	public String getTodoDescription() {
		return todoDescription;
	}
	public void setTodoDescription(String todoDescription) {
		this.todoDescription = todoDescription;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public TodoRequest(int todoId, String todoTitle, String todoDescription, String completed) {
		super();
		this.todoId = todoId;
		this.todoTitle = todoTitle;
		this.todoDescription = todoDescription;
		this.completed = completed;
	}
	public TodoRequest() {
		super();
	}
	public TodoRequest(String todoTitle, String todoDescription, String completed) {
		super();
		this.todoTitle = todoTitle;
		this.todoDescription = todoDescription;
		this.completed = completed;
	}
	@Override
	public String toString() {
		return "TodoRequest [todoId=" + todoId + ", todoTitle=" + todoTitle + ", todoDescription=" + todoDescription
				+ ", completed=" + completed + "]";
	}
	

}
