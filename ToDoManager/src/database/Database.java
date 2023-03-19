package database;

import java.util.ArrayList;
import java.util.List;

import model.Task;
import model.User;

public class Database {
	
	private List<User> users = new ArrayList<>();
	
	public Database()
	{
		users.add(new User("abc", "abc@gmail.com", "abc"));
		users.add(new User("efg", "gef@gmail.com", "efg"));
	}
	
	public List<User> getAllUsers()
	{
		return this.users;
	}
	
	public boolean insertUser(User user) throws Exception
	{
		for(User u: this.users)
		{
			if(u.getEmail().equals(user.getEmail()))
				throw new Exception("User can't be registered as email already exists");
		}
		this.users.add(user);
		return true;
	}
	
	public User getUserByEmail(String email)
	{
		User newUser = null;
		
		for(User u: this.users)
		{
			if(u.getEmail().equals(email))
			{
				newUser = u;
				break;
			}
		}
		
		return newUser;
	}
	
	public boolean login(String email, String password)
	{
		for(User u: this.users)
		{
			if(u.getEmail().equals(email))
			{
				if(u.getPassword().equals(password))
					return true;
			}
		}
		
		return false;
	}
	
	public void updateUser(User newUser, User u) 
	{
		u.setEmail(newUser.getEmail());
		u.setPassword(newUser.getPassword());
		u.setUserName(newUser.getUserName());
	}
	
	
	public void assignTaskToUser(Task newTask, User user)
	{
		user.setTask(newTask);
	}
	
	public List<Task> getAllTasks(User user) throws Exception
	{
		if(user.getTaskList().size()==0 || user.getTaskList() == null)
			throw new Exception("No Task is assigned yet");
		
		return user.getTaskList();
	}
	
	
	public void deleteTask(User user, int sNum) throws Exception
	{
		if(user.getTaskList().size()==0)
			throw new Exception("No Task is assigned yet");
		
		user.getTaskList().remove(sNum); 
	}
	
	public Task searchTask(User user, String title) throws Exception
	{
		if(user.getTaskList().size()==0)
			throw new Exception("No Task is assigned yet");
		
		for(Task t: user.getTaskList())
		{
			if(t.getTaskTitle().equals(title))
				return t;
		}
		
		return null;
		
	}
	

}
