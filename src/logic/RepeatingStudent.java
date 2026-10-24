
package logic;

public class RepeatingStudent extends Student {
    private double examGrade;
    public RepeatingStudent(String name, String major, double examGrade) {
        super(name, major);
        this.examGrade = examGrade;
    }

    @Override
    public double getFinalGrade(double preGradeFactor){
        return examGrade;
    }

    @Override
    public String toString(){
        return super.toString() + "*";
    }

}
