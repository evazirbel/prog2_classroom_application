// je nach preGradeFactor kann eine finale Note schlechter werden --> erwünscht?

package gui;

import io.CourseDataReader;
import io.CsvDataReader;
import io.TagValueDataReader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import logic.Course;
import logic.Student;
import java.io.File;
import java.util.Map;
import java.util.Optional;

public class MainPane extends Pane {
    private Map<String, String> majorMap;
    private Course course;

    public MainPane(Map<String, String> majorMap) {
        this.majorMap = majorMap;

        //create several widgets
        Button loadButton = new Button("Load Data...");
        Label numberLabel = new Label("Number of Students: ");
        Label numberLabelValue = new Label("0");
        Label preGradeFactorLabel = new Label("Pre Grade Factor: ");
        Slider preGradeFactorSlider = new Slider(0, 1, 0);
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        //horizontal box to display in same line, distance 5 pixels to next box
        HBox numberBox = new HBox(5);
        numberBox.getChildren().addAll(numberLabel, numberLabelValue);

        // this box contains alllll the other stuff
        VBox mainBox = new VBox(10);
        mainBox.getChildren().addAll(
                loadButton,
                numberBox,
                textArea,
                preGradeFactorLabel,
                preGradeFactorSlider
        );

        // action! (still inside constructor)
        loadButton.setOnAction(event -> { //as soon as it gets clicked, sth. will happen
            FileChooser fileChooser = new FileChooser(); //a window will pop up & user will have to do sth:
            File selectedFile = fileChooser.showOpenDialog(null); //user will have to choose a file
            if(selectedFile != null) { //checking whether format is correct
                CourseDataReader cdr;

                if(selectedFile.getName().endsWith(".txt")) {
                    cdr = new TagValueDataReader(selectedFile.getPath(), majorMap);

                }else if(selectedFile.getName().endsWith(".csv")) {
                    cdr = new CsvDataReader(selectedFile.getPath(), majorMap);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to read student data, sorry: " + selectedFile + "Do you have a '.txt' or '.csv' format?");
                    alert.showAndWait(); //created an alert & used method to display I guess?
                    return;
                }

                Optional<Course> myCourse = cdr.readCourseData();
                if (myCourse.isPresent()) { //check whether box contains sth, if empty skip
                    this.course = myCourse.get();
                    refreshText(numberLabelValue, textArea, preGradeFactorSlider.getValue()); //slider's value will be used, so cool
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to read student data, sorry: " + selectedFile + "Do you have a '.txt' or '.csv' format?");
                    alert.showAndWait(); //created an alert & used method to display I guess?
                    return;
                    }
                }
        });
        preGradeFactorSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            preGradeFactorLabel.setText("Pre Grade Factor: " + String.valueOf(newValue));
            refreshText(numberLabelValue, textArea, preGradeFactorSlider.getValue());
        });
        //give this construction box to Pane:
        this.getChildren().addAll(mainBox);
    }

    private void refreshText(Label countLabel, TextArea textArea, double preGradeFactor){
        if(course == null){
            return;
        }

        countLabel.setText(String.valueOf(course.getClassList().size()));
        String result = "";
        for(Student student : course.getClassList()){
            double grade = student.getFinalGrade(preGradeFactor);
            result += student + ": " + grade + "\n";
        }
        textArea.setText(result);
    }
}
