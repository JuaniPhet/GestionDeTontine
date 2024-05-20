package InterfaceGraphique;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.Button;
import javax.swing.JTextField;

public class Member {

	public JFrame memberFrame;
	private JTable tableEpargnes;
	private JTable tableEmprunts;
	private JTextField textFieldTotal;
	private JLabel labelTableEpargne, labelTableEmprunt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Member window = new Member();
					window.memberFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 *Connection to the database 
	 */
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public String memberConnected = Authentification.getUserConnected();
	public String memberConnectedPrename = Authentification.getUserConnectedPrename();
	
	/**
	 * Create connection method
	 */
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/tontine","root","");
			System.out.println("Connected to the database!");
			System.out.println(memberConnected + " consulte ses epargnes");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Take information from the database to show them in the software 
	 */
	public void showTableEpargnes() {
		try {
			connect();
			String [] entete = {"Savings Date", "Savings Amount"};
			String [] ligne = new String[6];
			
			DefaultTableModel model = new DefaultTableModel(null, entete);
			
			String sql = "SELECT `date`,`montant` FROM `tb_epargne` WHERE nom='"+memberConnected+"' and prenom='"+memberConnectedPrename+"'";
			Statement st = con.createStatement();
			rs = st.executeQuery(sql);
			
			double epargneTotal = 0;
			while(rs.next()) {
				ligne[0] = rs.getString("date");
				ligne[1] = rs.getString("montant");
				model.addRow(ligne);
				epargneTotal += rs.getDouble("montant");
				System.out.println(epargneTotal);
			}
			tableEpargnes.setModel(model);
			textFieldTotal.setText(Double.toString(epargneTotal));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showTableEmprunts() {
		try {
			connect();
			String [] entete = {"Borrowing Date", "Borrowing Amount"};
			String [] ligne = new String[6];
			
			DefaultTableModel model = new DefaultTableModel(null, entete);
			
			String sql = "SELECT `date`,`montant` FROM `tb_emprunt` WHERE nom='"+memberConnected+"'";
			Statement st = con.createStatement();
			rs = st.executeQuery(sql);
			
			double empruntTotal = 0;
			while(rs.next()) {
				ligne[0] = rs.getString("date");
				ligne[1] = rs.getString("montant");
				empruntTotal += rs.getDouble("montant");
				model.addRow(ligne);
			}
			tableEmprunts.setModel(model);
			textFieldTotal.setText(Double.toString(empruntTotal));
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the application.
	 */
	public Member() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		memberFrame = new JFrame();
		memberFrame.setUndecorated(true);
		memberFrame.setResizable(false);
		memberFrame.setAlwaysOnTop(true);
		memberFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		memberFrame.setBounds(100, 100, 1100, 710);
		memberFrame.setLocationRelativeTo(null);
		memberFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 289, 720);
		panel.setBackground(new Color(153, 204, 204));
		memberFrame.getContentPane().add(panel);
		
		JButton prevMenuButton = new JButton("PREVIOUS MENU");
		prevMenuButton.setBounds(10, 635, 269, 53);
		prevMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberFrame.dispose();
				new Menu().menuFrame.setVisible(true);
			}
		});
		panel.setLayout(null);
		prevMenuButton.setForeground(new Color(255, 250, 250));
		prevMenuButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		prevMenuButton.setBorderPainted(false);
		prevMenuButton.setBackground(new Color(0, 128, 0));
		panel.add(prevMenuButton);
		
		JButton showEpargneButton = new JButton("MY SAVINGS");
		showEpargneButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				labelTableEpargne.setVisible(true);
				labelTableEmprunt.setVisible(false);
				tableEpargnes.setVisible(true);
				tableEmprunts.setVisible(false);
				showTableEpargnes();
			}
		});
		showEpargneButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		showEpargneButton.setBounds(10, 11, 269, 53);
		showEpargneButton.setForeground(new Color(0, 0, 0));
		showEpargneButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		showEpargneButton.setBackground(new Color(211, 211, 211));
		panel.add(showEpargneButton);
		
		JButton showEmpruntButton = new JButton("MY LOANS");
		showEmpruntButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				labelTableEpargne.setVisible(false);
				labelTableEmprunt.setVisible(true);
				tableEpargnes.setVisible(false);
				tableEmprunts.setVisible(true);
				showTableEmprunts();
			}
		});
		showEmpruntButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		showEmpruntButton.setBounds(10, 110, 269, 53);
		showEmpruntButton.setForeground(new Color(0, 0, 0));
		showEmpruntButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		showEmpruntButton.setBackground(new Color(211, 211, 211));
		panel.add(showEmpruntButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(519, 0, 456, 57);
		memberFrame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		labelTableEpargne = new JLabel("SAVING TABLE");
		labelTableEpargne.setBounds(0, 11, 456, 47);
		labelTableEpargne.setHorizontalTextPosition(SwingConstants.CENTER);
		labelTableEpargne.setHorizontalAlignment(SwingConstants.CENTER);
		labelTableEpargne.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		labelTableEpargne.setVisible(false);
		panel_1.add(labelTableEpargne);
		
		labelTableEmprunt = new JLabel("BORROWING TABLE");
		labelTableEmprunt.setBounds(0, 11, 456, 46);
		labelTableEmprunt.setHorizontalTextPosition(SwingConstants.CENTER);
		labelTableEmprunt.setHorizontalAlignment(SwingConstants.CENTER);
		labelTableEmprunt.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		labelTableEmprunt.setVisible(false);
		panel_1.add(labelTableEmprunt);
		
		JScrollPane scrollPaneTableEpargne = new JScrollPane();
		scrollPaneTableEpargne.setBounds(299, 68, 560, 592);
		memberFrame.getContentPane().add(scrollPaneTableEpargne);
		
		tableEpargnes = new JTable();
		scrollPaneTableEpargne.setViewportView(tableEpargnes);
		tableEpargnes.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		
		JScrollPane scrollPaneTableEmprunt = new JScrollPane();
		scrollPaneTableEmprunt.setBounds(300, 68, 560, 592);
		memberFrame.getContentPane().add(scrollPaneTableEmprunt);
		
		tableEmprunts = new JTable();
		tableEmprunts.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		scrollPaneTableEmprunt.setViewportView(tableEmprunts);
		
		JLabel labelTotal = new JLabel("TOTAL");
		labelTotal.setBackground(Color.ORANGE);
		labelTotal.setHorizontalAlignment(SwingConstants.CENTER);
		labelTotal.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		labelTotal.setBounds(869, 68, 221, 57);
		memberFrame.getContentPane().add(labelTotal);
		tableEpargnes.setVisible(false);
		
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
		memberFrame.getContentPane().add(exitButton);
		
		textFieldTotal = new JTextField();
		textFieldTotal.setHorizontalAlignment(SwingConstants.TRAILING);
		textFieldTotal.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		textFieldTotal.setBounds(869, 136, 221, 64);
		memberFrame.getContentPane().add(textFieldTotal);
		textFieldTotal.setColumns(10);
		
	}
}
