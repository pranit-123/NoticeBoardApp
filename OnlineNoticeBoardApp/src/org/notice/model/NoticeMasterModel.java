package org.notice.model;

import java.sql.Date;

public class NoticeMasterModel {
	private int Eventid;
	private String EventName;
	private String EventDescription;
	private String edate;
	private String organized_for;
	
	public String getOrganized_for() {
	    return organized_for;
	  }

	  public void setOrganizer(String organized_for) {
	    this.organized_for = organized_for;
	  } 
	
	public String getEventDescription() {
		return EventDescription;
	}

	public void setEventDescription(String eventDescription) {
		EventDescription = eventDescription;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public NoticeMasterModel() {

	}

	public NoticeMasterModel(int Eventid, String edate, String EventName, String EventDescription) {
		this.Eventid = Eventid;
		this.edate = edate;
		this.EventDescription = EventDescription;
		this.EventName = EventName;
		this.organized_for = organized_for;
	}

	public void setOrganized_for(String organized_for) {
		this.organized_for = organized_for;
	}

	public int getEventid() {
		return Eventid;
	}

	public void setEventid(int eventid) {
		Eventid = eventid;
	}

	public String getEventName() {
		return EventName;
	}

	public void setEventName(String eventName) {
		EventName = eventName;
	}

}