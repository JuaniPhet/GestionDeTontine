package InterfaceGraphique;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Comissaire {

	public JFrame comissaireFrame;
	private JTextField textFieldName, textFieldPrenom, textFieldDate, textFieldMontant;
	private JPanel formMembers;
	private JLabel nameLabel, prenameLabel, dateLabel, montantLabel;
	private JButton okEpargneButton, okEmpruntButton;
	private JTable tableEpargneMembers, tableEmpruntMembers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Comissaire window = new Comissaire();
					window.comissaireFrame.setVisible(true);
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
	
	/**
	 * Create connection method
	 */
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/tontine","root","");
			System.out.println("Connected to the database!");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setFieldVide() {
		textFieldName.setText("");
		textFieldPrenom.setText("");
		textFieldDate.setText("");
		textFieldMontant.setText("");
	}
	
	/**
	 * Take information from the database to show them in the software 
	 */
	public void showTableEpargne() {
		try {
			connect();
			String [] entete = {"Id", "Name", "Prename", "Borrowing Date", "Savings Date"};
			String [] ligne = new String[6];
			
			DefaultTableModel model = new DefaultTableModel(null, entete);
			
			String sql = "select * from tb_epargne";
			Statement st = con.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				ligne[0] = rs.getString("id");
				ligne[1] = rs.getString("nom");
				ligne[2] = rs.getString("prenom");
				ligne[3] = rs.getString("date");
				ligne[4] = rs.getString("montant");
				model.addRow(ligne);
			}
			tableEpargneMembers.setModel(model);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showTableEmprunt() {
		try {
			connect();
			String [] entete = {"Id", "Name", "Prename", "Borrowing Date", "Savings Date"};
			String [] ligne = new String[6];
			
			DefaultTableModel model = new DefaultTableModel(null, entete);
			
			String sql = "select * from tb_emprunt";
			Statement st = con.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				ligne[0] = rs.getString("id");
				ligne[1] = rs.getString("nom");
				ligne[2] = rs.getString("prenom");
				ligne[3] = rs.getString("date");
				ligne[4] = rs.getString("montant");
				model.addRow(ligne);
			}
			tableEmpruntMembers.setModel(model);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public Comissaire() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		comissaireFrame = new JFrame();
		comissaireFrame.setUndecorated(true);
		comissaireFrame.setBounds(100, 100, 1100, 710);
		comissaireFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		comissaireFrame.setLocationRelativeTo(null);
		comissaireFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(153, 204, 204));
		panel.setBounds(0, 0, 289, 720);
		comissaireFrame.getContentPane().add(panel);
		
		JButton prevMenuButton = new JButton("PREVIOUS MENU ");
		prevMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comissaireFrame.dispose();
				new Menu().menuFrame.setVisible(true);
			}
		});
		prevMenuButton.setForeground(new Color(255, 250, 250));
		prevMenuButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		prevMenuButton.setBorderPainted(false);
		prevMenuButton.setBackground(new Color(0, 128, 0));
		prevMenuButton.setBounds(10, 635, 269, 53);
		panel.add(prevMenuButton);
		
		JButton addEpargneButton = new JButton("Add Savings");
		addEpargneButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				formMembers.setVisible(true);
				nameLabel.setVisible(true);
				textFieldName.setVisible(true);
				prenameLabel.setVisible(true);
				textFieldPrenom.setVisible(true);
				dateLabel.setVisible(true);
				textFieldDate.setVisible(true);
				montantLabel.setVisible(true);
				textFieldMontant.setVisible(true);
				okEpargneButton.setVisible(true);
				okEmpruntButton.setVisible(false);
				tableEmpruntMembers.setVisible(false);
				tableEpargneMembers.setVisible(true);
				showTableEpargne();
			}
		});
		addEpargneButton.setHorizontalTextPosition(SwingConstants.CENTER);
		addEpargneButton.setForeground(Color.BLACK);
		addEpargneButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		addEpargneButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		addEpargneButton.setBackground(new Color(211, 211, 211));
		addEpargneButton.setBounds(10, 31, 269, 53);
		panel.add(addEpargneButton);
		
		JButton addEmpruntButton = new JButton("Add a Borrow");
		addEmpruntButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				formMembers.setVisible(true);
				nameLabel.setVisible(true);
				textFieldName.setVisible(true);
				prenameLabel.setVisible(true);
				textFieldPrenom.setVisible(true);
				dateLabel.setVisible(true);
				textFieldDate.setVisible(true);
				montantLabel.setVisible(true);
				textFieldMontant.setVisible(true);
				okEmpruntButton.setVisible(true);
				okEpargneButton.setVisible(false);
				tableEpargneMembers.setVisible(false);
				tableEmpruntMembers.setVisible(true);
				showTableEmprunt();
			}
		});
		addEmpruntButton.setHorizontalTextPosition(SwingConstants.CENTER);
		addEmpruntButton.setForeground(Color.BLACK);
		addEmpruntButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		addEmpruntButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		addEmpruntButton.setBackground(new Color(211, 211, 211));
		addEmpruntButton.setBounds(10, 106, 269, 53);
		panel.add(addEmpruntButton);

		formMembers = new JPanel();
		formMembers.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		formMembers.setBackground(new Color(240, 255, 240));
		formMembers.setBorder(new LineBorder(new Color(0, 0, 0)));
		formMembers.setBounds(295, 31, 795, 320);
		comissaireFrame.getContentPane().add(formMembers);
		formMembers.setLayout(null);
		formMembers.setVisible(false);
		
		nameLabel = new JLabel("First Name :");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		nameLabel.setBounds(20, 40, 92, 36);
		formMembers.add(nameLabel);
		nameLabel.setVisible(false);
		
		textFieldName = new JTextField();
		textFieldName.setText(" ");
		nameLabel.setLabelFor(textFieldName);
		textFieldName.setBounds(122, 40, 253, 30);
		formMembers.add(textFieldName);
		textFieldName.setColumns(10);
		textFieldName.setVisible(false);
		
		prenameLabel = new JLabel("Second Name :");
		prenameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		prenameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		prenameLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		prenameLabel.setBounds(20, 83, 92, 36);
		formMembers.add(prenameLabel);
		prenameLabel.setVisible(false);
		
		textFieldPrenom = new JTextField();
		prenameLabel.setLabelFor(textFieldPrenom);
		textFieldPrenom.setColumns(10);
		textFieldPrenom.setBounds(122, 88, 253, 30);
		formMembers.add(textFieldPrenom);
		textFieldPrenom.setVisible(false);
		
		dateLabel = new JLabel("Date :");
		dateLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		dateLabel.setBounds(20, 130, 92, 36);
		formMembers.add(dateLabel);
		dateLabel.setVisible(false);
		
		okEpargneButton = new JButton("Save");
		okEpargneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect();
					pst = con.prepareStatement("insert into tb_epargne(nom, prenom, date, montant) values (?,?,?,?)");
					pst.setString(1, textFieldName.getText());
					pst.setString(2, textFieldPrenom.getText());
					pst.setString(3, textFieldDate.getText());
					pst.setString(4, textFieldMontant.getText());
					pst.executeUpdate();
					con.close();
					showTableEpargne();
					setFieldVide();
				} catch (Exception e2) {
					e2.printStackTrace();
;				}
			}
		});
		okEpargneButton.setHorizontalTextPosition(SwingConstants.CENTER);
		okEpargneButton.setForeground(Color.BLACK);
		okEpargneButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		okEpargneButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		okEpargneButton.setBackground(new Color(211, 211, 211));
		okEpargneButton.setBounds(122, 245, 253, 53);
		formMembers.add(okEpargneButton);
		okEpargneButton.setVisible(false);
		
		okEmpruntButton = new JButton("Borrow");
		okEmpruntButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect();
					pst = con.prepareStatement("insert into tb_emprunt(nom, prenom, date, montant) values (?,?,?,?)");
					pst.setString(1, textFieldName.getText());
					pst.setString(2, textFieldPrenom.getText());
					pst.setString(3, textFieldDate.getText());
					pst.setString(4, textFieldMontant.getText());
					pst.executeUpdate();
					con.close();
					showTableEmprunt();
					setFieldVide();
				} catch (Exception e2) {
					e2.printStackTrace();
;				}
			}
		});
		okEmpruntButton.setHorizontalTextPosition(SwingConstants.CENTER);
		okEmpruntButton.setForeground(Color.BLACK);
		okEmpruntButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		okEmpruntButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		okEmpruntButton.setBackground(new Color(211, 211, 211));
		okEmpruntButton.setBounds(122, 245, 253, 53);
		formMembers.add(okEmpruntButton);
		okEmpruntButton.setVisible(false);
		
		montantLabel = new JLabel("Amount :");
		montantLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		montantLabel.setHorizontalAlignment(SwingConstants.CENTER);
		montantLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		montantLabel.setBounds(20, 177, 92, 36);
		formMembers.add(montantLabel);
		montantLabel.setVisible(false);
		
		textFieldDate = new JTextField();
		textFieldDate.setColumns(10);
		textFieldDate.setBounds(122, 129, 253, 30);
		formMembers.add(textFieldDate);
		textFieldDate.setVisible(false);
		
		textFieldMontant = new JTextField();
		textFieldMontant.setColumns(10);
		textFieldMontant.setBounds(122, 177, 253, 30);
		formMembers.add(textFieldMontant);
		textFieldMontant.setVisible(false);
		
		JScrollPane scrollPaneTableEpargneMember = new JScrollPane();
		scrollPaneTableEpargneMember.setBounds(299, 362, 791, 326);
		comissaireFrame.getContentPane().add(scrollPaneTableEpargneMember);
		
		tableEpargneMembers = new JTable();
		scrollPaneTableEpargneMember.setViewportView(tableEpargneMembers);
		tableEpargneMembers.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		tableEpargneMembers.setVisible(false);
		
		JScrollPane scrollPaneTableEmpruntMember = new JScrollPane();
		scrollPaneTableEmpruntMember.setBounds(299, 362, 791, 326);
		comissaireFrame.getContentPane().add(scrollPaneTableEmpruntMember);
		
		tableEmpruntMembers = new JTable();
		scrollPaneTableEmpruntMember.setViewportView(tableEmpruntMembers);
		tableEmpruntMembers.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		tableEmpruntMembers.setVisible(false);
		
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
		comissaireFrame.getContentPane().add(exitButton);
		
	}
}
