package logic;

import java.util.List;

public class Course {
    private final String ID; //should never be changed
    private String name;
    private List<Student> studentList;

    public Course(String id, String name, List<Student> classList){
        this.ID = id;
        this.name = name;
        this.studentList = classList;
    }

    public String getId(){
        return this.ID;
    }
    public String getName(){
        return this.name;
    }
    public List<Student> getClassList(){
        return this.studentList;
    }
}
