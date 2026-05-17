package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.DbContext;
import model.Enrollment;
import model.Lesson;
import model.LessonType;
import model.ScheduleEntry;
import model.Student;
import model.Teacher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScheduleCreatingService {

    
    private static final Map<String, Integer> ROOM_CAPACITY = new HashMap<>();


    static {
        ROOM_CAPACITY.put("LEC-101", 120);
        ROOM_CAPACITY.put("LEC-202", 80);
        ROOM_CAPACITY.put("LEC-303", 60);
        

        ROOM_CAPACITY.put("LAB-104", 30);
        ROOM_CAPACITY.put("LAB-105", 30);
        ROOM_CAPACITY.put("SEM-201", 25);
        ROOM_CAPACITY.put("SEM-202", 25);
    }

    private final Map<String, List<Date>>  roomOccupancy = new HashMap<>();

    public ScheduleCreatingService(){
        for (String room : ROOM_CAPACITY.keySet()) {
            roomOccupancy.put(room, new ArrayList<>());
        }
    }
    
    public void printTeacherSchedule(Teacher teacher) {
        // TODO implement here
        List<ScheduleEntry> schedule = teacher.getSchedule();
        if(schedule.isEmpty()) {
            System.out.println(teacher.getFirstName() + " has no scheduled lessons");
            return;
        }

        System.out.println("=== Schedule of " + teacher.getFirstName() 
        + " " + teacher.getLastName() + " ===");

        for (ScheduleEntry entry : schedule) {
            Lesson l = entry.getLesson();
            System.out.println("  Cours: " + l.getCourse().getCourseName());

            System.out.println("  Room: " + l.getRoom()
                    + "  [" + l.getType() + "]");

            for (Date d : entry.getDays()) {
                System.out.println("  Time: " + d);
            }
        }

    }


    //private helper funcs
    private boolean sameHourSlot(Date a, Date b) {
        Calendar ca = Calendar.getInstance();
        Calendar cb = Calendar.getInstance();
        ca.setTime(a);
        cb.setTime(b);
        return ca.get(Calendar.YEAR)         == cb.get(Calendar.YEAR)
            && ca.get(Calendar.DAY_OF_YEAR)  == cb.get(Calendar.DAY_OF_YEAR)
            && ca.get(Calendar.HOUR_OF_DAY)  == cb.get(Calendar.HOUR_OF_DAY);
    }


    private ScheduleEntry findOrCreateEntry(List<ScheduleEntry> schedule, Lesson lesson) {
        for (ScheduleEntry se : schedule) {
            if (se.getLesson() == lesson) return se;
        }
        ScheduleEntry newEntry = new ScheduleEntry(lesson);
        schedule.add(newEntry);
        return newEntry;
    }

    private boolean roomMatchesType(String room, LessonType type) {
        if (type == LessonType.LECTURE) {
            return room.startsWith("LEC");
        }
        // PRACTICE → LAB or SEM
        return room.startsWith("LAB") || room.startsWith("SEM");
    }

    private boolean isRoomFree(String room, Date time) {
        List<Date> booked = roomOccupancy.getOrDefault(room, new ArrayList<>());
        for (Date d : booked) {
            // Same hour = conflict (lessons are 1-hour slots)
            if (sameHourSlot(d, time)) return false;
        }
        return true;
    }

   private boolean isValidSlot(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int dow  = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        boolean weekday = (dow >= Calendar.MONDAY && dow <= Calendar.FRIDAY);
        boolean inHours = (hour >= 8 && hour < 19);
        return weekday && inHours;
    }

    private String findFreeRoom(LessonType type) {
        for (String room : ROOM_CAPACITY.keySet()) {
            if (roomMatchesType(room, type)) return room;
        }
        return null;
    }


    // Attaching the lesson to every approved, enrolled student's schedule. 
    private void attachToStudents(Lesson lesson, Date time) {
        List<Enrollment> enrollments = DbContext.getInstance().allEnrollments();
        for (Enrollment en : enrollments) {
            if (!en.getCourse().equals(lesson.getCourse())) continue;
            for (Map.Entry<Student, Boolean> entry : en.getStudents().entrySet()) {
                if (!entry.getValue()) continue; // not approved
                Student student = entry.getKey();
                ScheduleEntry se = findOrCreateEntry(student.getSchedule(), lesson);
                se.addDay(time);
            }
        }
    }


    //Adds a lesson to the schedule of its instructor and all enrolled students.
    public boolean addLesson(Lesson lesson) {
        String room = lesson.getRoom();
        Date time   = lesson.getTime();

        if (room == null || time == null) {
            System.out.println("Lesson must have a room and a time.");
            return false;
        }

        //Check room type matches lesson type
        if (!roomMatchesType(room, lesson.getType())) {
            System.out.println("Room " + room + " is not suitable for "
                    + lesson.getType() + " lessons.");
            return false;
        }

        //Check room is free at that time
        if (!isRoomFree(room, time)) {
            System.out.println("Room " + room + " is already booked at "
                    + time + ". Choose another slot.");
            return false;
        }

        //Validate weekday & business hour
        if (!isValidSlot(time)) {
            System.out.println("Lessons can only be scheduled on weekdays between 08:00 and 19:00.");
            return false;
        }

        //Book the room
        roomOccupancy.get(room).add(time);

        //Attach ScheduleEntry to teacher
        Teacher instructor = lesson.getInstructor();
        if (instructor != null) {
            ScheduleEntry entry = findOrCreateEntry(instructor.getSchedule(), lesson);
            entry.addDay(time);
            System.out.println("Lesson \"" + lesson.getTitle()
                    + "\" scheduled for " + instructor.getFirstName()
                    + " " + instructor.getLastName()
                    + " in " + room + " at " + time);
        }

        //Attach ScheduleEntry to enrolled students
        attachToStudents(lesson, time);

        return true;
    }



}