package util;

import java.util.ArrayList;
import business.Vendor;
import business.VendorDB;

public class VendorDE {
 
	private static ArrayList<Vendor> vendorList = null;
	private static Vendor vendor;
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
				vendorList = VendorDB.queryAll();
				printList(vendorList);
				break;
			// *****************************
			// *** 2.) Query by ID       ***
			// *****************************
			case 2:
				int vendorId = Console.getInt("Enter ID to Query: ");
				vendor = VendorDB.queryById(vendorId);
				if (vendor == null) {
					System.out.println("%%% VendorId Not Found");
				} else {
					System.out.println();
					System.out.println(vendor.toString());
				}
				break;
			// **************************
			// *** 3.) Query by Code  ***
			// **************************
			case 3:
				String code = Console.getString("Enter Vendor Code Value to Query: ");
				vendor = VendorDB.queryByCode(code);
				if (vendor == null) {
					System.out.println("%%% Record Not Found");
				} else {
					System.out.println();
					System.out.println(vendor.toString());
				}
				break;
			// ********************
			// *** 4.) Insert   ***
			// ********************
			case 4:
				code = Console.getString("Vendor Code: ");
				String name = Console.getString("Name: ");
				String address = Console.getString("Address: ");
				String city = Console.getString("City: ");
				String state = Console.getString("State: ");
				String zip = Console.getString("ZipCode: ");
				String phone = Console.getString("Phone#: ");
				String email = Console.getString("Email: ");
				boolean isPreApproved = Console.getYNBoolean("Pre-Approved? Y/N: ");
				boolean isActive = Console.getYNBoolean("Active? Y/N: ");
				vendor = new Vendor(0, code, name, address, city, state, zip,
					phone, email, isPreApproved, isActive);
				isSuccessful = VendorDB.insert(vendor);
				if (isSuccessful) {
					System.out.println("%%% Successfully Inserted");
					System.out.println();
					System.out.println(vendor.toString());
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
				vendor = VendorDB.queryById(updateId);
				if (vendor == null) {
					System.out.println("%%% Record Not Found");
				} else {
					String updCode = Console.getStringAllowNull("Enter Updated Code [ENTER] for No Change: ");
					if (updCode.isEmpty()) {
						vendor.setCode(vendor.getCode());
					} else {
						vendor.setCode(updCode);
						updCounter++;
					}
					String updName = Console.getStringAllowNull("Enter Updated Name [ENTER] for No Change: ");
					if (updName.isEmpty()) {
						vendor.setName(vendor.getName());
					} else {
						vendor.setName(updName);
						updCounter++;
					}
					String updAddress = Console.getStringAllowNull("Enter Updated Address [ENTER] for No Change: ");
					if (updAddress.isEmpty()) {
						vendor.setAddress(vendor.getAddress());
					} else {
						vendor.setAddress(updAddress);
						updCounter++;
					}
					String updCity = Console.getStringAllowNull("Enter Updated City [ENTER] for No Change: ");
					if (updCity.isEmpty()) {
						vendor.setCity(vendor.getCity());
					} else {
						vendor.setCity(updCity);
						updCounter++;
					}
					String updState = Console.getStringAllowNull("Enter Updated State [ENTER] for No Change: ");
					if (updState.isEmpty()) {
						vendor.setState(vendor.getState());
					} else {
						vendor.setState(updState);
						updCounter++;
					}
					String updZip = Console.getStringAllowNull("Enter Updated Zip [ENTER] for No Change: ");
					if (updZip.isEmpty()) {
						vendor.setZip(vendor.getZip());
					} else {
						vendor.setZip(updZip);
						updCounter++;
					}
					String updPhone = Console.getStringAllowNull("Enter Updated Phone [ENTER] for No Change: ");
					if (updPhone.isEmpty()) {
						vendor.setPhone(vendor.getPhone());
					} else {
						vendor.setPhone(updPhone);
						updCounter++;
					}
					String updEmail = Console.getStringAllowNull("Enter Updated Email [ENTER] for No Change: ");
					if (updEmail.isEmpty()) {
						vendor.setEmail(vendor.getEmail());
					} else {
						vendor.setEmail(updEmail);
						updCounter++;
					}
					String updIsPreApproved = Console.getStringAllowNull("Enter isPreApproved Flag of Y or N or [ENTER] for No Change: ");
					if (updIsPreApproved.isEmpty()) {
						vendor.setPreApproved(vendor.isPreApproved());
					} else if ((updIsPreApproved.equalsIgnoreCase("Y")) && (!vendor.isPreApproved())) {
						vendor.setPreApproved(true);
						updCounter++;
					} else if ((updIsPreApproved.equalsIgnoreCase("N")) && (vendor.isPreApproved())) {
						vendor.setPreApproved(false);
						updCounter++;
					} else {
						vendor.setPreApproved(vendor.isPreApproved());
					}
					String updIsActive = Console.getStringAllowNull("Enter isActive Flag of Y or N or [ENTER] for No Change: ");
					if (updIsActive.isEmpty()) {
						vendor.setActive(vendor.isActive());
					} else if ((updIsActive.equalsIgnoreCase("Y")) && (!vendor.isActive())) {
						vendor.setActive(true);
						updCounter++;
					} else if ((updIsActive.equalsIgnoreCase("N")) && (vendor.isActive())) {
						vendor.setActive(false);
						updCounter++;
					} else {
						vendor.setActive(vendor.isActive());
					}
					if (updCounter > 0) {
						isSuccessful = VendorDB.update(vendor);
						if (isSuccessful) {
							System.out.println("%%% Successfully Updated");
							System.out.println();
							System.out.println(vendor.toString());	
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
				vendor = VendorDB.queryById(deleteId);
				if (vendor == null) {
					System.out.println("%%% Record Not Found");
				} else {
   				    isSuccessful = VendorDB.delete(vendor);
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
		System.out.println("*** Administer Vendores  ***");
		System.out.println("****************************");
		System.out.println("1.) Display All ");
		System.out.println("2.) Query by ID");
		System.out.println("3.) Query a by Vendor Code");		
		System.out.println("4.) Create a New Vendor");
		System.out.println("5.) Update a Vendor");
		System.out.println("6.) Delete a Vendor");
		System.out.println("7.) Return to Main Menu");
		System.out.println();
	}
	
	// *******************
	// *** Print List  ***
	// *******************
	public static void printList(ArrayList<Vendor> vendorList) {
		System.out.println();
		for (Vendor v : vendorList) {
			System.out.println(v.toString());
		}
	}
	
} 
