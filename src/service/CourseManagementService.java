package service;

import java.io.*;
import java.util.*;

import model.Course;

/**
 * 
 */
public class CourseManagementService {

    /**
     * Default constructor
     */
    public CourseManagementService() {
    }

    /**
     * 
     */
    private List<Course> courses;




    /**
     * @param Course c 
     * @return
     */
    public void addCourse(Course c) {
        // TODO implement here
        courses.add(c);
    }

    /**
     * @param Course c 
     * @return
     */
    public void removeCourse(Course c) {
        // TODO implement here
        courses.remove(c);
       
    }

    /**
     * @return
     */
    public List<Course> showCourses() {
        // TODO implement here
        return courses;
    }

}