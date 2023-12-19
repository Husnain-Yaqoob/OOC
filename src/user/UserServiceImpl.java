/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import database.DatabaseManager;
import models.TaxUsers;
import models.User;
import models.UserResponse;
import models.UserRole;
import taxcalculator.IncomeTaxCalculator;
import taxcalculator.PRSICalculator;
import taxcalculator.TaxCalculator;
import taxcalculator.USCcalculator;
/**
 *
 * @author moizb
 */
public class UserServiceImpl implements UserService{
    Connection connection = DatabaseManager.establishConnection(); 
	@Override
	public UserResponse login(String username, String password) {
	    UserResponse response = new UserResponse();
	    User loggedInUser = new User();
	    String query = "SELECT Users.*, Roles.RoleName FROM Users " +
	                   "INNER JOIN Roles ON Users.RoleID = Roles.RoleID " +
	                   "WHERE username = ? AND password = ?";
	    
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, username);
	        preparedStatement.setString(2, password);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                // User exists with the provided credentials

	                loggedInUser.setUsername(resultSet.getString("username"));
	                loggedInUser.setPassword(resultSet.getString("password"));
	                loggedInUser.setName(resultSet.getString("name"));
	                loggedInUser.setSurname(resultSet.getString("surname"));
	                loggedInUser.setRole(resultSet.getString("RoleName")); // Assuming UserRole is an enum
	                loggedInUser.setAge(resultSet.getString("age"));
	                loggedInUser.setUserId(resultSet.getString("UserID"));
	                response.setMessage("Login Successful.");
	                response.setStatus(true);
	                response.setUser(loggedInUser);
	                return response;
	            } else {
	                response.setMessage("Invalid credentials. Login failed.");
	                response.setStatus(false);
	                response.setUser(loggedInUser);
	                return response;
	            }
	        }
	    }catch (SQLException e) {
			e.printStackTrace();
			// Handle the exception according to your requirements
			response.setMessage(e.getMessage());
			response.setStatus(false);
			response.setUser(loggedInUser);
			return response;
		}
	}

	@Override
	public UserResponse register(String username, String password, String name, String surname, String role, String age) {
	    UserResponse response = new UserResponse();
	    String query = "INSERT INTO Users (username, password, name, surname, RoleID, age) VALUES (?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, username);
	        preparedStatement.setString(2, password);
	        preparedStatement.setString(3, name);
	        preparedStatement.setString(4, surname);

	        preparedStatement.setInt(5, Integer.valueOf(role));

	        preparedStatement.setString(6, age);

	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            response.setMessage("User registered successfully!");
	            response.setStatus(true);
	        } else {
	            response.setMessage("User registration failed!");
	            response.setStatus(false);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle the exception according to your requirements
	        response.setMessage(e.getMessage());
	        response.setStatus(false);
	        return response;
	    }
	    return response;
	}

	@Override
	public void modifyProfile(Scanner scanner, String userName) {
		
		System.out.println("Enter new password (press Enter to keep current password):");
		String newPassword = scanner.nextLine();

		if (!newPassword.isEmpty()) {
		    while (true) {

		        if (Helper.isValidPassword(newPassword)) {
		            System.out.println("Password is valid.");
		            // Move forward with the next input or action
		            break; // Exit the loop as a valid password is entered
		        } else {
		            System.out.println("Password is not valid. It must contain at least 8 characters with 1 special character.");
		            // Continue the loop to ask for the password again
		        }
		    }
		} else {
		    // The user chose to keep the current password
		    System.out.println("Keeping the current password.");
		}

		
		

		System.out.println("Enter new name (press Enter to keep current name):");
		String newName = scanner.nextLine();

		System.out.println("Enter new surname (press Enter to keep current surname):");
		String newSurname = scanner.nextLine();

		System.out.println("Enter new age (press Enter to keep current age):");
		String newAge = scanner.nextLine();

		updateProfile(newPassword, newName, newSurname, newAge, userName);

	}

	@Override
	public void updateProfile(String newPassword, String newName, String newSurname, String newAge, String userId) {
	    String query = "UPDATE Users SET Password = IFNULL(?, Password), Name = IFNULL(?, Name), "
	            + "Surname = IFNULL(?, Surname), Age = IFNULL(?, Age) WHERE UserID = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setString(1, newPassword);
	        preparedStatement.setString(2, newName);
	        preparedStatement.setString(3, newSurname);
	        preparedStatement.setString(4, newAge);
	        preparedStatement.setInt(5, Integer.valueOf(userId));
	        preparedStatement.executeUpdate();
	        System.out.println("Profile updated successfully!");
	        UserOperation operation = new UserOperation();
	        User user = new User();
	        user.setUserId(userId);
        	operation.setUser(user);
        	operation.setOperationType("Profile Updated");
        	saveUserOperation(operation);
	    } catch (Exception e) {
	        System.out.println("Error Occurred in updating user: " + e.getMessage());
	    }
	}


	@Override
	public UserRole getUserRoleFromString(String roleString) {
		return roleString.equals("Admin") ? UserRole.ADMIN : UserRole.REGULAR;
	}

	@Override
	public void seeAllUsers(User loggedInUser) {
	    try {
	        String query = "SELECT Users.UserID, Users.Username, Roles.RoleName, Users.Name, Users.Surname, Users.Age " +
	                "FROM Users " +
	                "JOIN Roles ON Users.RoleID = Roles.RoleID";

	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {

	            System.out.println("All Users:");

	            System.out.printf("%-5s %-15s %-8s %-15s %-15s %-5s\n", "ID", "Username", "Role", "Name", "Surname", "Age");

	            // Display user information
	            while (resultSet.next()) {
	                System.out.printf("%-5d %-15s %-8s %-15s %-15s %-5s\n", resultSet.getInt("UserID"),
	                        resultSet.getString("Username"), resultSet.getString("RoleName"),
	                        resultSet.getString("Name"), resultSet.getString("Surname"), resultSet.getString("Age"));
	            }
	            UserOperation operation = new UserOperation();
	        	operation.setUser(loggedInUser);
	        	operation.setOperationType("View All Users");
	        	saveUserOperation(operation);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error Occurred in getting all users: " + e.getMessage());
	    }
	}

	@Override
	public void removeUser(String usernameToRemove, User loggedInUser) {
	    try {
	        String query = "DELETE FROM Users WHERE Username = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, usernameToRemove);

	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("User removed successfully!");
	                UserOperation operation = new UserOperation();
		        	operation.setUser(loggedInUser);
		        	operation.setOperationType("User Removed");
		        	saveUserOperation(operation);
	            } else {
	                UserOperation operation = new UserOperation();
		        	operation.setUser(loggedInUser);
		        	operation.setOperationType("User Not found to remove");
	                System.out.println("No user found with the provided username.");
	            }
	            
	        }
	    } catch (SQLException e) {
	        System.out.println("Error Occurred in removing user: " + e.getMessage());
	    }
	}


	@Override
	public void adminMenu(Scanner scanner, User loggedInUser) {
	    int adminChoice;

	    do {
	        System.out.println("Admin Menu:");
	        System.out.println("1. Modify Profile");
	        System.out.println("2. See all Users");
	        System.out.println("3. Remove User");
	        System.out.println("4. Review Operations Performed by Other Users");
	        System.out.println("5. Calculate Tax");
	        System.out.println("6. Return to Main Menu");

	        adminChoice = scanner.nextInt();
	        scanner.nextLine(); // Consume the newline character

	        switch (adminChoice) {
	            case 1:
	                modifyProfile(scanner, loggedInUser.getUserId());
	                break;

	            case 2:
	                seeAllUsers(loggedInUser);
	                break;

	            case 3:
	                System.out.print("Enter the username of the user to remove: ");
	                String usernameToRemove = scanner.nextLine();
	                removeUser(usernameToRemove, loggedInUser);
	                break;

	            case 4:
	                System.out.print("In progress reviewUserOperations");
	                System.out.println("Reviewing User Operations:");
	                List<UserOperation> userOperations = getUserOperations();
	                for (UserOperation operation : userOperations) {
	                    System.out.println("OperationID: " + operation.getOperationID());
	                    System.out.println("UserID: " + operation.getUser().getUserId());
	                    System.out.println("OperationType: " + operation.getOperationType());
	                    System.out.println("Timestamp: " + operation.getTimestamp());
	                    System.out.println("--------------");
	                }
	                break;
	                
	            case 5:
	            	calculateTax(scanner, loggedInUser);
	                
	                break;

	            case 6:
	                System.out.println("Returning to Main Menu.");
	                break;

	            default:
	                System.out.println("Invalid choice");
	        }
	    } while (adminChoice != 6);
	}

	@Override
	public void regularUserMenu(Scanner scanner, User loggedInUser) {
	    int regularUserChoice;

	    do {
	        System.out.println("Regular User Menu:");
	        System.out.println("1. Modify Profile");
	        System.out.println("2. Save and See the System of Equations Computed and Their Solutions");
	        System.out.println("3. Calculate Tax");
	        System.out.println("4. Return to Main Menu");
	        regularUserChoice = scanner.nextInt();

	        // Consume the newline character
	        scanner.nextLine();

	        switch (regularUserChoice) {
	            case 1:
	                modifyProfile(scanner, loggedInUser.getUserId());
	                break;

	            case 2:
	                // Implement logic for saving and seeing equations for regular user
	                break;
	            case 3:
	            	calculateTax(scanner, loggedInUser);
	                break;
	            case 4:
	                System.out.println("Returning to Main Menu.");
	                break;

	            default:
	                System.out.println("Invalid choice");
	        }
	    } while (regularUserChoice != 4);
	}
	
	
	@Override
	public void saveUserOperation(UserOperation operation) {
        String query = "INSERT INTO UserOperations (UserID, OperationType) VALUES (?, ?)";
        
        try (Connection connection = DatabaseManager.establishConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, Integer.valueOf(operation.getUser().getUserId()));
            preparedStatement.setString(2, operation.getOperationType());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User operation saved successfully!");
            } else {
                System.out.println("Failed to save user operation.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
	
	@Override
	public List<UserOperation> getUserOperations() {
	    // Implement logic to retrieve user operations from the database
	    // You can use JDBC to fetch the data
	    // Ensure that the UserOperation model and the database operations are appropriately implemented
	    List<UserOperation> userOperations = new ArrayList<>();

	    // Example: Fetch operations using JDBC
	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery("SELECT * FROM UserOperations")) {

	        while (resultSet.next()) {
	        	UserOperation operation = new UserOperation();
	            // Set OperationID
	            operation.setOperationID(resultSet.getInt("OperationID"));
	            User user = new User();
	            user.setUserId(String.valueOf(resultSet.getInt("UserID")));
	            operation.setUser(user);
	            operation.setOperationType(resultSet.getString("OperationType"));
	            // Set Timestamp
	            operation.setTimestamp(resultSet.getTimestamp("Timestamp").toLocalDateTime());
	            // Add the UserOperation object to the list
	            userOperations.add(operation);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exceptions
	    }

	    return userOperations;
	}
	
	@Override
	public void calculateTax(Scanner scanner, User loggedInUser) {
		System.out.println("Select Tax Type:");
        System.out.println("1. Income tax/PAYE");
        System.out.println("2. USC");
        System.out.println("3. PRSI");

        // Prompt user for tax type
        System.out.print("Enter the number corresponding to the tax type: ");
        int taxTypeChoice = scanner.nextInt();

        // Get the tax type name based on user choice
        String taxTypeName = getTaxTypeName(taxTypeChoice);

        // Prompt user for details
        System.out.print("Enter person's name: ");
        String name = scanner.next();

        System.out.print("Enter gross income: ");
        double grossIncome = scanner.nextDouble();

        System.out.print("Enter tax credits: ");
        double taxCredits = scanner.nextDouble();

        // Calculate tax owed (you need to implement this logic)
        double taxOwed = calculateTaxOwed(grossIncome, taxCredits, taxTypeName);
        TaxUsers taxUsers = new TaxUsers();
        // Save data to the database
        taxUsers.setName(name);
        taxUsers.setGrossIncome(grossIncome);
        taxUsers.setTaxCredits(taxCredits);
        taxUsers.setTaxOwed(taxOwed);
        taxUsers.setTaxType(getTaxTypeId(taxTypeName));
        saveToDatabase(taxUsers, loggedInUser.getUserId());

        // Show the calculated tax owed
        System.out.println("Tax Owed: " + taxOwed);
	
	}
	

	
    private String getTaxTypeName(int taxTypeChoice) {
        switch (taxTypeChoice) {
            case 1:
                return "Income tax/PAYE";
            case 2:
                return "USC";
            case 3:
                return "PRSI";
            default:
                throw new IllegalArgumentException("Invalid tax type choice");
        }
    }
    
    
    private int getTaxTypeId(String taxType) {
        switch (taxType) {
            case "Income tax/PAYE":
                return 1;
            case "USC":
                return 2;
            case "PRSI":
                return 3;
            default:
                throw new IllegalArgumentException("Invalid tax type choice");
        }
    }
    
    @Override
    public double calculateTaxOwed(double grossIncome, double taxCredits, String taxTypeName) {
    	switch (taxTypeName) {
		case "Income tax/PAYE":
			 TaxCalculator itc = new IncomeTaxCalculator();
			 return itc.calculateTax(grossIncome, taxCredits);
		case "USC":
			 TaxCalculator usc = new USCcalculator();
			 return usc.calculateTax(grossIncome, taxCredits);
		case "PRSI":
			TaxCalculator prsi = new PRSICalculator();
			 return prsi.calculateTax(grossIncome, taxCredits);
		default:
			throw new IllegalArgumentException("Unexpected value: " + taxTypeName);
		}
       
    }

    @Override
    public void saveToDatabase(TaxUsers taxUsers, String userId) {
        try {
            String insertQuery = "INSERT INTO TaxUsers (Name, GrossIncome, TaxCredits, TaxOwed, TaxTypeID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, taxUsers.getName());
                preparedStatement.setDouble(2, taxUsers.getGrossIncome());
                preparedStatement.setDouble(3, taxUsers.getTaxCredits());
                preparedStatement.setDouble(4, taxUsers.getTaxOwed());
                preparedStatement.setInt(5, taxUsers.getTaxType());
                preparedStatement.executeUpdate();
                UserOperation operation = new UserOperation();
    	        User user = new User();
    	        user.setUserId(userId);
            	operation.setUser(user);
            	operation.setOperationType("Tax calculated");
            	saveUserOperation(operation);
            	
            }
            System.out.println("Tax Added Successfully");
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
            e.printStackTrace();
            // Handle the exception according to your requirements
        }
    }
}
