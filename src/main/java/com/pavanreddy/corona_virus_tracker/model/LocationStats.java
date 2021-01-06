package com.pavanreddy.corona_virus_tracker.model;

public class LocationStats {
	
	private String state;
	private String country;
	private int totalReportedCase;
	private int latestReportedCase;
	
	public LocationStats() {
		
	}

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

	public int getTotalReportedCase() {
		return totalReportedCase;
	}

	public void setTotalReportedCase(int totalReportedCase) {
		this.totalReportedCase = totalReportedCase;
	}

	public int getLatestReportedCase() {
		return latestReportedCase;
	}

	public void setLatestReportedCase(int latestReportedCase) {
		this.latestReportedCase = latestReportedCase;
	}

	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", totalReportedCase=" + totalReportedCase
				+ ", latestReportedCase=" + latestReportedCase + "]";
	}
	
}
