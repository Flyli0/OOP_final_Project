package service;

import config.DbContext;
import model.Student;
import java.util.List;

public class ReportGenerationService {

    public static void generateAcademicReport() {
        DbContext db = DbContext.getInstance();
        List<Student> students = db.allStudent();

        System.out.println("\n=== 📊 UNIVERSITY ACADEMIC REPORT ===");
        System.out.println("Total Registered Students: " + students.size());
        System.out.println("Total Created Courses: " + db.allCourses().size());
        System.out.println("Total Active Enrollments: " + db.allEnrollments().size());

        if (students.isEmpty()) {
            System.out.println("\n[!] No students in the database yet.");
            System.out.println("=====================================\n");
            return;
        }

        double totalGpa = 0.0;
        double maxGpa = -1.0;
        double minGpa = 100.0; // Ставим с запасом, чтобы найти минимум
        Student topStudent = null;
        Student worstStudent = null;

        // Пробегаемся по всем студентам в базе
        for (Student s : students) {
            double gpa = s.getTranscript().calculateTotalGPA();
            totalGpa += gpa;

            // Ищем отличника
            if (gpa > maxGpa) {
                maxGpa = gpa;
                topStudent = s;
            }
            // Ищем отстающего
            if (gpa < minGpa) {
                minGpa = gpa;
                worstStudent = s;
            }
        }

        double avgGpa = totalGpa / students.size();

        System.out.println("\n--- Academic Performance ---");
        System.out.printf("Average University GPA: %.2f\n", avgGpa);

        if (topStudent != null) {
            System.out.printf("🏆 Top Student: %s %s (GPA: %.2f)\n", topStudent.getFirstName(), topStudent.getLastName(), maxGpa);
        }
        if (worstStudent != null) {
            System.out.printf("⚠️ Lowest GPA: %s %s (GPA: %.2f)\n", worstStudent.getFirstName(), worstStudent.getLastName(), minGpa);
        }

        System.out.println("=====================================\n");
    }
}