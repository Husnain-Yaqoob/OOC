/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hasan
 */
public class Helper {
    UserService service = new UserServiceImpl();
    public static boolean isValidPassword(String password) {
        // Regular expression for at least 8 characters with at least 1 special character
        String regex = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?])(?=.*[a-zA-Z0-9]).{8,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
    
	public static boolean isValidRole(String role) {
		return role.equals("Admin") || role.equals("Regular") ? true : false;
    }
}
