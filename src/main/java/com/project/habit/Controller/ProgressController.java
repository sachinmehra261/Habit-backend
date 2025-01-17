package com.project.habit.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.habit.Model.Progress;
import com.project.habit.Services.ProgressServices;

@RestController
@RequestMapping("/progress")
public class ProgressController {

	@Autowired
	ProgressServices progressServices;
	
	@PostMapping("/saveProgressByHabitId/{habitId}")
	public boolean saveProgress(@RequestBody Progress progress,@PathVariable int habitId) {
		return progressServices.saveProgress(progress,habitId);
	}
	
	@GetMapping("/getAllProgressByHabitId/{habitId}")
	public List<Progress> getAllProgressByHabitId(@PathVariable int habitId) {
		return progressServices.getAllProgressByHabitId(habitId);
	}
	
	@DeleteMapping("/deleteProgressByDate/{date}")
	public boolean deleteProgressByDate(@PathVariable LocalDate date) {
		return progressServices.deleteProgressByDate(date);
	}
}
