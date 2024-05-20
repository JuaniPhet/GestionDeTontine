package InterfaceGraphique;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Label;
import java.awt.TextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Button;

public class Authentification {

	public JFrame authentificationFrame;
	private JPasswordField textMdp;
	public String emailUserBd;
	public String mdpUserBd;
	private static String userConnected;
	private static String userConnectedPrename;
	private static String typeConnected;
	
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/tontine","root","");
			System.out.println("Connected to the database!");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Authentification window = new Authentification();
					window.authentificationFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * take a variable from extern class
	 */
	public static String getUserConnected() {
		return userConnected;
	}
	public static String getUserConnectedPrename() {
		return userConnectedPrename;
	}
	public static String getTypeConnected() {
		return typeConnected;
	}
	
	/**
	 * Create the application.
	 */
	public Authentification() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		authentificationFrame = new JFrame();
		authentificationFrame.setAlwaysOnTop(true);
		authentificationFrame.setUndecorated(true);
		authentificationFrame.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		authentificationFrame.setResizable(false);
		authentificationFrame.setBounds(100, 100, 805, 531);
		authentificationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		authentificationFrame.setLocationRelativeTo(null);
		authentificationFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel.setBounds(23, 40, 756, 65);
		authentificationFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("TontiPlus+");
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setBackground(Color.LIGHT_GRAY);
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 737, 43);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("LOG");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(23, 116, 756, 65);
		authentificationFrame.getContentPane().add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.scrollbar);
		panel_1.setBorder(null);
		panel_1.setBounds(387, 223, 392, 224);
		authentificationFrame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		Label label = new Label("Email");
		label.setFont(new Font("Trebuchet MS", Font.PLAIN, 23));
		label.setAlignment(Label.CENTER);
		label.setBounds(70, 16, 260, 26);
		panel_1.add(label);
		
		TextField textMail = new TextField();
		textMail.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		textMail.setForeground(SystemColor.textText);
		textMail.setBounds(10, 55, 376, 33);
		panel_1.add(textMail);
		
		Label label_1 = new Label("Password");
		label_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 23));
		label_1.setAlignment(Label.CENTER);
		label_1.setBounds(70, 94, 260, 26);
		panel_1.add(label_1);
		
		textMdp = new JPasswordField();
		textMdp.setBounds(10, 126, 376, 33);
		panel_1.add(textMdp);
		
		JButton cancelButton = new JButton("CANCEL");
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					textMail.setText("");
					textMdp.setText("");
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		cancelButton.setBorderPainted(false);
		cancelButton.setForeground(new Color(255, 250, 250));
		cancelButton.setBackground(new Color(220, 20, 60));
		cancelButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		cancelButton.setBounds(203, 170, 173, 43);
		panel_1.add(cancelButton);
		
		JButton connectButton = new JButton("LOG IN");
		
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
				String emailUser = textMail.getText().toString();
				@SuppressWarnings("deprecation")
				String mdpUser = textMdp.getText().toString();
					
				String sql = "SELECT `nom`,`prenom`,`email`,`type`,`mot_de_passe` FROM `tb_members`";
				try {
					pst = con.prepareStatement(sql);
					rs = pst.executeQuery();
					Boolean connected = false;
						
					while(rs.next()) {
						emailUserBd = rs.getString("email");
						mdpUserBd = rs.getString("mot_de_passe");

						if(emailUser.equals(emailUserBd) && mdpUser.equals(mdpUserBd)) {
							userConnected = rs.getString("nom").toLowerCase();
							userConnectedPrename = rs.getString("prenom").toLowerCase();
							typeConnected = rs.getString("type").toLowerCase();
							connected = true;
							authentificationFrame.dispose();
							new Menu().menuFrame.setVisible(true);
						}
					}
//					if(!connected) {
//						JOptionPane.showMessageDialog(connectButton, "Adresse email ou Mot de passe incorrect!");
//					}
					if(connected) {
						System.out.println(userConnected + " is connected as "+typeConnected);
					}else {
						JOptionPane.showMessageDialog(connectButton, "Adresse email ou Mot de passe incorrect!");
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}		
			}
		});
	
		connectButton.setForeground(new Color(255, 250, 250));
		connectButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		connectButton.setBorderPainted(false);
		connectButton.setBackground(new Color(0, 128, 0));
		connectButton.setBounds(20, 170, 173, 43);
		panel_1.add(connectButton);
		
		Button exitButton = new Button("X");
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		exitButton.setForeground(new Color(255, 255, 255));
		exitButton.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		exitButton.setBackground(new Color(255, 0, 0));
		exitButton.setBounds(754, 10, 25, 24);
		authentificationFrame.getContentPane().add(exitButton);
		
		JLabel lblNewLabel_2 = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/tontine.jpg")).getImage();
		lblNewLabel_2.setIcon(new ImageIcon(img));
		lblNewLabel_2.setBounds(23, 213, 338, 245);
		authentificationFrame.getContentPane().add(lblNewLabel_2);
		
		
	}
}
