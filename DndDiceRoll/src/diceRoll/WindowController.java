package diceRoll;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;

public class WindowController {
	Random rand = new Random();
	File diceImageFile = null;
	private double startMoveX = -1, startMoveY = -1;
	private Boolean dragging = false;
	private Rectangle moveTrackingRect;
	public Image screencap;
	private Popup moveTrackingPopup;
	
	
	
	
	@FXML
    private VBox window;
	@FXML
    private ImageView ImgIconDice;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblClose;

    @FXML
    private Label Content;

    @FXML
    void closeWindow(MouseEvent event) {
    	((Label)event.getSource()).getScene().getWindow().hide();
    }
    @FXML
    void IconDiceChange(MouseEvent event) {

    	int diceNumber = rand.nextInt(6) + 1;
    	
    	File file = new File((Integer.toString(diceNumber)+ ".png"));
    	
    	Image die = null;
		try {
			
			die = new Image(file.toURI().toURL().toString());
			
		} catch (MalformedURLException e) {
			System.out.println("The Image could not be found");
		}
    	
    	ImgIconDice.setImage(die);
    }
    @FXML
    public void startMoveWindow(MouseEvent evt) {
    	  setScreencap();
    	  startMoveX = evt.getScreenX();
    	  startMoveY = evt.getScreenY();
    	  dragging = true;
    	  
    	  
    	  moveTrackingRect = new Rectangle();
    	  moveTrackingRect.setWidth(window.getWidth());
    	  moveTrackingRect.setHeight(window.getHeight());
    	  moveTrackingRect.setOpacity(0.7);
    	  ImagePattern imagePattern = new ImagePattern(screencap);
    	  moveTrackingRect.setFill(imagePattern);
    	  

    	  moveTrackingPopup = new Popup();
    	  moveTrackingPopup.getContent().add(moveTrackingRect);
    	  moveTrackingPopup.show(window.getScene().getWindow());
    	  moveTrackingPopup.setOnHidden( (e) -> resetMoveOperation());

    	  
    	  
    	  
    }
    private void resetMoveOperation() {
    	  startMoveX = 0;
    	  startMoveY = 0;
    	  dragging = false;
    	  moveTrackingRect = null;
    	  window.setVisible(true);
    	}
    @FXML
    public void moveWindow(MouseEvent evt) {
    	
    	if (dragging) {
    		
    	    double endMoveX = evt.getScreenX();
    	    double endMoveY = evt.getScreenY();

    	    Window w = window.getScene().getWindow();

    	    double stageX = w.getX();
    	    double stageY = w.getY();
    	    
    	    moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
    	    moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
    	    window.setVisible(false);
    	  } 
    }
    @FXML
    public void endMoveWindow(MouseEvent evt) {
    	if (dragging) {
    	    double endMoveX = evt.getScreenX();
    	    double endMoveY = evt.getScreenY();

    	    Window w = window.getScene().getWindow();

    	    double stageX = w.getX();
    	    double stageY = w.getY();

    	    w.setX(stageX + (endMoveX - startMoveX));
    	    w.setY(stageY + (endMoveY - startMoveY));

    	    if (moveTrackingPopup != null) {
    	      moveTrackingPopup.hide();
    	      moveTrackingPopup = null;
    	    }
    	  }

    	  resetMoveOperation();
    }
    @FXML
    public void setScreencap() {
        WritableImage image = window.snapshot(new SnapshotParameters(), null);
        screencap = (Image) image;
        
        
    }
    @FXML
    void startResizeWindow(MouseEvent evt) {
    	startMoveX = evt.getScreenX();
  	  	startMoveY = evt.getScreenY();
  	  	dragging = true;
    }
    
    @FXML
    void resizeWindow(MouseEvent evt) {

    	if (dragging){
    		
    		
    		double mouseLocationX = evt.getScreenX();
    	    double mouseLocationY = evt.getScreenY();

    	    Window w = window.getScene().getWindow();


    	    double stageWidth = w.getWidth();
    	    double stageHeight = w.getHeight();
    	    
    	    System.out.println(stageWidth + (startMoveX - mouseLocationX));
    	    System.out.println(stageHeight + (startMoveY - mouseLocationY));
    	    window.setPrefWidth(stageWidth - (startMoveX - mouseLocationX));
    	    window.setPrefHeight(stageHeight - (startMoveY - mouseLocationY));
    	}
    }

    @FXML
    void stopResize(MouseEvent evt) {
    	startMoveX = 0;
    	startMoveY = 0;
    }


}
