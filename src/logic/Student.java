
package logic;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String major;
    private List<Double> grades;

    // multiple constructors in case a student has no grades yet
    public Student(String name, String major){
        this.name = name;
        this.major = major;
        this.grades = new ArrayList<>();
    }
    public Student(String name, String major, List<Double> grades){
        this.name = name;
        this.major = major;
        this.grades = grades;
    }

    public String getName(){
        return this.name;
    }
    public String getMajor(){
        return this.major;
    }
    public List<Double> getGrades(){
        return this.grades;
    }

    public double computeAverage(){
        double sum = 0;
        double result = 0;

        if(this.grades.isEmpty()){
            return 0.00;
        } else if (this.grades.size() == 1) {
            return grades.get(0);
        } else {
            double worstGrade = findMinimum();
            for (Double grade : grades) {  //enhanced for loop used in ArrayList
                sum += grade;
            }
            return (sum - worstGrade) / (grades.size() - 1);
        }
    }

    public double findMinimum(){
        if(this.grades.isEmpty()){
            return 0.00;
        }
        double minimum = grades.get(0);
        for(int i = 1; i < grades.size(); i++){
            if(minimum > grades.get(i)) {
                minimum = grades.get(i);
            }
        }
        return minimum;
    }

    public void displayStudentInfo(){
        double roundedAverage = Math.round(this.computeAverage()* 10.0) / 10.0;
        System.out.println("The average grade for " + this.name + " (" + this.major + ") is: " + roundedAverage);
    }
}
