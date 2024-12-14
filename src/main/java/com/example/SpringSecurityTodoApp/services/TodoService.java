package com.example.SpringSecurityTodoApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.SpringSecurityTodoApp.Dto.TodoRequest;
import com.example.SpringSecurityTodoApp.entity.Todo;
import com.example.SpringSecurityTodoApp.entity.User;
import com.example.SpringSecurityTodoApp.repository.TodoRepository;
import com.example.SpringSecurityTodoApp.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class TodoService {

    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TodoRepository todoRepo;

    public Todo save(Todo todo, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        todo.setUser(user);
        return todoRepo.save(todo);
    }

    public List<Todo> getAllTodos() {
        return todoRepo.findAll();
    }

    public Optional<Todo> getTodoById(int id) {
        return todoRepo.findById(id);
    }

    public void deleteTodoById(int id) {
        Optional<Todo> todo = todoRepo.findById(id);
        if (todo.isPresent()) {
            todoRepo.deleteById(id);
        } else {
            throw new IllegalArgumentException("Todo not found with ID: " + id);
        }
    }


    public List<Todo> findAll() {
        return todoRepo.findAll();
    }

    public List<Todo> getCompletedTodos() {
        return todoRepo.findByCompleted("YES");
    }

    public List<Todo> getIncompletedTodos() {
        return todoRepo.findByCompleted("NO");
    }

    @Transactional
    public void update(Todo updatedTodo, long userId) {
        // Check if the Todo exists
        Optional<Todo> existingTodoOpt = todoRepo.findById(updatedTodo.getTodoId());
        if (existingTodoOpt.isPresent()) {
            Todo existingTodo = existingTodoOpt.get();
            if (existingTodo.getUser().getId() == userId) {
                // Update properties
                existingTodo.setTodoTitle(updatedTodo.getTodoTitle());
                existingTodo.setTodoDescription(updatedTodo.getTodoDescription());
                existingTodo.setCompleted(updatedTodo.getCompleted());
                todoRepo.save(existingTodo); // Save updated Todo to the database
            } else {
                throw new IllegalArgumentException("User not authorized to update this Todo");
            }
        } else {
            throw new IllegalArgumentException("Todo not found with ID: " + updatedTodo.getTodoId());
        }
    }
    
    
	
	public List<User> getAllUsers() {
	    return userRepository.findAll();
	}
	
	public List<Todo> getTodosByUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        return todoRepo.findByUser(user);
    }
	
	public void updateTodoStatus(int todoId, String email, String status) {
        Todo todo = todoRepo.findById(todoId).orElseThrow(() -> 
            new IllegalArgumentException("Todo not found with ID: " + todoId)
        );

        if (!todo.getUser().getEmail().equals(email)) {
            throw new SecurityException("Unauthorized access to this todo");
        }

        todo.setCompleted(status);
        todoRepo.save(todo);
    }

	public List<Todo> getTodosForUser(Long userId) {
		// TODO Auto-generated method stub
		return todoRepo.findByUserId(userId);
	}

	
	
	public List<Todo> getTodosAssignedToUser(Long userId) {
	    return todoRepo.findByAssignedUserId(userId);
	}
	 public List<Todo> getAssignedTodos(long userId) {
	        return todoRepo.findByAssignedUserId(userId);
	    }
	
	 public List<Todo> findByAssignedUserId(Long userId) {
		    return todoRepo.findByAssignedUserId(userId);
	 }
	 
	 //status changing

	 public void changeStatus(int todoId, String status) {
		    Todo todo = todoRepo.findById(todoId)
		                              .orElseThrow(() -> new RuntimeException("Todo not found"));
		    todo.setCompleted(status); // Update status to "Yes" or "No"
		    todoRepo.save(todo);
		}

	 public void markCompleted(int todoId) {
		    System.out.println("Marking todo with ID " + todoId + " as completed.");
		    Todo todo = todoRepo.findById(todoId)
		                        .orElseThrow(() -> new RuntimeException("Todo not found"));
		    todo.setCompleted("Yes");
		    todoRepo.save(todo);
		    System.out.println("Todo marked as completed: " + todo);
		}

		public void markIncomplete(int todoId) {
		    System.out.println("Marking todo with ID " + todoId + " as incomplete.");
		    Todo todo = todoRepo.findById(todoId)
		                        .orElseThrow(() -> new RuntimeException("Todo not found"));
		    todo.setCompleted("No");
		    todoRepo.save(todo);
		    System.out.println("Todo marked as incomplete: " + todo);
		}

	
	 
}