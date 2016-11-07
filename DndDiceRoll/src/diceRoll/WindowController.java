package diceRoll;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;
import diceRoll.Die;

public class WindowController {
	Random rand = new Random();
	File diceImageFile = null;
	private double startMoveX = -1, startMoveY = -1;
	private Boolean dragging = false;
	private Rectangle moveTrackingRect;
	public Image screencap;
	private Popup moveTrackingPopup;
	public int dieNumber = 1;
	boolean choiceBoxFlag;
	public int dieIdNumber = 0;
	public List<Die> CreatedDies = new ArrayList<Die>();
	boolean customDieFlag = false;
	public Die dieLocations[] = new Die[6];

	@FXML
	private VBox window;

	@FXML
	private ImageView ImgIconDice;

	@FXML
	private Label lblTitle;

	@FXML
	private Label lblClose;

	@FXML
	private VBox tabbox;

	@FXML
	private VBox VbTabContent;

	@FXML
	private HBox HbTabTitle;

	@FXML
	private Label tabTitleText;

	@FXML
	private HBox HbDiceContainer;
	@FXML
	private ChoiceBox<String> dieTypeSelection;

	@FXML
	private Button submitDie;

	// Took way too long to realize that I could just init all of these in one
	// line.
	@FXML
	private Label diceLabel1, diceLabel2, diceLabel3, diceLabel4, diceLabel5, diceLabel6;

	@FXML
	private Label customDieLabel;

	@FXML
	private TextField customDieNumber;

	@FXML
	private TextField rollNumber;

	@FXML
	private Label rollsDice1, rollsDice2, rollsDice3, rollsDice4, rollsDice5, rollsDice6;

	@FXML
	private Label customSides1, customSides2, customSides3, customSides4, customSides5, customSides6;

	@FXML
	private ImageView exit1, exit2, exit3, exit4, exit5, exit6;

	@FXML
	private Button rollDice;

	@FXML
	private Label diceTotal1, diceTotal2, diceTotal3, diceTotal4, diceTotal5, diceTotal6, diceTotal;

	@FXML
	private Label errorMessage;

	@FXML
	void initialize() {
		customDieNumber.setText("");
		dieTypeSelection.getItems().add("Four-Sided Die");
		dieTypeSelection.getItems().add("Six-Sided Die");
		dieTypeSelection.getItems().add("Eight-Sided Die");
		dieTypeSelection.getItems().add("Ten-Sided Die");
		dieTypeSelection.getItems().add("Tweleve-Sided Die");
		dieTypeSelection.getItems().add("Twenty-Sided Die");
		dieTypeSelection.getItems().add("Custom Die");

		dieTypeSelection.getSelectionModel().select(1);
		dieTypeSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String oldString, String newString) {

				if (newString == "Custom Die") {
					customDieLabel.setVisible(true);
					customDieNumber.setVisible(true);

					customDieFlag = true;
				} else {
					customDieLabel.setVisible(false);
					customDieNumber.setVisible(false);

					customDieFlag = false;
				}
			}
		});

	}

	@FXML
	void createDie(ActionEvent event) {

		int custom = 2;
		int rolls = 1;
		errorMessage.setText("");
		if (customDieFlag == false) {

		} else {
			try {
				custom = Integer.parseInt(customDieNumber.getText());
				if (custom < 2) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				errorMessage.setText("Not a Valid Number of Sides");
				return;
			}
		}

		try {
			rolls = Integer.parseInt(rollNumber.getText());
			if (rolls < 1) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			rolls = 1;
		}

		// This will create a unique id for each created die
		String dieName = "Die" + Integer.toString(dieIdNumber);
		String selection = dieTypeSelection.getValue().toString();

		switch (selection) {
		case "Four-Sided Die":
			Die die = new Die(4, dieName, selection, rolls);
			CreatedDies.add(die);
			break;

		case "Six-Sided Die":
			Die die6 = new Die(6, dieName, selection, rolls);
			CreatedDies.add(die6);
			break;

		case "Eight-Sided Die":
			Die die8 = new Die(8, dieName, selection, rolls);
			CreatedDies.add(die8);
			break;

		case "Ten-Sided Die":
			Die die10 = new Die(10, dieName, selection, rolls);
			CreatedDies.add(die10);
			break;

		case "Tweleve-Sided Die":
			Die die12 = new Die(12, dieName, selection, rolls);
			CreatedDies.add(die12);
			break;

		case "Twenty-Sided Die":
			Die die20 = new Die(20, dieName, selection, rolls);
			CreatedDies.add(die20);
			break;

		case "Custom Die":
			Die dieC = new Die(custom, dieName, selection, rolls);
			CreatedDies.add(dieC);
			break;
		}

		if (diceLabel1.getText() == "") {
			// if Dice is a Custom dice, add in Custom sides amount
			if (selection == "Custom Die") {
				customSides1.setText((CreatedDies.get(dieIdNumber).getNumber()) + " sides");
				// Otherwise print normal text
			}
			diceLabel1.setText((CreatedDies.get(dieIdNumber).getDieLabel()));
			// This adds the amount of rolls
			rollsDice1.setText(Integer.toString(CreatedDies.get(dieIdNumber).getRolls()) + " Roll(s)");
			// This sets the DieIDNumber to a location in an array. Allowing us
			// to manage where Dice are
			// Located on the dice rolling screen
			dieLocations[0] = CreatedDies.get(dieIdNumber);
		} else if (diceLabel2.getText() == "") {

			if (selection == "Custom Die") {
				customSides2.setText((CreatedDies.get(dieIdNumber).getNumber()) + " sides");
			}

			diceLabel2.setText((CreatedDies.get(dieIdNumber).getDieLabel()));

			rollsDice2.setText(Integer.toString(CreatedDies.get(dieIdNumber).getRolls()) + " Roll(s)");

			dieLocations[1] = CreatedDies.get(dieIdNumber);
		} else if (diceLabel3.getText() == "") {

			if (selection == "Custom Die") {
				customSides3.setText((CreatedDies.get(dieIdNumber).getNumber()) + " sides");
			}

			diceLabel3.setText((CreatedDies.get(dieIdNumber).getDieLabel()));

			rollsDice3.setText(Integer.toString(CreatedDies.get(dieIdNumber).getRolls()) + " Roll(s)");

			dieLocations[2] = CreatedDies.get(dieIdNumber);
		} else if (diceLabel4.getText() == "") {

			if (selection == "Custom Die") {
				customSides4.setText((CreatedDies.get(dieIdNumber).getNumber()) + " sides");
			}

			diceLabel4.setText((CreatedDies.get(dieIdNumber).getDieLabel()));

			rollsDice4.setText(Integer.toString(CreatedDies.get(dieIdNumber).getRolls()) + " Roll(s)");

			dieLocations[3] = CreatedDies.get(dieIdNumber);
		} else if (diceLabel5.getText() == "") {

			if (selection == "Custom Die") {
				customSides5.setText((CreatedDies.get(dieIdNumber).getNumber()) + " sides");
			}

			diceLabel5.setText((CreatedDies.get(dieIdNumber).getDieLabel()));

			rollsDice5.setText(Integer.toString(CreatedDies.get(dieIdNumber).getRolls()) + " Roll(s)");

			dieLocations[4] = CreatedDies.get(dieIdNumber);
		} else if (diceLabel6.getText() == "") {

			if (selection == "Custom Die") {
				customSides6.setText((CreatedDies.get(dieIdNumber).getNumber()) + " sides");
			}

			diceLabel6.setText((CreatedDies.get(dieIdNumber).getDieLabel()));

			rollsDice6.setText(Integer.toString(CreatedDies.get(dieIdNumber).getRolls()) + " Roll(s)");

			dieLocations[5] = CreatedDies.get(dieIdNumber);
		} else {
			errorMessage.setText("No Room. Die was not Created.");
			CreatedDies.remove(dieIdNumber);
			return;
		}
		dieIdNumber = dieIdNumber + 1;
	}

	@FXML
	void closeWindow(MouseEvent event) {
		((Label) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	void IconDiceChange(MouseEvent event) {

		int newDieNumber = rand.nextInt(6) + 1;

		if (dieNumber == newDieNumber) {
			newDieNumber = newDieNumber + 1;
			if (newDieNumber > 6) {
				newDieNumber = newDieNumber - 2;
			}
		}

		File file = new File((Integer.toString(newDieNumber) + ".png"));

		Image die = null;
		try {

			die = new Image(file.toURI().toURL().toString());

		} catch (MalformedURLException e) {
			System.out.println("The Image could not be found");
		}

		ImgIconDice.setImage(die);
		dieNumber = newDieNumber;
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
		moveTrackingPopup.setOnHidden((e) -> resetMoveOperation());

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
	public void openOrCloseTab(MouseEvent event) {
		choiceBoxFlag = dieTypeSelection.isVisible();

		tabAnimatedResizeVbTabContent(VbTabContent);
		tabAnimatedResizeHBox(HbTabTitle);
		tabResizeHbDiceContentResize(HbDiceContainer);
		/*
		 * if (HbDiceContainer.getWidth() == 380){
		 * HbDiceContainer.setPrefWidth(560); } else{
		 * HbDiceContainer.setPrefWidth(380); }
		 */

		if (choiceBoxFlag == true) {
			dieTypeSelection.setVisible(false);
			submitDie.setVisible(false);
			rollNumber.setVisible(false);
		} else {
			dieTypeSelection.setVisible(true);
			submitDie.setVisible(true);
			rollNumber.setVisible(true);
		}

		if (choiceBoxFlag == true) {

			customDieLabel.setVisible(false);
			customDieNumber.setVisible(false);

		} else {

			if (customDieFlag == false) {

				customDieLabel.setVisible(false);
				customDieNumber.setVisible(false);
			} else {

				customDieLabel.setVisible(true);
				customDieNumber.setVisible(true);
			}
		}
	}

	public void tabResizeHbDiceContentResize(HBox DiceContainer) {
		int width = (int) DiceContainer.getWidth();

		if (width == 399) {
			Timeline timeline = new Timeline();
			timeline.getKeyFrames()
					.add(new KeyFrame(Duration.ZERO, new KeyValue(DiceContainer.prefWidthProperty(), 399)));
			timeline.getKeyFrames()
					.add(new KeyFrame(Duration.seconds(.6), new KeyValue(DiceContainer.prefWidthProperty(), 800)));

			timeline.play();
		} else if (width > 399) {
			Timeline timeline = new Timeline();
			timeline.getKeyFrames()
					.add(new KeyFrame(Duration.ZERO, new KeyValue(DiceContainer.prefWidthProperty(), 800)));
			timeline.getKeyFrames()
					.add(new KeyFrame(Duration.seconds(.6), new KeyValue(DiceContainer.prefWidthProperty(), 399)));

			timeline.play();
		}
	}

	public void tabAnimatedResizeVbTabContent(VBox TabContent) {
		int width = (int) TabContent.getWidth();
		if (width == 0) {
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(TabContent.prefWidthProperty(), 0)));
			timeline.getKeyFrames()
					.add(new KeyFrame(Duration.seconds(.6), new KeyValue(TabContent.prefWidthProperty(), 180)));

			timeline.play();
		} else {
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(TabContent.prefWidthProperty(), 180)));
			timeline.getKeyFrames()
					.add(new KeyFrame(Duration.seconds(.6), new KeyValue(TabContent.prefWidthProperty(), 0)));

			timeline.play();
		}

	}

	public void tabAnimatedResizeHBox(HBox TabTitle) {
		int width = (int) TabTitle.getWidth();
		if (width == 0) {
			Timeline timeline = new Timeline();

			timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(TabTitle.prefWidthProperty(), 0)));
			timeline.getKeyFrames()
					.add(new KeyFrame(Duration.seconds(.6), new KeyValue(TabTitle.prefWidthProperty(), 180)));

			timeline.play();

		} else {
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, new KeyValue(TabTitle.prefWidthProperty(), 180)));
			timeline.getKeyFrames()
					.add(new KeyFrame(Duration.seconds(.6), new KeyValue(TabTitle.prefWidthProperty(), 0)));

			timeline.play();

		}

	}

	// This block of methods is not good. There is def a better way to do this
	// and I'm sorry for this.
	@FXML
	void destroyDie1(MouseEvent event) {
		diceLabel1.setText("");
		customSides1.setText("");
		rollsDice1.setText("");
		diceTotal1.setText("");
		dieLocations[0] = null;
	}

	@FXML
	void destroyDie2(MouseEvent event) {
		diceLabel2.setText("");
		customSides2.setText("");
		rollsDice2.setText("");
		diceTotal2.setText("");
		dieLocations[1] = null;
	}

	@FXML
	void destroyDie3(MouseEvent event) {
		diceLabel3.setText("");
		customSides3.setText("");
		rollsDice3.setText("");
		diceTotal3.setText("");
		dieLocations[2] = null;
	}

	@FXML
	void destroyDie4(MouseEvent event) {
		diceLabel4.setText("");
		customSides4.setText("");
		rollsDice4.setText("");
		diceTotal4.setText("");
		dieLocations[3] = null;
	}

	@FXML
	void destroyDie5(MouseEvent event) {
		diceLabel5.setText("");
		customSides5.setText("");
		rollsDice5.setText("");
		diceTotal5.setText("");
		dieLocations[4] = null;
	}

	@FXML
	void destroyDie6(MouseEvent event) {
		diceLabel6.setText("");
		customSides6.setText("");
		rollsDice6.setText("");
		diceTotal6.setText("");
		dieLocations[5] = null;
	}

	@FXML
	void rollDice(ActionEvent event) {
		Random rand = new Random();
		int diceRolls[] = new int[6];
		int Total = 0;
		for (int i = 0; i < 6; i++) {

			Die dieToRoll = dieLocations[i];

			if (dieToRoll == null) {

			} else {
				int numberOfSides = dieToRoll.getNumber();
				int numberOfRolls = dieToRoll.getRolls();
				int sum = 0;

				for (int x = 0; x < numberOfRolls; x++) {
					int roll = rand.nextInt(numberOfSides) + 1;
					sum = sum + roll;

				}

				diceRolls[i] = sum;
				sum = 0;
			}

		}

		if (diceRolls[0] > 0) {
			diceTotal1.setText(Integer.toString(diceRolls[0]));
			Total = Total + diceRolls[0];
		}
		if (diceRolls[1] > 0) {
			diceTotal2.setText(Integer.toString(diceRolls[1]));
			Total = Total + diceRolls[1];
		}
		if (diceRolls[2] > 0) {
			diceTotal3.setText(Integer.toString(diceRolls[2]));
			Total = Total + diceRolls[2];
		}
		if (diceRolls[3] > 0) {
			diceTotal4.setText(Integer.toString(diceRolls[3]));
			Total = Total + diceRolls[3];
		}
		if (diceRolls[4] > 0) {
			diceTotal5.setText(Integer.toString(diceRolls[4]));
			Total = Total + diceRolls[4];
		}
		if (diceRolls[5] > 0) {
			diceTotal6.setText(Integer.toString(diceRolls[5]));
			Total = Total + diceRolls[5];
		}
		if (Total > 0) {
			diceTotal.setText(Integer.toString(Total));
		}

	}

	

}
