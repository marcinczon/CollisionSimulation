
import FX_Controllers.ControllerFXML_Base;
import FX_Controllers.ControllerFXML_CollisionTable;
import Parameters.GeneralParameters;
import Parameters.ScreenParameter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application
{

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

	public static void ShowStage2()
	{
		stage2.show();
	}

	public static void ShowStage3()
	{
		stage3.show();
	}

	public static void ShowStage4()
	{
		stage4.show();
	}

	public static void main(String[] args)
	{
		launch();
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
			primaryStage.show();

			stage2.setScene(scene2);
			stage2.setResizable(true);
			stage2.show();

			stage3.setScene(scene3);
			stage3.setResizable(true);
			stage3.show();

			stage4.setScene(scene4);
			stage4.setResizable(false);
			stage4.show();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
