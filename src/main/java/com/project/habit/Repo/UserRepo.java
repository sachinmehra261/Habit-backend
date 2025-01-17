package com.project.habit.Repo;

import com.project.habit.Model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    public Optional<User> findByEmail(String email);
}
