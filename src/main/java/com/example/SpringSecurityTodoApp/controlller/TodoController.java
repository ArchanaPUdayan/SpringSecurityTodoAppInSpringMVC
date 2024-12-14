package com.example.SpringSecurityTodoApp.controlller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.SpringSecurityTodoApp.Dto.TodoRequest;
import com.example.SpringSecurityTodoApp.entity.Todo;
import com.example.SpringSecurityTodoApp.entity.User;

import com.example.SpringSecurityTodoApp.services.TodoService;
import com.example.SpringSecurityTodoApp.services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
//@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService service;

    @PostMapping("/reg")
    public ModelAndView saveTodo(@ModelAttribute("todoRequest") TodoRequest todoRequest) {
        long userId = 1L; // Replace with dynamic user ID, possibly fetched from session or request
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        Todo todo = new Todo();
        todo.setTodoId(todoRequest.getTodoId());
        todo.setTodoTitle(todoRequest.getTodoTitle());
        todo.setTodoDescription(todoRequest.getTodoDescription());
        todo.setCompleted(todoRequest.getCompleted());
        todo.setUser(user);

        service.save(todo, userId);
        List<Todo> allTodos = service.getAllTodos();
        ModelAndView mv = new ModelAndView("display");
        mv.addObject("todos", allTodos);

        return mv;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Optional<Todo> todoOptional = service.getTodoById(id);
        if (todoOptional.isPresent()) {
            Todo todo = todoOptional.get();
            model.addAttribute("todo", todo);
            return "edit";
        } else {
            return "redirect:/";
        }
    }

    
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") int id) {
        System.out.println("Deleting Todo with ID: " + id); // Debug log
        service.deleteTodoById(id);
        return "redirect:/display";
    }


    @PostMapping("/update")
    public String updateTodo(@ModelAttribute("todoRequest") TodoRequest todoRequest, RedirectAttributes redirectAttributes) {
        try {
            long userId = 1L; // Replace with actual logic for logged-in user
            User user = userService.findUserById(userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }

            Todo updatedTodo = new Todo();
            updatedTodo.setTodoId(todoRequest.getTodoId());
            updatedTodo.setTodoTitle(todoRequest.getTodoTitle());
            updatedTodo.setTodoDescription(todoRequest.getTodoDescription());
            updatedTodo.setCompleted(todoRequest.getCompleted());
            updatedTodo.setUser(user);

            service.update(updatedTodo, userId);

            redirectAttributes.addFlashAttribute("message", "Todo updated successfully!");
            return "redirect:/display";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred while updating the todo.");
            return "redirect:/error";
        }
    }

    @GetMapping("/display")
    public String displayTodos(Model model) {
        List<Todo> todos = service.findAll();
        List<User> users = service.getAllUsers();
        model.addAttribute("todos", todos);
        model.addAttribute("users", users);
        return "display";
    }

    @GetMapping("/completed-todos")
    public String getCompletedTodos(Model model) {
        List<Todo> completedTodos = service.getCompletedTodos();
        model.addAttribute("todos", completedTodos);
        return "completed-todos";
    }

    @GetMapping("/incompleted-todos")
    public String getIncompletedTodos(Model model) {
        List<Todo> incompletedTodos = service.getIncompletedTodos();
        model.addAttribute("todos", incompletedTodos);
        return "incompleted-todos";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return "redirect:/login?logout"; // Redirect to login page with a query parameter
    }
    
    @GetMapping("/user/todos")
    public String getUserTodos(Model model, HttpSession session) {
        // Get the logged-in user's details
        String email = (String) session.getAttribute("loggedInUserEmail"); // Assuming you store the email in session
        if (email == null) {
            return "redirect:/login"; // Redirect to login if session is invalid
        }

        User user = userService.findUserByEmail(email); // Retrieve user details
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        List<Todo> assignedTodos = service.getAssignedTodos(user.getId()); // Fetch assigned tasks
        model.addAttribute("todos", assignedTodos);

        return "user"; // View to display user-specific tasks
    }
    
    @PostMapping("/todos/change-status")
    public String changeStatus(@RequestParam("todoId") int todoId, @RequestParam("status") String status) {
        service.changeStatus(todoId, status);
        return "redirect:/user-page"; // Redirect back to the user's tasks page
    }
    
    
    @PostMapping("/todos/mark-completed")
    public String markCompleted(@RequestParam("todoId") int todoId) {
        service.markCompleted(todoId);
        return "redirect:/user-page";
    }

    @PostMapping("/todos/mark-incomplete")
    public String markIncomplete(@RequestParam("todoId") int todoId) {
        service.markIncomplete(todoId);
        return "redirect:/user-page";
    }

    
 
    

    

}

