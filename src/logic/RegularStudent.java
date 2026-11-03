
package logic;

import java.util.ArrayList;
import java.util.List;

public class RegularStudent extends Student {
    private double examGrade;
    private List<Double> grades;

    public RegularStudent(String name, String major, double examGrade, List<Double> grades) {
        super(name, major);
        this.examGrade = examGrade;
        this.grades = grades;
    }

    private double computeGradeAverage(){
        double sum = 0;
        double result = 0;
        if(this.grades.isEmpty()){
            return 0.00;
        } else if (this.grades.size() == 1) {
            return grades.get(0);
        } else {
            double worstGrade = findMinimum();
            for (Double grade : grades) {
                sum += grade;
            }
            return (sum - worstGrade) / (grades.size() - 1);
        }
    }

    private double findMinimum(){
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

    @Override
    public double getFinalGrade(double preGradeFactor){
        return (computeGradeAverage() * preGradeFactor) + (this.examGrade * (1.0 - preGradeFactor));
    }
}
