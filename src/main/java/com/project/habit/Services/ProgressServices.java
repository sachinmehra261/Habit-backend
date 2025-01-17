package com.project.habit.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.habit.Model.Habit;
import com.project.habit.Model.Progress;
import com.project.habit.Repo.HabitRepo;
import com.project.habit.Repo.ProgressRepo;

@Service
public class ProgressServices {

	@Autowired
	ProgressRepo progressRepo;

	@Autowired
	HabitRepo habitRepo;

	public boolean saveProgress(Progress progress, int habitId) {
		System.out.println("PROGRESSSSS");
		try {
			Habit userHabit = habitRepo.findById(habitId).get();
			if (userHabit != null) {
				progress.setHabit(userHabit);
				progressRepo.save(progress);
				return true;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public List<Progress> getAllProgressByHabitId(int habitId) {
		try {
			List<Progress> progressList = progressRepo.findAllByHabitId(habitId);
			if (!progressList.isEmpty()) {
				return progressList;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public boolean deleteProgressByDate(LocalDate date) {
		try {
			System.out.println(date);
			Progress progress =  progressRepo.findByDate(date);
			if(progress != null) {
				progressRepo.delete(progress);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
}
