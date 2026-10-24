package logic;

import java.util.List;

public class Course  {

    private List<Student> studentList;
    private final String id;
    private final String name;

    public Course(String id, String name, List<Student> classList){
        this.id = id;
        this.name = name;
        this.studentList = classList;
    }

    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public List<Student> getClassList(){
        return this.studentList;
    }
}
