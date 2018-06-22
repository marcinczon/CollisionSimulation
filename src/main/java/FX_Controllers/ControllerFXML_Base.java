package FX_Controllers;

import static FX_Controllers.ControllerFXML_CollisionTable2.controllerFXML_CollisionTable2;
import static FX_Controllers.ControllerFXML_ThreadTable.controllerFXML_ThreadTable;

import java.net.URL;
import java.util.ResourceBundle;

import GameObjects.Ball;
import GameObjects.BallController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ControllerFXML_Base implements Initializable
{
	public static ControllerFXML_Base controllerFXML_Base;

	private BallController ballController = new BallController();
	private ObservableList<Ball> BALL_OBS_LIST_REFERENCE;
	private int ballSelected = 0;
	private int increaseManualVelocity = 10;

	// FXML ITEMS

	@FXML
	private Pane LEFT_PANE;

	@FXML
	private Pane RIGHT_PANE;

	@FXML
	private Button buttonThreads;

	@FXML
	private Button ButtonAddBall;

	@FXML
	private Button Up;

	@FXML
	private Button Down;

	@FXML
	private Button Left;

	@FXML
	private Button Right;

	@FXML
	private Button buttonZero;

	@FXML
	private Pane LabelPane;

	@FXML
	private Label Label_Status;

	@FXML
	TextField inputGravity;

	@FXML
	ChoiceBox choiceBoxMode;

	@FXML
	private TableView<Ball> NodeTable;

	@FXML
	public void buttonThreadClicked()
	{
		controllerFXML_ThreadTable.threadList();
	}

	int counterTable = 0;

	private TableColumn<Ball, Integer> numberColumn = new TableColumn<Ball, Integer>("Number");
	private TableColumn<Ball, Integer> PosXColumn = new TableColumn<Ball, Integer>("PosX");
	private TableColumn<Ball, Integer> PosYColumn = new TableColumn<Ball, Integer>("PosY");
	private TableColumn<Ball, Integer> VxColumn = new TableColumn<Ball, Integer>("Vx");
	private TableColumn<Ball, Integer> VyColumn = new TableColumn<Ball, Integer>("Vy");

	@SuppressWarnings("restriction")
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		controllerFXML_Base = this;

		BALL_OBS_LIST_REFERENCE = ballController.getBallList();

		// Creating Table

		NodeTable.setStyle("-fx-font-size:12px;");
		numberColumn.setCellValueFactory(new PropertyValueFactory<Ball, Integer>("P_BallNumber"));
		numberColumn.setPrefWidth(50);
		PosXColumn.setCellValueFactory(new PropertyValueFactory<Ball, Integer>("P_PosX"));
		PosXColumn.setPrefWidth(50);
		PosYColumn.setCellValueFactory(new PropertyValueFactory<Ball, Integer>("P_PosY"));
		PosYColumn.setPrefWidth(50);
		VxColumn.setCellValueFactory(new PropertyValueFactory<Ball, Integer>("P_VxActual"));
		VxColumn.setPrefWidth(50);
		VyColumn.setCellValueFactory(new PropertyValueFactory<Ball, Integer>("P_VyActual"));
		VyColumn.setPrefWidth(50);
		NodeTable.getColumns().addAll(numberColumn, PosXColumn, PosYColumn, VxColumn, VyColumn);
		NodeTable.setItems(BALL_OBS_LIST_REFERENCE);

		// Choice Box Parameters

		choiceBoxMode.setItems(FXCollections.observableArrayList("Position", "Velocity"));
		choiceBoxMode.getSelectionModel().selectFirst();

		// Buttons

		ButtonAddBall.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				ballController.addFewBalls();
				RIGHT_PANE.getChildren().clear();
				RIGHT_PANE.getChildren().addAll(ballController.getNodeFromBallList());

				// Every time when added new balls, have to be updated interlock list-collisions
				// bit class
				for (Ball ball : BALL_OBS_LIST_REFERENCE)
				{
					ball.updateStatusBit();
				}
				ControllerFXML_ThreadTable.threadList();
				controllerFXML_CollisionTable2.generateColisionBitTable();

			}

		});

		Up.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				if (ballController.getNodeFromBallList().size() > 0)
				{
					if (choiceBoxMode.getSelectionModel().getSelectedItem().equals("Velocity"))
					{
						BALL_OBS_LIST_REFERENCE.get(ballSelected).setVyActual(BALL_OBS_LIST_REFERENCE.get(ballSelected).getVyActual() - increaseManualVelocity);
					}
					if (choiceBoxMode.getSelectionModel().getSelectedItem().equals("Position"))
					{
						BALL_OBS_LIST_REFERENCE.get(ballSelected).manualUp();
					}
				}
			}
		});

		Down.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				if (choiceBoxMode.getSelectionModel().getSelectedItem().equals("Velocity"))
				{
					BALL_OBS_LIST_REFERENCE.get(ballSelected).setVyActual(BALL_OBS_LIST_REFERENCE.get(ballSelected).getVyActual() + increaseManualVelocity);
				}
				if (choiceBoxMode.getSelectionModel().getSelectedItem().equals("Position"))
				{
					BALL_OBS_LIST_REFERENCE.get(ballSelected).manualDown();
				}
			}
		});

		Left.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				if (choiceBoxMode.getSelectionModel().getSelectedItem().equals("Velocity"))
				{
					BALL_OBS_LIST_REFERENCE.get(ballSelected).setVxActual(BALL_OBS_LIST_REFERENCE.get(ballSelected).getVxActual() - increaseManualVelocity);
				}
				if (choiceBoxMode.getSelectionModel().getSelectedItem().equals("Position"))
				{
					BALL_OBS_LIST_REFERENCE.get(ballSelected).manualLeft();
				}
			}
		});

		Right.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				if (choiceBoxMode.getSelectionModel().getSelectedItem().equals("Velocity"))
				{
					BALL_OBS_LIST_REFERENCE.get(ballSelected).setVxActual(BALL_OBS_LIST_REFERENCE.get(ballSelected).getVxActual() + increaseManualVelocity);
				}
				if (choiceBoxMode.getSelectionModel().getSelectedItem().equals("Position"))
				{
					BALL_OBS_LIST_REFERENCE.get(ballSelected).manualRight();
				}
			}
		});

		buttonZero.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				BALL_OBS_LIST_REFERENCE.get(ballSelected).setVxActual(0);
				BALL_OBS_LIST_REFERENCE.get(ballSelected).setVyActual(0);
			}
		});

		// Listeners

		inputGravity.textProperty().addListener((observable, oldValue, newValue) ->
		{
			BALL_OBS_LIST_REFERENCE.stream().forEach(s -> s.setGravity(Double.parseDouble(newValue)));
		});

		NodeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
			System.out.println(newSelection.toString());
			newSelection.setRED();
			oldSelection.setGray();
			ballSelected = newSelection.getBallNumber();
		});

		// Set Screnn Size for Game

		ballController.setScreenSizeX((int) RIGHT_PANE.getPrefWidth());
		ballController.setScreenSizeY((int) RIGHT_PANE.getPrefHeight());

	}

	public int getScreenSizeX()
	{
		return (int) RIGHT_PANE.getPrefWidth();
	}

	public int getScreenSizeY()
	{
		return (int) RIGHT_PANE.getPrefHeight();
	}

	public void addObjectToPane(Node shape)
	{
		RIGHT_PANE.getChildren().add(shape);
	}

}
