package createAccount;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import LoginScreen.Loginscreen;

public class CreateAccountController implements Initializable{
	
	@FXML
	private FileChooser filechooser = new FileChooser();
	private File file;
	private FileInputStream fis;
	@FXML
	private ImageView pic;
	@FXML
	private TextField name;
	@FXML
	private TextField aadharno;
	@FXML
	private TextField mobileno;
	@FXML
	private TextField city;
	@FXML
	private TextField address;
	@FXML
	private TextField accountno;
	@FXML
	private TextField pin;
	@FXML
	private TextField balance;
	@FXML
	private TextField answer ;
	
	@FXML
	private DatePicker dob;
	
	@FXML
	private ComboBox<String> gender;
	@FXML
	private ComboBox<String> martialstatus;
	@FXML
	private ComboBox<String> religion;
	@FXML
	private ComboBox<String> accounttype;
	@FXML
	private ComboBox<String> questions;
	
	ObservableList<String> list = FXCollections.observableArrayList("Male","Female","Other");
	ObservableList<String> list1 = FXCollections.observableArrayList("Single","Married");
	ObservableList<String> list2 = FXCollections.observableArrayList("Hindu","Muslim","Buddhist","Christian","Others");
	ObservableList<String> list3 = FXCollections.observableArrayList("Saving Account","Current Account");
	ObservableList<String> list4 = FXCollections.observableArrayList("What is your pet name?","What is your father's birth place?","What is your brother's nick name?");
	
	@FXML
	private void closeApp(MouseEvent event) {
		Platform.exit();
		System.exit(0);
	}
	
	public void backtoLogin(MouseEvent event) throws IOException {
		Loginscreen.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/LoginScreen/LoginScreen.fxml")));

	}
	
	public void setUpPic(MouseEvent event) {
		filechooser.getExtensionFilters().add(new ExtensionFilter("Image Files","*.png","*.jpg"));
		file = filechooser.showOpenDialog(null);
		if(file!= null) {
			Image img = new Image(file.toURI().toString(),150,150,true,true);
			pic.setImage(img);
			pic.setPreserveRatio(true);
			
		}
	}
	
	public void newAccount(MouseEvent event) {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "INSERT INTO userdata (Name, AadharNo, MobileNo, Gender, Religion, MartialStatus, DOB, City, Address, AccountNo, PIN, AccountType, Balance, SecurityQuestion, Answer, ProfilePic) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, name.getText());
			ps.setString(2, aadharno.getText());
			ps.setString(3, mobileno.getText());
			ps.setString(4, gender.getValue());
			ps.setString(5, martialstatus.getValue());
			ps.setString(6, religion.getValue());
			ps.setString(7, ((TextField)dob.getEditor()).getText());
			ps.setString(8, city.getText());
			ps.setString(9, address.getText());
			ps.setString(10, accountno.getText());
			ps.setString(11, pin.getText());
			ps.setString(12, accounttype.getValue());
			ps.setString(13, balance.getText());
			ps.setString(14, questions.getValue());
			ps.setString(15, answer.getText());
			fis = new FileInputStream(file);
			ps.setBinaryStream(16, (InputStream)fis, (int)file.length());
			
			int i = ps.executeUpdate();
			if(i>0) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("Account Created");
				a.setHeaderText("Account Created Sucessfully.");
				a.setResizable(true);
				a.setContentText("Your account has been created Sucessfully, You can now login into your account.");
				a.showAndWait();
				
			}
			else {
				Alert a = new Alert(AlertType.ERROR);
				
				a.setTitle("ERROR");
				a.setHeaderText("Account is not created");
				a.setResizable(true);
				a.setContentText("Your account is not created, There is some Error. TRY AGAIN!!!");
				a.showAndWait();
			}
			
		}catch(Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("ERROR in creating the account.");
			a.setResizable(true);
			a.setContentText("Your account is not created, There is some technical issue "+ e.getMessage());
			a.showAndWait();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		gender.setItems(list);
		martialstatus.setItems(list1);
		religion.setItems(list2);
		accounttype.setItems(list3);
		questions.setItems(list4);
		
		
	}

}
