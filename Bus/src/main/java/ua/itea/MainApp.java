package ua.itea;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MainApp extends Application {

	private static final String APP_NAME = "Bus";
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 900;
	private static final int MAX_PASSENGERS_ON_STATION = 70;
	private static final int BUS1_MAX = 20;
	private static final int BUS2_MAX = 7;
	private static final int BUS3_MAX = 25;
	private TextArea t1 = new TextArea();
	private TextArea t2 = new TextArea();
	private TextArea t3 = new TextArea();

	private HBox topHBox;
	private HBox leftHBox;
	private HBox centerHBox;
	private HBox bottomHBox;

	private Pane topLeftPane;
	private Pane topCenterPane;
	private Pane topRightPane;
	private Label lTopLeft;
	private Label lTopLeftSpeed;
	private Label lTopLeftOccupied;
	private Label lTopLeftMax;
	private Label lTopCenter;
	private Label lTopCenterSpeed;
	private Label lTopCenterOccupied;
	private Label lTopCenterMax;
	private Label lTopRight;
	private Label lTopRightSpeed;
	private Label lTopRightOccupied;
	private Label lTopRightMax;

	private Pane centerLeftPane;
	private Pane centerCenterPane;

	private Pane bottomPane;

	private TranslateTransition trBus1;
	private TranslateTransition trBus2;
	private TranslateTransition trBus3;

	private Button exitBtn;

	public static void main(String[] args) {
		launch(args);
	}

	private void initLayout() {
		topHBox = new HBox();
		topHBox.setPrefHeight(300);
		leftHBox = new HBox();
		centerHBox = new HBox();
		bottomHBox = new HBox();
		bottomHBox.setPrefHeight(300);

		topLeftPane = new Pane();
		topCenterPane = new Pane();
		topRightPane = new Pane();
		topLeftPane.setPrefSize(400, 300);
		topCenterPane.setPrefSize(400, 300);
		topRightPane.setPrefSize(400, 300);

		centerLeftPane = new Pane();
		centerCenterPane = new Pane();

		bottomPane = new Pane();
		bottomPane.setPrefHeight(300);

		topHBox.getChildren().addAll(topLeftPane, topCenterPane, topRightPane);
		leftHBox.getChildren().add(centerLeftPane);
		centerHBox.getChildren().add(centerCenterPane);
		bottomHBox.getChildren().add(bottomPane);

		lTopLeft = new Label("Bus1");
		lTopLeft.setPrefSize(100, 20);
		lTopLeft.setLayoutX(30);
		lTopLeft.setLayoutY(20);
		lTopLeftSpeed = new Label("");
		lTopLeftSpeed.setPrefSize(100, 20);
		lTopLeftSpeed.setLayoutX(30);
		lTopLeftSpeed.setLayoutY(50);
		lTopLeftOccupied = new Label("");
		lTopLeftOccupied.setPrefSize(100, 20);
		lTopLeftOccupied.setLayoutX(110);
		lTopLeftOccupied.setLayoutY(50);
		lTopLeftMax = new Label("");
		lTopLeftMax.setPrefSize(100, 20);
		lTopLeftMax.setLayoutX(180);
		lTopLeftMax.setLayoutY(50);
		t1 = new TextArea();
		t1.setLayoutX(30);
		t1.setLayoutY(88);
		lTopCenter = new Label("Bus2");
		lTopCenter.setPrefSize(100, 20);
		lTopCenter.setLayoutX(30);
		lTopCenter.setLayoutY(20);
		lTopCenterSpeed = new Label("");
		lTopCenterSpeed.setPrefSize(100, 20);
		lTopCenterSpeed.setLayoutX(30);
		lTopCenterSpeed.setLayoutY(50);
		lTopCenterOccupied = new Label("");
		lTopCenterOccupied.setPrefSize(100, 20);
		lTopCenterOccupied.setLayoutX(110);
		lTopCenterOccupied.setLayoutY(50);
		lTopCenterMax = new Label("");
		lTopCenterMax.setPrefSize(100, 20);
		lTopCenterMax.setLayoutX(180);
		lTopCenterMax.setLayoutY(50);
		t2 = new TextArea();
		t2.setLayoutX(30);
		t2.setLayoutY(88);
		lTopRight = new Label("Bus3");
		lTopRight.setPrefSize(100, 20);
		lTopRight.setLayoutX(30);
		lTopRight.setLayoutY(20);
		lTopRightSpeed = new Label("");
		lTopRightSpeed.setPrefSize(100, 20);
		lTopRightSpeed.setLayoutX(30);
		lTopRightSpeed.setLayoutY(50);
		lTopRightOccupied = new Label("");
		lTopRightOccupied.setPrefSize(100, 20);
		lTopRightOccupied.setLayoutX(110);
		lTopRightOccupied.setLayoutY(50);
		lTopRightMax = new Label("");
		lTopRightMax.setPrefSize(100, 20);
		lTopRightMax.setLayoutX(180);
		lTopRightMax.setLayoutY(50);
		t3 = new TextArea();
		t3.setLayoutX(30);
		t3.setLayoutY(88);

		t1.setPrefWidth(330.0);
		t1.setPrefHeight(200.0);
		t2.setPrefWidth(330.0);
		t2.setPrefHeight(200.0);
		t3.setPrefWidth(330.0);
		t3.setPrefHeight(200.0);

		/*
		 * exitBtn = new Button("Exit"); exitBtn.setPrefSize(100, 20);
		 * exitBtn.setLayoutX(10); exitBtn.setLayoutY(10);
		 */
		/*
		 * Label rt = new Label("asdasdasd"); rt.setPrefSize(100, 20);
		 * rt.setLayoutX(30); rt.setLayoutY(20);
		 */

		topLeftPane.getChildren().addAll(lTopLeft, lTopLeftSpeed, lTopLeftOccupied, lTopLeftMax, t1);
		topCenterPane.getChildren().addAll(lTopCenter, lTopCenterSpeed, lTopCenterOccupied, lTopCenterMax, t2);
		topRightPane.getChildren().addAll(lTopRight, lTopRightSpeed, lTopRightOccupied, lTopRightMax, t3);

		Rectangle rect = new Rectangle(0, 0, 100, 50);
		InputStream imgStream = this.getClass().getClassLoader().getResourceAsStream("images/etalon.png");
		Image img = new Image(imgStream);
		rect.setFill(new ImagePattern(img));

		centerLeftPane.getChildren().addAll(rect);
		// bottomPane.getChildren().add(rt);

		trBus1 = new TranslateTransition(Duration.seconds(15), rect);
		trBus1.setByX(600);
		trBus1.play();

		trBus1 = new TranslateTransition(Duration.seconds(2), rect);
		trBus1.setByY(100);
		trBus1.play();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initLayout();
		BorderPane root = new BorderPane(centerHBox, topHBox, null, bottomHBox, leftHBox);
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		try {
			primaryStage.setTitle(APP_NAME);

			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

		process();

		/*
		 * primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { public void
		 * handle(WindowEvent we) { System.out.println("Stage is closing"); } });
		 * primaryStage.close();
		 */

	}

	private void process() {

		BusStation bs = new BusStation();
		Random random = new Random();

		List<City> route = bs.getRoute();
		List<City> bl1 = new ArrayList<City>();
		bl1.addAll(route);
		List<City> bl2 = new ArrayList<City>();
		bl2.addAll(route);
		List<City> bl3 = new ArrayList<City>();
		bl3.addAll(route);

		Object o = new Object();
		CountDownLatch cl1 = new CountDownLatch(BUS1_MAX);
		Bus b1 = new Bus("LAZ", BUS1_MAX, 0, bl1, cl1, t1, lTopLeftOccupied, o);
		lTopLeft.setText(b1.getName());
		lTopLeftSpeed.setText("Speed:" + b1.getSpeed());
		lTopLeftOccupied.setText("Occupied:" + b1.getCurPassengers());
		lTopLeftMax.setText("Max:" + b1.getMaxPassengers());

		Object o1 = new Object();
		CountDownLatch cl2 = new CountDownLatch(BUS2_MAX);
		Bus b2 = new Bus("Mercedes", BUS2_MAX, 0, bl2, cl2, t2, lTopCenterOccupied, o1);
		lTopCenter.setText(b2.getName());
		lTopCenterSpeed.setText("Speed:" + b2.getSpeed());
		lTopCenterOccupied.setText("Occupied:" + b2.getCurPassengers());
		lTopCenterMax.setText("Max:" + b2.getMaxPassengers());

		Object o2 = new Object();
		CountDownLatch cl3 = new CountDownLatch(BUS3_MAX);
		Bus b3 = new Bus("Bogdan", BUS3_MAX, 0, bl3, cl3, t3, lTopRightOccupied, o2);
		lTopRight.setText(b3.getName());
		lTopRightSpeed.setText("Speed:" + b3.getSpeed());
		lTopRightOccupied.setText("Occupied:" + b3.getCurPassengers());
		lTopRightMax.setText("Max:" + b3.getMaxPassengers());

		for (int i = 0; i < MAX_PASSENGERS_ON_STATION; i++) {
			new Passenger("Man" + random.nextInt(50) + random.nextInt(50), b1, b2, b3);
		}
	}

}
