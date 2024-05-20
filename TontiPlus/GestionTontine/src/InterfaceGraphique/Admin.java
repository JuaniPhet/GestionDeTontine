package InterfaceGraphique;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Admin {

	public JFrame adminFrame;
	private JTextField textFieldName;
	private JTextField textFieldPrenom;
	private JTextField textFieldProfession;
	private JTextField textFieldEmail;
	private JTextField textFieldAdress;
	private JTextField textFieldPhone;
	private JTextField textFieldNationality;
	private JTable tableRegistMembers;
	public JComboBox textSex, textTypeMember;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin window = new Admin();
					window.adminFrame.setVisible(true);
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
	 * Create the application.
	 */
	public Admin() {
		initialize();
	}
	
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		adminFrame = new JFrame();
		adminFrame.setUndecorated(true);
		adminFrame.setBounds(100, 100, 1100, 710);
		adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminFrame.setLocationRelativeTo(null);
		adminFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(135, 206, 250));
		panel.setBounds(0, 0, 289, 720);
		adminFrame.getContentPane().add(panel);
		
		JButton prevMenuButton = new JButton("MENU PR\u00C9C\u00C9DENT ");
		prevMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminFrame.dispose();
				new Menu().menuFrame.setVisible(true);
			}
		});
		prevMenuButton.setForeground(new Color(255, 250, 250));
		prevMenuButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		prevMenuButton.setBorderPainted(false);
		prevMenuButton.setBackground(new Color(25, 25, 112));
		prevMenuButton.setBounds(10, 635, 269, 53);
		panel.add(prevMenuButton);
		
		JButton addMemberButton = new JButton("Ajouter un Membre");
		addMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect();
					pst = con.prepareStatement("insert into tb_members(nom, prenom, profession, email, adresse, telephone, sexe, nationalite, type) values (?,?,?,?,?,?,?,?,?)");
					pst.setString(1, textFieldName.getText());
					pst.setString(2, textFieldPrenom.getText());
					pst.setString(3, textFieldProfession.getText());
					pst.setString(4, textFieldEmail.getText());
					pst.setString(5, textFieldAdress.getText());
					pst.setString(6, textFieldPhone.getText());
					pst.setString(7, textSex.getSelectedItem().toString());
					pst.setString(8, textFieldNationality.getText());
					pst.setString(9, textTypeMember.getSelectedItem().toString());
					pst.executeUpdate();
					con.close();
					JOptionPane.showMessageDialog(addMemberButton, textFieldName.getText()+" Ajouté");
				} catch (Exception e2) {
					e2.printStackTrace();
;				}
			}
		});
		addMemberButton.setHorizontalTextPosition(SwingConstants.CENTER);
		addMemberButton.setForeground(Color.BLACK);
		addMemberButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		addMemberButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		addMemberButton.setBackground(new Color(211, 211, 211));
		addMemberButton.setBounds(10, 30, 269, 53);
		panel.add(addMemberButton);
		
		JButton deleteMemberButton = new JButton("Supprimer un Membre");
		deleteMemberButton.setHorizontalTextPosition(SwingConstants.CENTER);
		deleteMemberButton.setForeground(Color.BLACK);
		deleteMemberButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		deleteMemberButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		deleteMemberButton.setBackground(new Color(211, 211, 211));
		deleteMemberButton.setBounds(10, 209, 269, 53);
		panel.add(deleteMemberButton);
		
		JButton updateMemberButton = new JButton("Modifier un Membre");
		updateMemberButton.setHorizontalTextPosition(SwingConstants.CENTER);
		updateMemberButton.setForeground(Color.BLACK);
		updateMemberButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		updateMemberButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		updateMemberButton.setBackground(new Color(211, 211, 211));
		updateMemberButton.setBounds(10, 120, 269, 53);
		panel.add(updateMemberButton);
		
		JButton addInformation = new JButton("Ajouter une Information");
		addInformation.setHorizontalTextPosition(SwingConstants.CENTER);
		addInformation.setForeground(Color.BLACK);
		addInformation.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		addInformation.setBorder(new LineBorder(new Color(0, 0, 0)));
		addInformation.setBackground(new Color(211, 211, 211));
		addInformation.setBounds(10, 299, 269, 53);
		panel.add(addInformation);
		
		JPanel formMembers = new JPanel();
		formMembers.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		formMembers.setBackground(new Color(240, 255, 240));
		formMembers.setBorder(new LineBorder(new Color(0, 0, 0)));
		formMembers.setBounds(295, 31, 795, 320);
		adminFrame.getContentPane().add(formMembers);
		formMembers.setLayout(null);
		
		JLabel nameLabel = new JLabel("Nom :");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		nameLabel.setBounds(20, 40, 92, 36);
		formMembers.add(nameLabel);
		
		textFieldName = new JTextField();
		textFieldName.setText(" ");
		nameLabel.setLabelFor(textFieldName);
		textFieldName.setBounds(122, 40, 253, 30);
		formMembers.add(textFieldName);
		textFieldName.setColumns(10);
		
		JLabel prenameLabel = new JLabel("Prenom :");
		prenameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		prenameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		prenameLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		prenameLabel.setBounds(20, 83, 92, 36);
		formMembers.add(prenameLabel);
		
		textFieldPrenom = new JTextField();
		prenameLabel.setLabelFor(textFieldPrenom);
		textFieldPrenom.setColumns(10);
		textFieldPrenom.setBounds(122, 88, 253, 30);
		formMembers.add(textFieldPrenom);
		
		JLabel professionLabel = new JLabel("Profession :");
		professionLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		professionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		professionLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		professionLabel.setBounds(20, 143, 92, 36);
		formMembers.add(professionLabel);
		
		textFieldProfession = new JTextField();
		professionLabel.setLabelFor(textFieldProfession);
		textFieldProfession.setColumns(10);
		textFieldProfession.setBounds(122, 148, 253, 30);
		formMembers.add(textFieldProfession);
		
		JLabel emailLabel = new JLabel("Email :");
		emailLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emailLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		emailLabel.setBounds(20, 205, 92, 36);
		formMembers.add(emailLabel);
		
		textFieldEmail = new JTextField();
		emailLabel.setLabelFor(textFieldEmail);
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(122, 210, 253, 30);
		formMembers.add(textFieldEmail);
		
		JLabel adressLabel = new JLabel("Adresse :");
		adressLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		adressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		adressLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		adressLabel.setBounds(20, 268, 92, 36);
		formMembers.add(adressLabel);
		
		textFieldAdress = new JTextField();
		adressLabel.setLabelFor(textFieldAdress);
		textFieldAdress.setColumns(10);
		textFieldAdress.setBounds(122, 268, 253, 30);
		formMembers.add(textFieldAdress);
		
		JLabel phoneLabel = new JLabel("T\u00E9l\u00E9phone  :");
		phoneLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		phoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		phoneLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		phoneLabel.setBounds(423, 40, 92, 36);
		formMembers.add(phoneLabel);
		
		textFieldPhone = new JTextField();
		phoneLabel.setLabelFor(textFieldPhone);
		textFieldPhone.setColumns(10);
		textFieldPhone.setBounds(525, 40, 253, 30);
		formMembers.add(textFieldPhone);
		
		JLabel sexLabel = new JLabel("Sexe :");
		sexLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		sexLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sexLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		sexLabel.setBounds(423, 83, 92, 36);
		formMembers.add(sexLabel);
		
		JLabel nationalityLabel = new JLabel("Nationalit\u00E9 :");
		nationalityLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nationalityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nationalityLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		nationalityLabel.setBounds(423, 143, 92, 36);
		formMembers.add(nationalityLabel);
		
		textFieldNationality = new JTextField();
		nationalityLabel.setLabelFor(textFieldNationality);
		textFieldNationality.setColumns(10);
		textFieldNationality.setBounds(525, 148, 253, 30);
		formMembers.add(textFieldNationality);
		
		JLabel typeLabel = new JLabel("Type Membre :");
		typeLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		typeLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		typeLabel.setBounds(404, 205, 112, 36);
		formMembers.add(typeLabel);
		
		String s1[] = {"Simple", "Administrateur"};
		textTypeMember = new JComboBox(s1);
		typeLabel.setLabelFor(textTypeMember);
		textTypeMember.setSelectedIndex(0);
		textTypeMember.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		textTypeMember.setSelectedIndex(0);
		textTypeMember.setBounds(525, 211, 253, 30);
		formMembers.add(textTypeMember);
		
		String[] s2 = { "Masculin", "Feminin" };
		textSex = new JComboBox(s2);
		sexLabel.setLabelFor(textSex);
		textSex.setSelectedIndex(0);
		textSex.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		textSex.setBounds(525, 89, 253, 30);
		formMembers.add(textSex);
		
		JScrollPane scrollPaneTableRegistMember = new JScrollPane();
		scrollPaneTableRegistMember.setBounds(299, 362, 791, 326);
		adminFrame.getContentPane().add(scrollPaneTableRegistMember);
		
		tableRegistMembers = new JTable();
		scrollPaneTableRegistMember.setViewportView(tableRegistMembers);
		tableRegistMembers.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
	}
}
