package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage){
		//FXMLで初期画面を表示
		Stage window = primaryStage;
		window.setTitle("Texercise");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Texercise.fxml"));
			Scene scene = new Scene(root,600,250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}