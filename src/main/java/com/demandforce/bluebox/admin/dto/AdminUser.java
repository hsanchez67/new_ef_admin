package com.demandforce.bluebox.admin.dto;


public class AdminUser {
	public enum Status
    {
        Inactive, Active, Activating, Delete
    }

    private Integer id;
    private String email;
    private String password; 
    private String username;
    private Status status = Status.Active;
    private String firstName;
    private String lastName;
    private String role;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "AdminUser [id=" + id + ", email=" + email + ", password="
				+ password + ", username=" + username + ", status=" + status
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", role=" + role + "]";
	}
    
    
}
