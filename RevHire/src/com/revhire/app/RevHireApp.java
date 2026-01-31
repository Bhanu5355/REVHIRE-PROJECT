package com.revhire.app;

import java.util.List;
import java.util.Scanner;

import com.revhire.entity.Application;
import com.revhire.entity.JobPosting;
import com.revhire.entity.Resume;
import com.revhire.entity.UserAccount;
import com.revhire.exception.InvalidCredentialsException;
import com.revhire.exception.ValidationException;
import com.revhire.service.ApplicationService;
import com.revhire.service.JobPostingService;
import com.revhire.service.ResumeService;
import com.revhire.service.UserAccountService;

public class RevHireApp {

    private static ApplicationService appService = new ApplicationService();
    private static ResumeService resumeService = new ResumeService();
    private static JobPostingService jobService = new JobPostingService();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserAccountService userService = new UserAccountService();

        while (true) {
        	System.out.println("\n===== Welcome to RevHire =====");
        	System.out.println("1. Register");
        	System.out.println("2. Login");
        	System.out.println("3. Forgot Password");
        	System.out.println("4. Exit");
        	System.out.print("Choose option: ");

            String input = sc.nextLine();

        	int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid number (1-4)");
                continue;
            }

            try {
            	if (choice == 1) {

            	    System.out.print("Role (JOB_SEEKER / EMPLOYER): ");
            	    String role = sc.nextLine();

            	    System.out.print("Name: ");
            	    String name = sc.nextLine();

            	    System.out.print("Email: ");
            	    String email = sc.nextLine();

            	    // 🔐 Email validation at console level
            	    if (email == null || email.trim().isEmpty()) {
            	        System.out.println("⚠️ Email cannot be empty");
            	        continue;
            	    }

            	    email = email.trim();

            	    if (!email.contains("@") || !email.contains(".")) {
            	        System.out.println("⚠️ Invalid email format. Example: user@gmail.com");
            	        continue;
            	    }

            	    System.out.print("Password: ");
            	    String pass = sc.nextLine();

            	    if (pass == null || pass.trim().isEmpty()) {
            	        System.out.println("⚠️ Password cannot be empty");
            	        continue;
            	    }

            	    System.out.print("Security Question: ");
            	    String q = sc.nextLine();

            	    System.out.print("Security Answer: ");
            	    String a = sc.nextLine();

            	    try {
            	        userService.register(role, name, email, pass, q, a);
            	        System.out.println("Registration successful");
            	    } catch (ValidationException ve) {
            	        System.out.println("⚠️ " + ve.getMessage());
            	    }
            	}

                
                else if (choice == 2) {

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    // 🔐 Email validation at console level (UX improvement)
                    if (email == null || email.trim().isEmpty()) {
                        System.out.println("⚠️ Email cannot be empty");
                        continue;
                    }

                    email = email.trim();

                    if (!email.contains("@") || !email.contains(".")) {
                        System.out.println("⚠️ Invalid email format. Example: user@gmail.com");
                        continue;
                    }

                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    try {
                        UserAccount user = userService.login(email, pass);
                        System.out.println("Welcome " + user.getName());

                        if ("JOB_SEEKER".equalsIgnoreCase(user.getRole())) {
                            jobSeekerMenu(sc, user.getUserId());
                        } else {
                            employerMenu(sc);
                        }

                    } catch (InvalidCredentialsException ice) {
                        System.out.println("⚠️ Invalid email or password");
                    }
                }

                else if (choice == 3) {

                    System.out.print("Enter registered email: ");
                    String email = sc.nextLine();

                    try {
                        String question =
                            userService.forgotPasswordGetQuestion(email);

                        System.out.println("Security Question: " + question);

                        System.out.print("Answer: ");
                        String ans = sc.nextLine();

                        System.out.print("New Password: ");
                        String newPass = sc.nextLine();

                        boolean updated =
                            userService.resetPassword(email, ans, newPass);

                        System.out.println(updated
                            ? "Password updated successfully"
                            : "Failed to update password");

                    } catch (ValidationException ve) {
                        System.out.println("⚠️ " + ve.getMessage());
                    }
                }


                else if (choice == 4) {
                    System.out.println("Thank you for using RevHire");
                    break;
                }

                else {
                    System.out.println("Invalid option");
                }

            } catch (ValidationException ve) {
                System.out.println("⚠️ " + ve.getMessage());
            } catch (InvalidCredentialsException ice) {
                System.out.println("⚠️ " + ice.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }

    // ================= JOB SEEKER MENU =================
    private static void jobSeekerMenu(Scanner sc, int seekerId) {

        while (true) {
            System.out.println("\n--- Job Seeker Menu ---");
            System.out.println("1. Create Resume");
            System.out.println("2. View Resume");
            System.out.println("3. View All Jobs");
            System.out.println("4. Search Jobs");
            System.out.println("5. Apply Job");
            System.out.println("6. View Applications");
            System.out.println("7. withdraw Application");
            
            System.out.println("8. Logout");
            System.out.print("Choose option: ");
            String input = sc.nextLine();

            int option;
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid number (1-8)");
                continue;
            }


            // 1. CREATE RESUME
            if (option == 1) {
            	System.out.print("Full Name: ");
            	String name = sc.nextLine();
            	
                System.out.print("Objective: ");
                String obj = sc.nextLine();

                System.out.print("Education: ");
                String edu = sc.nextLine();

                System.out.print("Experience: ");
                String exp = sc.nextLine();

                System.out.print("Skills: ");
                String skills = sc.nextLine();

                System.out.print("Projects: ");
                String proj = sc.nextLine();

                boolean saved = resumeService.createResume(
                        seekerId, name, obj, edu, exp, skills, proj
                );


                System.out.println(saved
                        ? "Resume created successfully"
                        : "Failed to create resume");
            }

            // 2. VIEW RESUME
            else if (option == 2) {
                Resume r = resumeService.viewResume(seekerId);

                if (r == null) {
                    System.out.println("No resume found");
                } else {
                    System.out.println("\n--- My Resume ---");
                    System.out.println("Name: " + r.getFullName());
                    System.out.println("Objective: " + r.getObjective());
                    System.out.println("Education: " + r.getEducationText());
                    System.out.println("Experience: " + r.getExperienceText());
                    System.out.println("Skills: " + r.getSkillsText());
                    System.out.println("Projects: " + r.getProjectsText());
                    System.out.println("Last Updated: " + r.getUpdatedAt());
                    
                    
                }
            }

            // 3. VIEW ALL JOBS
            else if (option == 3) {
            	List<JobPosting> jobs = jobService.viewOpenJobs();

            	if (jobs.isEmpty()) {
            	    System.out.println("❌ No jobs available at the moment");
            	} else {
            	    System.out.println("\n--- Available Jobs ---");
            	    for (JobPosting j : jobs) {
            	        System.out.println(
            	            j.getJobId() + " | " +
            	            j.getTitle() + " | " +
            	            j.getLocation() + " | " +
            	            j.getJobType() + " | " +
            	            j.getSalaryMin() + "-" + j.getSalaryMax() +
            	            " | Deadline: " + j.getDeadline());
            	    }
            	}

            }

            // 4. SEARCH JOBS
            else if (option == 4) {
                System.out.print("Enter Location: ");
                String loc = sc.nextLine();

                System.out.print("Enter Job Type: ");
                String type = sc.nextLine();

                List<JobPosting> jobs = jobService.searchJobs(loc, type);

                if (jobs.isEmpty()) {
                    System.out.println("❌ No jobs found for given location and job type");
                } else {
                    System.out.println("\n--- Search Results ---");
                    for (JobPosting j : jobs) {
                        System.out.println(
                                j.getJobId() + " | " +
                                j.getTitle() + " | " +
                                j.getLocation() + " | " +
                                j.getJobType());
                    }
                }
            }

            // 5. APPLY JOB
            else if (option == 5) {
                System.out.print("Enter Job ID: ");
                int jobId = sc.nextInt();
                sc.nextLine();

                System.out.print("Cover Letter (optional): ");
                String cover = sc.nextLine();

                try {
                    boolean applied = appService.apply(jobId, seekerId, 0, cover);
                    if (applied) {
                        System.out.println("✅ Applied successfully");
                    } else {
                        System.out.println("❌ Failed to apply for job");
                    }
                } catch (ValidationException e) {
                    System.out.println("⚠️ " + e.getMessage());
                }
            }

            // 6. VIEW APPLICATIONS
            else if (option == 6) {
                List<Application> list =
                        appService.viewMyApplications(seekerId);

                System.out.println("\n--- My Applications ---");
                for (Application a : list) {
                    System.out.println(
                            a.getApplicationId() + " | Job " +
                            a.getJobId() + " | " +
                            a.getStatus() + " | " +
                            a.getAppliedAt());
                }
            }
         // ---------- WITHDRAW APPLICATION ----------
            else if (option == 7) {

                System.out.print("Enter Application ID to withdraw: ");
                int appId = Integer.parseInt(sc.nextLine());

                try {
                    boolean ok = appService.withdraw(appId);

                    if (ok) {
                        System.out.println("Application withdrawn successfully");
                    } else {
                        System.out.println("❌ Application not found");
                    }

                } catch (ValidationException ve) {
                    System.out.println("⚠️ " + ve.getMessage());
                }
            }


            // ---------- LOGOUT ----------
            else if (option == 8) {
                System.out.println("Logged out successfully");
                break;
            }


            // 8. LOGOUT
            else if (option == 8) {
                System.out.println("Logged out successfully");
                break;
            }
 
            else {
                System.out.println("Invalid option");
            }
        }
    }

    // ================= EMPLOYER MENU =================
    private static void employerMenu(Scanner sc) {

        while (true) {
            System.out.println("\n--- Employer Menu ---");
            System.out.println("1. View Applicants for Job");
            System.out.println("2. Shortlist Applicant");
            System.out.println("3. Reject Applicant");
            System.out.println("4. Reopen");
            System.out.println("5. Logout");
            System.out.print("Choose option: ");
            String input = sc.nextLine();

            int option;
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid number (1-8)");
                continue;
            }


            if (option == 1) {
                System.out.print("Enter Job ID: ");
                int jobId = sc.nextInt();
                sc.nextLine();

                List<Application> list =
                        appService.viewApplicantsByJob(jobId);

                if (list.isEmpty()) {
                    System.out.println("❌ No applicants found for this job");
                } else {
                    System.out.println("\n--- Applicants ---");
                    for (Application a : list) {
                        System.out.println(
                            "ApplicationId: " + a.getApplicationId() +
                            ", SeekerId: " + a.getSeekerId() +
                            ", Status: " + a.getStatus()
                        );
                    }
                }
            }


            else if (option == 2) {
                System.out.print("Enter Application ID to shortlist: ");
                int appId = Integer.parseInt(sc.nextLine());

                try {
                    boolean ok = appService.shortlist(appId);

                    if (ok) {
                        System.out.println("Applicant shortlisted");
                    } else {
                        System.out.println("❌ Application not found");
                    }

                } catch (ValidationException ve) {
                    System.out.println("⚠️ " + ve.getMessage());
                }
            }


            else if (option == 3) {
                System.out.print("Enter Application ID to reject: ");
                int appId = Integer.parseInt(sc.nextLine());

                try {
                    boolean ok = appService.reject(appId);

                    if (ok) {
                        System.out.println("Applicant rejected");
                    } else {
                        System.out.println("❌ Application not found");
                    }

                } catch (ValidationException ve) {
                    System.out.println("⚠️ " + ve.getMessage());
                }
            }
            else if (option == 4) {
                System.out.print("Enter Application ID to reopen: ");
                int appId = Integer.parseInt(sc.nextLine());

                try {
                    boolean ok = appService.reopen(appId);
                    if (ok) {
                        System.out.println("✅ Application reopened successfully");
                    } else {
                        System.out.println("❌ Application not found");
                    }
                } catch (ValidationException ve) {
                    System.out.println("⚠️ " + ve.getMessage());
                }
            }


            else if (option == 5) {
                System.out.println("Logged out successfully");
                break;
            }

            else {
                System.out.println("Invalid option");
            }
        }
    }

}
