package com.project.habit.Controller;

import java.util.List;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.habit.Model.Habit;
import com.project.habit.Response.HabitResponse;
import com.project.habit.Services.HabitServices;

@RestController
@RequestMapping("/habits")
public class HabitController {
	
	@Autowired
	HabitServices habitServices;
	
	@PostMapping("/addHabit/{userId}")
	public ResponseEntity<HabitResponse<Habit>> addHabit(@RequestBody Habit habit,@PathVariable int userId) {
		return habitServices.addHabit(habit,userId);
		
	}
	
	@GetMapping("/getAllHabits/{userId}")
	public  ResponseEntity<HabitResponse<List<Habit>>> getAllHabitsByUserId(@PathVariable int userId) {
		return habitServices.getAllHabitsByUserId(userId);
		
	}

	@GetMapping("/getHabitById/{habitId}")
	public  ResponseEntity<HabitResponse<Habit>> getHabitById(@PathVariable int habitId) {
		System.out.println(habitId);
		return habitServices.getHabitById(habitId);

	}
	
	@PutMapping("/updateHabit/{id}")
	public ResponseEntity<HabitResponse<Habit>> updateHabitById(@RequestBody Habit habit,@PathVariable int id) {
		return habitServices.updateHabitById(habit,id);
	}

	@PutMapping("/markAsCompleted/{habitId}")
	public ResponseEntity<HabitResponse<Habit>> markAsCompleted(@PathVariable int habitId){
		return habitServices.markAsCompleted(habitId);
	}
	
	@DeleteMapping("/deleteHabit/{id}")
	public ResponseEntity<HabitResponse<Habit>> addHabit(@PathVariable int id) {
		return habitServices.deleteHabit(id);
		
	}

}
