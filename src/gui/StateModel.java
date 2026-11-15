package gui;
import logic.Course;
import logic.Student;

import java.util.ArrayList;
import java.util.List;

public class StateModel {

    //instance variables are needed:
    private Course course;
    private double preGradeFactor;
    private boolean sortByGrade;

    //the following is a list containing StateObserver-objects called "observers"
    private List<StateObserver> observers;

    //constructor: no parameters needed bc every student gets the same settings
    //e.g. no differences in preGradeFactor; checkbox unticked (for each one of them)
    //otherwise, MainPane would have to do it this way: StateModel stateModel = new stateModel(0.5, false);
    public StateModel() {
        this.preGradeFactor = 0.5;
        this.observers = new ArrayList<>();

        //when checkbox is unticked:
        this.sortByGrade = false;
    }

    public void addObserver(StateObserver observer) {
        this.observers.add(observer);
    }

    private void sendStateChangedEvent() {
        for (StateObserver observer : observers) {
            observer.stateChanged();
        }
    }

    public void setCourse(Course course) {
        this.course = course;
        sortStudents();
        sendStateChangedEvent();
    }
    public Course getCourse() {
        return this.course;
    }


    public void setPreGradeFactor(double preGradeFactor) {
        this.preGradeFactor = preGradeFactor;
        sortStudents();
        sendStateChangedEvent();
    }
    public double getPreGradeFactor(){
        return this.preGradeFactor;
    }


    public void setSortByGrade(boolean sortByGrade) {
        this.sortByGrade = sortByGrade;
        sortStudents();
        sendStateChangedEvent();
    }
    //like a getter, but booleans use "is" instead of "get"
    public boolean isSortByGrade() {
        return this.sortByGrade;
    }


    //this method needs to be called in set-methods
    private void sortStudents() {

        if (course == null) return;

        List<Student> students = course.getClassList();

        if (sortByGrade) {
            students.sort((s1, s2) -> Double.compare(s2.getFinalGrade(preGradeFactor), s1.getFinalGrade(preGradeFactor)));

        } else {
            students.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
        }
    }
}
