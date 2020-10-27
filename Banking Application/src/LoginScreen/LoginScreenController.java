package LoginScreen;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginScreenController implements Initializable {

	@FXML
	public static Stage stage = null;
	public static String acc;
	@FXML
	private Pane main_area;
	@FXML
	private TextField accountno;
	@FXML
	private PasswordField pin;
	@FXML
	private void closeApp(MouseEvent event) {
		Platform.exit();
		System.exit(0);
		
	}
	@FXML
	private void createAccount(MouseEvent event) throws IOException {
		Parent fxml =  FXMLLoader.load(getClass().getResource("/createAccount/CreateAccount.fxml"));
		main_area.getChildren().removeAll();
		main_area.getChildren().addAll(fxml);
		
	}
	@FXML
	private void forgotPassword(MouseEvent event) throws IOException {
		Parent fxml = FXMLLoader.load(getClass().getResource("/forgotPassword/ForgotPassword.fxml"));
		main_area.getChildren().removeAll();
		main_area.getChildren().addAll(fxml);
		
	}
	
	

	public void loginAccount(MouseEvent event) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, accountno.getText());
			ps.setString(2, pin.getText());
			acc = accountno.getText();

			rs = ps.executeQuery();
			if(rs.next()) {
				((Node)event.getSource()).getScene().getWindow().hide();
				Parent root = FXMLLoader.load(getClass().getResource("/dashboard/Dashboards.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/LoginScreen/application.css").toExternalForm());
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.setScene(scene);
				stage.show();
				this.stage = stage;
				
			}
			else {
				Alert a = new Alert(AlertType.ERROR);
				
				a.setTitle("ERROR");
				a.setHeaderText("ERROR in Login.");
				a.setResizable(true);
				a.setContentText("Your account is not created, There is some Error. TRY AGAIN!!!");
				a.showAndWait();
			}
			
		}catch(Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("ERROR in Login.");
			a.setResizable(true);
			a.setContentText(" There is some Error. TRY AGAIN!!!");
			a.showAndWait();
		}
	}

	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
