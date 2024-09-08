package com.example.MikoMini.db.entity;

import java.sql.Date;

public class AppInfo {
	
	public int ID;
	public String app_name;
	public String app_state;
	public Date created_at;
	public Date updated_at;
	public String update_available;
	public int numOfAttempts;
	
	public AppInfo(int iD, String app_name, String app_state, Date created_at, Date updated_at, String update_available,
			int numOfAttempts) {
		super();
		ID = iD;
		this.app_name = app_name;
		this.app_state = app_state;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.update_available = update_available;
		this.numOfAttempts = numOfAttempts;
	}
	
	
	
	public AppInfo() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public String getApp_state() {
		return app_state;
	}
	public void setApp_state(String app_state) {
		this.app_state = app_state;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public String getUpdate_available() {
		return update_available;
	}
	public void setUpdate_available(String update_available) {
		this.update_available = update_available;
	}
	public int getNumOfAttempts() {
		return numOfAttempts;
	}
	@Override
	public String toString() {
		return "AppInfo [ID=" + ID + ", app_name=" + app_name + ", app_state=" + app_state + ", created_at="
				+ created_at + ", updated_at=" + updated_at + ", update_available=" + update_available
				+ ", numOfAttempts=" + numOfAttempts + "]";
	}
	public void setNumOfAttempts(int numOfAttempts) {
		this.numOfAttempts = numOfAttempts;
	}
	

}
