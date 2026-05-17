package service;


/**
 * 
 */

import config.DbContext;
import model.Course;
import model.Enrollment;
import model.Mark;
import model.Student;
import model.Teacher;
import model.Transcript;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MarkPuttingService {

    private static final BufferedReader br = 
    new BufferedReader(new InputStreamReader(System.in));



    public void putMarksForCourse(Teacher teacher, Course course) {
        Enrollment enrollment = EnrollmentService.getEnrollment(course);
        if (enrollment == null) {
            System.out.println("No open enrollment found for course: " + course.getCourseName());
            return;
        }

        // Verify teacher is assigned
        if (!enrollment.getInstructors().contains(teacher)) {
            System.out.println("You are not assigned to this course.");
            return;
        }

        HashMap<Student, Boolean> students = enrollment.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students enrolled in " + course.getCourseName());
            return;
        }

        System.out.println("=== Entering marks for: " + course.getCourseName() + " ===");
        for (Student student : students.keySet()) {
            Boolean approved = students.get(student);
            if (!approved) {
                System.out.println("Skipping " + student.getFirstName()
                        + " " + student.getLastName() + " (not approved).");
                continue;
            }
            putMarkForStudent(teacher, student, course);
        }

        // Persist changes
        DbContext.saveStudents();
        System.out.println("All marks saved.");
    }

    public void putMarkForStudent(Teacher teacher, Student student, Course course) {
        System.out.println("─── Student: "
                + student.getFirstName() + " " + student.getLastName() + " ───");
        try {
            double att1   = promptDouble("  1st Attestation (0-30): ", 0, 30);
            double att2   = promptDouble("  2nd Attestation (0-30): ", 0, 30);
            double finalE = promptDouble("  Final Exam      (0-40): ", 0, 40);

            double total = att1 + att2 + finalE;
            Mark mark = new Mark(total, new Date());

            // Record in transcript
            String semester = getCurrentSemester();
            student.getTranscript().addEntry(course, mark, semester);

            // Also delegate to Teacher.putMark for any extra side-effects
            teacher.putMark(student, course, mark);

            System.out.printf("  → Total: %.1f  (%s)%n", total, letterGrade(total));

            // Enforce fail counter rule: fail = total < 50
            if (total < 50) {
                System.out.println("  ⚠ Student failed this course.");
            }

        } catch (Exception e) {
            System.out.println("Input error: " + e.getMessage());
        }
    }

    public void putMark(Teacher teacher, Student student, Course course,
                        double att1, double att2, double finalExam) {
        validateRange(att1,    0, 30, "1st attestation");
        validateRange(att2,    0, 30, "2nd attestation");
        validateRange(finalExam, 0, 40, "Final exam");

        double total = att1 + att2 + finalExam;
        Mark mark = new Mark(total, new Date());
        student.getTranscript().addEntry(course, mark, getCurrentSemester());
        teacher.putMark(student, course, mark);

        System.out.printf("Mark recorded: %s → %s | %.1f (%s)%n",
                student.getFirstName() + " " + student.getLastName(),
                course.getCourseName(),
                total,
                letterGrade(total));

        DbContext.saveStudents();
    }


    public static String letterGrade(double total) {
        if (total >= 95) return "A  (4.0)";
        if (total >= 90) return "A- (3.7)";
        if (total >= 85) return "B+ (3.3)";
        if (total >= 80) return "B  (3.0)";
        if (total >= 75) return "B- (2.7)";
        if (total >= 70) return "C+ (2.3)";
        if (total >= 65) return "C  (2.0)";
        if (total >= 60) return "C- (1.7)";
        if (total >= 55) return "D+ (1.3)";
        if (total >= 50) return "D  (1.0)";
        return "F  (0.0)";
    }

    /** Returns a simple semester string like "Spring 2026". */
    private static String getCurrentSemester() {

        java.util.Calendar cal = java.util.Calendar.getInstance();
        int month = cal.get(java.util.Calendar.MONTH); // 0-based
        int year  = cal.get(java.util.Calendar.YEAR);
        String term = (month >= 1 && month <= 5) ? "Spring"
                    : (month >= 8 && month <= 11) ? "Fall"
                    : "Summer";
        return term + " " + year;

    }

    /** Prompts the user for a double within [min, max], re-asking on bad input. */
    private double promptDouble(String prompt, double min, double max) throws Exception {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(br.readLine().trim());
                if (val < min || val > max) {
                    System.out.printf("  Value must be between %.0f and %.0f.%n", min, max);
                } else {
                    return val;
                }
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    private static void validateRange(double val, double min, double max, String label) {
        if (val < min || val > max) {
            throw new IllegalArgumentException(
                label + " must be between " + min + " and " + max + ", got " + val);
        }
    }
}
