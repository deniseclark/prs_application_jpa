package util;

import java.util.ArrayList;
import business.Status;
import business.StatusDB;

public class StatusDE {
 
	private static ArrayList<Status> statusList = null;
	private static Status status;
	private static boolean displayMenu = true;
	private static boolean isSuccessful = false;
	private static int menuOption = 0;

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
				statusList = StatusDB.queryAll();
				printList(statusList);
				break;
			// *****************************
			// *** 2.) Query by ID       ***
			// *****************************
			case 2:
				int statusId = Console.getInt("Enter StatusID to Query: ");
				status = StatusDB.queryById(statusId);
				if (status == null) {
					System.out.println("%%% Record Not Found");
				} else {
					System.out.println();
					System.out.println(status.toString());
				}
				break;
			// *********************************
			// *** 3.) Query by Description  ***
			// *********************************
			case 3:
				String description = Console.getString("Enter Description Value to Query: ");
				status = StatusDB.queryByDescription(description);
				if (status == null) {
					System.out.println("%%% Record Not Found");
				} else {
					System.out.println();
					System.out.println(status.toString());
				}
				break;
			// ********************
			// *** 4.) Insert   ***
			// ********************
			case 4:
				description = Console.getString("Description: ");	
				status = new Status(0, description, true);
				isSuccessful = StatusDB.insert(status);
				if (isSuccessful) {
					System.out.println("%%% Successfully Inserted");
					System.out.println();
					System.out.println(status.toString());
				} else {
					System.out.println("%%% Record Not Inserted");
				}
				break;
			// ******************
			// *** 5.) Update ***
			// ******************
			case 5:
				int updCounter = 0;
				int updateId = Console.getInt("\nEnter ID to Update: ");
				status = StatusDB.queryById(updateId);
				if (status == null) {
					System.out.println("%%% Record Not Found");
				} else {
					String updDescription = Console.getStringAllowNull("Enter Updated Description [ENTER] for No Change: ");
					if (updDescription.isEmpty()) {
						status.setDescription(status.getDescription());
					} else {
						status.setDescription(updDescription);
						updCounter++;
					}
					String updIsActive = Console.getStringAllowNull("Enter isActive Flag of Y or N or [ENTER] for No Change: ");
					if (updIsActive.isEmpty()) {
						status.setActive(status.isActive());
					} else if ((updIsActive.equalsIgnoreCase("Y")) && (!status.isActive())) {
						status.setActive(true);
						updCounter++;
					} else if ((updIsActive.equalsIgnoreCase("N")) && (status.isActive())) {
						status.setActive(false);
						updCounter++;
					} else {
						status.setActive(status.isActive());
					}
					if (updCounter > 0) {
						isSuccessful = StatusDB.update(status);
						if (isSuccessful) {
							System.out.println("%%% Successfully Updated");
							System.out.println();
							System.out.println(status.toString());	
						} else {
							System.out.println("%%% Record Not Updated");
						}
					} else {
						System.out.println("%%% No Changes Entered - Record not Updated");
					}
				}
				break;
			// ******************
			// *** 6.) Delete ***
			// ******************
			case 6:
				int deleteId = Console.getInt("\nEnter ID to Delete: ");
				status = StatusDB.queryById(deleteId);
				if (status == null) {
					System.out.println("%%% Record Not Found");
				} else {
   				    isSuccessful = StatusDB.delete(status);
   					if (isSuccessful) {
   						System.out.println("%%% Successfully Deleted");
   					} else {
   						System.out.println("%%% Record Not Deleted");
   					}	
				}
				break;	
				
			// ******************
			// *** 7.) Exit   ***
			// ******************
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
		System.out.println("****************************");
		System.out.println("*** Administer Statuses  ***");
		System.out.println("****************************");
		System.out.println("1.) Display All ");
		System.out.println("2.) Query by ID");
		System.out.println("3.) Query a by Description");		
		System.out.println("4.) Create a New Status");
		System.out.println("5.) Update a Status");
		System.out.println("6.) Delete a Status");
		System.out.println("7.) Return to Main Menu");
		System.out.println();
	}
	
	// *******************
	// *** Print List  ***
	// *******************
	public static void printList(ArrayList<Status> statusList) {
		System.out.println();
		for (Status s : statusList) {
			System.out.println(s.toString());
		}
	}
	
} 
