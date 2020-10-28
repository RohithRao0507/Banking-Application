package dashboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import LoginScreen.LoginScreenController;
import LoginScreen.Loginscreen;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;


public class DashboardController implements Initializable {

	
	private double xoffset = 0;
	private double yoffset = 0;
	@FXML
	public static String ac = LoginScreenController.acc;
	@FXML
	private Pane dashboard_main;
	@FXML
	private Text name;
	@FXML
	private Circle profilepic;
	@FXML
	private Button min;
	@FXML
	private void closeApp(MouseEvent event) {
		Platform.exit();
		System.exit(0);
		
	}
	@FXML 
	private void minimizeApp(MouseEvent event) {
		Stage stage = (Stage) min.getScene().getWindow();
		stage.setIconified(true);
		
		
	}
	
	public void setData(){
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM userdata WHERE AccountNo=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, ac);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				name.setText(rs.getString("Name"));
				InputStream is = rs.getBinaryStream("ProfilePic");
				OutputStream os = new FileOutputStream(new File("pic.jpg"));
				byte[] content = new byte[1024];
				int size = 0;
				while((size = is.read(content)) != -1) {
					os.write(content, 0, size);
				}
				os.close();
				is.close();
//				profilepic.setStroke(Color.WHITE);
				Image img = new Image("file:pic.jpg",false);
				profilepic.setFill(new ImagePattern(img));
				
				
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
			a.setContentText(" There is some Error. TRY AGAIN!!!"+e.getMessage());
			a.showAndWait();
		}
		
	}
	
	@FXML 
	public void click(MouseEvent event) {
		xoffset = event.getSceneX();
		yoffset = event.getSceneY();
	}
	@FXML
	public void drag(MouseEvent event) {
		LoginScreenController.stage.setX(event.getSceneX()-xoffset);
		LoginScreenController.stage.setY(event.getSceneY()-yoffset);
		
	}
	@FXML
	public void accountInformation(MouseEvent event) throws IOException{
		Parent fxml = FXMLLoader.load(getClass().getResource("/accountInfo/AccountInformation.fxml"));
		dashboard_main.getChildren().removeAll();
		dashboard_main.getChildren().addAll(fxml);
	}
	@FXML
	public void withdraw(MouseEvent event) throws IOException{
		Parent fxml = FXMLLoader.load(getClass().getResource("/withdraw/WithdrawAmount.fxml"));
		dashboard_main.getChildren().removeAll();
		dashboard_main.getChildren().addAll(fxml);
	}
	@FXML
	public void deposit(MouseEvent event) throws IOException{
		Parent fxml = FXMLLoader.load(getClass().getResource("/deposit/DepositAmount.fxml"));
		dashboard_main.getChildren().removeAll();
		dashboard_main.getChildren().addAll(fxml);
	}
	@FXML
	public void PinChange(MouseEvent event) throws IOException{
		Parent fxml = FXMLLoader.load(getClass().getResource("/changepin/ChangePIN.fxml"));
		dashboard_main.getChildren().removeAll();
		dashboard_main.getChildren().addAll(fxml);
	}
	@FXML
	public void transferAmount(MouseEvent event) throws IOException{
		Parent fxml = FXMLLoader.load(getClass().getResource("/transferamount/TransferAmount.fxml"));
		dashboard_main.getChildren().removeAll();
		dashboard_main.getChildren().addAll(fxml);
	}
	@FXML
	public void mainScreen() throws IOException{
		Parent fxml = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
		dashboard_main.getChildren().removeAll();
		dashboard_main.getChildren().addAll(fxml);
	}
	
	public void logout(MouseEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/LoginScreen/LoginScreen.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/LoginScreen/application.css").toExternalForm());
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.show();
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
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setData();
		try {
			mainScreen();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
