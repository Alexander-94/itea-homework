package homework1.pudge;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PudgeInternationalization extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PudgeInternationalization frame = new PudgeInternationalization();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setTitle("PudgeInternationalization");
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	private Locale currentLocale;
	private ResourceBundle messages;
	
	private JLabel lblPudgeImg;
	private BufferedImage pugdeImg;
	private String imgPath = "src\\assets\\";
	private JLabel lblPhrase1;
	private JLabel lblPhrase2;
	private JLabel lblPhrase3;
	private JButton btnSave;
    private JButton btnLoad;
    private JRadioButton rdbtnRU;
    private JRadioButton rdbtnEN;
    private JRadioButton rdbtnDE;
		
	private void updateForm() {
		messages = ResourceBundle.getBundle("assets\\pudge", currentLocale);
		try {
			pugdeImg = ImageIO.read(new File(imgPath+messages.getString("img")));
		} catch (IOException e) {
			System.out.println("Couldn't load image...");
			e.printStackTrace();
		}
		lblPudgeImg.setIcon(new ImageIcon(pugdeImg));
		lblPhrase1.setText(messages.getString("phrase1"));		
		lblPhrase2.setText(messages.getString("phrase2"));	
		lblPhrase3.setText(messages.getString("phrase3"));
		btnSave.setText(messages.getString("save"));
		btnLoad.setText(messages.getString("load"));		
	}
	
	private void saveForm() {	
		try(DataOutputStream dos=new DataOutputStream(new FileOutputStream("pudge.bin"))){	
			dos.writeUTF(messages.getString("img"));
			dos.writeUTF(messages.getString("phrase1"));
			dos.writeUTF(messages.getString("phrase2"));
			dos.writeUTF(messages.getString("phrase3"));
			dos.writeUTF(messages.getString("save"));
			dos.writeUTF(messages.getString("load"));				
			if(rdbtnRU.isSelected()) {
				dos.writeInt(1);
			}else if(rdbtnEN.isSelected()) {
				dos.writeInt(2);
			}else if(rdbtnDE.isSelected()) {
				dos.writeInt(3);
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Locale has been saved...");
	}
	
	private void loadForm() {
		try(DataInputStream dis=new DataInputStream(new FileInputStream("pudge.bin"))){			
		 
			try {
				pugdeImg = ImageIO.read(new File(imgPath+dis.readUTF()));
			} catch (IOException e) {
				System.out.println("Couldn't load image...");
				e.printStackTrace();
			}
			lblPudgeImg.setIcon(new ImageIcon(pugdeImg));
			lblPhrase1.setText(dis.readUTF());		
			lblPhrase2.setText(dis.readUTF());	
			lblPhrase3.setText(dis.readUTF());
			btnSave.setText(dis.readUTF());
			btnLoad.setText(dis.readUTF());
			
			int i = dis.readInt();
			if(i==1) {
				rdbtnRU.setSelected(true);
				rdbtnEN.setSelected(false);
				rdbtnDE.setSelected(false);				
			}else if(i == 2) {
				rdbtnRU.setSelected(false);
				rdbtnEN.setSelected(true);
				rdbtnDE.setSelected(false);
			}else if(i == 3) {
				rdbtnRU.setSelected(false);
				rdbtnEN.setSelected(false);
				rdbtnDE.setSelected(true);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Locale has been loaded...");
	}
	
	/**
	 * Create the frame.
	 */
	public PudgeInternationalization() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 436, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblPhrase2 = new JLabel("----");
		lblPhrase2.setBounds(31, 284, 256, 23);
		contentPane.add(lblPhrase2);
		
		lblPhrase3 = new JLabel("----");
		lblPhrase3.setBounds(31, 315, 256, 22);
		contentPane.add(lblPhrase3);
		
		lblPhrase1 = new JLabel("----");
		lblPhrase1.setBounds(31, 253, 256, 23);
		contentPane.add(lblPhrase1);
		
		btnSave = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveForm();
			}
		});
		btnSave.setBounds(277, 96, 136, 35);
		contentPane.add(btnSave);
		
		btnLoad = new JButton("\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044C");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadForm();
			}
		});
		btnLoad.setBounds(277, 142, 136, 35);
		contentPane.add(btnLoad);
				
		lblPudgeImg = new JLabel();
		lblPudgeImg.setBounds(10, 21, 256, 220);
		contentPane.add(lblPudgeImg);
		
		currentLocale = new Locale("ru","RU");		
		messages = ResourceBundle.getBundle("assets\\pudge", currentLocale);
		
		System.out.println(imgPath+messages.getString("img"));
		try {
			pugdeImg = ImageIO.read(new File(imgPath+messages.getString("img")));
		} catch (IOException e) {
			System.out.println("Couldn't load image...");
			e.printStackTrace();
		}
		lblPhrase1.setText(messages.getString("phrase1"));				
		lblPhrase2.setText(messages.getString("phrase2"));			
		lblPhrase3.setText(messages.getString("phrase3"));		
		btnSave.setText(messages.getString("save"));
		btnLoad.setText(messages.getString("load"));
			
		
		rdbtnRU = new JRadioButton("RU");
		rdbtnRU.setBounds(272, 54, 52, 23);		
		rdbtnRU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentLocale = new Locale("ru","RU");
				updateForm();
			}
		});
		rdbtnRU.setSelected(true);
		contentPane.add(rdbtnRU);
		
		rdbtnEN = new JRadioButton("EN");
		rdbtnEN.setBounds(326, 54, 52, 23);
		rdbtnEN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentLocale = new Locale("en","EN");
				updateForm();
			}
		});
		contentPane.add(rdbtnEN);
		
		
		rdbtnDE = new JRadioButton("DE");
		rdbtnDE.setBounds(376, 54, 52, 23);
		rdbtnDE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentLocale = new Locale("de","DE");
				updateForm();
			}
		});
		contentPane.add(rdbtnDE);
						
		ButtonGroup rdbtnGroup = new ButtonGroup();
		rdbtnGroup.add(rdbtnRU);
		rdbtnGroup.add(rdbtnEN);
		rdbtnGroup.add(rdbtnDE);
				
		loadForm();				        


		
		//lblPudgeImg.setIcon(new ImageIcon(pugdeImg));
		
	}
}
