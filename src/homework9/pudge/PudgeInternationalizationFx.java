package homework9.pudge;

import javafx.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PudgeInternationalizationFx extends Application {

	private static final int WIDTH = 450;
	private static final int HEIGHT = 350;
	private static final String ASSETS_PATH = "src\\assets\\";
	private static final String TITLE = "Pudge Internationalization";

	private Locale currentLocale;
	private ResourceBundle messages;
	private Label lblPudgeImg;
	private Image pugdeImg;
	private Label lblPhrase1;
	private Label lblPhrase2;
	private Label lblPhrase3;
	private Button btnSave;
	private Button btnLoad;
	private RadioButton rdbtnRU;
	private RadioButton rdbtnEN;
	private RadioButton rdbtnDE;

	public PudgeInternationalizationFx() {
		lblPudgeImg = new Label();
		lblPudgeImg.setPrefSize(300, 220);
		lblPudgeImg.setLayoutX(20);
		lblPudgeImg.setLayoutY(20);

		lblPhrase1 = new Label("----");
		lblPhrase1.setPrefSize(300, 24);
		lblPhrase1.setLayoutX(30);
		lblPhrase1.setLayoutY(255);

		lblPhrase2 = new Label("----");
		lblPhrase2.setPrefSize(300, 24);
		lblPhrase2.setLayoutX(30);
		lblPhrase2.setLayoutY(280);

		lblPhrase3 = new Label("----");
		lblPhrase3.setPrefSize(300, 24);
		lblPhrase3.setLayoutX(30);
		lblPhrase3.setLayoutY(305);

		btnSave = new Button("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		btnSave.setPrefSize(136, 35);
		btnSave.setLayoutX(300);
		btnSave.setLayoutY(89);
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				saveForm();
			}
		});

		btnLoad = new Button("\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044C");
		btnLoad.setPrefSize(136, 35);
		btnLoad.setLayoutX(300);
		btnLoad.setLayoutY(142);
		btnLoad.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loadForm();
			}
		});

		currentLocale = new Locale("ru", "RU");
		messages = ResourceBundle.getBundle("assets\\pudge", currentLocale);

		System.out.println(ASSETS_PATH + messages.getString("img"));

		try {
			pugdeImg = new Image(new File(ASSETS_PATH + messages.getString("img")).toURI().toString());
		} catch (NullPointerException e) {
			System.out.println("Couldn't load image...");
			e.printStackTrace();
		}

		lblPhrase1.setText(messages.getString("phrase1"));
		lblPhrase2.setText(messages.getString("phrase2"));
		lblPhrase3.setText(messages.getString("phrase3"));
		btnSave.setText(messages.getString("save"));
		btnLoad.setText(messages.getString("load"));

		rdbtnRU = new RadioButton("RU");
		rdbtnRU.setLayoutX(300);
		rdbtnRU.setLayoutY(52);
		rdbtnRU.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				currentLocale = new Locale("ru", "RU");
				updateForm();
			}
		});
		rdbtnRU.setSelected(true);

		rdbtnEN = new RadioButton("EN");
		rdbtnEN.setLayoutX(350);
		rdbtnEN.setLayoutY(52);
		rdbtnEN.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				currentLocale = new Locale("en", "EN");
				updateForm();
			}
		});

		rdbtnDE = new RadioButton("DE");
		rdbtnDE.setLayoutX(400);
		rdbtnDE.setLayoutY(52);
		rdbtnDE.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				currentLocale = new Locale("de", "DE");
				updateForm();
			}
		});

		ToggleGroup rdbtnGroup = new ToggleGroup();
		rdbtnRU.setToggleGroup(rdbtnGroup);
		rdbtnEN.setToggleGroup(rdbtnGroup);
		rdbtnDE.setToggleGroup(rdbtnGroup);

		loadForm();

	}

	private void updateForm() {
		messages = ResourceBundle.getBundle("assets\\pudge", currentLocale);
		try {
			pugdeImg = new Image(new File(ASSETS_PATH + messages.getString("img")).toURI().toString());// ImageIO.read(new
																										// File(imgPath+messages.getString("img")));
		} catch (NullPointerException e) {
			System.out.println("Couldn't load image...");
			e.printStackTrace();
		}
		lblPudgeImg.setGraphic(new ImageView(pugdeImg));
		lblPhrase1.setText(messages.getString("phrase1"));
		lblPhrase2.setText(messages.getString("phrase2"));
		lblPhrase3.setText(messages.getString("phrase3"));
		btnSave.setText(messages.getString("save"));
		btnLoad.setText(messages.getString("load"));
	}

	private void saveForm() {
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("pudge.bin"))) {
			dos.writeUTF(messages.getString("img"));
			dos.writeUTF(messages.getString("phrase1"));
			dos.writeUTF(messages.getString("phrase2"));
			dos.writeUTF(messages.getString("phrase3"));
			dos.writeUTF(messages.getString("save"));
			dos.writeUTF(messages.getString("load"));
			if (rdbtnRU.isSelected()) {
				dos.writeInt(1);
			} else if (rdbtnEN.isSelected()) {
				dos.writeInt(2);
			} else if (rdbtnDE.isSelected()) {
				dos.writeInt(3);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Locale has been saved...");
	}

	private void loadForm() {
		try (DataInputStream dis = new DataInputStream(new FileInputStream("pudge.bin"))) {

			try {
				pugdeImg = new Image(new File(ASSETS_PATH + dis.readUTF()).toURI().toString());
			} catch (NullPointerException e) {
				System.out.println("Couldn't load image...");
				e.printStackTrace();
			}

			lblPudgeImg.setGraphic(new ImageView(pugdeImg));
			lblPhrase1.setText(dis.readUTF());
			lblPhrase2.setText(dis.readUTF());
			lblPhrase3.setText(dis.readUTF());
			btnSave.setText(dis.readUTF());
			btnLoad.setText(dis.readUTF());

			int i = dis.readInt();
			if (i == 1) {
				rdbtnRU.setSelected(true);
				rdbtnEN.setSelected(false);
				rdbtnDE.setSelected(false);
			} else if (i == 2) {
				rdbtnRU.setSelected(false);
				rdbtnEN.setSelected(true);
				rdbtnDE.setSelected(false);
			} else if (i == 3) {
				rdbtnRU.setSelected(false);
				rdbtnEN.setSelected(false);
				rdbtnDE.setSelected(true);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Locale has been loaded...");
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			Pane root = new Pane();
			root.setPrefSize(WIDTH, HEIGHT);
			root.getChildren().addAll(lblPudgeImg, lblPhrase1, lblPhrase2, lblPhrase3, btnSave, btnLoad, rdbtnRU,
					rdbtnEN, rdbtnDE);
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets().add(new File(ASSETS_PATH + "application.css").toURI().toString());
			primaryStage.setScene(scene);
			primaryStage.setTitle(TITLE);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
