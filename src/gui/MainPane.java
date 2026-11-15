package gui;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import java.util.Map;


// use: ctrl + click on a class to read everything about it
public class MainPane extends StackPane {

    private Map<String, String> majorMap;

    public MainPane(Map<String, String> majorMap) {

        this.majorMap = majorMap;

        // for kinda 2 areas in 1 box but next to each other:
        SplitPane splitPane = new SplitPane();
        splitPane.getStyleClass().add("split-pane-divider");

        // build 3 panes: stateModel & graphics & control
        StateModel stateModel = new StateModel();
        GraphicsPane graphicsPane = new GraphicsPane(stateModel);
        ControlPane controlPane = new ControlPane(majorMap, stateModel);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab chartTab = new Tab("grades chart", graphicsPane);
        Tab reportTab = new Tab("statistics / report");
        tabPane.getTabs().addAll(chartTab, reportTab);

        // for SplitPane use getItems() instead of getChildren():
        splitPane.getItems().addAll(controlPane, tabPane);
        splitPane.setDividerPosition(0, 0.3);

        // put this (splitPane) into MainPane
        this.getChildren().add(splitPane);

        // important hierarchy: crashed my brain big time...
        // Stage aka THE WINDOW
            // MainPane
                // SplitPane
                    // ControlPane
                    // GraphicsPane
    }
}
