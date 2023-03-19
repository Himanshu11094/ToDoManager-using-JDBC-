package jdbc;

import java.sql.*;
import java.util.*;
import model.User;
import model.Task;

public class UserDBConnection {
	
	Connection conn = DBConnection.getConnection();
	
	public List<User> getAllUsers() throws Exception
	{
		String sql = "Select * from User";
		
		List<User> users = new ArrayList<>();
		
		
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next())
			{
				User u1 = new User();
				
				u1.setUserName(rs.getString(1));
				u1.setEmail(rs.getString(2));				
				u1.setPassword(rs.getString(3));
				users.add(u1);
			}
		}
		
		catch (SQLException e)
		{
			throw new Exception(e.getMessage());
		}
		return users;
	}
	
	public boolean insertUser(User user) throws Exception
	{
		String sql = "INSERT into User values(?,?,?)";
		
		System.out.println(sql);
		
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getEmail());			
			statement.setString(3, user.getPassword());
			
			statement.execute();
			
			
		}
		
		catch(SQLException ex)
		{
			System.out.println("ERROR");
			throw new Exception(ex.getMessage());
		}
		
		return true;	
	}
	
	public User getUserByEmail(String email) throws Exception
	{
		User user = null;
		String sql = "SELECT userName, userEmail, userPassword from User where userEmail=?";
		PreparedStatement statement;
		
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				user = new User();
				user.setUserName(rs.getString(1));
				user.setEmail(rs.getString(2));
				user.setPassword(rs.getString(3));
			}
			
			else 
				throw new Exception("No customer with "+email+" found");
		}
		catch(SQLException e)
		{
			throw new Exception(e.getMessage());
		}
		
		return user;
	}
	
	
	public boolean login(String email, String password) throws Exception
	{
		User user = null;
		
		String sql = "SELECT userPassword from User where userEmail=?";
		
		PreparedStatement statement;
		
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				if(password.equals(rs.getString(1)))
					return true;
				else return false;
			}
			throw new Exception ("invalid credentials");
		}
		catch (SQLException e)
		{
			throw new Exception(e.getMessage());
		}
		
		 
	}
	
	public void createTaskTable(String email) throws Exception
	{
		try {
			Statement statement = conn.createStatement();
			
			String sql = "create table Task" + 
	                   "(taskId varchar not NULL, " +
	                   " taskTitle VARCHAR(255), " + 
	                   " taskText VARCHAR(255), " + 
	                   " assignedTo VARCHAR(255), " + 
	                   " Status VARCHAR(255), "+
	                   " PRIMARY KEY ( assignedTo))"; 
			
			statement.executeUpdate(sql);
			System.out.println("Table created in database");
		}
		
		catch (SQLException e)
		{
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateUser(User newUser, User u) throws Exception
	{
		String primaryEmail = u.getEmail();
		
		String sql = "UPDATE User SET userName= ?, userEmail = ?, userPassword= ? WHERE userEmail = ?";
		
		try {
						
			PreparedStatement  statement = conn.prepareStatement(sql);
			statement.setString(1, newUser.getUserName());
			statement.setString(2, newUser.getEmail());
			statement.setString(3, newUser.getPassword());
			statement.setString(4, primaryEmail);
			
			statement.execute();
			
		}
		
		catch(SQLException ex)
		{
			System.out.println("ERROR");
			throw new Exception(ex.getMessage());
		}
	}
	
	public void updateTask(Task newTask) throws Exception
	{
		String primaryId = newTask.getTaskId();
		
		String sql = "UPDATE Task SET taskTitle= ?, taskText= ?, status= ? WHERE taskId = ?";
		
		try {
						
			PreparedStatement  statement = conn.prepareStatement(sql);
			statement.setString(1, newTask.getTaskTitle());
			statement.setString(2, newTask.getTaskText());
			statement.setString(3, String.valueOf(newTask.isStatus()));
			statement.setString(4, primaryId);
			
			statement.execute();
			
		}
		
		catch(SQLException ex)
		{
			System.out.println("ERROR");
			throw new Exception(ex.getMessage());
		}
		

	}

	
	public void assignTaskToUser(Task newTask, User user) throws Exception
	{
		//INSERT INTO table_name (Column1, Column 2....)
		//VALUES (value1, value2, ...);
		
		String sql = "INSERT into Task VALUES(?,?,?,?,?)";
		
		//System.out.println(sql);
		
		try {
			UUID uuid = UUID.randomUUID();
			String taskId = String.valueOf(uuid);
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, taskId);
			statement.setString(2, newTask.getTaskTitle());
			statement.setString(3, newTask.getTaskText());
			statement.setString(4, user.getEmail());
			statement.setString(5, String.valueOf(newTask.isStatus()));
			
			statement.execute();
			
			 
		}
		
		catch(SQLException ex)
		{
			System.out.println("ERROR");
			throw new Exception(ex.getMessage());
		}
		
	}
	
	public List<Task> getAllTasks(User user) throws Exception
	{
	 	
		List<Task> tasks = new ArrayList<>();
		
		///////
		
		String sql = "SELECT taskId, taskTitle, taskText, assignedTo, status from Task where assignedTo=?";
		PreparedStatement statement;
		
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, user.getEmail());
			ResultSet rs = statement.executeQuery();
			
			 
				while(rs.next())
				{
					Task t1 = new Task();
					t1.setTaskId(rs.getString(1));
					t1.setTaskTitle(rs.getString(2));
					t1.setTaskText(rs.getString(3));
					t1.setAssignedTo(rs.getString(4));
					t1.setStatus(Boolean.valueOf(rs.getString(5)));
					
					tasks.add(t1);
				}
				
				if(tasks.size()==0 || tasks==null)
					throw new Exception("No tasks assigned yet...");
			  
		}
		
		 
		catch (SQLException e)
		{
			throw new Exception("No tasks Assigned yet...");
		}
		return tasks;
	}
	
	public void deleteTask(User user, int sNum) throws Exception
	{
		List<Task> taskList = getAllTasks(user);
		
		if(taskList.size()==0)
		throw new Exception("No Task is assigned yet");
			
		
		
		String taskId = taskList.get(sNum).getTaskId(); 
		
		//DELETE FROM exam WHERE name = 'Ellen Thornton';
		
		String sql = "DELETE FROM Task WHERE taskId =?";
		
		try {
						
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, taskId);
		
			statement.execute();			 
		}
		
		catch(SQLException ex)
		{
			System.out.println("ERROR");
			throw new Exception(ex.getMessage());
		} 
		
		
	}
	
	public Task searchTask(User user, String title) throws Exception
	{
		List<Task> taskList = getAllTasks(user);
		
		if(taskList.size()==0)
			throw new Exception("No Task is assigned yet");
		
		for(Task t: taskList)
		{
			if(t.getTaskTitle().trim().equalsIgnoreCase(title.trim()))
			{
				return t;
			}
			 
			
		}
		
		return null;
	}
	
	public void updateTaskTableEmail(String oldEmail, String newEmail) throws Exception
	{
		
		
		String sql = "UPDATE Task SET assignedTo=? WHERE assignedTo=?";
		
		//"UPDATE Task SET taskTitle= ?, taskText= ?, status= ? WHERE taskId = ?";
		
		try {
						
			PreparedStatement  statement = conn.prepareStatement(sql);
			statement.setString(1, newEmail);
			statement.setString(2, oldEmail);
						
			statement.execute();
			System.out.println("email updation in TASK table...");
			
		}
		
		catch(SQLException ex)
		{
			System.out.println("ERROR");
			throw new Exception(ex.getMessage());
		}
		

	}

}
