package InterfaceGraphique;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Button;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Menu {

	public JFrame menuFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.menuFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String memberTypeConnected = Authentification.getTypeConnected().toString();
	public String presidentOption = "president";
	public String auditorOption = "auditor";
	public String secretaryOption = "secretary";
	public String simpleOption = "simple";
			
	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		menuFrame = new JFrame();
		menuFrame.setAlwaysOnTop(true);
		menuFrame.setResizable(false);
		menuFrame.setUndecorated(true);
		menuFrame.setBounds(100, 100, 1100, 710);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setLocationRelativeTo(null);
		menuFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(135, 206, 250));
		panel.setBounds(0, 0, 298, 720);
		menuFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton deconnectButton = new JButton("LOG OUT");
		deconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFrame.dispose();
				new Authentification().authentificationFrame.setVisible(true);
			}
		});
		deconnectButton.setForeground(new Color(255, 250, 250));
		deconnectButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		deconnectButton.setBorderPainted(false);
		deconnectButton.setBackground(new Color(220, 20, 60));
		deconnectButton.setBounds(10, 635, 269, 53);
		panel.add(deconnectButton);
		
		JButton presidentButton = new JButton("PRESIDENT");
		presidentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(memberTypeConnected.equals(presidentOption) || memberTypeConnected.equals(secretaryOption)) {
					menuFrame.dispose();
					new President().presidentFrame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(presidentButton, "Sorry "+Authentification.getUserConnected()+", You are not a President.\n You can't go there!");
				}
			}
		});
		presidentButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		presidentButton.setHorizontalTextPosition(SwingConstants.CENTER);
		presidentButton.setForeground(new Color(0, 0, 0));
		presidentButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		presidentButton.setBackground(new Color(211, 211, 211));
		presidentButton.setBounds(10, 303, 269, 53);
		panel.add(presidentButton);
		
		JButton memberButton = new JButton("MEMBER");
		memberButton.setHorizontalTextPosition(SwingConstants.CENTER);
		memberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFrame.dispose();
				new Member().memberFrame.setVisible(true);
			}
		});
		memberButton.setForeground(Color.BLACK);
		memberButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		memberButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		memberButton.setBackground(new Color(211, 211, 211));
		memberButton.setBounds(10, 215, 269, 53);
		panel.add(memberButton);
		
		JButton commissaireButton = new JButton("AUDITOR");
		commissaireButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(memberTypeConnected.equals(auditorOption) || memberTypeConnected.equals(secretaryOption)) {
						menuFrame.dispose();
						new Comissaire().comissaireFrame.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(commissaireButton, "Sorry "+Authentification.getUserConnected()+", You are not an Auditor.\n You can't go there!");
					}
			}
		});
		commissaireButton.setHorizontalTextPosition(SwingConstants.CENTER);
		commissaireButton.setForeground(Color.BLACK);
		commissaireButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		commissaireButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		commissaireButton.setBackground(new Color(211, 211, 211));
		commissaireButton.setBounds(10, 128, 269, 53);
		panel.add(commissaireButton);
		
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
		exitButton.setBounds(1065, 1, 25, 24);
		menuFrame.getContentPane().add(exitButton);
		
	}
}
