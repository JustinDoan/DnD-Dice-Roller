package diceRoll;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		  Parent parent = FXMLLoader.load(getClass().getResource("DndWindow.fxml"));
		  Scene scene = null;
		  
		  String osName = System.getProperty("os.name");
		  if( osName != null && osName.startsWith("Windows") ) {

		    scene = (new WindowsDropShadow()).getShadowScene(parent);
		    primaryStage.initStyle(StageStyle.TRANSPARENT);

		  } else {
		    scene = new Scene( parent );
		    primaryStage.initStyle(StageStyle.UNDECORATED);
		  }

		    scene.getStylesheets().add(getClass().getResource("decoration.css").toString());
		    
		    primaryStage.setTitle("Title");
		    primaryStage.setScene( scene );;
		    primaryStage.show();
		    
		  }
		
}


