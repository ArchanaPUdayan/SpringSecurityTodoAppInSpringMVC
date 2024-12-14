package com.example.SpringSecurityTodoApp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.SpringSecurityTodoApp.Dto.UserDto;
import com.example.SpringSecurityTodoApp.entity.Todo;
import com.example.SpringSecurityTodoApp.entity.User;

@Service
public interface UserService {

	User save(UserDto userDto);

	User findUserById(long userId);

	List<Todo> getAllTodos();

	void updateCompletionStatus(int id, String string);

	List<User> findAllUsers();

	List<User> getAllUsers();

	void assignTodoToUser(int todoId, long userId);
	
	List<Todo> getTodosByUser(String email);

	User validateUser(String email, String password);

	Long getUserIdByEmail(String email);

	String getUserRoleByEmail(String email);

	User findUserByEmail(String email);

	

	User findByEmail(String username);

	
	


	

	

	
	
	
}
