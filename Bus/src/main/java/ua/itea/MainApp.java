package ua.itea;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

	private static final String APP_NAME = "Bus Competition";
	private static final int WIDTH = 1180;
	private static final int HEIGHT = 760;
	private static final int MAX_PASSENGERS_ON_STATION = 70;
	private static final int BUS1_MAX = 20;
	private static final int BUS2_MAX = 7;
	private static final int BUS3_MAX = 25;
	public static final int BUS_PATH = 900;
	public static final String BUS1_IMG_PATH = "images/laz.png";
	private static final String BUS2_IMG_PATH = "images/merc.png";
	private static final String BUS3_IMG_PATH = "images/bogdan.png";
	private static final String BUS1_NAME = "LAZ";
	private static final String BUS2_NAME = "Mercedes";
	private static final String BUS3_NAME = "Bogdan";
	private static final String CSS_STYLE_PATH = "application.css";
	public static final int BUS_WIDTH = 160;
	public static final int BUS_HEIGHT = 60;
	public static final int BUS_START_Y = 100;
	public static final int BUS_DELTA_Y = 85;
	private DBWorker dbWorker;
	private List<City> cityList;
	private Player player;
	private MusicStopThread musicStopThread;
		
	private TextArea t1;
	private TextArea t2;
	private TextArea t3;
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
	private Rectangle bus1Rect;
	private Rectangle bus2Rect;
	private Rectangle bus3Rect;
	private Line bus1Line;
	private Line bus2Line;
	private Line bus3Line;
	private TranslateTransition trBus1;
	private TranslateTransition trBus2;
	private TranslateTransition trBus3;
	private int busStopsCnt;
	private int busMoveDst;
	private Button exitBtn;
	private Button startBtn;

	public MainApp() {
		dbWorker = new DBWorker();
		busStopsCnt = dbWorker.getAllCitiesCnt();
		busMoveDst = BUS_PATH / busStopsCnt;
		cityList = dbWorker.getAllCities();	
		
		lTopLeft = new Label(BUS1_NAME);
		lTopCenter = new Label(BUS2_NAME);
		lTopRight = new Label(BUS3_NAME);

		topHBox = new HBox();
		topHBox.setPrefHeight(300);
		leftHBox = new HBox();
		centerHBox = new HBox();
		bottomHBox = new HBox();
		bottomHBox.setPrefSize(WIDTH, 100);

		topLeftPane = new Pane();
		topCenterPane = new Pane();
		topRightPane = new Pane();
		topLeftPane.setPrefSize(400, 300);
		topCenterPane.setPrefSize(400, 300);
		topRightPane.setPrefSize(400, 300);

		centerLeftPane = new Pane();
		centerLeftPane.setPrefWidth(100);
		centerCenterPane = new Pane();
		centerCenterPane.setPrefWidth(900);

		bottomPane = new Pane();
		bottomPane.setPrefSize(WIDTH, 100);

		topHBox.getChildren().addAll(topLeftPane, topCenterPane, topRightPane);
		leftHBox.getChildren().add(centerLeftPane);
		centerHBox.getChildren().add(centerCenterPane);
		bottomHBox.getChildren().add(bottomPane);

		lTopLeft.setPrefSize(100, 20);
		lTopLeft.setLayoutX(20);
		lTopLeft.setLayoutY(20);
		lTopLeftSpeed = new Label("");
		lTopLeftSpeed.setPrefSize(100, 20);
		lTopLeftSpeed.setLayoutX(20);
		lTopLeftSpeed.setLayoutY(50);
		lTopLeftOccupied = new Label("");
		lTopLeftOccupied.setPrefSize(100, 20);
		lTopLeftOccupied.setLayoutX(105);
		lTopLeftOccupied.setLayoutY(50);
		lTopLeftMax = new Label("");
		lTopLeftMax.setPrefSize(100, 20);
		lTopLeftMax.setLayoutX(180);
		lTopLeftMax.setLayoutY(50);
		t1 = new TextArea();
		t1.setLayoutX(7);
		t1.setLayoutY(88);
		t1.setEditable(false);
		lTopCenter.setPrefSize(100, 20);
		lTopCenter.setLayoutX(20);
		lTopCenter.setLayoutY(20);
		lTopCenterSpeed = new Label("");
		lTopCenterSpeed.setPrefSize(100, 20);
		lTopCenterSpeed.setLayoutX(20);
		lTopCenterSpeed.setLayoutY(50);
		lTopCenterOccupied = new Label("");
		lTopCenterOccupied.setPrefSize(100, 20);
		lTopCenterOccupied.setLayoutX(105);
		lTopCenterOccupied.setLayoutY(50);
		lTopCenterMax = new Label("");
		lTopCenterMax.setPrefSize(100, 20);
		lTopCenterMax.setLayoutX(180);
		lTopCenterMax.setLayoutY(50);
		t2 = new TextArea();
		t2.setLayoutX(7);
		t2.setLayoutY(88);
		t2.setEditable(false);
		lTopRight.setPrefSize(100, 20);
		lTopRight.setLayoutX(20);
		lTopRight.setLayoutY(20);
		lTopRightSpeed = new Label("");
		lTopRightSpeed.setPrefSize(100, 20);
		lTopRightSpeed.setLayoutX(20);
		lTopRightSpeed.setLayoutY(50);
		lTopRightOccupied = new Label("");
		lTopRightOccupied.setPrefSize(100, 20);
		lTopRightOccupied.setLayoutX(105);
		lTopRightOccupied.setLayoutY(50);
		lTopRightMax = new Label("");
		lTopRightMax.setPrefSize(100, 20);
		lTopRightMax.setLayoutX(180);
		lTopRightMax.setLayoutY(50);
		t3 = new TextArea();
		t3.setLayoutX(7);
		t3.setLayoutY(88);
		t3.setEditable(false);

		t1.setPrefWidth(380.0);
		t1.setPrefHeight(207.0);
		t2.setPrefWidth(380.0);
		t2.setPrefHeight(207.0);
		t3.setPrefWidth(380.0);
		t3.setPrefHeight(207.0);

		startBtn = new Button("Start");
		startBtn.setPrefSize(100, 40);
		startBtn.setLayoutX(495);
		startBtn.setLayoutY(30);

		exitBtn = new Button("Exit");
		exitBtn.setPrefSize(100, 40);
		exitBtn.setLayoutX(606);
		exitBtn.setLayoutY(30);

		topLeftPane.getChildren().addAll(lTopLeft, lTopLeftSpeed, lTopLeftOccupied, lTopLeftMax, t1);
		topCenterPane.getChildren().addAll(lTopCenter, lTopCenterSpeed, lTopCenterOccupied, lTopCenterMax, t2);
		topRightPane.getChildren().addAll(lTopRight, lTopRightSpeed, lTopRightOccupied, lTopRightMax, t3);

		drawRoute(centerCenterPane, BUS_WIDTH, BUS_HEIGHT, busStopsCnt, busMoveDst, cityList);

		bus1Rect = new Rectangle(0, BUS_START_Y, BUS_WIDTH, BUS_HEIGHT);
		bus1Line = createBusAndLine(bus1Rect, bus1Line, BUS_WIDTH, BUS_HEIGHT, BUS1_IMG_PATH, BUS_PATH, busMoveDst,
				BUS_DELTA_Y * 0);

		bus2Rect = new Rectangle(0, BUS_START_Y + BUS_DELTA_Y, BUS_WIDTH, BUS_HEIGHT);
		bus2Line = createBusAndLine(bus2Rect, bus2Line, BUS_WIDTH, BUS_HEIGHT, BUS2_IMG_PATH, BUS_PATH, busMoveDst,
				BUS_DELTA_Y * 1);

		bus3Rect = new Rectangle(0, BUS_START_Y + BUS_DELTA_Y * 2, BUS_WIDTH, BUS_HEIGHT);
		bus3Line = createBusAndLine(bus3Rect, bus3Line, BUS_WIDTH, BUS_HEIGHT, BUS3_IMG_PATH, BUS_PATH, busMoveDst,
				BUS_DELTA_Y * 2);

		createLinePoints(centerCenterPane, BUS_WIDTH, BUS_HEIGHT, busMoveDst);
		createLinePoints(centerCenterPane, BUS_WIDTH, BUS_HEIGHT + BUS_DELTA_Y, busMoveDst);
		createLinePoints(centerCenterPane, BUS_WIDTH, BUS_HEIGHT + BUS_DELTA_Y * 2, busMoveDst);

		centerCenterPane.getChildren().addAll(bus1Rect, bus2Rect, bus3Rect, bus1Line, bus2Line, bus3Line);

		bottomPane.getChildren().addAll(startBtn, exitBtn);		
	}

	private void drawRoute(Pane pane, int startX, int startY, int stopsCnt, int busMoveDst, List<City> cityList) {
		int tX = startX + 20;
		int tY = startY;
		for (int i = 0; i < stopsCnt; i++) {
			Label l = new Label(cityList.get(i).getName());
			Pane p = new Pane();
			p.setPrefSize(busMoveDst, 10);
			p.setLayoutX(tX - 50);
			p.setLayoutY(tY);
			l.setPrefSize(100, 10);
			l.setAlignment(Pos.CENTER);
			l.setScaleX(0.8);
			l.setScaleY(0.8);
			p.getChildren().add(l);
			pane.getChildren().add(p);
			tX += busMoveDst;
		}
	}

	private void createLinePoints(Pane pane, int startX, int startY, int busMoveDst) {
		int tX = startX + 20;
		int tY = busMoveDst + startY;
		for (int i = 0; i < busStopsCnt; i++) {
			pane.getChildren().add(new Circle(tX, tY, 4, Color.DARKCYAN));
			tX += busMoveDst;
		}
	}

	public Line createBusAndLine(Rectangle busRect, Line busLine, int busWidth, int busHeight, String imgPath,
			int busPath, int busMoveDst, int busDeltaY) {
		InputStream imgStream = this.getClass().getClassLoader().getResourceAsStream(imgPath);
		busRect.setFill(new ImagePattern(new Image(imgStream)));
		busLine = new Line(busWidth + 20, busMoveDst + busHeight + busDeltaY, busWidth + 20 + busPath - busMoveDst,
				busWidth + busDeltaY);
		busLine.setStrokeWidth(3);
		busLine.setStroke(Color.CADETBLUE);
		return busLine;
	}

	private void process() {
		Random random = new Random();
		BusStation bs = new BusStation(dbWorker);
		List<City> route = bs.getRoute();

		List<City> bl1 = new ArrayList<City>();
		bl1.addAll(route);
		List<City> bl2 = new ArrayList<City>();
		bl2.addAll(route);
		List<City> bl3 = new ArrayList<City>();
		bl3.addAll(route);

		CountDownLatch cl1 = new CountDownLatch(BUS1_MAX);
		Bus b1 = new Bus(BUS1_NAME, BUS1_MAX, 0, bl1, cl1, t1, lTopLeftOccupied, trBus1, bus1Rect, musicStopThread);
		lTopLeft.setText(b1.getName());
		lTopLeftSpeed.setText("Speed:" + b1.getSpeed());
		lTopLeftOccupied.setText("Occupied:" + b1.getCurPassengers());
		lTopLeftMax.setText("Max:" + b1.getMaxPassengers());

		CountDownLatch cl2 = new CountDownLatch(BUS2_MAX);
		Bus b2 = new Bus(BUS2_NAME, BUS2_MAX, 0, bl2, cl2, t2, lTopCenterOccupied, trBus2, bus2Rect, musicStopThread);
		lTopCenter.setText(b2.getName());
		lTopCenterSpeed.setText("Speed:" + b2.getSpeed());
		lTopCenterOccupied.setText("Occupied:" + b2.getCurPassengers());
		lTopCenterMax.setText("Max:" + b2.getMaxPassengers());

		CountDownLatch cl3 = new CountDownLatch(BUS3_MAX);
		Bus b3 = new Bus(BUS3_NAME, BUS3_MAX, 0, bl3, cl3, t3, lTopRightOccupied, trBus3, bus3Rect, musicStopThread);
		lTopRight.setText(b3.getName());
		lTopRightSpeed.setText("Speed:" + b3.getSpeed());
		lTopRightOccupied.setText("Occupied:" + b3.getCurPassengers());
		lTopRightMax.setText("Max:" + b3.getMaxPassengers());

		for (int i = 0; i < MAX_PASSENGERS_ON_STATION; i++) {
			new Passenger("Man" + random.nextInt(50) + random.nextInt(50), b1, b2, b3);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		try {
			BorderPane root = new BorderPane(centerHBox, topHBox, null, bottomHBox, leftHBox);
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets().add(getClass().getClassLoader().getResource(CSS_STYLE_PATH).toExternalForm());			
			primaryStage.setScene(scene);
			primaryStage.setTitle(APP_NAME);
			primaryStage.setResizable(false);
			primaryStage.show();			
			player = new Player();
			musicStopThread = new MusicStopThread(player);
		} catch (Exception e) {
			e.printStackTrace();
		}

		startBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				player.play();
				process();
				startBtn.setDisable(true);
			}
		});

		exitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

}
