package ui;

import java.util.List;
import java.util.Scanner;

 
import service.Service;
import database.Database;

import model.User;
import model.Task;
import jdbc.DBConnection;
import jdbc.UserDBConnection;

 

public class UserMenu {
	
	public static void adminMenu()
	{
		System.out.println("Select Option\n"+
	                       "1: See all Users\n"+  
				           "2: Fetch User by email\n"+
	                       "3: LogOff\n");		
	}
	
	public static void userMenu()
	{
		System.out.println("Select Option\n"+
	                        "1: Show Profile\n"+  				           
				            "2: Edit Profile\n"+
				            "3: TASK\n"+
	                        "4: LogOff\n");		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UserDBConnection  userDatabase = new UserDBConnection();
		Service userService = new Service(userDatabase);
		
		String email, password, userName;
		int choice;
		boolean flag=true;
		
		Scanner sc = new Scanner(System.in);
		
		do
		{
			// Home Page
			System.out.println("\nSelect Operation to perform: \n" + 
	                 "1: Login\n" + 
			         "2: Register User \n"+
	                 "3: EXIT");
			
			choice = sc.nextInt();
			
			switch(choice)
			{
			
			case 1: 
						
				System.out.println("enter the email \n");
				email = sc.next();
				System.out.println("enter the password\n");
				password = sc.next();
				
				if(email.equals("admin@gmail.com") && password.equals("admin"))
				{
					adminDashboard(sc, userService);
				}
				
				else
				{
					try {
						if(userService.validateCredentials(email, password))
							email = userDashboard(sc, userService, email, userDatabase);
						else
							System.out.println("Invalid Credentials...try again!");
					}
					catch (Exception e)
					{
						System.out.println(e.getMessage());
					}
				}
				
				break;
				
			case 2: System.out.println("Register the Customer");
					registration(sc, userService);
					break;
			
			case 3: System.out.println("Exiting the application");
			        flag = !flag;    
			        break;
			    
				
			default: System.out.println("enter the valid input");
			
			}
			
			
		}while(flag);
	

	} // Main ends here
	
	public static void adminDashboard(Scanner sc, Service userService)
	{
		boolean flag =true;
		
		do 
		{
			System.out.println("\n************ Admin Dashboard************ \n");
			adminMenu();
			int choice = sc.nextInt();
			
			switch(choice)
			{
			case 1:   
				try {
					List<User> listUser = userService.getAllUsers();
					
					for(User u: listUser)
					{
						System.out.println(u);
					}
					break;
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				break;
				
			case 2:
				System.out.println("enter the email of User");
				String email = sc.next();
				
				try {
					User u =  userService.getUserByEmail(email);
					System.out.println("User Name: " + u.getUserName()+
					                   "\n email: "+ u.getEmail());
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				break;
				
			case 3: System.out.println("Logging Off...");
			        flag = !flag;
			        break;
			        
				
			default: System.out.println("Wrong choice");
					
			
			}
			
		} while(flag);

	}
	
	
	public static String userDashboard(Scanner sc, Service userService, String email, UserDBConnection userDatabase)
	{
		boolean flag = true;
	
		do 
		{
			System.out.println("\n************ User Dashboard************ \n");
			userMenu();
			 
			int choice = sc.nextInt();
			
			switch(choice)
			{
			
			case 1: try {
				User u =  userService.getUserByEmail(email);
				System.out.println("User Name: " + u.getUserName()+				            
				                   "\n email: "+ u.getEmail());
			}
			catch(Exception e)
			{e.printStackTrace();}
			break;
			

				
				
			case 2:   email = editProfile(sc, userService, email, userDatabase);
					  break;
			
			case 3:   taskManagement(sc, userService, email, userDatabase);
					  break;			
					  
			case 4: System.out.println("Logging Off...");
			        flag = !flag;
			        break;
				
			default: System.out.println("Wrong choice");
			}
			
			
		}while(flag);
		
		return email;
		
	}
	
	public static String editProfile(Scanner sc, Service userService, String email, UserDBConnection userDatabase)
	{
		boolean flag=true;
		
		do {
			System.out.println("\nSelect Option\n"+
                    "1: Edit email\n"+  
		            "2: Edit Password\n"+
		            "3: Edit User Name\n"+
		            "4: EXIT\n");
			
			int choice = sc.nextInt();
			
			switch(choice)
			{
			case 1:
				
				try {
					User u = userService.getUserByEmail(email);
					
					System.out.println("Old Email: " + u.getEmail());
					System.out.println("Enter the new email to update: ");
					String newEmail = sc.next();
					User newUser = new User(u.getUserName(), newEmail, u.getPassword());
					userDatabase.updateUser(newUser, u);
					userDatabase.updateTaskTableEmail(email, newEmail);
					System.out.println("Email has been updated...");
					email=newEmail;
					break;
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case 2: 
				try {
					User u = userService.getUserByEmail(email);
					
					 
						System.out.println("Enter the new Password...");
						String newPassword = sc.next();
						sc.useDelimiter("\r?\n");
						User newUser = new User(u.getUserName(), u.getEmail(), newPassword);
						 
						userDatabase.updateUser(newUser,  u);
					 

						System.out.println("Password has been updated...");
						break;
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
				
			case 3:
				try {
					User u = userService.getUserByEmail(email);
					
					System.out.println("Old User Name: " + u.getUserName());
					System.out.println("Enter the new User name to update: ");
					String newUserName = sc.next();
					User newUser = new User(newUserName, u.getEmail(), u.getPassword());
					userDatabase.updateUser(newUser, u);
					System.out.println("Name has been updated...");
					break;
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
				
			case 4: System.out.println("Exiting...");
	                flag = !flag;
	                break;
				
			default: System.out.println("Wrong choice");
				
			}
			
		}while(flag);
		
		return email;
	}
	
	public static void registration(Scanner sc, Service userService)
	{
		System.out.println("Enter the email: ");
		String email = sc.next();
		System.out.println("Enter the Password: ");
		String pass = sc.next();
		System.out.println("Enter the User name: ");
		String name = sc.next();

		
		User newUser = new User(name, email, pass);
		
		try {
			if(userService.registerUser(newUser))
				System.out.println("registration completed...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void taskManagement(Scanner sc, Service userService, String email, UserDBConnection userDatabase)
	{
		boolean flag=true;
		int choice;
		
		
		do {
			System.out.println("\nSelect Option to perform: "+
		                        "\n 1: Add Task \n"+
					            "\n 2: Update Task \n"+
		                        "\n 3: Delete Task \n"+
					            "\n 4: Search Task \n"+
		                        "\n 5: Show All Tasks \n"+
					            "\n 6: Status of Task \n"+
		                        "\n 7: EXIT \n");
			
			sc.useDelimiter("\r?\n");
			choice = sc.nextInt();
			sc.useDelimiter("\r?\n");
			
			switch(choice)
			{
			
			//Task(String taskTitle, String taskText, List<String> assignedTo, boolean status)
			
			case 1: try {
			System.out.println("Enter the title of the task...");
			String title = sc.next();
			sc.useDelimiter("\r?\n");
			System.out.println("Enter the detailed text of the task...");
			 
			String taskText = sc.next();
			sc.useDelimiter("\r?\n");
			boolean status = false;
			Task newTask = new Task(title, taskText, email, status);
			
			User newUser = userService.getUserByEmail(email);
			
			userDatabase.assignTaskToUser(newTask, newUser);
			System.out.println("Task Added...");
			
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
			case 2:  try {
				User u = userService.getUserByEmail(email);
				
                System.out.println("Enter the task title you are looking for...");
				
				String title  = sc.next();
				sc.useDelimiter("\r?\n");
				
				Task searchedTask = userDatabase.searchTask(u, title);
				
				if( searchedTask==null )
					throw new Exception("There is no task with this title...");
				
				boolean check=true;
				do {
					System.out.println("\nSelect Option to Edit: "+ 
				                        "\n 1: Edit Title"+
				                        "\n 2: Edit Task Text"+
				                        "\n 3: Edit Task Status"+
				                        "\n 4: EXIT");
					
					int input = sc.nextInt();
					
					switch(input)
					{
					case 1: try {
						System.out.println("Enter the new Title to update...");
						String newTitle = sc.next();
						
						Task newTask = new Task(newTitle, searchedTask.getTaskText(), searchedTask.getAssignedTo(), searchedTask.isStatus());
						newTask.setTaskId(searchedTask.getTaskId());
						userDatabase.updateTask(newTask);							
						System.out.println("Title has been updated...");
					}

							catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println(e.getMessage());
							}
							break;
					
					
					case 2: 
						try {
							System.out.println("Enter the new Task Text to update...");
							
							String newTaskText = sc.next();
							sc.useDelimiter("\r?\n");
							
							Task newTask = new Task(searchedTask.getTaskTitle(), newTaskText, searchedTask.getAssignedTo(), searchedTask.isStatus());;
							newTask.setTaskId(searchedTask.getTaskId());
							userDatabase.updateTask(newTask);
							
							
					        System.out.println("text has been updated...");

						}

					        catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println(e.getMessage());
							}
							break;
					        
					case 3: 
						try {
							System.out.println("Enter the new Status to update...");
							String newStatus = sc.next();
							
							Task newTask = new Task(searchedTask.getTaskTitle(), searchedTask.getTaskText(), searchedTask.getAssignedTo(), Boolean.valueOf(newStatus));
							newTask.setTaskId(searchedTask.getTaskId());
							userDatabase.updateTask(newTask);							
							
					        System.out.println("Status has been updated...");
						}


			        catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
					break;
			        
						
					case 4: System.out.println("Exiting...");
					check = !check;
							break;
					
					default: System.out.println("Enter the valid input...");
					}
					
				}while(check);				
				
			}
			
			catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			break;
			
			case 3:  try {
				
				User u = userService.getUserByEmail(email);
				
			    int sNum=0; 
				for(Task t: userDatabase.getAllTasks(u))
				{
					System.out.println(
							"\n Task "+ sNum +": "+ t.getTaskTitle() );
					sNum++;    
				}
				System.out.println("Select the task you want to delete: " );
				
				userDatabase.deleteTask(u, sc.nextInt());
				
				System.out.println("Entered Task has been deleted...");
				
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			break;
				
			case 4:  try {
				User u = userService.getUserByEmail(email);
				
				System.out.println("Enter the task title you are looking for...");
				
				//String title  = sc.next();
				
				Task searchedTask = userDatabase.searchTask(u, sc.next());
				
				if(searchedTask == null)
					throw new Exception("Could not find the entered record...");
				
				System.out.println("\n TaskID: "+ searchedTask.getTaskId() +
				           "\n Task Title: "+ searchedTask.getTaskTitle() +
				           "\n Task Text: "+ searchedTask.getTaskText() + 
				           "\n Task Status: "+ searchedTask.isStatus());
				
			}
			
			catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			break;
			
			
			case 5:  try {
				User u  = userService.getUserByEmail(email);
				List<Task> taskList=null;
				
				 
				taskList = userDatabase.getAllTasks(u);
				 
				
				for(Task t: taskList)
				{
					System.out.println("\n TaskID: "+ t.getTaskId() +
							           "\n Task Title: "+ t.getTaskTitle() +
							           "\n Task Text: "+ t.getTaskText() + 
							           "\n Task Status: "+ t.isStatus());
				}
				
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("No Task Assigned yet...");
			}
			break;
			
			case 6:  try {
				User u  = userService.getUserByEmail(email);
				
				List<Task> taskList=null;
				
				 
				taskList = userDatabase.getAllTasks(u);
				 
				 
				
				System.out.println("\nTask Title\tTask status");
				for(Task t: taskList)
				{
					
					System.out.println(t.getTaskTitle()+"\t\t" + t.isStatus() );
				}
			}
			
			catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("No Task Assigned yet...");
			}
			break;
			
			case 7: System.out.println("Exiting...");
            flag = !flag;
            break;
		
			default: System.out.println("Wrong choice");
			}
			
		}while(flag);
	}

}
