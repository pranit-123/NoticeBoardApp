package org.notice.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.notice.config.DBconfig;
import org.notice.config.DBhelper;
import org.notice.model.NoticeMasterModel;
import org.notice.model.VerficationUser;

public class NoticeMasterRepository extends DBhelper {
	private List<NoticeMasterModel> eventsTable = new ArrayList<>();
	private Map<String, VerficationUser> usersTable = new HashMap<>();
	// to add new event
	public boolean isAddNewEvent(NoticeMasterModel model) {
		try {
			stmt = con.prepareStatement(
					"INSERT INTO eventmaster (eventid, edate, ename, edescription, organized_for) VALUES (?, ?, ?, ?, ?)");
			stmt.setInt(1, model.getEventid());
			stmt.setString(2, model.getEdate());
			stmt.setString(3, model.getEventName());
			stmt.setString(4, model.getEventDescription());
			stmt.setString(5, model.getOrganized_for());
			int value = stmt.executeUpdate();
			return value > 0;
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
			return false;
		}
	}

	// Method to verify user credentials and retrieve role
	public VerficationUser verifyUsernameAndPassword(String username, String password) {
		try {
			stmt = con.prepareStatement(
					"SELECT username, password, role FROM registration WHERE username = ? AND password = ?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				VerficationUser user = new VerficationUser();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				return user;
				// return new VerficationUser(rs.getString("username"),
				// rs.getString("password"), rs.getString("role"));
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
		return null;
	}

	// Method to register a new user with a specific role
	public boolean registerUser(String username, String password, String role) {
		try {
			stmt = con.prepareStatement("INSERT INTO registration (username, password, role) VALUES (?, ?, ?)");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, role);
			int value = stmt.executeUpdate();
			return value > 0;
		} catch (Exception ex) {
			System.err.println("Error: " + ex);
			return false;
		}
	}

	public List<NoticeMasterModel> getEventsByOrganizer(String role) {
		List<NoticeMasterModel> events = new ArrayList<>();
		try {
			// Retrieve events specific to the role or common events
			stmt = con.prepareStatement(
					"SELECT eventid, edate, ename, edescription, organized_for FROM eventmaster WHERE organized_for = 'admin' OR organized_for = 'all' OR organized_for = 'student'");
			// stmt.setString(1, role);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				NoticeMasterModel event = new NoticeMasterModel();
				event.setEventid(rs.getInt("eventid"));
				event.setEdate(rs.getString("edate"));
				event.setEventName(rs.getString("ename"));
				event.setEventDescription(rs.getString("edescription"));
				event.setOrganizer(rs.getString("organized_for"));
				events.add(event);
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
		return events;
	}

	// Method to check if an event exists by ID
	public boolean doesEventExist(int eventId) {
		try {
			stmt = con.prepareStatement("SELECT eventid FROM eventmaster WHERE eventid = ?");
			stmt.setInt(1, eventId);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
			return false;
		}
	}

	// Method to update event
	public boolean updateEvent(NoticeMasterModel model) {
		try {
			if (!doesEventExist(model.getEventid())) {
				return false;
			}
			stmt = con.prepareStatement(
					"UPDATE eventmaster SET edate = ?, ename = ?, edescription = ?, organized_for = ? WHERE eventid = ?");
			stmt.setString(1, model.getEdate());
			stmt.setString(2, model.getEventName());
			stmt.setString(3, model.getEventDescription());
			stmt.setString(4, model.getOrganized_for());
			stmt.setInt(5, model.getEventid());
			int value = stmt.executeUpdate();
			return value > 0;
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
			return false;
		}
	}
    // Method to view all users 
	 public List<VerficationUser> getAllUsers() {
	        List<VerficationUser> users = new ArrayList<>();
	        try {
	            String query = "SELECT * FROM registration"; // Adjust the table name and columns as per your database
	            stmt = con.prepareStatement(query);
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	                VerficationUser user = new VerficationUser();
	                user.setUsername(rs.getString("username"));
	                user.setRole(rs.getString("role"));
	                users.add(user);
	            }
	        } catch (Exception ex) {
	            System.out.println("Error: " + ex);
	        }
	        return users;
	    }
	
	// Method to delete event
	public boolean deleteEvent(int eventId) {
		try {
			if (!doesEventExist(eventId)) {
				return false;
			}
			stmt = con.prepareStatement("DELETE FROM eventmaster WHERE eventid = ?");
			stmt.setInt(1, eventId);
			int value = stmt.executeUpdate();
			return value > 0;
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
			return false;
		}
	}

	public List<NoticeMasterModel> getAllEvents(String role) {
		List<NoticeMasterModel> events = new ArrayList<>();
		try {
			String query = "SELECT eventid, edate, ename, edescription, organized_for FROM eventmaster WHERE organized_for = ? OR organized_for = 'all'";
			stmt = con.prepareStatement(query);
			stmt.setString(1, role);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				NoticeMasterModel event = new NoticeMasterModel();
				event.setEventid(rs.getInt("eventid"));
				event.setEdate(rs.getString("edate"));
				event.setEventName(rs.getString("ename"));
				event.setEventDescription(rs.getString("edescription"));
				event.setOrganizer(rs.getString("organized_for"));
				events.add(event);
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
		return events;
	}
	// Method to view Profile of user
    public VerficationUser getUserByUsernameAndPassword(String username, String password) {
        VerficationUser user = usersTable.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
