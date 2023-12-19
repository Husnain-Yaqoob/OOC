/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package user;
import java.util.List;
import java.util.Scanner;

import models.TaxUsers;
import models.User;
import models.UserResponse;
import models.UserRole;

/**
 *
 * @author hasan
 */
public interface UserService {
    public UserResponse login(String username, String password);
	public UserResponse register(String username, String password, String name, String surname, String role, String age);
	
	public void adminMenu(Scanner scanner, User loggedInUser);
	public void regularUserMenu(Scanner scanner, User loggedInUser);
	
	public void seeAllUsers(User loggedInUser);
	public void removeUser(String usernameToRemove, User loggedInUser);
	
	public void modifyProfile(Scanner scanner, String userName);
	public void updateProfile(String newPassword, String newName, String newSurname, String newAge, String userId);
	
	public void saveUserOperation(UserOperation operation);
	public List<UserOperation> getUserOperations();
	
	public UserRole getUserRoleFromString(String roleString);
	
	public void calculateTax(Scanner scanner, User loggedInUser);
	public double calculateTaxOwed(double grossIncome, double taxCredits, String taxTypeName);
	public void saveToDatabase(TaxUsers taxUsers, String userId);
}
