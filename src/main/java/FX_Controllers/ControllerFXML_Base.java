package FX_Controllers;

import static FX_Controllers.ControllerFXML_CollisionTable.controllerFXML_CollisionTable;
import static FX_Controllers.ControllerFXML_ThreadTable.controllerFXML_ThreadTable;
import static Parameters.GeneralParameters.generalParameters;
import static Parameters.ScreenParameter.screenParameter;
import static FX_Controllers.ControllerFXML_MessageTable.controllerFXML_MessageTable;

import java.awt.Event;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javax.xml.stream.events.StartDocument;

import com.sun.prism.paint.Color;

import GameObjects.Ball;
import GameObjects.BallController;
import GameObjects.BallParameter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import main.*;

public class ControllerFXML_Base implements Initializable
{
	// Class instances

	StartInterface startInstance;
	public static ControllerFXML_Base controllerFXML_Base;

	private BallController ballController = new BallController();
	private ObservableList<Ball> BALL_OBS_LIST_REFERENCE;
	private ObservableList<BallParameter> BALL_PARAMETER_OBS_LIST = FXCollections.observableArrayList();
	private int ballSelected = 0;
	private int increaseManualVelocity = 10;

	// TEMP-----------
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;
	Line line = new Line();
	// --------------------

	MenuBar menuBar = new MenuBar();
	Menu fileMenu = new Menu("File");
	MenuItem exitMenuItem = new MenuItem("Exit");

	Menu windowMenu = new Menu("Window");
	MenuItem collisionMenuItem = new MenuItem("Collision");
	MenuItem threedMenuItem = new MenuItem("Threeds");
	MenuItem messageMenuItem = new MenuItem("Status");

	// @FXML DECLARATIONS

	@FXML
	private Pane LEFT_PANE;

	@FXML
	private Pane RIGHT_PANE;

	@FXML
	private Button buttonThreads;

	@FXML
	private Button buttonWindows;

	@FXML
	private Button buttonCollision;

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
	SplitPane SplitPane;

	@FXML
	private TableView<BallParameter> NodeTable;

	// @FXML ACTIONS

	@FXML
	public void buttonThreadClicked()
	{
		startInstance.ShowStage2();
	}

	@FXML
	public void buttonCollisionsClicked()
	{
		startInstance.ShowStage3();
	}

	@FXML
	public void buttonMessagesClicked()
	{
		startInstance.ShowStage4();
	}

	@FXML
	public void upClicked()
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

	@FXML
	public void downClicked()
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

	@FXML
	public void leftClicked()
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

	@FXML
	public void rightClicked()
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

	@FXML
	public void plusClicked()
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

	@FXML
	public void zeroClicked()
	{
		BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().setVelocityX(0);
		BALL_OBS_LIST_REFERENCE.get(ballSelected).getParameter().setVelocityY(0);
	}

	Circle testCirle = new Circle();
	Random random = new Random();

	@FXML
	public void rightPaneDragged(MouseEvent event)
	{
		line.setEndX(event.getX());
		line.setEndY(event.getY());
	}

	@FXML
	public void rightPanePressed(MouseEvent event)
	{
		event.setDragDetect(true);
		testCirle.setRadius(random.nextInt(30));
		testCirle.setCenterX(event.getX());
		testCirle.setCenterY(event.getY());

		line.setStartX(event.getX());
		line.setStartY(event.getY());

		RIGHT_PANE.getChildren().add(testCirle);
		RIGHT_PANE.getChildren().add(line);

	}

	@FXML
	public void rightPaneReleased(MouseEvent event)
	{
		event.setDragDetect(false);

		int weight = ((int) testCirle.getRadius());
		int x = ((int) line.getStartX());
		int y = ((int) line.getStartY());
		int Vx = (int) (line.getStartX() - line.getEndX());
		int Vy = ((int) (line.getStartY() - line.getEndY()));

		ballController.addNewBall(x, y, Vx, Vy, weight);

		updateTable();
		ControllerFXML_ThreadTable.threadList();
		controllerFXML_CollisionTable.generateColisionBitTable();

		RIGHT_PANE.getChildren().remove(testCirle);
		RIGHT_PANE.getChildren().remove(line);

		refreshScreen();

	}

	@FXML
	public void onMouseClickedClear(MouseEvent event)
	{
		// ballController.clearBallList();
		// ControllerFXML_ThreadTable.threadList();
		// RIGHT_PANE.getChildren().clear();
		// RIGHT_PANE.getChildren().addAll(ballController.getNodeFromBallList());
	}

	// *********************
	// Initialize Functions
	// *********************

	private void initialzeInstances()
	{
		controllerFXML_Base = this;

		try
		{
			startInstance = (StartInterface) Class.forName("Start").newInstance();
		} catch (InstantiationException e)
		{

			e.printStackTrace();
		} catch (IllegalAccessException e)
		{

			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{

			e.printStackTrace();
		}

		BALL_OBS_LIST_REFERENCE = ballController.getBallList();
		for (Ball ball : BALL_OBS_LIST_REFERENCE)
		{
			BALL_PARAMETER_OBS_LIST.add(ball.getParameter());
		}
	}

	// Table

	private TableColumn<BallParameter, Integer> numberColumn = new TableColumn<BallParameter, Integer>("Number");
	private TableColumn<BallParameter, Integer> PosXColumn = new TableColumn<BallParameter, Integer>("PosX");
	private TableColumn<BallParameter, Integer> PosYColumn = new TableColumn<BallParameter, Integer>("PosY");
	private TableColumn<BallParameter, Integer> VxColumn = new TableColumn<BallParameter, Integer>("Vx");
	private TableColumn<BallParameter, Integer> VyColumn = new TableColumn<BallParameter, Integer>("Vy");
	private TableColumn<BallParameter, Integer> WeightColumn = new TableColumn<BallParameter, Integer>("Weight");

	private void initializeTable()
	{
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
		NodeTable.getColumns().addAll(numberColumn, PosXColumn, PosYColumn, VxColumn, VyColumn, WeightColumn);
		NodeTable.setItems(BALL_PARAMETER_OBS_LIST);
	}

	public void initialize(URL url, ResourceBundle resourceBundle)
	{

		initialzeInstances();
		initializeTable();

		// Choice Box Parameters

		choiceBoxMode.setItems(FXCollections.observableArrayList("Position", "Velocity"));
		choiceBoxMode.getSelectionModel().selectFirst();

		// Listeners

		inputGravity.textProperty().addListener((observable, oldValue, newValue) ->
		{
			generalParameters.setGravity(Integer.parseInt(newValue));
		});

		NodeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
			System.out.println(newSelection.toString());
		});

		// Set Screnn Size for Game

		screenParameter.setScreenMaxX((int) RIGHT_PANE.getPrefWidth());
		screenParameter.setScreenMaxY((int) RIGHT_PANE.getPrefHeight());

	}

	private void updateTable()
	{
		BALL_PARAMETER_OBS_LIST.clear();
		for (Ball ball : BALL_OBS_LIST_REFERENCE)
		{
			BALL_PARAMETER_OBS_LIST.add(ball.getParameter());
		}
	}

	private void refreshScreen()
	{
		RIGHT_PANE.getChildren().clear();
		RIGHT_PANE.getChildren().addAll(ballController.getNodeFromBallList());
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

	public Pane getRightPane()
	{
		return RIGHT_PANE;
	}

}
