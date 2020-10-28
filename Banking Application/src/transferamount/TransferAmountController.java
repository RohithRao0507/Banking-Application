package transferamount;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import LoginScreen.LoginScreenController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class TransferAmountController implements Initializable{

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public static String ac = LoginScreenController.acc;
	@FXML
	private Label account_no;
	@FXML
	private Label balance;
	@FXML 
	private TextField amt_field;
	@FXML 
	private TextField account_no_field;
	@FXML
	private PasswordField pin_field;
	
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH);
	int day = cal.get(Calendar.DAY_OF_MONTH);
	int hour = cal.get(Calendar.HOUR);
	int minutes = cal.get(Calendar.MINUTE);
	int seconds = cal.get(Calendar.SECOND);
	int daynight = cal.get(Calendar.AM_PM);
	
	DateFormat dateformat = new SimpleDateFormat("yyyy/mm/dd");
	Date d = new Date();
	String date= dateformat.format(d);
	
	LocalTime localTime = LocalTime.now();
	DateTimeFormatter dt =  DateTimeFormatter.ofPattern("hh:mm:ss a");
	String time= localTime.format(dt);
	
	
	
	public void setInfo(){
		
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM userdata WHERE AccountNo=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, ac);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				account_no.setText(rs.getString("AccountNo"));
				balance.setText(rs.getString("Balance"));
			}
			
			else {
				Alert a = new Alert(AlertType.ERROR);
				
				a.setTitle("Invalid PIN");
				a.setHeaderText("You Entered Wrong PIN");
				a.setResizable(true);
				a.setContentText("Please enter the correct pin. TRY AGAIN!!!");
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
	
	public void checkButton(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM userdata WHERE AccountNo=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, account_no_field.getText());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				Alert a = new Alert(AlertType.INFORMATION);
				
				a.setTitle("Account Information");
				a.setHeaderText("Please confirm receivers account");
				a.setResizable(true);
				a.setContentText("Account No = "+account_no_field.getText()+"\nName:  "+rs.getString("Name")+"\nMobile No:  "+rs.getString("MobileNo"));
				a.showAndWait();
				
			}
			
			else {
				Alert a = new Alert(AlertType.ERROR);
				
				a.setTitle("Invalid Account Number");
				a.setHeaderText("You Entered Wrong AccountNo");
				a.setResizable(true);
				a.setContentText("Please enter the correct AccountNo. TRY AGAIN!!!");
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
	
	public void TransferAmountButton(){
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, ac);
			ps.setString(2, pin_field.getText());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				int transfer_amt = Integer.parseInt(amt_field.getText());
				int ta = Integer.parseInt(balance.getText());
				if(transfer_amt>ta) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Transaction Cancelled!!");
					a.setHeaderText("Insufficient Balance");
					a.setResizable(true);
					a.setContentText("You don't have enough balance required for transaction");
					a.showAndWait();
					
				}
				else {
					int total = ta-transfer_amt;
					String sql1 = "UPDATE userdata SET Balance='"+total+"'WHERE AccountNo = '"+ac+"'";
					ps = con.prepareStatement(sql1);
					ps.execute();
					
					String sql2 = "SELECT * FROM userdata WHERE AccountNo=? ";
					ps = con.prepareStatement(sql2);
					ps.setString(1, account_no_field.getText());
					rs = ps.executeQuery();
					
					if(rs.next()) {
						
						int cur = Integer.parseInt(amt_field.getText());
						int prev = Integer.parseInt(rs.getString("Balance"));
						
							int total1 = prev+cur;
							String sql4 = "UPDATE userdata SET Balance='"+total1+"'WHERE AccountNo = '"+account_no_field.getText()+"'";
							ps = con.prepareStatement(sql4);
							ps.execute();
					
							String sql5 ="INSERT INTO transfer(AccountNo, Amount, SendTo, Date, Time) VALUES (?,?,?,?,?)";
							ps = con.prepareStatement(sql5);
							ps.setString(1, ac);
							ps.setString(2, String.valueOf(amt_field.getText()));
							ps.setString(3, String.valueOf(account_no_field.getText()));
							ps.setString(4, date);
							ps.setString(5, time);
							
							
							int i = ps.executeUpdate();
							if(i>0) {
								Alert a = new Alert(AlertType.INFORMATION);
								
								a.setTitle("Amount Transfer");
								a.setHeaderText("Amount Transfered sucessfully");
								a.setResizable(true);
								a.setContentText("You have Transfered amount of Rs "+cur+" sucessfully\n"+"To AccountNo = "+account_no_field.getText());
								a.showAndWait();
								
								account_no_field.setText("");
								amt_field.setText("");
								pin_field.setText("");
								balance.setText(String.valueOf(total));
								
							}
						
					}
					
							else 
								{
								Alert a = new Alert(AlertType.ERROR);
						
								a.setTitle("ERROR");
								a.setHeaderText("There is some sort of error");
								a.setResizable(true);
								a.setContentText("Your account is not created, There is some Error. TRY AGAIN!!!");
								a.showAndWait();
								}
				}
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
	


	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setInfo();
		
	}

}
