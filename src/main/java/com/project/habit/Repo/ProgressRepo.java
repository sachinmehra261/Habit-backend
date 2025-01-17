package com.project.habit.Repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.habit.Model.Progress;

public interface ProgressRepo extends JpaRepository<Progress, Integer>{

	List<Progress> findAllByHabitId(int habitId);

	Progress findByDate(LocalDate date);


}
