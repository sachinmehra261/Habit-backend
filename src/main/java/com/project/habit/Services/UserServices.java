package com.project.habit.Services;

import com.project.habit.Model.Habit;
import com.project.habit.Repo.HabitRepo;
import com.project.habit.Repo.UserRepo;
import com.project.habit.Response.UserResponse;
import com.project.habit.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

	@Autowired
	UserRepo userRepo;

	@Autowired
	HabitRepo habitRepo;

	public ResponseEntity<UserResponse<User>> registerUser(User user) {
		UserResponse<User> response = new UserResponse<User>();
		try {
			String userPassword = user.getPassword();
			String userEmail = user.getEmail();
			String userName = user.getUsername();

			Optional<User> savedUser = userRepo.findByEmail(userEmail);

			if (savedUser.isEmpty()) {
				if (userEmail.isEmpty() || userPassword.isEmpty() || userName.isEmpty()) {
					response.setMessage("Please fill the data correctly");
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
				} else if (!userEmail.isEmpty() && !userPassword.isEmpty()) {
					User userEntry = new User(userName, userEmail, userPassword);
					userRepo.save(userEntry);
					response.setMessage("You were registered successfully");
					response.setData(userEntry);
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
				} else {
					response.setMessage("Something went wrong");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
			} else {
				response.setMessage("Email already exists");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}
		} catch (Exception e) {
			System.out.println(e);
			response.setMessage("Internal server error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	public ResponseEntity<UserResponse<User>> loginUser(User user) {
		UserResponse<User> response = new UserResponse<User>();
		try {
			Optional<User> userOptional = userRepo.findByEmail(user.getEmail());

			if (userOptional.isPresent()) {
				if (!user.getPassword().isEmpty()) {

					User userEntry = userOptional.get(); // Now safe to use get() after checking isPresent()

					if (userEntry.getPassword().equals(user.getPassword())) {
						response.setMessage("Login Successful");
						response.setData(userEntry);
						return ResponseEntity.status(HttpStatus.ACCEPTED).body(response); // HTTP 202 if login is
																							// successful
					} else {
						response.setMessage("password was wrong");
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // HTTP 401 Unauthorized
					}
				} else {
					response.setMessage("Please enter a valid password");
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
				}
			} else {
				response.setMessage("Please enter a valid email");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			System.out.println(e);
			response.setMessage("Internal server error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	public ResponseEntity<UserResponse<User>> deleteUserById(int id) {

		UserResponse<User> response = new UserResponse<>();
		try {
			Optional<User> userOptional = userRepo.findById(id);
			if (userOptional.isPresent()) {
				User userEntry = userOptional.get();
				List<Habit> habits = habitRepo.findAllByUserId(id);
				if(!habits.isEmpty()){
					for(Habit habit : habits){
						habit.setUser(null);
					}
				}
				userRepo.delete(userEntry);
				response.setData(userEntry);
				response.setMessage("User deleted successfully");
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.setMessage("User not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		}catch (Exception e){
			response.setMessage("Internal Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	public boolean updateUserById(User user) {
		Optional<User> userOptional = userRepo.findByEmail(user.getEmail());
		if (userOptional.isPresent()) {
			User userEntry = userOptional.get();
			userEntry.setEmail(user.getEmail());
			userEntry.setPassword(user.getPassword());
			userEntry.setUsername(user.getUsername());
			userRepo.save(userEntry);
			return true;

		}
		return false;
	}
}
