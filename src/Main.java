import io.*;
import logic.Course;
import logic.Student;
import java.util.Optional;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        String[] fileNames = {"grades-v03.txt", "grades-test-data.txt", "grades-v04.txt"};

        MajorMapReader mapReader = new MajorMapReader("major-map.txt");
        Map<String, String> majorMap = mapReader.readMap();

        for(String fileName : fileNames) {
            CourseDataReader cdr;

            if (fileName.equals("grades-v04.txt")) {
                cdr = new TagValueDataReader(fileName, majorMap);
            } else {
                cdr = new CsvDataReader(fileName, majorMap);
            }

             Optional<Course> myCourse = cdr.readCourseData();
             if(myCourse.isPresent()) { //check whether box contains sth, if empty skip
                 Course course = myCourse.get(); //look inside the box & name it as object "course"
                 List<Student> myClass = course.getClassList();
                 System.out.println("Grades for: " + course.getName() + " (" + course.getId() + ")");

                 double preGradeFactor = 0.3;
                 for(Student student : myClass){
                     double finalGrade = student.getFinalGrade(preGradeFactor);
                     double roundedGrade = Math.round(finalGrade * 10.0) / 10.0;
                     System.out.println("The final grade for " + student + " (" + student.getMajor() + ") is: " + roundedGrade);
                 }

                 CourseReportWriter crw = new CourseReportWriter("grades-report.txt");
                 crw.printReport(course, preGradeFactor);
             }else{
                 System.out.println("Unable to read student data, sorry: " + fileName);
             }
        }
    }
}


