package com.revhire.entity;
public class EmployerProfile {

    private int employerId;        
    private int userId;             
    private int companyId;          
    private String designation;

    public EmployerProfile() {}

    public EmployerProfile(int employerId, int userId, int companyId, String designation) {
        this.employerId = employerId;
        this.userId = userId;
        this.companyId = companyId;
        this.designation = designation;
    }

    public int getEmployerId() { return employerId; }
    public void setEmployerId(int employerId) { this.employerId = employerId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation;
    
    }

	@Override
	public String toString() {
		return "EmployerProfile [employerId=" + employerId + ", userId="
				+ userId + ", companyId=" + companyId + ", designation="
				+ designation + "]";
	}
}

