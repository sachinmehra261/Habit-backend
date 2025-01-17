package com.project.habit.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.habit.Model.Habit;
import com.project.habit.Model.Progress;
import com.project.habit.Model.User;
import com.project.habit.Repo.HabitRepo;
import com.project.habit.Repo.ProgressRepo;
import com.project.habit.Repo.UserRepo;
import com.project.habit.Response.HabitResponse;
import com.project.habit.Response.UserResponse;

@Service
public class HabitServices {

	@Autowired
	HabitRepo habitRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	ProgressRepo progressRepo;

	public ResponseEntity<HabitResponse<Habit>> addHabit(Habit habit, int userId) {

		HabitResponse<Habit> response = new HabitResponse<Habit>();

		try {
			Optional<User> userOptional = userRepo.findById(userId);
			if (userOptional.isPresent()) {
				User user = userOptional.get();
				habit.setUser(user);
				habitRepo.save(habit);
				response.setData(habit);
				response.setMessage("Habit added successfully");
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.setMessage("User not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	public ResponseEntity<HabitResponse<Habit>> deleteHabit(int id) {

		HabitResponse<Habit> response = new HabitResponse<Habit>();

		try {
			Optional<Habit> habitOptional = habitRepo.findById(id);
			if (habitOptional.isPresent()) {

				Habit habit = habitOptional.get();

				List<Progress> progresses = progressRepo.findAllByHabitId(id);

				for (Progress progress : progresses) {
					progress.setHabit(null);
					progressRepo.delete(progress);
				}
				habitRepo.delete(habit);
				response.setMessage("Habit Deleted Successfully");
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.setMessage("Habit not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	public ResponseEntity<HabitResponse<List<Habit>>> getAllHabitsByUserId(int userId) {
		HabitResponse<List<Habit>> response = new HabitResponse<>();
		try {
			List<Habit> habits = habitRepo.findAllByUserId(userId);
			if (!habits.isEmpty()) {
				response.setData(habits);
				response.setMessage("Habits Fetched Successfully");
				return ResponseEntity.ok(response); // Use ResponseEntity.ok to return a proper HTTP response
			} else {
				response.setMessage("No Habits Yet");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	public ResponseEntity<HabitResponse<Habit>> updateHabitById(Habit userHabit, int id) {
		HabitResponse<Habit> response = new HabitResponse<>();
		try {
			Optional<Habit> habitOptional = habitRepo.findById(id);
			if (habitOptional.isPresent()) {
				Habit habit = habitOptional.get();
				habit.setName(userHabit.getName());
				habit.setFrequency(userHabit.getFrequency());
				habitRepo.save(habit);
				response.setData(habit);
				response.setMessage("Habit Updated Successfully");
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.setMessage("Habit not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
