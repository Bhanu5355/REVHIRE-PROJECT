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


            int choice = sc.nextInt();
            sc.nextLine();

            try {
                if (choice == 1) {
                    System.out.print("Role (JOB_SEEKER / EMPLOYER): ");
                    String role = sc.nextLine();

                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    System.out.print("Security Question: ");
                    String q = sc.nextLine();

                    System.out.print("Security Answer: ");
                    String a = sc.nextLine();

                    userService.register(role, name, email, pass, q, a);
                    System.out.println("Registration successful");
                }

                else if (choice == 2) {
                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    UserAccount user = userService.login(email, pass);
                    System.out.println("Welcome " + user.getName());

                    if ("JOB_SEEKER".equalsIgnoreCase(user.getRole())) {
                        jobSeekerMenu(sc, user.getUserId());
                    } else {
                        employerMenu(sc);
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

            int option = sc.nextInt();
            sc.nextLine();

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

            // 4. SEARCH JOBS
            else if (option == 4) {
                System.out.print("Enter Location: ");
                String loc = sc.nextLine();

                System.out.print("Enter Job Type: ");
                String type = sc.nextLine();

                List<JobPosting> jobs = jobService.searchJobs(loc, type);

                System.out.println("\n--- Search Results ---");
                for (JobPosting j : jobs) {
                    System.out.println(
                            j.getJobId() + " | " +
                            j.getTitle() + " | " +
                            j.getLocation() + " | " +
                            j.getJobType());
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
					appService.apply(jobId, seekerId, 0, cover);
				} catch (ValidationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.out.println("✅ Applied successfully");
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
                int appId = sc.nextInt();
                sc.nextLine();

                boolean ok = appService.withdraw(appId);

                if (ok) {
                    System.out.println("Application withdrawn successfully");
                } else {
                    System.out.println("Unable to withdraw application");
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
            System.out.println("4. Logout");
            System.out.print("Choose option: ");

            int option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                System.out.print("Enter Job ID: ");
                int jobId = sc.nextInt();
                sc.nextLine();

                List<Application> list =
                        appService.viewApplicantsByJob(jobId);

                System.out.println("\n--- Applicants ---");
                for (Application a : list) {
                    System.out.println(
                            "ApplicationId: " + a.getApplicationId() +
                            ", SeekerId: " + a.getSeekerId() +
                            ", Status: " + a.getStatus()
                    );
                }
            }

            else if (option == 2) {
                System.out.print("Enter Application ID to shortlist: ");
                int appId = sc.nextInt();
                sc.nextLine();

                appService.shortlist(appId);
                System.out.println("Applicant shortlisted");
            }

            else if (option == 3) {
                System.out.print("Enter Application ID to reject: ");
                int appId = sc.nextInt();
                sc.nextLine();

                appService.reject(appId);
                System.out.println("Applicant rejected");
            }

            else if (option == 4) {
                System.out.println("Logged out successfully");
                break;
            }

            else {
                System.out.println("Invalid option");
            }
        }
    }

}
