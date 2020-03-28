package com.example.demo.model;

public class LocationStats {

	private String state;
	private String country;
	private int latestCaseTotal;
	private int diffFromPrevDay;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestCaseTotal() {
		return latestCaseTotal;
	}
	public void setLatestCaseTotal(int latestCaseTotal) {
		this.latestCaseTotal = latestCaseTotal;
	}
	
	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}
	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}
	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", latestCaseTotal=" + latestCaseTotal + "]";
	}
	
		
	
}
