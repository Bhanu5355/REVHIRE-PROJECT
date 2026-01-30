package com.revhire.entity;
public class JobSeekerProfile {

    private int seekerId;           
    private int userId;             
    private String location;
    private int experienceYears;

    public JobSeekerProfile() {}

    public JobSeekerProfile(int seekerId, int userId, String location, int experienceYears) {
        this.seekerId = seekerId;
        this.userId = userId;
        this.location = location;
        this.experienceYears = experienceYears;
    }

    public int getSeekerId() { return seekerId; }
    public void setSeekerId(int seekerId) { this.seekerId = seekerId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears;
    
    }

	@Override
	public String toString() {
		return "JobSeekerProfile [seekerId=" + seekerId + ", userId=" + userId
				+ ", location=" + location + ", experienceYears="
				+ experienceYears + "]";
	}

}

