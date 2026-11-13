package gui;
import logic.Course;

import java.util.ArrayList;
import java.util.List;

public class StateModel {
    private Course course;
    private double preGradeFactor;
    private boolean sortByGrade;

    //this is a list containing StateObserver-objects called "observers"
    private List<StateObserver> observers;

    public StateModel() {
        this.preGradeFactor = 0.5;
        this.sortByGrade = false;
        this.observers = new ArrayList<>();

    }

    public void addObserver(StateObserver observer){
        this.observers.add(observer);
    }

    private void sendStateChangedEvent(){
        for (StateObserver observer : observers) {
            observer.stateChanged();
        }
    }

    public void setCourse(Course course){
        this.course = course;
        sendStateChangedEvent();
    }
    public Course getCourse(){
        return this.course;
    }

    public void setPreGradeFactor(double preGradeFactor){
        this.preGradeFactor = preGradeFactor;
        sendStateChangedEvent();
    }
    public double getPreGradeFactor(){
        return this.preGradeFactor;
    }

    public void setSortByGrade(boolean sortByGrade){
        this.sortByGrade = sortByGrade;
        sendStateChangedEvent();
    }
    public boolean isSortByGrade(){
        return this.sortByGrade;
    }
}
