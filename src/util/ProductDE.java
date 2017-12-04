package util;

import java.util.ArrayList;
import business.Product;
import business.ProductDB;
import business.Vendor;
import business.VendorDB;

public class ProductDE {
 
	private static ArrayList<Product> productList = null;
	private static Product product;
   	private static Vendor vendor;
	private static boolean displayMenu = true;
	private static boolean isSuccessful = false;
	private static int menuOption = 0;
    private static boolean isValidVendor = false;

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
				productList = ProductDB.queryAll();
				printList(productList);
				break;
			// *****************************
			// *** 2.) Query by ID       ***
			// *****************************
			case 2:
				int productId = Console.getInt("Enter ID to Query: ");
				product = ProductDB.queryById(productId);
				if (product == null) {
					System.out.println("%%% ProductId Not Found");
				} else {
					System.out.println();
					System.out.println(product.toString());
				}
				break;
			// ********************************
			// *** 3.) Query by PartNumber  ***
			// ********************************
			case 3:
                isValidVendor = false;
               	vendor = new Vendor();
               	while (!isValidVendor) {
               		int vendorId = Console.getInt("\nVendor Id: ");
               		vendor = VendorDB.queryById(vendorId);
               		if (vendor == null) {
               			System.out.println("%%% Invalid Entry - Try again");
               		} else {
               			isValidVendor = true;
               		} 
               	} 
				String partNumber = Console.getString("Enter Product PartNumber Value to Query: ");   
				product = ProductDB.queryByPartNumber(partNumber, vendor);
				if (product == null) {
					System.out.println("%%% Record Not Found");
				} else {
					System.out.println();
					System.out.println(product.toString());
				}
				break;
			// ********************
			// *** 4.) Insert   ***
			// ********************
			case 4:
                isValidVendor = false;
               	vendor = new Vendor();
               	while (!isValidVendor) {
               		int vendorId = Console.getInt("\nVendor Id: ");
               		vendor = VendorDB.queryById(vendorId);
               		if (vendor == null) {
               			System.out.println("%%% Invalid Entry - Try again");
               		} else {
               			isValidVendor = true;
               		} 
               	} 
				partNumber = Console.getString("Part Number: ");
				String name = Console.getString("Name: ");
				double price = Console.getDouble("Price: ");
                                String unit = Console.getString("Unit: ");
				String photoPath = Console.getStringAllowNull("PhotoPath: ");
				boolean isActive = Console.getYNBoolean("Active? Y/N: ");
				product = new Product(0, vendor, partNumber, name, 
					price, unit, photoPath, isActive);
				isSuccessful = ProductDB.insert(product);
				if (isSuccessful) {
					System.out.println("%%% Successfully Inserted");
					System.out.println();
					System.out.println(product.toString());
				} else {
					System.out.println("%%% Record Not Inserted");
				}
				break;
			// *****************************************
			// *** 5.) Update (Cannot Update Vendor) ***
			// *****************************************
			case 5:
				int updCounter = 0;
				int updateId = Console.getInt("\nEnter ID to Update: ");
				product = ProductDB.queryById(updateId);
				if (product == null) {
					System.out.println("%%% Record Not Found");
				} else {
					String updPartNumber = Console.getStringAllowNull("Enter Updated Part Number [ENTER] for No Change: ");
					if (updPartNumber.isEmpty()) {
						product.setPartNumber(product.getPartNumber());
					} else {
						product.setPartNumber(updPartNumber);
						updCounter++;
					}
					String updName = Console.getStringAllowNull("Enter Updated Name [ENTER] for No Change: ");
					if (updName.isEmpty()) {
						product.setName(product.getName());
					} else {
						product.setName(updName);
						updCounter++;
					}
					String updPrice = Console.getStringAllowNull("Enter Updated Price [ENTER] for No Change: ");
					if (updPrice.isEmpty()) {
						product.setPrice(product.getPrice());
					} else {
						product.setPrice(Double.parseDouble(updPrice));
						updCounter++;
					}
					String updUnit = Console.getStringAllowNull("Enter Updated Unit [ENTER] for No Change: ");
					if (updUnit.isEmpty()) {
						product.setUnit(product.getUnit());
					} else {
						product.setUnit(updUnit);
						updCounter++;
					}
					String updPhotoPath = Console.getStringAllowNull("Enter Updated PhotoPath [ENTER] for No Change: ");
					if (updPhotoPath.isEmpty()) {
						product.setPhotoPath(product.getPhotoPath());
					} else {
						product.setPhotoPath(updPhotoPath);
						updCounter++;
					}
					String updIsActive = Console.getStringAllowNull("Enter isActive Flag of Y or N or [ENTER] for No Change: ");
					if (updIsActive.isEmpty()) {
						product.setActive(product.isActive());
					} else if ((updIsActive.equalsIgnoreCase("Y")) && (!product.isActive())) {
						product.setActive(true);
						updCounter++;
					} else if ((updIsActive.equalsIgnoreCase("N")) && (product.isActive())) {
						product.setActive(false);
						updCounter++;
					} else {
						product.setActive(product.isActive());
					}
					if (updCounter > 0) {
						isSuccessful = ProductDB.update(product);
						if (isSuccessful) {
							System.out.println("%%% Successfully Updated");
							System.out.println();
							System.out.println(product.toString());	
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
				product = ProductDB.queryById(deleteId);
				if (product == null) {
					System.out.println("%%% Record Not Found");
				} else {
   				    isSuccessful = ProductDB.delete(product);
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
		System.out.println("*** Administer Products  ***");
		System.out.println("****************************");
		System.out.println("1.) Display All ");
		System.out.println("2.) Query by ID");
		System.out.println("3.) Query a by Vendor/PartNumber");		
		System.out.println("4.) Create a New Product");
		System.out.println("5.) Update a Product");
		System.out.println("6.) Delete a Product");
		System.out.println("7.) Return to Main Menu");
		System.out.println();
	}
	
	// *******************
	// *** Print List  ***
	// *******************
	public static void printList(ArrayList<Product> productList) {
		System.out.println();
		for (Product v : productList) {
			System.out.println(v.toString());
		}
	}
	
} 