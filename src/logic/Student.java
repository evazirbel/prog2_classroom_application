package logic;

public abstract class Student {
    public Student(String name, String major){
        this.name = name;
        this.major = major;
    }
    private final String name;
    private String major;

    public String getName(){
        return this.name;
    }

    public String getMajor() {
        return this.major;
    }

    public abstract double getFinalGrade(double PreGradeFactor);

    @Override
    public String toString(){
        return getName();
    }
}
