package com.project.habit.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.habit.Model.Habit;

@Repository
public interface HabitRepo extends JpaRepository<Habit, Integer>{

	public List<Habit> findAllByUserId(int userId);
}
