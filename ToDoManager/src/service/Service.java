package service;

import database.Database;
import model.Task;
import java.util.*;
import model.User;
import jdbc.DBConnection;
import jdbc.UserDBConnection;
public class Service {
	
	private UserDBConnection userDatabase;
	
	
	public Service(UserDBConnection database)
	{
		this.userDatabase = database;
	}
	
	public List<User> getAllUsers() throws Exception
	{
		if(this.userDatabase.getAllUsers().size()==0)
			throw new Exception("No User is registered yet");
		
		return this.userDatabase.getAllUsers();
	}
	
	public boolean registerUser(User user) throws Exception
	{
		if(user.getEmail() == null || user.getEmail().isEmpty())
			throw new Exception("Email cannot be empty or null");
		
		try {
			this.userDatabase.insertUser(user);
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
		}
		
		return true;
	}
	
	public User getUserByEmail(String email) throws Exception
	{
		if(email==null || email.isEmpty())
			throw new Exception("Email cannot be empty or null");
		
		User newUser = this.userDatabase.getUserByEmail(email);
		
		if(newUser == null)
			throw new Exception("User with email "+ email + " does not exist");
		
		return newUser;
	}
	
	
	public boolean validateCredentials(String email, String password) throws Exception
	{
		if(email==null || email.isEmpty())
			throw new Exception("email cannot be empty or null");
		
		return this.userDatabase.login(email, password);
	}
	
	
	/*
	public boolean updateUser(User newUser) throws Exception
	{
		try {
			this.userDatabase.updateUser(newUser);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}
	public boolean deleteUser(String email) throws Exception
	{
		try {
			this.userDatabase.deleteUser(email);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}
	
	public boolean changePassword(String email, String newPassword) throws Exception
	{
		try {
			this.userDatabase.updatePassword(newPassword, email);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}
	
	*/
	// Task related Methods
	
	

}
