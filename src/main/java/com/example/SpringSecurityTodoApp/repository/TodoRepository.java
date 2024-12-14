package com.example.SpringSecurityTodoApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SpringSecurityTodoApp.entity.Todo;
import com.example.SpringSecurityTodoApp.entity.User;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer>{
	
	List<Todo> findByCompleted(String completed);

	List<Todo> findByUserEmail(String name);
	
	List<Todo> findByUser(User user);
	List<Todo> findByUserId(Long userId);
//	List<Todo> findByAssignedUserId(Long assignedUserId);
	
	List<Todo> findByAssignedUserId(long userId);

	
	
	@Query("SELECT t FROM Todo t WHERE t.assignedUser.id = :Id")
	List<Todo> findByAssignedUserIdJPQL(@Param("Id") Long Id);
	List<Todo> findByAssignedUserId(Long assignedUser);
	

	}
