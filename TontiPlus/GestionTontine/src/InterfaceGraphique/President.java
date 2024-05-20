package InterfaceGraphique;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Button;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class President {

	public JFrame presidentFrame;
	private JTextField textFieldName;
	private JTextField textFieldPrenom;
	private JTextField textFieldProfession;
	private JTextField textFieldEmail;
	private JTextField textFieldAdress;
	private JTextField textFieldPhone;
	private JTextField textFieldNationality;
	private JTable tableRegistMembers;
	private JPasswordField pwdField;
	public JComboBox textSex, textTypeMember;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					President window = new President();
					window.presidentFrame.setVisible(true);
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
	
	/**
	 * Take informations from the table in database to show them in the software 
	 */
	public void showTable() {
		try {
			connect();
			String [] entete = {"Id", "Name", "Prename", "Occupation", "Email", "Adress", "Phone", "Gender", "Nationality", "Type"};
			String [] ligne = new String[12];
			
			DefaultTableModel model = new DefaultTableModel(null, entete);
			
			String sql = "select * from tb_members";
			Statement st = con.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				ligne[0] = rs.getString("id");
				ligne[1] = rs.getString("nom");
				ligne[2] = rs.getString("prenom");
				ligne[3] = rs.getString("profession");
				ligne[4] = rs.getString("email");
				ligne[5] = rs.getString("adresse");
				ligne[6] = rs.getString("telephone");
				ligne[7] = rs.getString("sexe");
				ligne[8] = rs.getString("nationalite");
				ligne[9] = rs.getString("type");
				model.addRow(ligne);
			}
			tableRegistMembers.setModel(model);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setFieldVide() {
		textFieldName.setText("");
		textFieldPrenom.setText("");
		textFieldProfession.setText("");
		textFieldEmail.setText("");
		textFieldAdress.setText("");
		textFieldPhone.setText("");
		textSex.setSelectedItem("");
		textFieldNationality.setText("");
		textTypeMember.setSelectedItem("");
		pwdField.setText("");
	}
	
	/**
	 * Create the application.
	 */
	public President() {
		initialize();
		showTable();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		presidentFrame = new JFrame();
		presidentFrame.setUndecorated(true);
		presidentFrame.setBounds(100, 100, 1100, 710);
		presidentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		presidentFrame.setLocationRelativeTo(null);
		presidentFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(135, 206, 250));
		panel.setBounds(0, 0, 289, 720);
		presidentFrame.getContentPane().add(panel);
		
		JButton prevMenuButton = new JButton("PREVIOUS MENU");
		prevMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				presidentFrame.dispose();
				new Menu().menuFrame.setVisible(true);
			}
		});
		prevMenuButton.setForeground(new Color(255, 250, 250));
		prevMenuButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		prevMenuButton.setBorderPainted(false);
		prevMenuButton.setBackground(new Color(25, 25, 112));
		prevMenuButton.setBounds(10, 635, 269, 53);
		panel.add(prevMenuButton);
		
		JButton addMemberButton = new JButton("Add a Member");
		addMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect();
					pst = con.prepareStatement("insert into tb_members(nom, prenom, profession, email, adresse, telephone, sexe, nationalite, type, mot_de_passe) values (?,?,?,?,?,?,?,?,?,?)");
					pst.setString(1, textFieldName.getText());
					pst.setString(2, textFieldPrenom.getText());
					pst.setString(3, textFieldProfession.getText());
					pst.setString(4, textFieldEmail.getText());
					pst.setString(5, textFieldAdress.getText());
					pst.setString(6, textFieldPhone.getText());
					pst.setString(7, textSex.getSelectedItem().toString());
					pst.setString(8, textFieldNationality.getText());
					pst.setString(9, textTypeMember.getSelectedItem().toString());
					pst.setString(10, pwdField.getText());
					pst.executeUpdate();
					con.close();
					showTable();
					JOptionPane.showMessageDialog(addMemberButton, textFieldName.getText()+" added");
					setFieldVide();
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
		
		JButton deleteMemberButton = new JButton("Remove a Member");
		deleteMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect();
					pst = con.prepareStatement("delete from tb_members where id=?");
					int id = tableRegistMembers.getSelectedRow();
					String select = (String)tableRegistMembers.getValueAt(id, 0);
					pst.setString(1, select);
					pst.executeUpdate();
					con.close();
					showTable();
					JOptionPane.showMessageDialog(null, textFieldName.getText()+" removed");
					setFieldVide();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		deleteMemberButton.setHorizontalTextPosition(SwingConstants.CENTER);
		deleteMemberButton.setForeground(Color.BLACK);
		deleteMemberButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		deleteMemberButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		deleteMemberButton.setBackground(new Color(211, 211, 211));
		deleteMemberButton.setBounds(10, 209, 269, 53);
		panel.add(deleteMemberButton);
		
		JButton updateMemberButton = new JButton("Edit a Member");
		updateMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = tableRegistMembers.getSelectedRow();
				String select = (String)tableRegistMembers.getValueAt(id, 0);
				
				try {
					connect();
					pst = con.prepareStatement("update tb_members set nom=?, prenom=?, profession=?, email=?, adresse=?, telephone=?, sexe=?, nationalite=?, type=?, mot_de_passe=?" + "where id=?");
					
					pst.setString(1, textFieldName.getText());
					pst.setString(2, textFieldPrenom.getText());
					pst.setString(3, textFieldProfession.getText());
					pst.setString(4, textFieldEmail.getText());
					pst.setString(5, textFieldAdress.getText());
					pst.setString(6, textFieldPhone.getText());
					pst.setString(7, textSex.getSelectedItem().toString());
					pst.setString(8, textFieldNationality.getText());
					pst.setString(9, textTypeMember.getSelectedItem().toString());
					pst.setString(10, pwdField.getText());
					pst.setString(11, select);
					pst.executeUpdate();
					con.close();
					showTable();
					JOptionPane.showMessageDialog(addMemberButton, textFieldName.getText()+" edited");
					setFieldVide();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		updateMemberButton.setHorizontalTextPosition(SwingConstants.CENTER);
		updateMemberButton.setForeground(Color.BLACK);
		updateMemberButton.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		updateMemberButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		updateMemberButton.setBackground(new Color(211, 211, 211));
		updateMemberButton.setBounds(10, 120, 269, 53);
		panel.add(updateMemberButton);
		
		JPanel formMembers = new JPanel();
		formMembers.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		formMembers.setBackground(new Color(240, 255, 240));
		formMembers.setBorder(new LineBorder(new Color(0, 0, 0)));
		formMembers.setBounds(295, 31, 795, 320);
		presidentFrame.getContentPane().add(formMembers);
		formMembers.setLayout(null);
		
		JLabel nameLabel = new JLabel("Name :");
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
		
		JLabel prenameLabel = new JLabel("Prename :");
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
		
		JLabel professionLabel = new JLabel("Occupation :");
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
		
		JLabel adressLabel = new JLabel("Adress :");
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
		
		JLabel phoneLabel = new JLabel("Phone  :");
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
		
		JLabel sexLabel = new JLabel("Gender :");
		sexLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		sexLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sexLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		sexLabel.setBounds(423, 83, 92, 36);
		formMembers.add(sexLabel);
		
		JLabel nationalityLabel = new JLabel("Nationality :");
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
		
		JLabel typeLabel = new JLabel("Type of Member :");
		typeLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		typeLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		typeLabel.setBounds(404, 205, 112, 36);
		formMembers.add(typeLabel);
		
		String s1[] = {"Simple", "President", "Auditor", "Secretary"};
		textTypeMember = new JComboBox(s1);
		typeLabel.setLabelFor(textTypeMember);
		textTypeMember.setSelectedIndex(0);
		textTypeMember.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		textTypeMember.setSelectedIndex(0);
		textTypeMember.setBounds(525, 211, 253, 30);
		formMembers.add(textTypeMember);
		
		String[] s2 = { "Male", "Female" };
		textSex = new JComboBox(s2);
		sexLabel.setLabelFor(textSex);
		textSex.setSelectedIndex(0);
		textSex.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		textSex.setBounds(525, 89, 253, 30);
		formMembers.add(textSex);
		
		pwdField = new JPasswordField();
		pwdField.setBounds(525, 268, 253, 30);
		formMembers.add(pwdField);
		
		JLabel mdpLabelInAdmin = new JLabel("Password :");
		mdpLabelInAdmin.setLabelFor(pwdField);
		mdpLabelInAdmin.setHorizontalTextPosition(SwingConstants.CENTER);
		mdpLabelInAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		mdpLabelInAdmin.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		mdpLabelInAdmin.setBounds(404, 263, 111, 36);
		formMembers.add(mdpLabelInAdmin);
		
		JScrollPane scrollPaneTableRegistMember = new JScrollPane();
		scrollPaneTableRegistMember.setBounds(299, 362, 791, 326);
		presidentFrame.getContentPane().add(scrollPaneTableRegistMember);
		
		tableRegistMembers = new JTable();
		tableRegistMembers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					connect();
					int id = tableRegistMembers.getSelectedRow();
					String select = (String)tableRegistMembers.getValueAt(id, 0);
					
					pst = con.prepareStatement("Select * from tb_members where id ='"+select+"'");
					rs = pst.executeQuery();
					
					if(rs.next()) {
						textFieldName.setText(rs.getString("nom"));
						textFieldPrenom.setText(rs.getString("prenom"));
						textFieldProfession.setText(rs.getString("profession"));
						textFieldEmail.setText(rs.getString("email"));
						textFieldAdress.setText(rs.getString("adresse"));
						textFieldPhone.setText(rs.getString("telephone"));
						textSex.setSelectedItem(rs.getString("sexe"));
						textFieldNationality.setText(rs.getString("nationalite"));
						textTypeMember.setSelectedItem(rs.getString("type"));
						pwdField.setText(rs.getString("mot_de_passe"));
					}
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		scrollPaneTableRegistMember.setViewportView(tableRegistMembers);
		tableRegistMembers.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		
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
		presidentFrame.getContentPane().add(exitButton);
		
	}
}
