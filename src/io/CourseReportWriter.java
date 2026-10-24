package io;

import logic.Course;
import logic.Student;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CourseReportWriter {
    private File reportFile;

    public CourseReportWriter(String fileName) {
        this.reportFile = new File(fileName);
    }

    public void printReport(Course course, double preGradeFactor){
        try(PrintWriter writer = new PrintWriter(reportFile)){
            writer.println(course.getName() + " (" + course.getId() + ")");
            for(Student student : course.getClassList()){
                String formattedAverage = String.format("%.2f", student.getFinalGrade(preGradeFactor));
                writer.println(student + ": " + formattedAverage);
            }
        }catch (FileNotFoundException e){
            System.err.println("Fehler beim Erstellen des Reports: " + e.getMessage());
        }
    }
}
