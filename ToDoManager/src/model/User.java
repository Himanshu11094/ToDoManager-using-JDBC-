package model;

import java.util.*;

public class User {
	
	private String userName;
	private String email;
	private String password;
	private List<Task> taskList;
	
	public User()
	{}
	public User(String userName, String email, String password) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.taskList = new ArrayList<>();
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", email=" + email + ", password=" + password + "]";
	}
		
	public void setTask(Task newTask)
	{
		taskList.add(new Task(newTask.getTaskTitle(), newTask.getTaskText(), newTask.getAssignedTo(), false));
	}
	
	public List<Task> getTaskList()
	{
		return taskList;
	}

}
