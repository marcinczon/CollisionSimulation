
import FX_Controllers.ControllerFXML_Base;
import FX_Controllers.ControllerFXML_CollisionTable;
import Parameters.GeneralParameters;
import Parameters.ScreenParameter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.StartInterface;

public class Start extends Application implements StartInterface
{

	public static Start start;
	// Future work
	// - Change calculating of ball, cant move thrue each other
	// - Add paddle to play

	// Reference to class
	static GeneralParameters generalParameters = new GeneralParameters();
	static ScreenParameter screenParameter = new ScreenParameter();

	static Stage stage2 = new Stage();
	static Stage stage3 = new Stage();
	static Stage stage4 = new Stage();

	static Parent collisionTableParrent;
	static Parent messageParent;

	public Start()
	{
		start = this;
	}

	public void test()
	{
		System.out.println("asdasd");
	}

	public void ShowStage2()
	{
		stage2.show();
	}

	public void ShowStage3()
	{
		stage3.show();
	}

	public void ShowStage4()
	{
		stage4.show();
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		try
		{
			messageParent = FXMLLoader.load(getClass().getResource("FX_FXML/FXML_MessageTable.fxml"));
			collisionTableParrent = FXMLLoader.load(getClass().getResource("FX_FXML/FXML_CollisionTable.fxml"));
			Parent baseParent = FXMLLoader.load(getClass().getResource("FX_FXML/FXML_Base.fxml"));
			Parent threedTableParent = FXMLLoader.load(getClass().getResource("FX_FXML/FXML_ThreadTable.fxml"));

			Scene scene1 = new Scene(baseParent);
			Scene scene2 = new Scene(threedTableParent);
			Scene scene3 = new Scene(collisionTableParrent);
			Scene scene4 = new Scene(messageParent);

			primaryStage.setScene(scene1);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Simulation");
			primaryStage.show();

			stage2.setScene(scene2);
			stage2.setResizable(true);
			stage2.setTitle("Threed");
			stage2.show();

			stage3.setScene(scene3);
			stage3.setResizable(true);
			stage3.setTitle("Collision");
			stage3.show();

			stage4.setScene(scene4);
			stage4.setResizable(false);
			stage4.setTitle("Messages");
			stage4.show();

			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX(0);
			primaryStage.setY(0);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{

		launch();
	}

}
