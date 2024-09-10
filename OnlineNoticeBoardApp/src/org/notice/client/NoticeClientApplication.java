package org.notice.client;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.notice.model.NoticeMasterModel;
import org.notice.model.VerficationUser;
import org.notice.service.NoticeMasterService;

public class NoticeClientApplication {

	public static void main(String[] args) {
		NoticeMasterService nms = new NoticeMasterService();
		Scanner sc = new Scanner(System.in);
		VerficationUser loggedInUser = null; // To store the logged-in user

		System.out.println(
				"*********************************************************************************************");
		System.out.println("                        Welcome to Online Notice Board");
		System.out.println(
				"*********************************************************************************************");

		do {
			int choice;
			System.out.println("1. Check UserCredentials");
			System.out.println("2. Insert/Add New Event");
			System.out.println("3. View All Users ");
			System.out.println("4. View Events ");
			System.out.println("5. Update Event by ID");
			System.out.println("6. Delete Event by ID");
			System.out.println("7. View User Profile");
			System.out.println("8. Exit");
			System.out.println(
					"__________________________________________________________________________________________________");
			System.out.println("Enter Your Choice");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				System.out.println("Enter Student/Admin Username:");
				String username = sc.nextLine();
				System.out.println("Enter Password:");
				String password = sc.nextLine();

				// Verify user credentials and retrieve user object
				loggedInUser = nms.verifyUser(username, password);
				if (loggedInUser != null) {
					System.out.println("Login Successfully................");
				} else {
					System.out.println("Invalid Username & Password");
					System.out.println("Would you like to register Above username and Password? (yes/no):");
					String response = sc.nextLine().trim().toLowerCase();

					// to do the User registration
					if (response.equals("yes")) {
						System.out.println("Enter Role (admin/user):");
						String role = sc.nextLine().trim().toLowerCase(); // Prompt for role

						boolean isRegistered = nms.registerUser(username, password, role); // Pass role to register
						System.out.println(isRegistered); // method
						if (isRegistered) {
							System.out.println("Registration successful. You can now log in.");
						} else {
							System.out.println("Registration failed. Please try again.");
						}
					}
				}
				break;

			case 2:
				// To check user is logged in and has admin role
				if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) 
				{
				System.out.println("Enter the event ID");
					int eventid = sc.nextInt();
					sc.nextLine();
					System.out.println("Enter the Event date In(yyyy-mm-dd)");
					String edate = sc.nextLine();
					System.out.println("Enter the event name");
					String ename = sc.nextLine();

					System.out.println("Write the Event Description");
					String edescription = sc.nextLine();

					System.out.println("Event organized for Whom");
					String organized_for = sc.nextLine();

					NoticeMasterModel model = new NoticeMasterModel();
					model.setEventid(eventid);
					model.setEdate(edate);
					model.setEventName(ename);
					model.setEventDescription(edescription);
					model.setOrganizer(organized_for); // ----------------//
					boolean b = nms.isAddEvent(model);
					if (b) {
						System.out.println("New Event Added Successfully.......");
					} else {
						System.out.println("Not Added");
					}
				} else {
					System.out.println("Please log in as an admin to add an event.");
				}
				break;

			case 3:
				if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
					List<VerficationUser> users = nms.viewAllUsers();
					if (users != null && !users.isEmpty()) {
						System.out.println("List of Users:");
						System.out.println("==============================================");
						System.out.println("Username\tUser_Roll");
						System.out.println("-------------------------------------");
						for (VerficationUser user : users) {
							System.out.println(user.getUsername() + "\t\t" + user.getRole());
						}
						System.out.println();
					} else {
						System.out.println("No users found.");
					}
				}
				else {
					System.out.println("You Are not a Admin so you Can't See the Users\n");
				}

				break;

			case 4:
				if (loggedInUser != null) {
					String role = loggedInUser.getRole();
					List<NoticeMasterModel> events = nms.viewEventsByOrganizer(role);

					if (events != null && !events.isEmpty()) {
						System.out.println("List of Events:");
						System.out.println("------------------------------------------");
						for (NoticeMasterModel event : events) {
							System.out.println("Event ID: " + event.getEventid());
							System.out.println("Event Date: " + event.getEdate());
							System.out.println("Event Name: " + event.getEventName());
							System.out.println("Event Description: " + event.getEventDescription());
							System.out.println("Event Organized For: " + event.getOrganized_for());
							System.out.println("-------------------------------------");
						}
					} else {
						System.out.println("No events found.");
					}
				} else {
					System.out.println("Please log in to view events.");
				}
				break;

			case 5:
				// To check user is logged in and has admin role
				if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
					System.out.println("Enter the event ID to update");
					int eventid = sc.nextInt();
					sc.nextLine();

					// Check if the event ID exists before proceeding
					boolean eventExists = nms.doesEventExist(eventid);
					if (!eventExists) {
						System.out.println("Event ID not found. Update failed.");
						break;
					}

					System.out.println("Enter the updated Event date (yyyy-mm-dd)");
					String updatedEdateStr = sc.nextLine();
					String updatedEdate = null;
					try {
						updatedEdate = String.valueOf(updatedEdateStr); // Convert string to SQL date
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid date format. Please use yyyy-mm-dd.");
						break;
					}
					System.out.println("Enter the updated event name");
					String updatedEname = sc.nextLine();

					System.out.println("Write the updated Event Description");
					String updatedEdescription = sc.nextLine();

					System.out.println("Enter the updated organized_for");
					String updatedOrganized_for = sc.nextLine();

					NoticeMasterModel updatedModel = new NoticeMasterModel();
					updatedModel.setEventid(eventid);
					updatedModel.setEdate(updatedEdate);
					updatedModel.setEventName(updatedEname);
					updatedModel.setEventDescription(updatedEdescription);
					updatedModel.setOrganizer(updatedOrganized_for);
					boolean isUpdated = nms.updateEvent(updatedModel);
					if (isUpdated) {
						System.out.println("Event Updated Successfully.......");
					} else {
						System.out.println("Update Failed: Event ID not found.");
					}
				} else {
					System.out.println("Please log in as an admin to update an event.");
					System.out.println();
				}

				break;

			case 6:
				// To check user is logged in and has admin role
				if (loggedInUser != null && "admin".equals(loggedInUser.getRole())) {
					System.out.println("Enter the event ID to delete");
					int deleteEventId = sc.nextInt();
					sc.nextLine();

					boolean isDeleted = nms.deleteEvent(deleteEventId);
					if (isDeleted) {
						System.out.println("Event Deleted Successfully.......\n");
					} else {
						System.out.println("Deletion failed: Event ID not found.");
					}
				} else {
					System.out.println("Please log in as an admin to delete an events.");
					System.out.println();
				}

				break;

			case 7:
				 // View user profile
                if (loggedInUser != null) {
                    System.out.println("User Profile:");
                    System.out.println("----------------------------------------");
                    System.out.println("Username: " + loggedInUser.getUsername());
                    System.out.println("Password: " + loggedInUser.getPassword());
                    System.out.println("Role: " + loggedInUser.getRole() +"\n");
                } else {
                    System.out.println("Please log in to view your profile.");
                }
                break;
                
			case 8:
				System.out.println("Exiting...");
				System.out.println("Thank You Visit Again..............");
				System.exit(0);

			default:
				System.out.println("Wrong Choice");
			}

		} while (true);
	}
}