package com.revhire.entity;
import java.sql.Timestamp;

public class Resume {

    private int resumeId;               // PK
    private int seekerId;               // FK -> JobSeekerProfile
    private String objective;
    private String educationText;
    private String experienceText;
    private String skillsText;
    private String projectsText;
    private String fullName;
    private Timestamp updatedAt;

    public Resume() {}

    public Resume(int resumeId, int seekerId, String objective, String educationText,
                  String experienceText, String skillsText, String projectsText, Timestamp updatedAt) {
        this.resumeId = resumeId;
        this.seekerId = seekerId;
        this.objective = objective;
        this.educationText = educationText;
        this.experienceText = experienceText;
        this.skillsText = skillsText;
        this.projectsText = projectsText;
        this.updatedAt = updatedAt;
    }

    public int getResumeId() { return resumeId; }
    public void setResumeId(int resumeId) { this.resumeId = resumeId; }

    public int getSeekerId() { return seekerId; }
    public void setSeekerId(int seekerId) { this.seekerId = seekerId; }

    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    public String getEducationText() { return educationText; }
    public void setEducationText(String educationText) { this.educationText = educationText; }

    public String getExperienceText() { return experienceText; }
    public void setExperienceText(String experienceText) { this.experienceText = experienceText; }

    public String getSkillsText() { return skillsText; }
    public void setSkillsText(String skillsText) { this.skillsText = skillsText; }

    public String getProjectsText() { return projectsText; }
    public void setProjectsText(String projectsText) { this.projectsText = projectsText; }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; 
    
    }

	@Override
	public String toString() {
		return "Resume [resumeId=" + resumeId + ", seekerId=" + seekerId
				+ ", objective=" + objective + ", educationText="
				+ educationText + ", experienceText=" + experienceText
				+ ", skillsText=" + skillsText + ", projectsText="
				+ projectsText + ", fullName=" + fullName + ", updatedAt="
				+ updatedAt + "]";
	
	}
}

