package com.example.SpringSecurityTodoApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SpringSecurityTodoApp.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User,Long>{
	
	User findByEmail(String email);
	
	Optional<User> findByFullname(String fullname);
	
	@Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = :email")
	User findByEmail1(@Param("email") String email);


}
