package application;

import javafx.application.Application;
import javafx.stage.Stage;
import controller.Controller_Main;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage)  {
		Controller_Main ctrl = new Controller_Main();
		ctrl.launch("Xem phim Java", null);
		
	/*		try {
				Pane root = FXMLLoader.load(getClass().getResource("/layout/Main.fxml"));
				Scene scene = new Scene(root,600,400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setTitle("Xem phim Youtube");
				Image iconSoftWare = new Image("/layout/iconMovie.png");
				primaryStage.getIcons().add(iconSoftWare);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
