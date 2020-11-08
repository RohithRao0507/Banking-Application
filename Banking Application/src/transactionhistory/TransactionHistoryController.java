package transactionhistory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import LoginScreen.LoginScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransactionHistoryController implements Initializable {

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	@FXML
	private TableView<History>withdraw_table;
	@FXML
	private TableColumn<History, String> withdraw_accountno;
	@FXML
	private TableColumn<History, String> withdraw_amount;
	@FXML
	private TableColumn<History, String> withdraw_currentbalance;
	@FXML
	private TableColumn<History, String> withdraw_date;
	@FXML
	private TableColumn<History, String> withdraw_time;
	
	
	@FXML
	private TableView<History>deposit_table;
	@FXML
	private TableColumn<History, String> deposit_accountno;
	@FXML
	private TableColumn<History, String> deposit_amount;
	@FXML
	private TableColumn<History, String> deposit_currentbalance;
	@FXML
	private TableColumn<History, String> deposit_date;
	@FXML
	private TableColumn<History, String> deposit_time;
	
	
	@FXML
	private TableView<History> transfer_table;
	@FXML
	private TableColumn<History, String> transfer_accountno;
	@FXML
	private TableColumn<History, String> transfer_amount;
	@FXML
	private TableColumn<History, String> transfer_currentbalance;
	@FXML
	private TableColumn<History, String> transfer_date;
	@FXML
	private TableColumn<History, String> transfer_time;
	
	
	@FXML
	private TableView<History>receive_table;
	@FXML
	private TableColumn<History, String> receive_accountno;
	@FXML
	private TableColumn<History, String> receive_amount;
	@FXML
	private TableColumn<History, String> receive_currentbalance;
	@FXML
	private TableColumn<History, String> receive_date;
	@FXML
	private TableColumn<History, String> receive_time;
	
	
	public void withdraw() {
		withdraw_accountno.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
		withdraw_amount.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
		withdraw_currentbalance.setCellValueFactory(new PropertyValueFactory<History,String>("generic"));
		withdraw_date.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
		withdraw_time.setCellValueFactory(new PropertyValueFactory<History,String>("time"));
		
		ObservableList<History> list = FXCollections.observableArrayList();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM withdraw WHERE AccountNo=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, LoginScreenController.acc);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new History(rs.getString("AccountNo"),rs.getString("WithdrawAmount"),rs.getString("RemainingAmount"),rs.getString("Date"),rs.getString("Time")));
			}
			
			
		}catch(Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("ERROR in Fetching Data.");
			a.setResizable(true);
			a.setContentText(" There is some Error in Fetching Data. TRY AGAIN!!!"+e.getMessage());
			a.showAndWait();
		}
		withdraw_table.setItems(list);
	}
	
	
	public void deposit() {
		deposit_accountno.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
		deposit_amount.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
		deposit_currentbalance.setCellValueFactory(new PropertyValueFactory<History,String>("generic"));
		deposit_date.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
		deposit_time.setCellValueFactory(new PropertyValueFactory<History,String>("time"));
		
		ObservableList<History> list = FXCollections.observableArrayList();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM deposit WHERE AccountNo=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, LoginScreenController.acc);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new History(rs.getString("AccountNo"),rs.getString("DepositAmount"),rs.getString("NewAmount"),rs.getString("Date"),rs.getString("Time")));
			}
			
			
		}catch(Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("ERROR in Fetching Data.");
			a.setResizable(true);
			a.setContentText(" There is some Error in Fetching Data. TRY AGAIN!!!"+e.getMessage());
			a.showAndWait();
		}
		deposit_table.setItems(list);
	}

	public void transfer() {
		transfer_accountno.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
		transfer_amount.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
		transfer_currentbalance.setCellValueFactory(new PropertyValueFactory<History,String>("generic"));
		transfer_date.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
		transfer_time.setCellValueFactory(new PropertyValueFactory<History,String>("time"));
		
		ObservableList<History> list = FXCollections.observableArrayList();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM transfer WHERE AccountNo=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, LoginScreenController.acc);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new History(rs.getString("AccountNo"),rs.getString("Amount"),rs.getString("SendTo"),rs.getString("Date"),rs.getString("Time")));
			}
			
			
		}catch(Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("ERROR in Fetching Data.");
			a.setResizable(true);
			a.setContentText(" There is some Error in Fetching Data. TRY AGAIN!!!"+e.getMessage());
			a.showAndWait();
		}
		transfer_table.setItems(list);
	}
	
	
	public void receive() {
		receive_accountno.setCellValueFactory(new PropertyValueFactory<History,String>("name"));
		receive_amount.setCellValueFactory(new PropertyValueFactory<History,String>("amount"));
		receive_currentbalance.setCellValueFactory(new PropertyValueFactory<History,String>("generic"));
		receive_date.setCellValueFactory(new PropertyValueFactory<History,String>("date"));
		receive_time.setCellValueFactory(new PropertyValueFactory<History,String>("time"));
		
		ObservableList<History> list = FXCollections.observableArrayList();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM transfer WHERE SendTo=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, LoginScreenController.acc);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new History(rs.getString("SendTo"),rs.getString("Amount"),rs.getString("AccountNo"),rs.getString("Date"),rs.getString("Time")));
			}
			
			
		}catch(Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("ERROR in Fetching Data.");
			a.setResizable(true);
			a.setContentText(" There is some Error in Fetching Data. TRY AGAIN!!!"+e.getMessage());
			a.showAndWait();
		}
		receive_table.setItems(list);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		withdraw();
		deposit();
		transfer();
		receive();
	}

}
