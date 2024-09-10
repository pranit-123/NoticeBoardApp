package org.notice.service;

import java.util.List;

import org.notice.model.NoticeMasterModel;
import org.notice.model.VerficationUser;
import org.notice.repository.NoticeMasterRepository;

public class NoticeMasterService {
	NoticeMasterRepository Nrepo = new NoticeMasterRepository();

	// Method to add the event
	public boolean isAddEvent(NoticeMasterModel model) {
		return Nrepo.isAddNewEvent(model);
	}

	// Method to check the user credentials
	public VerficationUser verifyUser(String username, String password) {
		return Nrepo.verifyUsernameAndPassword(username, password);
	}

	// Method to register for the specific role
	public boolean registerUser(String username, String password, String role) {
		return Nrepo.registerUser(username, password, role);
	}
    // Method is used to View All Users
	public List<VerficationUser> viewAllUsers() {
        return Nrepo.getAllUsers();
    }
	
	// Method to update an event
	public boolean updateEvent(NoticeMasterModel model) {
		return Nrepo.updateEvent(model);
	}

	// Method to delete an event
	public boolean deleteEvent(int eventId) {
		return Nrepo.deleteEvent(eventId);
	}

	// Method to check if an event exists by ID
	public boolean doesEventExist(int eventId) {
		return Nrepo.doesEventExist(eventId);
	}

	// Method to view all Events
	public List<NoticeMasterModel> viewEventsByOrganizer(String role) {
		return Nrepo.getAllEvents(role);
	}
	// Method to view User Profile
	public VerficationUser getUserProfile(String username, String password) {
        return Nrepo.getUserByUsernameAndPassword(username, password);
    }
}