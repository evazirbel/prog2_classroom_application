package gui;

import io.CourseDataReader;
import io.CsvDataReader;
import io.TagValueDataReader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import logic.Course;
import logic.Student;
import java.io.File;
import java.util.Map;
import java.util.Optional;


public class ControlPane extends StackPane {
    private Course course;
    public ControlPane(Map<String, String> majorMap, GraphicsPane graphicsPane) {

        //create several widgets
        Button loadButton = new Button("Load Data...");
        loadButton.getStyleClass().add("primary-button");

        Label numberLabel = new Label("Number of Students: ");
        Label numberLabelValue = new Label("0");
        Label preGradeFactorLabel = new Label("Pre Grade Factor: ");

        Slider preGradeFactorSlider = new Slider(0, 1, 0.5);
        preGradeFactorSlider.getStyleClass().add("slider-preGradeFactor");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.getStyleClass().add("text-area");

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

            if (selectedFile != null) { //checking whether format is correct
                CourseDataReader cdr;

                if (selectedFile.getName().endsWith(".txt")) {
                    cdr = new TagValueDataReader(selectedFile.getPath(), majorMap);

                } else if (selectedFile.getName().endsWith(".csv")) {
                    cdr = new CsvDataReader(selectedFile.getPath(), majorMap);

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to read student data, sorry: " + selectedFile + "Do you have a '.txt' or '.csv' format?");
                    alert.showAndWait(); //created an alert & used method to display I guess?
                    return;
                }

                Optional<Course> myCourse = cdr.readCourseData();
                if (myCourse.isPresent()) { //check whether box contains sth, if empty skip
                    this.course = myCourse.get();

                    //the slider's value will be used, so cool:
                    refreshText(numberLabelValue, textArea, preGradeFactorSlider.getValue());

                    //"send" students to GraphicsPane:
                    graphicsPane.setStudents(this.course.getClassList());


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to read student data, sorry: " + selectedFile + "Do you have a '.txt' or '.csv' format?");
                    alert.showAndWait(); //created an alert & used method to display I guess?
                    return;
                }
            }
        });
        preGradeFactorSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            preGradeFactorLabel.setText(String.format("Pre Grade Factor: %.1f", newValue.doubleValue()));
            refreshText(numberLabelValue, textArea, preGradeFactorSlider.getValue());
            graphicsPane.setPreGradeFactor(newValue.doubleValue());
        });
        this.getChildren().add(mainBox);
    }

    // the update-method: reacts as soon as sth happens(e.g. click)
    private void refreshText(Label numberLabelValue, TextArea textArea, double preGradeFactor) {
        if (course == null) {
            return;
        }

        numberLabelValue.setText(String.valueOf(course.getClassList().size()));
        String result = "";
        for (Student student : course.getClassList()) {
            double grade = student.getFinalGrade(preGradeFactor);
            String formattedGrade = String.format("%.1f", grade);
            result += student + ": " + formattedGrade + "\n";
        }
        textArea.setText(result);
    }
}
