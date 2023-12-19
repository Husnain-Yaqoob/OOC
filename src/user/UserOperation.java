/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import models.User;
/**
 *
 * @author moizb
 */

    public class UserOperation {
    private int operationID;
    private User user;
    private String operationType;
    private LocalDateTime timestamp;
    
	public int getOperationID() {
		return operationID;
	}
	public void setOperationID(int operationID) {
		this.operationID = operationID;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime localDateTime) {
		this.timestamp = localDateTime;
	}

    
}

