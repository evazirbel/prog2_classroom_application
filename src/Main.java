// aufräumen: alles unnötige aus Main methode raus--> in packages
// alles bzgl. schreiben/lesen --> in io
// course und student sind logic bzw. domain-logic (völlig unabhängig, auch vom gui)
// 3-schichten-architektur wäre das Ziel: io, logic, gui
// achtung: testdaten --> diese unbedingt mit abgeben

import io.CourseDataReader;
import io.MajorMapReader;
import io.CourseReportWriter;
import logic.Course;
import logic.Student;

import java.util.Optional;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        String[] fileNames = {"grades-v02b.txt", "grades-test-data.txt"};

        MajorMapReader mapReader = new MajorMapReader("major-map.txt");
        Map<String, String> majorMap = mapReader.readMap();

        for(String fileName : fileNames) {
             CourseDataReader cdr = new CourseDataReader(fileName, majorMap);
             Optional<Course> myCourse = cdr.readCourseData();
             if(myCourse.isPresent()) {
                 Course course = myCourse.get();
                 List<Student> myClass = course.getClassList();
                 System.out.println("Grades for: " + course.getName() + " (" + course.getId() + ")");
                 for(Student student : myClass) {
                     student.displayStudentInfo();
                 }
                 CourseReportWriter crw = new CourseReportWriter("grades-report.txt");
                 crw.printReport(course);
             }else{
                 System.out.println("Unable to read student data, sorry: " + fileName);
             }
        }
    }
}


