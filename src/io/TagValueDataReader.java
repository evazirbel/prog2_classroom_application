package io;

import logic.Course;
import logic.RegularStudent;
import logic.RepeatingStudent;
import logic.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TagValueDataReader implements CourseDataReader {

    // class variables
    private File courseDataFile;
    private Map<String, String> majorMap;

    // constructor with fileName & majorMap
    public TagValueDataReader(String fileName, Map<String, String> majorMap) {
        this.courseDataFile = new File(fileName);
        this.majorMap = majorMap;
    }

    // adjustments: annoyed of redundancy
    // I created the method: commitCurrentStudent
    public Optional<Course> readCourseData(){
        List<Student> studentList = new ArrayList<>();
        try (Scanner in = new Scanner(courseDataFile)) {
            String courseId = "";
            String courseName = "";

            if (in.hasNextLine()) {
                courseId = in.nextLine().split(":")[1].trim();
            }

            if (in.hasNextLine()) {
                courseName = in.nextLine().split(":")[1].trim();
            }

            //local variables:
            String currentName = "";
            String currentMajor = "";
            boolean currentIsRepeating = false;
            double currentExamGrade = 0.0;
            List<Double> currentGrades = new ArrayList<>();

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] tokens = line.split(":");

                if (tokens.length >= 2){
                    String key = tokens[0].trim();
                    String value = tokens[1].trim();

                    //just learned sth new: switch as an elegant way of if-else
                    switch (key) {
                        case "name" -> {
                            addCurrentStudent(studentList, currentName, currentMajor, currentIsRepeating, currentExamGrade, currentGrades);
                            currentName = value;
                            currentMajor = "";
                            currentIsRepeating = false;
                            currentExamGrade = 0.0;
                            currentGrades = new ArrayList<>();
                            }
                        case "major" -> currentMajor = majorMap.get(value);
                        case "is_repeating" -> currentIsRepeating = Boolean.parseBoolean(value);
                        case "exam-grade" -> currentExamGrade = Double.parseDouble(value);
                        case "pre-grade" -> {
                            String[] gradeTokens = value.split(",");
                            for (String token : gradeTokens) {
                                currentGrades.add(Double.parseDouble(token));
                            }
                        }
                    }
                }
            }
            addCurrentStudent(studentList, currentName, currentMajor, currentIsRepeating, currentExamGrade, currentGrades);

            Course course = new Course(courseId, courseName, studentList);
            return Optional.of(course);
        } catch (FileNotFoundException e) {
            System.err.println("Fehler beim Lesen der Daten: " + e.getMessage());
            return Optional.empty();
        }
    }

    private void addCurrentStudent(List<Student> list, String name, String major, boolean isRepeating, double examGrade, List<Double> grades) {
        if (name.isEmpty()) return;
        if (isRepeating) {
            list.add(new RepeatingStudent(name, major, examGrade));
        } else {
            list.add(new RegularStudent(name, major, examGrade, new ArrayList<>(grades)));
        }
    }
}

