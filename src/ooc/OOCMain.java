/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ooc;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import database.DatabaseManager;
import user.Helper;
import user.UserService;
import user.UserServiceImpl;
import models.*;
import user.*;
/**
 *
 * @author hasan
 */
public class OOCMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      try (Connection connection = DatabaseManager.establishConnection()) {

    	    Scanner scanner = new Scanner(System.in);

    	    int choice;
    	    do {
    	        System.out.println("Choose an option:");
    	        System.out.println("1. Login");
    	        System.out.println("2. Register");
    	        System.out.println("3. Exit");

    	        choice = scanner.nextInt();
    	        scanner.nextLine(); // Consume the newline character

    	        UserService userService = new UserServiceImpl();

    	        switch (choice) {
    	            case 1:
    	                System.out.print("Enter username: ");
    	                String username = scanner.nextLine();

    	                System.out.print("Enter password: ");
    	                String password = scanner.nextLine();

    	                System.out.println("Username: " + username);
    	                System.out.println("Password: " + password);
    	                UserResponse userResponse = userService.login(username, password);
    	                if (userResponse.isStatus()) {
    	                	UserOperation operation = new UserOperation();
    	                	operation.setUser(userResponse.getUser());
    	                	operation.setOperationType("LoggedIn");
    	                	userService.saveUserOperation(operation);
    	                	
    	                    System.out.println("Login successful as " + userResponse.getUser().getRole()+ " User\n");

    	                    // Check user role and perform actions accordingly
    	                    if (userResponse.getUser().getRole().equals("Admin")) {
    	                    	userService.adminMenu(scanner, userResponse.getUser());
    	                    } else {
    	                    	userService.regularUserMenu(scanner, userResponse.getUser());
    	                    }
    	                } else {
    	                	System.out.println("Save operation skipped.");
    	                    System.out.println(userResponse.getMessage());
    	                }
    	                break;

    	            case 2:
    	                // Register
    	                System.out.println("Enter username:");
    	                String username1 = scanner.nextLine();

    	                String password1;
    	                while (true) {
    	                    System.out.println("Enter password:");
    	                    password1 = scanner.nextLine();

    	                    if (Helper.isValidPassword(password1)) {
    	                        System.out.println("Password is valid.");
    	                        // Move forward with the next input or action
    	                        break; // Exit the loop as a valid password is entered
    	                    } else {
    	                        System.out.println("Password is not valid. It must contain at least 8 characters with 1 special character.");
    	                        // Continue the loop to ask for the password again
    	                    }
    	                }

    	                System.out.println("Enter name:");
    	                String name = scanner.nextLine();

    	                System.out.println("Enter surname:");
    	                String surname = scanner.nextLine();

    	                String role;
    	                while (true) {
    	                    System.out.println("Enter role: 'Admin' OR 'Rgular'");
    	                    String roleInput = scanner.nextLine();

    	                    if (Helper.isValidRole(roleInput)) {
    	                        role = roleInput.equals("Admin") ? "1" : "2";
    	                        System.out.println("Role is valid: " + role);
    	                        // Move forward with the next input or action
    	                        break; // Exit the loop as a valid role is entered
    	                    } else {
    	                        System.out.println("Invalid role. Please enter 'ADMIN' OR 'REGULAR'.");
    	                        // Continue the loop to ask for the role again
    	                    }
    	                }

    	                System.out.println("Enter age:");
    	                String age = scanner.nextLine();
    	                UserResponse response = userService.register(username1, password1, name, surname, role, age);
    	                System.out.println(response.getMessage());
    	                break;

    	            case 3:
    	                System.out.println("Application Closed.");
    	                break;

    	            default:
    	                System.out.println("Invalid choice");
    	        }
    	    } while (choice != 3);

    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    }
    }
    

