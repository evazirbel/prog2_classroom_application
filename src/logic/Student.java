//alternative1: student --> interface
//alternative2: gradingStrategy as interface between student&reg/repeatingStudent
//-> only 1 method: getFinalGrade
//Student: private final GradingStrategy --> gradingStrategy bei Erstellen mitgeben


package logic;

//abstract class
public abstract class Student {

    private final String name;
    private String major;

    public Student(String name, String major){
        this.name = name;
        this.major = major;
    }


    public String getName(){
        return this.name;
    }

    public String getMajor() {
        return this.major;
    }

    //needs to be written here as abstract method
    //in the real classes they have to be declared as well
    public abstract double getFinalGrade(double PreGradeFactor);

    @Override
    public String toString(){
        return getName();
    }
}
