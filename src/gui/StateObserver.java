package gui;

public interface StateObserver {

    //method that implementing classes must have as well
    //they @Override this method (customize)
    void stateChanged();
}
