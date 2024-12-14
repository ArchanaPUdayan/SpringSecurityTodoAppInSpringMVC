package com.example.SpringSecurityTodoApp.controlller;

import java.security.Principal;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import com.example.SpringSecurityTodoApp.Dto.UserDto;
import com.example.SpringSecurityTodoApp.entity.Todo;
import com.example.SpringSecurityTodoApp.entity.User;
import com.example.SpringSecurityTodoApp.services.CustomUserDetail;
import com.example.SpringSecurityTodoApp.services.TodoService;
import com.example.SpringSecurityTodoApp.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.SpringSecurityTodoApp.Dto.TodoRequest;


@Controller
//@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
   TodoService todoService;

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String getView(@ModelAttribute("user") UserDto userDto, Model model) {
        return "front";
    }

    @GetMapping("/registration")
    public String getRegistration(@ModelAttribute("user") UserDto userDto, Model model) {
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Registered Successfully");
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    public UserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (userDetails != null) {
            // Ensure that the username is not null before accessing
            String username = userDetails.getUsername();
            if (username != null) {
                // Do something with the username, such as redirecting or displaying a message
                return "login"; // or any view you want to return
            } else {
                // Handle null username case
                return "loginFailure";
            }
        } else {
            // Handle the case where userDetails is null (user not found)
            return "loginFailure";
        }
    }
//    @GetMapping("/user-page")
//    public String getUserPage(HttpSession session, Model model) {
//        Long userId = (Long) session.getAttribute("userId");
//        String role = (String) session.getAttribute("role");
//        System.out.println("Session User ID: " + userId + ", Role: " + role);
//
//        if (userId == null || !"USER".equals(role)) {
//            System.out.println("Unauthorized access. Redirecting to login...");
//            return "redirect:/login";
//        }
//
//        List<Todo> assignedTodos = todoService.getTodosAssignedToUser(userId);
//        model.addAttribute("assignedTodos", assignedTodos);
//
//        return "user"; // Render user.html
//    }
    
    @GetMapping("/user-page")
    public String getUserPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null || userDetails.getUsername() == null) {
            return "redirect:/login"; // Redirect to login if the user is not authenticated
        }
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "redirect:/error"; // Redirect to error page with appropriate error message
        }
        List<Todo> assignedTodos = todoService.findByAssignedUserId(user.getId());
        model.addAttribute("assignedTodos", assignedTodos);
        return "user";
    }

    
  
    

    @GetMapping("/admin-page")
    public String adminPage(Model model, Principal principal) {
    	 List<User> users = userService.findAllUsers(); // Add this method to get all users
    	 model.addAttribute("users", users);
    	model.addAttribute("todoRequest", new TodoRequest());

        return "admin";  
    }
    


    // Mark todo as complete
    @GetMapping("/complete/{id}")
    public String markAsComplete(@PathVariable("id") int id) {
        userService.updateCompletionStatus(id, "YES");
        return "redirect:/user-page"; // Redirect back to the user page
    }

    // Mark todo as incomplete
    @GetMapping("/incomplete/{id}")
    public String markAsIncomplete(@PathVariable("id") int id) {
        userService.updateCompletionStatus(id, "NO");
        return "redirect:/user-page"; // Redirect back to the user page
    }
    
    
    @GetMapping("/assign-todo/{id}")
    public String assignTodoPage(@PathVariable("id") int todoId, Model model) {
        // Retrieve all users to populate the dropdown
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("todoId", todoId); // Pass the todoId to the form
        return "assign-todo";
    }

    @PostMapping("/assign")
    public String assignTodoToUser(@RequestParam("todoId") int todoId, @RequestParam("userId") long userId) {
        userService.assignTodoToUser(todoId, userId);
        return "redirect:/user-page";
    }

    
    


}

