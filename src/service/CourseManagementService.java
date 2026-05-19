package service;

import config.DbContext;
import model.Course;
import model.Student;
import model.Teacher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class CourseManagementService {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static DbContext db = DbContext.getInstance();


    public static void addCourseForRegistration() throws IOException {
        System.out.println("\n--- 📚 CREATE NEW COURSE ---");
        System.out.print("Enter Course Name: ");
        String name = br.readLine();

        System.out.print("Enter Credits Amount: ");
        int credits = Integer.parseInt(br.readLine());

        System.out.print("Enter Target Major (e.g. SITE, BS): ");
        String major = br.readLine();

        System.out.print("Enter Year of Study (1-4): ");
        int year = Integer.parseInt(br.readLine());

        Course newCourse = new Course(name, credits, major, year);
        db.addCourse(newCourse);
        System.out.println("✅ Success: Course '" + name + "' added for registration!");
    }


    public static void assignTeacherToCourse(Course course, Teacher teacher, String lessonType) {
        if (course != null && teacher != null) {
            course.addTeacher(teacher);
            System.out.println("✅ Success: Teacher " + teacher.getFirstName() + " assigned to " + course.getCourseName() + " for " + lessonType);
        } else {
            System.out.println("❌ Error: Course or Teacher not found.");
        }
    }


    public static boolean canStudentRegister(Student student, Course course, String studentMajor, int studentYear) {
        System.out.println("\n--- Checking Registration Constraints ---");


        if (course.getTargetMajor() != null && !course.getTargetMajor().equalsIgnoreCase(studentMajor) && course.getType() != Course.CourseType.FREE) {
            System.out.println("❌ Registration Failed: This course is for " + course.getTargetMajor() + " major.");
            return false;
        }


        int currentCredits = student.getCreditsNum();
        if (currentCredits + course.getCredits() > 21) {
            System.out.println("❌ Registration Failed: Exceeds maximum 21 credits! (Current: " + currentCredits + ")");
            return false;
        }

        System.out.println("✅ Student is eligible to register for " + course.getCourseName());
        return true;
    }
}