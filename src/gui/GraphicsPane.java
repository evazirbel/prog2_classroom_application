//balken sortieren nach grösse, soll auf seite der graphicspane passieren
//problem dabei: ich gebe graphics pane der control pane mit
//gleichzeitig in die andere richtung geht das nicht
//mit checkbox "sort by grade" soll das gelöst werden
//noten links müssen mit geändert werden
//das geht mit einer state klasse StateModel
    //private List <StateHandlerList>
    //im konstruktor die list initialisieren mit new
    //methode addStateHandler(Statehandler statehandler){statehandlerList.add(statehandler)}
    //wenn sich was in der setMethode ändert:
    //interface, zb statehandler: methode stateChanged();
    //diese mit einer void methode notify über for-each: stateHandler.stateChanged();

//in MainPane statemodel erzeugen
//dieses dann je den anderen beiden klassen mitgeben

//nächste version: sogar mit mouse etwas verändern

package gui;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import logic.Student;
import java.util.ArrayList;
import java.util.List;

public class GraphicsPane extends Pane implements StateObserver {
    private double preGradeFactor;
    private List<Student> students;
    private static final double THRESHOLD = 4;
    private StateModel stateModel;

    //constructor
    public GraphicsPane(StateModel stateModel) {
        this.students = new ArrayList<>();
        this.preGradeFactor = 0.5;
        this.stateModel = stateModel;
        stateModel.addObserver(this);

        //as the window gets smaller/bigger the graphic must adjust(listener):
        widthProperty().addListener((observable, oldValue, newValue) -> drawGraphic());
        heightProperty().addListener((observable, oldValue, newValue) -> drawGraphic());
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        drawGraphic();
    }

    public void setPreGradeFactor(double preGradeFactor) {
        //adjustments with slider lead to different grades
        this.preGradeFactor = preGradeFactor;

        //so a new picture will be drawn
        drawGraphic();
    }

    @Override
    public void stateChanged() {
        if (stateModel.getCourse() != null) {
            this.students = stateModel.getCourse().getClassList();
            this.preGradeFactor = stateModel.getPreGradeFactor();
            drawGraphic();
        }
    }

    private void drawGraphic(){
        //necessary so there is always just 1 box per student instead of multiple ones overlaying:
        getChildren().clear();

        if (students.isEmpty()){
            return; //to avoid problems
        }

        double margin = 30.0;
        double barWidth = (getWidth() - 2 * margin) / students.size();

        for (int i = 0; i < students.size(); i++){

            Student student = students.get(i);
            double barHeight = student.getFinalGrade(preGradeFactor) / 6 * (this.getHeight() - 2 * margin);
            double x = margin + barWidth * i;
            double y = getHeight() - margin - barHeight;

            //create gradeBars
            Rectangle gradeBar = new Rectangle(x, y, barWidth, barHeight);
            gradeBar.setStroke(Color.BLACK);
            if (student.getFinalGrade(preGradeFactor) >= THRESHOLD){
                gradeBar.setFill(javafx.scene.paint.Color.CORNFLOWERBLUE);
            }else{
                gradeBar.setFill(javafx.scene.paint.Color.RED);
            }
            getChildren().add(gradeBar);

            String grade = String.format("%.1f", student.getFinalGrade(preGradeFactor));
            Tooltip tip = new Tooltip(student.getName() + "\n" + grade);
            Tooltip.install(gradeBar, tip);

            //create nameTexts
            Text nameText = new Text(student.getName()); //creates some sort of name tag
            nameText.getTransforms().add(new Translate(x + barWidth / 2, getHeight() - margin - 10)); // centers name tag
            nameText.getTransforms().add(new Rotate(-90)); // rotates name tag -> nice and better readability
            getChildren().add(nameText); // puts all the adjustments together and makes it a good name tag
        }

        double threshholdY = getHeight()- margin - (THRESHOLD / 6 * (getHeight() - 2 * margin));
        Line threshholdLine = new Line(0, threshholdY, getWidth(), threshholdY);
        threshholdLine.setStroke(Color.RED);
        threshholdLine.setStrokeWidth(4);
        getChildren().add(threshholdLine);
    }
}
