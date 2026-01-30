package com.revhire.entity;
public class Company {

    private int companyId;          
    private String name;
    private String industry;
    private String size;
    private String website;
    private String location;

    public Company() {}

    public Company(int companyId, String name, String industry, String size, String website, String location) {
        this.companyId = companyId;
        this.name = name;
        this.industry = industry;
        this.size = size;
        this.website = website;
        this.location = location;
    }

    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; 
    
    }

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name
				+ ", industry=" + industry + ", size=" + size + ", website="
				+ website + ", location=" + location + "]";
	}
}
