package io;

import logic.Course;
import logic.Student;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CourseDataReader {

    // class variables
    private File courseDataFile;
    private Map<String, String> majorMap;

    // constructor with fileName & majorMap
    public CourseDataReader(String fileName, Map<String, String> majorMap) {
        this.courseDataFile = new File(fileName);
        this.majorMap = majorMap;
    }

    // method moved from main to io
    public Optional<Course> readCourseData(){
        List<Student> studentList = new ArrayList<>();
        try(Scanner in = new Scanner(courseDataFile)){
            String courseId = "";
            String courseName = "";
            if(in.hasNextLine()) {
                courseId = in.nextLine().trim();
            }
            if(in.hasNextLine()) {
                courseName = in.nextLine().trim();
            }
            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] tokens = line.split(",");

                if(tokens.length >= 3){
                    String studentName = tokens[0].trim();
                    String studentMajor = majorMap.get(tokens[1].trim());

                    List<Double> studentGrades = new ArrayList<>();
                    for(int i = 2; i < tokens.length; i++) {
                        double grade = 0; // set =0 to avoid NullPointerException
                        try{
                            grade = Double.parseDouble(tokens[i].trim()); // txts: contain Strings that need to be parsed
                            studentGrades.add(grade);
                        } catch (NumberFormatException e) {
                            System.err.println("Fehler bei: " + studentName + ": " + e.getMessage()); //System.err -> highlighted in terminal
                            return Optional.empty();
                        }
                    }
                    Student student = new Student(studentName, studentMajor, studentGrades);
                    studentList.add(student);
                }

            }
            Course course = new Course(courseId, courseName, studentList);
            return Optional.of(course);
        } catch (FileNotFoundException e){
            System.err.println("Fehler beim Lesen der Daten: " + e.getMessage());
            return Optional.empty();
        }
    }
}

