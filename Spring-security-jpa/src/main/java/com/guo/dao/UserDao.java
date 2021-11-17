package com.guo.dao;


import com.guo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
}