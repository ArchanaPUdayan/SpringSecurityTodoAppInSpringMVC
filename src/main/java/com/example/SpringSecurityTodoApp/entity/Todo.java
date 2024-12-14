package com.example.SpringSecurityTodoApp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int todoId;

    private String todoTitle;
    private String todoDescription;
    private String completed;
    

    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false) // Foreign key to the users table
    private User user;
    
    
    
    @ManyToOne
    
    @JoinColumn
    private User assignedUser;  // This is the field for assigning a user



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



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public User getAssignedUser() {
		return assignedUser;
	}



	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}



	public Todo(int todoId, String todoTitle, String todoDescription, String completed, User user, User assignedUser) {
		super();
		this.todoId = todoId;
		this.todoTitle = todoTitle;
		this.todoDescription = todoDescription;
		this.completed = completed;
		this.user = user;
		this.assignedUser = assignedUser;
	}



	public Todo() {
		super();
	}



	public Todo(String todoTitle, String todoDescription, String completed, User user, User assignedUser) {
		super();
		this.todoTitle = todoTitle;
		this.todoDescription = todoDescription;
		this.completed = completed;
		this.user = user;
		this.assignedUser = assignedUser;
	}



	@Override
	public String toString() {
		return "Todo [todoId=" + todoId + ", todoTitle=" + todoTitle + ", todoDescription=" + todoDescription
				+ ", completed=" + completed + ", user=" + user + ", assignedUser=" + assignedUser + "]";
	}

    // Getter and setter for assignedUser
    
}