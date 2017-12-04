package util;

import java.util.ArrayList;
import business.User;
import business.UserDB;

public class UserDE {
 
	private static ArrayList<User> userList = null;
	private static User user;
	private static boolean displayMenu = true;
	private static boolean isSuccessful = false;
	private static int menuOption = 0;
	String userName = null;

	// ***********************************
	// *** Display Administrative Menu ***
	// ***********************************
	public static void displayMenu() {
		
		while (displayMenu) {
			displayMenuOptions();
			menuOption = Console.getInt("Menu Option: ");
			
			switch (menuOption) {
			// *****************************
			// *** 1.) Query All Records ***
			// *****************************
			case 1:
				userList = UserDB.queryAll();
				printList(userList);
				break;
			// *****************************
			// *** 2.) Query by ID       ***
			// *****************************
			case 2:
				int userId = Console.getInt("Enter UserID to Query: ");
				user = UserDB.queryById(userId);
				if (user == null) {
					System.out.println("%%% Record Not Found");
				} else {
					System.out.println();
					System.out.println(user.toString());
				}
				break;
			// ******************************
			// *** 3.) Query by UserName  ***
			// ******************************
			case 3:
				String userName = Console.getString("Enter UserName Value to Query: ");
				user = UserDB.queryByUserName(userName);
				if (user == null) {
					System.out.println("%%% Record Not Found");
				} else {
					System.out.println();
					System.out.println(user.toString());
				}
				break;
			// ******************************
			// *** 4.) Insert a New User  ***
			// ******************************
			case 4:
				String firstName = Console.getString("First Name: ");
				String lastName = Console.getString("Last Name: ");
				String phone = Console.getString("Phone#: ");
				String email = Console.getString("Email: ");
				String username = Console.getString("Enter UserName: ");
				String password = Console.getString("Password: ");
				boolean isReviewer = Console.getYNBoolean("Reviewer? Y/N: ");
				boolean isAdmin = Console.getYNBoolean("Administrtor? Y/N: ");		
				user = new User(0, username, password, firstName, lastName, 
						phone, email, isReviewer, isAdmin, true);
				isSuccessful = UserDB.insert(user);
				if (isSuccessful) {
					System.out.println("%%% Successfully Inserted");
					System.out.println();
					System.out.println(user.toString());
				} else {
					System.out.println("%%% Record Not Inserted");
				}
				break;
			// ****************************
			// *** 5.) Update a User    ***
			// ****************************
			case 5:
				int updCounter = 0;
				int updateId = Console.getInt("\nEnter ID to Update: ");
				user = UserDB.queryById(updateId);
				if (user == null) {
					System.out.println("%%% Record Not Found");
				} else {
					String updFirstName = Console.getStringAllowNull("Enter Updated FirstName [ENTER] for No Change: ");
					if (updFirstName.isEmpty()) {
						user.setFirstName(user.getFirstName());
					} else {
						user.setFirstName(updFirstName);
						updCounter++;
					}
					String updLastName = Console.getStringAllowNull("Enter Updated LastName [ENTER] for No Change: ");
					if (updLastName.isEmpty()) {
						user.setLastName(user.getLastName());
					} else {
						user.setLastName(updLastName);
						updCounter++;
					}
					String updUserName = Console.getStringAllowNull("Enter Updated UserName [ENTER] for No Change: ");
					if (updUserName.isEmpty()) {
						user.setUserName(user.getUserName());
					} else {
						user.setUserName(updUserName);
						updCounter++;
					}
					String updPassword = Console.getStringAllowNull("Enter Updated Password [ENTER] for No Change: ");
					if (updPassword.isEmpty()) {
						user.setPassword(user.getPassword());
					} else {
						user.setPassword(updPassword);
						updCounter++;
					}
					String updPhone = Console.getStringAllowNull("Enter Updated Phone# [ENTER] for No Change: ");
					if (updPhone.isEmpty()) {
						user.setPhone(user.getPhone());
					} else {
						user.setPhone(updPhone);
						updCounter++;
					}
					String updEmail = Console.getStringAllowNull("Enter Updated Email [ENTER] for No Change: ");
					if (updEmail.isEmpty()) {
						user.setEmail(user.getEmail());
					} else {
						user.setEmail(updEmail);
						updCounter++;
					}
					String updIsAdmin = Console
							.getStringAllowNull("Enter isAdmin Flag of Y or N or [ENTER] for No Change: ");
					if (updIsAdmin.isEmpty()) {
						user.setAdmin(user.isAdmin());
					} else if ((updIsAdmin.equalsIgnoreCase("Y")) && (!user.isAdmin())) {
						user.setAdmin(true);
						updCounter++;
					} else if ((updIsAdmin.equalsIgnoreCase("N")) && (user.isAdmin())) {
						user.setAdmin(false);
						updCounter++;
					} else {
						user.setAdmin(user.isAdmin());
					}
					if (updCounter > 0) {
						isSuccessful = UserDB.update(user);
						if (isSuccessful) {
							System.out.println("%%% Successfully Updated");
							System.out.println();
							System.out.println(user.toString());	
						} else {
							System.out.println("%%% Record Not Updated");
						}
					} else {
						System.out.println("%%% No Changes Entered - Record not Updated");
					}
				}
				break;
			// **************************
			// *** 6.) Delete a User  ***
			// **************************
			case 6:
				int deleteId = Console.getInt("\nEnter ID to Delete: ");
				user = UserDB.queryById(deleteId);
				if (user == null) {
					System.out.println("%%% Record Not Found");
				} else {
   				    isSuccessful = UserDB.delete(user);
   					if (isSuccessful) {
   						System.out.println("%%% Successfully Deleted");
   					} else {
   						System.out.println("%%% Record Not Deleted");
   					}	
				}
				break;	
				
			// **************************************
			// *** 7.) Exit and Return to Caller  ***
			// **************************************
			case 7:
			default:
				displayMenu = false;
				break;
			} 
		} 
	}	
	
	// *****************************
	// *** Display Menu Options  ***
	// *****************************
	public static void displayMenuOptions() {
		System.out.println("\n");
		System.out.println("***************************");
		System.out.println("*** Administer Users    ***");
		System.out.println("***************************");
		System.out.println("1.) Display All Users");
		System.out.println("2.) Query a User by ID");
		System.out.println("3.) Query a User by Username");		
		System.out.println("4.) Create a New User");
		System.out.println("5.) Update a User");
		System.out.println("6.) Delete a User");
		System.out.println("7.) Return to Main Menu");
		System.out.println();
	}
		
	// *******************
	// *** Print List  ***
	// *******************
	public static void printList(ArrayList<User> userList) {
		System.out.println();
		for (User s : userList) {
			System.out.println(s.toString());
		}
	}
	
	// ************************************************
	// *** Update User Account (Non-Admin Accounts) ***
	// ************************************************
	public static void nonAdminUpdates(User user) {	
		int updCounter = 0;
		if (user != null) {  
			String updFirstName = Console.getStringAllowNull("\nEnter Updated FirstName [ENTER] for No Change: ");
			if (updFirstName.isEmpty()) {
				user.setFirstName(user.getFirstName());
			} else {
				user.setFirstName(updFirstName);
				updCounter++;
			}
			String updLastName = Console.getStringAllowNull("Enter Updated LastName [ENTER] for No Change: ");
			if (updLastName.isEmpty()) {
				user.setLastName(user.getLastName());
			} else {
				user.setLastName(updLastName);
				updCounter++;
			}
			String updPassword = Console.getStringAllowNull("Enter Updated Password [ENTER] for No Change: ");
			if (updPassword.isEmpty()) {
				user.setPassword(user.getPassword());
			} else {
				user.setPassword(updPassword);
				updCounter++;
			}
			String updPhone = Console.getStringAllowNull("Enter Updated Phone# [ENTER] for No Change: ");
			if (updPhone.isEmpty()) {
				user.setPhone(user.getPhone());
			} else {
				user.setPhone(updPhone);
				updCounter++;
			}
			String updEmail = Console.getStringAllowNull("Enter Updated Email [ENTER] for No Change: ");
			if (updEmail.isEmpty()) {
				user.setEmail(user.getEmail());
			} else {
				user.setEmail(updEmail);
				updCounter++;
			}
			if (updCounter > 0) {
				UserDB.update(user);
				System.out.println(user.toString());
			} else {
				System.out.println("\n%%% No Changes Entered - Record not Updated");
			}			
		}  
	}
} 
