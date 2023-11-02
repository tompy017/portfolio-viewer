package perlas.de.portfolio.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DePerlasGUI extends Application {


	
	public static void main(String[] args) {
		launch(DePerlasGUI.class);
	}
	
	
	@Override
	public void start(Stage window) throws Exception {
        
// first view
        Label label1 = new Label("First view!");
        Button button1 = new Button("To the second view!");
        
        //layout
        BorderPane layout1 = new BorderPane();
        layout1.setTop(label1);
        layout1.setCenter(button1);
        
        Scene firstView = new Scene(layout1, 640, 480);
        
        
// second view
        Button button2 = new Button("To the third view!");
        Label label2 = new Label("Second view!");
        
        //layout
        VBox layout2 = new VBox();
        layout2.setSpacing(20);
        layout2.getChildren().addAll(button2, label2);
        
        Scene secondView= new Scene(layout2);
        
        
// third view
        Label label3 = new Label("Third view!");
        Button button3 = new Button("To the first view!");
        
        //layout
        GridPane layout3 = new GridPane();
        layout3.getChildren().addAll(label3, button3);
        //coordinates (column, row)
        layout3.setConstraints(label3, 0, 0);
        layout3.setConstraints(button3, 1, 1);
        
        Scene thirdView = new Scene(layout3);
        
        
        
// event handlers
        button1.setOnAction((click) -> {
            window.setScene(secondView);
        });
        
        button2.setOnAction((click) -> {
            window.setScene(thirdView);
        });
        
        button3.setOnAction((event) -> {
            window.setScene(firstView);
        });
        
        
        
// initial view
        window.setTitle("De Perlas");
        window.setScene(firstView);
        window.show();
    }
}


