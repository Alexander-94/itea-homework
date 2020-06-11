package org.guiform;

import java.awt.GridLayout;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GuiApp extends JFrame {

	private static final String INNER_IMG_FILE = "inner.png";
	private static final String INNER_TXT_FILE = "inner.txt";
	private static final String OUTER_IMG_FILE = "outer.png";
	private static final String OUTER_TXT_FILE = "outer.txt";

	public GuiApp() {
		super("Text and Image GUI");

		URL innerImgUrl = this.getClass().getClassLoader().getResource(INNER_IMG_FILE);
		JLabel innerImgLabel = new JLabel(new ImageIcon(innerImgUrl));
		JTextArea innerTxtArea = new JTextArea();
		
		try (Scanner sc = new Scanner(this.getClass().getClassLoader().getResourceAsStream(INNER_TXT_FILE), "UTF-8");) {
			while (sc.hasNextLine()) {
				innerTxtArea.append(sc.nextLine()+"\n");
			}
		} catch (Exception e) {
            e.printStackTrace();
		}
		
		JLabel outerImgLabel = new JLabel(new ImageIcon(OUTER_IMG_FILE));
		JTextArea outerTxtArea = new JTextArea();
		try (Scanner sc = new Scanner(new FileInputStream(OUTER_TXT_FILE))) {
			while (sc.hasNextLine()) {
				outerTxtArea.append(sc.nextLine()+"\n");
			}
		} catch (Exception e) {
            e.printStackTrace();
		}


		JPanel contents = new JPanel();
		GridLayout layout = new GridLayout(2, 2);
		contents.setLayout(layout);
		contents.add(innerImgLabel).setLocation(0, 0);
		contents.add(new JScrollPane(innerTxtArea)).setLocation(0, 1);
		contents.add(outerImgLabel).setLocation(1, 0);
		contents.add(new JScrollPane(outerTxtArea)).setLocation(1, 1);

		setContentPane(contents);
		setSize(400, 400);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new GuiApp();
	}
}
