package model;

import java.util.*;

public class Task {
	
	private String taskId;
	private String taskTitle;
	private String taskText;
	private String assignedTo; // (String entered by the user – store email of the user)
	boolean status;

	 public Task()
	 {}

	public Task(String taskTitle, String taskText, String assignedTo, boolean status) {
		 
		
		this.taskTitle = taskTitle;
		this.taskText = taskText;
		this.assignedTo = assignedTo;
		this.status = status;
	}

	 

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskText() {
		return taskText;
	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskTitle=" + taskTitle + ", taskText=" + taskText + ", assignedTo="
				+ assignedTo + ", status=" + status + "]";
	}
	
	

}
