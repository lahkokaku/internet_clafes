package views;

import java.time.LocalDate;
import java.util.Vector;

import controllers.PCBookController;
import controllers.PCController;
import controllers.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.PC;
import models.PCBook;
import models.User;
import stores.UserStore;
import util.StageManager;

public class ViewPCBookedView extends BaseView {

	private Label viewPCBooked_lb;
	
	private TableView<PCBook> pcBookedTable_tv;
	
	private Label finishTitle_lb;
	private Label finish_lb;
	private DatePicker finish_dp;
	private Label cancelTitle_lb;
	private Label cancel_lb;
	private DatePicker cancel_dp;
	private Label assignTitle_lb;
	private Label assignBookID_lb;
	private TextField assignBookID_tf;
	private Label assignPcID_lb;
	private TextField assignPcID_tf;
	
	private MenuBar menubar;
	private Menu generalMenu_menu;
	private MenuItem viewAllPC_mi;
	private MenuItem logout_mi;
	
	private Menu operatorMenu_menu;
	private MenuItem viewPCBookedData_mi;
	
	private MenuItem makeReport_mi;
	
	private BorderPane bp;
	private GridPane gpLeft;
	private GridPane gpCenter;
	private Button finish_btn;
	private Button cancel_btn;
	private Button assign_btn;

	@Override
	public void initialize() {
		this.bp = new BorderPane();
		this.gpLeft = new GridPane();
		this.gpCenter = new GridPane();
		
		initializeMenu();
		initializeLabels();
		initializeFormComponents();
		
	}
	
	private void initializeMenu() {
		this.generalMenu_menu = new Menu("General Menu");
		this.viewAllPC_mi = new MenuItem("View All PC");
		this.logout_mi = new MenuItem("Logout");
		this.generalMenu_menu.getItems().addAll(viewAllPC_mi, logout_mi);
				
		this.operatorMenu_menu = new Menu("Operator Menu");
		this.viewPCBookedData_mi = new MenuItem("View Booked PC");
		this.makeReport_mi = new MenuItem("Make Report");
		this.operatorMenu_menu.getItems().addAll(viewPCBookedData_mi, makeReport_mi);		
		
		this.menubar = new MenuBar(generalMenu_menu, operatorMenu_menu);
	}
	
	public void initializeLabels() {
		this.viewPCBooked_lb = new Label("View Booked PC");
		this.finishTitle_lb = new Label("Finish Booking");
		this.finish_lb = new Label("Book Date to Finish");
		this.cancelTitle_lb = new Label("Cancel Booking");
		this.cancel_lb = new Label("Book Date to Cancel");
		this.assignTitle_lb = new Label("Assign user to new PC");
		this.assignBookID_lb = new Label("Book ID to assign");
		this.assignPcID_lb = new Label("PC ID to be assigned");
	}
	
	public void initializeFormComponents() {
		pcBookedTable_tv = new TableView<PCBook>();
		setTableComponent();
		this.finish_dp = new DatePicker(LocalDate.now());
		this.cancel_dp = new DatePicker(LocalDate.now());
		this.assignBookID_tf = new TextField();
		this.assignPcID_tf = new TextField();
		this.finish_btn = new Button("Finish Book");
		this.cancel_btn = new Button("Cancel Book");
		this.assign_btn = new Button("Assign to New PC");
	}

	private void setTableComponent() {
		TableColumn<PCBook, Integer> idColumn = new TableColumn<PCBook, Integer>("Booked ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<PCBook, Integer>("bookID"));
		idColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<PCBook, Integer> pcIdColumn = new TableColumn<PCBook, Integer>("PC ID");
		pcIdColumn.setCellValueFactory(new PropertyValueFactory<PCBook, Integer>("pcID"));
		pcIdColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<PCBook, Integer> userIdColumn = new TableColumn<PCBook, Integer>("User ID");
		userIdColumn.setCellValueFactory(new PropertyValueFactory<PCBook, Integer>("userID"));
		userIdColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<PCBook, LocalDate> bookedDateColumn = new TableColumn<PCBook, LocalDate>("Book Date");
		bookedDateColumn.setCellValueFactory(new PropertyValueFactory<PCBook, LocalDate>("bookedDate"));
		bookedDateColumn.setMinWidth(gpCenter.getWidth() / 4);
		
		pcBookedTable_tv.getColumns().addAll(idColumn, pcIdColumn, userIdColumn, bookedDateColumn);
		
		Vector<PCBook> pcBooks = PCBookController.getAllPCBookedData();
		for (PCBook pcBook : pcBooks) {
			pcBookedTable_tv.getItems().add(pcBook);
		}
	}
	
	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setLeft(gpLeft);
		this.bp.setCenter(gpCenter);

		this.gpLeft.add(viewPCBooked_lb, 0, 1);
		this.gpLeft.add(pcBookedTable_tv, 0, 2);
		
		int i = 1;
		
		this.gpCenter.add(finishTitle_lb, 0, i++);
		this.gpCenter.add(finish_lb, 0, i++);
		this.gpCenter.add(finish_dp, 0, i++);
		this.gpCenter.add(finish_btn, 0, i++);
		
		// Untuk Empty Space
		this.gpCenter.add(new Label(""), 0, i++);
		
		this.gpCenter.add(cancelTitle_lb, 0, i++); 
		this.gpCenter.add(cancel_lb, 0, i++); 
		this.gpCenter.add(cancel_dp, 0, i++);
		this.gpCenter.add(cancel_btn, 0, i++);
		
		// Untuk Empty Space
		this.gpCenter.add(new Label(""), 0, i++);

		this.gpCenter.add(assignTitle_lb, 0, i++);
		this.gpCenter.add(assignBookID_lb, 0, i);
		this.gpCenter.add(assignBookID_tf, 1, i++);
		this.gpCenter.add(assignPcID_lb, 0, i);
		this.gpCenter.add(assignPcID_tf, 1, i++);
		this.gpCenter.add(assign_btn, 1, i++);
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		this.gpLeft.setPadding(new Insets(15, 15, 15, 15));
		this.gpCenter.setPadding(new Insets(15, 15, 15, 15));
		viewPCBooked_lb.setFont(Font.font(20));
		finishTitle_lb.setFont(Font.font(null, FontWeight.BOLD, -1));
		cancelTitle_lb.setFont(Font.font(null, FontWeight.BOLD, -1));
		GridPane.setColumnSpan(pcBookedTable_tv, 2);
		GridPane.setHalignment(pcBookedTable_tv, HPos.CENTER);
	}
	
	@Override
	public void setAction() {
		// General
		viewAllPC_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllPCView().getScene());
		});
		logout_mi.setOnAction(e->{
			UserController.logout();
		});
		
		// Operator
		viewPCBookedData_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewPCBookedView().getScene());
		});
		
		// Customer and Operator
		makeReport_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new MakeReportView().getScene());
		});
		
		finish_btn.setOnMouseClicked(event -> {
			Vector<PCBook> pcBooks = PCBookController.getPCBookedByDate(finish_dp.getValue());
			
			if(pcBooks.isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("No bookings found");
				alert.showAndWait();
				return;
			}
			
			if(!pcBooks.get(0).getBookedDate().isBefore(LocalDate.now())) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("PC Book date has not passed yet");
				alert.showAndWait();
				return;
			}
			
			int result = PCBookController.finishBook(pcBooks);

			if(result > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("PC Books has been successfuly finished");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewPCBookedView().getScene());
				return;
			}
		});
		cancel_btn.setOnMouseClicked(event -> {
			Vector<PCBook> pcBooks = PCBookController.getPCBookedByDate(cancel_dp.getValue());
			
			if(pcBooks.isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("No bookings found");
				alert.showAndWait();
				return;
			}
			
			if(pcBooks.get(0).getBookedDate().isAfter(LocalDate.now())) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("PC Book date has passed");
				alert.showAndWait();
				return;
			}
			
			int result = 0;
			
			for (PCBook pcBook : pcBooks) {
				result += PCBookController.deleteBookData(pcBook.getBookID());
			}
			
			if(result > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("PC Books has been successfuly finished");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewPCBookedView().getScene());
				return;
			}
		});
		
		assignBookID_tf.setOnKeyTyped(e->{
			String input = assignBookID_tf.getText();
			
			if(input.length() <= 0) {
				return;
			}
			
			if(!Character.isDigit(input.charAt(input.length() - 1))) {
				assignBookID_tf.clear();
			}
		});
		assignPcID_tf.setOnKeyTyped(e->{
			String input = assignPcID_tf.getText();
			
			if(input.length() <= 0) {
				return;
			}
			
			if(!Character.isDigit(input.charAt(input.length() - 1))) {
				assignPcID_tf.clear();
			}
		});
		assign_btn.setOnAction(e->{
			// Check apakah booking id dan pc id sudah diisi
			if(assignBookID_tf.equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Book ID must be filled");
				alert.showAndWait();
				return;
			}
			if(assignPcID_tf.equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("PC ID must be filled");
				alert.showAndWait();
				return;
			}
			
			// Check apakah pcBook ada
			PCBook pcBook = PCBookController.getPCBookedDetail(Integer.parseInt(assignBookID_tf.getText()));
			if(pcBook == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Booking not found");
				alert.showAndWait();
				return;
			}
			
			// Check apakah pc ada dan available
			PC pc = PCController.getPCDetail(Integer.parseInt(assignPcID_tf.getText()));
			if(pc == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("PC not found");
				alert.showAndWait();
				return;
			}
			if(!pc.getPcCondition().equals("Usable")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("PC unavailable");
				alert.showAndWait();
				return;
			}
			
			int result = PCBookController.assignUserToNewPC(Integer.parseInt(assignBookID_tf.getText()), Integer.parseInt(assignPcID_tf.getText()));
			if(result > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("New PC has been assigned for the booking");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewPCBookedView().getScene());
			}
		});
	}

}
