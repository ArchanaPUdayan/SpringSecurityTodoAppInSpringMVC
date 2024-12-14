package com.example.SpringSecurityTodoApp.services;

import java.util.List;
import java.util.Optional;
import java.security.Principal;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringSecurityTodoApp.Dto.UserDto;
import com.example.SpringSecurityTodoApp.entity.Role;
import com.example.SpringSecurityTodoApp.entity.Todo;
import com.example.SpringSecurityTodoApp.entity.User;
import com.example.SpringSecurityTodoApp.repository.RoleRepository;
import com.example.SpringSecurityTodoApp.repository.TodoRepository;
import com.example.SpringSecurityTodoApp.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private TodoRepository todoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    TodoService todoService;
    

    @Override
    public User save(UserDto userDto) {
        // Check if the user already exists
        User existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("A user with the given email already exists.");
        }

        // Check if roles exist, otherwise create them
        if (roleRepository.findByName("ADMIN") == null) {
            roleRepository.save(new Role("ADMIN"));
        }
        if (roleRepository.findByName("USER") == null) {
            roleRepository.save(new Role("USER"));
        }

        // Assign role based on condition
        Role role = userRepository.count() == 0 ? 
                    roleRepository.findByName("ADMIN") : 
                    roleRepository.findByName("USER");

        // Create a new user object
        User user = new User(
            userDto.getEmail(),
            passwordEncoder.encode(userDto.getPassword()),
            role,
            userDto.getFullname()
        );

        // Save the user to the database
        return userRepository.save(user);
    }

    @Override
    public User findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));
    }

	@Override
	public List<Todo> getAllTodos() {
		// TODO Auto-generated method stub
		 return todoRepository.findAll();
	}

	@Override
	public void updateCompletionStatus(int id, String status) {
	    Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found"));

	    // If the completed field is expected to be a String, convert the boolean value
	    todo.setCompleted(Boolean.toString("YES".equalsIgnoreCase(status)));
	    
	    todoRepository.save(todo);
	}
	

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	 public void assignTodoToUser(int todoId, long userId) {
	        Optional<Todo> todoOptional = todoRepository.findById(todoId);
	        Optional<User> userOptional = userRepository.findById(userId);

	        if (todoOptional.isPresent() && userOptional.isPresent()) {
	            Todo todo = todoOptional.get();
	            todo.setAssignedUser(userOptional.get());
	            todoRepository.save(todo);
	        } else {
	            throw new IllegalArgumentException("Invalid Todo ID or User ID");
	        }
	    }

	
//	public List<Todo> getTodosByUser(String fullname) {
//	    User user = userRepository.findByFullname(fullname);
//	    if (user == null) {
//	        throw new IllegalArgumentException("User not found with fullname: " + fullname);
//	    }
//	    return todoRepository.findByUser(user);
//	}


	
    public void updateCompletion(int id, String status) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            Todo todoToUpdate = todo.get();
            todoToUpdate.setCompleted(Boolean.toString(status.equalsIgnoreCase("YES")));

            todoRepository.save(todoToUpdate);
        }
    }
    
    public List<Todo> getTodosByUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        List<Todo> todos = todoRepository.findByUser(user);
        System.out.println("Todos for user: " + todos); // Log the todos retrieved
        return todos;
    }

	@Override
	public User validateUser(String email, String password) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email);

	    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
	        return user;
	    }
	    return null;
	}

	@Override
	public Long getUserIdByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserRoleByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

	@Override
	public User findByEmail(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(username);
	}




	
	}






  
