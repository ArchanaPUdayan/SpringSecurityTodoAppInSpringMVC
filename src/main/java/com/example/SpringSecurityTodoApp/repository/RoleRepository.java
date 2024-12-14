package com.example.SpringSecurityTodoApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringSecurityTodoApp.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

	Role findByName(String string);

}
