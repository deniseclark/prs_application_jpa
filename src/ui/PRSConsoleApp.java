package ui;

import business.User;
import business.UserDB;
import util.Console;
import util.ProductDE;
import util.StatusDE;
import util.UserDE;
import util.VendorDE;

public class PRSConsoleApp {

	public static  User loginUser = null;
	
	public static void main(String[] args) {
		
		int menuOption = 0;
		boolean exitApp = false;
		System.out.println();
		System.out.println("**************************************");
		System.out.println("***  Purchase Request System (PRS) ***");
		System.out.println("**************************************\n");
		
		// ******************************************
		// *** Prompt User for Login Credentials  ***
		// ******************************************
		int attempts = 0;
		boolean isValidLogin = false;
		while (!isValidLogin)  {
             isValidLogin = login();
			 attempts++;
			 if ((!isValidLogin) && (attempts >= 3)) {
		         System.out.println("\n%%% Too Many Failed Login Attempts");
		         System.out.println("Contact the Administrator");
				 System.exit(1);
			 }
		}
		
		// *************************************************
		// ***                                           ***
		// ***            Administrator Menu             ***
		// ***                                           ***
		// ***         (User is an Administrator)        ***
		// ***                                           ***
		// *************************************************
		if (loginUser.isAdmin()) {
			while ((!exitApp) && (isValidLogin)) {
				displayMenuAdmin();
				menuOption = Console.getInt("Menu Option: ");
				switch (menuOption) {
				// ********************************
				// *** Administer User Accounts ***
				// ********************************
				case 1:
					UserDE.displayMenu();
					break;
				// ********************************************
				// *** Administer Purchase Request Statuses ***
				// ********************************************
				case 2:
					StatusDE.displayMenu();
					break;
				// **************************
				// *** Administer Vendors ***
				// **************************
				case 3:
					VendorDE.displayMenu();
					break;
				// ***************************
				// *** Administer Products ***
				// ***************************
				case 4:
					ProductDE.displayMenu();
					break;
				// ************************************
				// *** Administer Purchase Requests ***
				// ************************************
				case 5:
					//PurchaseRequestDE.displayMenu(loginUser.getId());
					break;
				case 6:
				default:
					exitApp = true;
					System.out.println("\nGoodbye!");
					break;
			    }  // end case 
		    }	// end while
		} 
	}  
	
	
	// **************************************************
	// *** Prompt and Authenticate Login Credentials  ***
	// **************************************************
	public static boolean login() {
		String userName = Console.getString("Username:  ");
		String password = Console.getString("Password:  ");
		loginUser = UserDB.authenticateUser(userName, password);
		if (loginUser != null) {
			System.out.println("Successful Login");
			System.out.println("Welcome "+
				loginUser.getFirstName() + " " +
			    loginUser.getLastName());
				if (loginUser.isAdmin()) {
					System.out.println("Logged in as an Administrator");
				} else if (loginUser.isReviewer()) {
					System.out.println("Logged in as a Reviewer");				
				} else {
					System.out.println("Logged in as a Non-Administrative Account");
				}
				return true;
			} else {
				System.out.println("%%% Invalid User or Password");
				return false;
			}
		}
	
	
	// ***********************************
	// *** Display Administrator Menu  ***
	// ***********************************
	public static void displayMenuAdmin() {
		System.out.println("\n");
		System.out.println("********************************");
		System.out.println("*** PRS Administrator's Menu ***");
		System.out.println("********************************");
		System.out.println("1.) Administer Users");
		System.out.println("2.) Administer Status's");
		System.out.println("3.) Administer Vendors");
		System.out.println("4.) Administer Products");
		System.out.println("5.) Administer Purchase Requests");	
        System.out.println("6.) Exit");
		System.out.println();
	}
	
	// **************************
	// *** Display User Menu  ***
	// **************************
	// Not Wired in Yet
	public static void displayMenuUser() {
		System.out.println("\n");
		System.out.println("*******************************");
		System.out.println("*** PRS Console Application ***");
		System.out.println("*******************************");
		System.out.println("1.) View my Account");
		System.out.println("2.) Update My Account/Password");
		System.out.println("3.) View Available Products");
		System.out.println("4.) View my Open Purchase Requests");
		System.out.println("5.) Create a New Purchase Request");
		System.out.println("6.) Update My Purchase Requests");
		if (loginUser.isReviewer()) {
			System.out.println("7.) Approve Purchase Requests");
			System.out.println("8.) Exit");
		} else {
			System.out.println("7.) Exit");	
		}
		System.out.println();
	}


}
