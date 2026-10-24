package io;

import logic.Course;
import logic.RegularStudent;
import logic.RepeatingStudent;
import logic.Student;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CsvDataReader implements CourseDataReader {

    // class variables
    private File courseDataFile;
    private Map<String, String> majorMap;

    // constructor with fileName & majorMap
    public CsvDataReader(String fileName, Map<String, String> majorMap) {
        this.courseDataFile = new File(fileName);
        this.majorMap = majorMap;
    }

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

                if(tokens.length >= 4){
                    String studentName = tokens[0].trim();
                    String studentMajor = majorMap.get(tokens[1].trim());
                    String isRepeating = tokens[2].trim();

                    double examGrade = 0.0;
                    try {
                        examGrade = Double.parseDouble(tokens[3].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Fehler bei Prüfungsnote von: " + studentName);
                        return Optional.empty();
                    }

                    Student student;
                    if (isRepeating.equals("r")) {
                        student = new RepeatingStudent(studentName, studentMajor, examGrade);
                    } else {
                        List<Double> studentGrades = new ArrayList<>();
                        for (int i = 4; i < tokens.length; i++) {
                            try {
                                studentGrades.add(Double.parseDouble(tokens[i].trim()));
                            } catch (NumberFormatException e) {
                                System.err.println("Fehler bei Übungsnote von: " + studentName);
                                return Optional.empty();
                            }
                        }
                        student = new RegularStudent(studentName, studentMajor, examGrade, studentGrades);
                    }
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

