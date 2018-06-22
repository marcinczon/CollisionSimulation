package FX_Controllers;

import static FX_Controllers.ControllerFXML_CollisionTable.controllerFXML_CollisionTable;
import static FX_Controllers.ControllerFXML_ThreadTable.controllerFXML_ThreadTable;

import java.net.URL;
import java.util.ResourceBundle;

import GameObjects.Ball;
import GameObjects.BallController;
import GameObjects.BallParameter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import static Parameters.GeneralParameters.generalParameters;
import static Parameters.ScreenParameter.screenParameter;

public class ControllerFXML_Base implements Initializable
{
	public static ControllerFXML_Base controllerFXML_Base;

	private BallController ballController = new BallController();
	private ObservableList<Ball> BALL_OBS_LIST_REFERENCE;
	private ObservableList<BallParameter> BALL_PARAMETER_OBS_LIST = FXCollections.observableArrayList();
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
	private TableView<BallParameter> NodeTable;

	@FXML
	public void buttonThreadClicked()
	{
		controllerFXML_ThreadTable.threadList();
	}

	int counterTable = 0;

	private TableColumn<BallParameter, Integer> numberColumn = new TableColumn<BallParameter, Integer>("Number");
	private TableColumn<BallParameter, Integer> PosXColumn = new TableColumn<BallParameter, Integer>("PosX");
	private TableColumn<BallParameter, Integer> PosYColumn = new TableColumn<BallParameter, Integer>("PosY");
	private TableColumn<BallParameter, Integer> VxColumn = new TableColumn<BallParameter, Integer>("Vx");
	private TableColumn<BallParameter, Integer> VyColumn = new TableColumn<BallParameter, Integer>("Vy");
	private TableColumn<BallParameter, Integer> WeightColumn = new TableColumn<BallParameter, Integer>("Weight");

	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		controllerFXML_Base = this;

		BALL_OBS_LIST_REFERENCE = ballController.getBallList();
		for (Ball ball : BALL_OBS_LIST_REFERENCE)
		{
			BALL_PARAMETER_OBS_LIST.add(ball.getParameter());
		}

		// Creating Table

		NodeTable.setStyle("-fx-font-size:12px;");
		numberColumn.setCellValueFactory(new PropertyValueFactory<BallParameter, Integer>("P_BaseNumber"));
		numberColumn.setPrefWidth(50);
		PosXColumn.setCellValueFactory(new PropertyValueFactory<BallParameter, Integer>("P_PosX"));
		PosXColumn.setPrefWidth(50);
		PosYColumn.setCellValueFactory(new PropertyValueFactory<BallParameter, Integer>("P_PosY"));
		PosYColumn.setPrefWidth(50);
		VxColumn.setCellValueFactory(new PropertyValueFactory<BallParameter, Integer>("P_VxActual"));
		VxColumn.setPrefWidth(50);
		VyColumn.setCellValueFactory(new PropertyValueFactory<BallParameter, Integer>("P_VyActual"));
		VyColumn.setPrefWidth(50);
		WeightColumn.setCellValueFactory(new PropertyValueFactory<BallParameter, Integer>("P_Weight"));
		WeightColumn.setPrefWidth(50);
		NodeTable.getColumns().addAll(numberColumn, PosXColumn, PosYColumn, VxColumn, VyColumn,WeightColumn);
		NodeTable.setItems(BALL_PARAMETER_OBS_LIST);

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
				updateTable();
				ControllerFXML_ThreadTable.threadList();
				controllerFXML_CollisionTable.generateColisionBitTable();

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
						BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().setVelocityY(BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().getVelocityY() - increaseManualVelocity);
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
					BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().setVelocityY(BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().getVelocityY() + increaseManualVelocity);
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
					BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().setVelocityX(BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().getVelocityX() - increaseManualVelocity);
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
					BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().setVelocityX(BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().getVelocityX() + increaseManualVelocity);
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
				BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().setVelocityX(0);
				BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().setVelocityY(0);
			}
		});

		// Listeners

		inputGravity.textProperty().addListener((observable, oldValue, newValue) ->
		{
			generalParameters.setGravity(Integer.parseInt(newValue));
		});

		NodeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
			// System.out.println(newSelection.toString());
			// newSelection.setRED();
			// oldSelection.setGray();
			// ballSelected = newSelection.getParameter().getBaseNumber();
		});

		// Set Screnn Size for Game

		screenParameter.setScreenMaxX((int) RIGHT_PANE.getPrefWidth());
		screenParameter.setScreenMaxY((int) RIGHT_PANE.getPrefHeight());

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
		//shape.
		RIGHT_PANE.getChildren().add(shape);
	}

	private void updateTable()
	{
		BALL_PARAMETER_OBS_LIST.clear();
		for (Ball ball : BALL_OBS_LIST_REFERENCE)
		{
			BALL_PARAMETER_OBS_LIST.add(ball.getParameter());
		}
	}

	public Pane getRIGHT_PANE()
	{
		return RIGHT_PANE;
	}



}
