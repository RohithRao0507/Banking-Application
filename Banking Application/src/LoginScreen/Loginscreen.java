package LoginScreen;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;



public class Loginscreen extends Application {
	
	
	public static Stage stage = null;
	private double xoffset = 0;
	private double yoffset = 0;
	
	@Override
	public void start(Stage primarystage) {
		try {
		
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primarystage.initStyle(StageStyle.UNDECORATED);
			primarystage.setScene(scene);
			
			// for moving the window
			root.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					
					xoffset = arg0.getSceneX();
					yoffset = arg0.getSceneY();
					
				}
				
			});
			
			root.setOnMouseDragged(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					stage.setX(arg0.getSceneX()-xoffset);
					stage.setY(arg0.getSceneY()-yoffset);
				}
				
			});

			
			this.stage = primarystage;
			primarystage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
