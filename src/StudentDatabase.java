import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StudentDatabase {

	private JFrame frame;
	private JTextField text_rno;
	private JTextField text_name;
	private JTable table;
	private JTextField student_rno_search;
	private JTextField text_mobno;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentDatabase window = new StudentDatabase();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentDatabase() {
		initialize();
		connect();
		table_load();
	}
	
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/studentdb","root",""); 
		}catch(ClassNotFoundException e){
			
		}catch(SQLException e){
			
		}
	}
	
	public void table_load() {
		try {
			pst = conn.prepareStatement("select * from students");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 803, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student DataBase");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 42));
		lblNewLabel.setBounds(208, 0, 420, 78);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel.setBounds(10, 88, 411, 261);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Student Name  : ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(24, 104, 177, 13);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Roll Number     : ");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(24, 53, 177, 13);
		panel.add(lblNewLabel_1_1);
		
		text_rno = new JTextField();
		text_rno.setBounds(191, 47, 193, 32);
		panel.add(text_rno);
		text_rno.setColumns(10);
		
		text_name = new JTextField();
		text_name.setColumns(10);
		text_name.setBounds(191, 98, 193, 32);
		panel.add(text_name);
		
		JLabel lblNewLabel_1_2 = new JLabel("Mobile Number : ");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_2.setBounds(24, 161, 161, 13);
		panel.add(lblNewLabel_1_2);
		
		text_mobno = new JTextField();
		text_mobno.setColumns(10);
		text_mobno.setBounds(191, 155, 193, 32);
		panel.add(text_mobno);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String rno,name,mobNo;
				
				rno = text_rno.getText();
				name = text_name.getText();
				mobNo = text_mobno.getText();
				
				try {
					pst = conn.prepareStatement("insert into students(rollno,name,mobile)values(?,?,?)");
					pst.setString(1,rno);
					pst.setString(2,name);
					pst.setString(3,mobNo);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Student Added Successfully !");
					table_load();
					text_rno.setText("");
					text_name.setText("");
					text_mobno.setText("");
					text_name.requestFocus();
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(55, 383, 94, 41);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExit.setBounds(307, 383, 94, 41);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text_rno.setText("");
				text_name.setText("");
				text_mobno.setText("");
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClear.setBounds(182, 383, 94, 41);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(431, 163, 325, 261);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_1.setBounds(431, 88, 325, 65);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Roll Number");
		lblNewLabel_1_1_1.setBounds(21, 21, 121, 22);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblNewLabel_1_1_1);
		
		student_rno_search = new JTextField();
		student_rno_search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					String rno = student_rno_search.getText();
					
					pst = conn.prepareStatement("select rollno,name,mobile from students where rollno = ?");
					pst.setString(1, rno);
					ResultSet rs =pst.executeQuery();
					
					if(rs.next()==true) {
						String rollno = rs.getString(1);
						String name = rs.getString(2);
						String mobNo = rs.getString(3);
						
						text_rno.setText(rollno);
						text_name.setText(name);
						text_mobno.setText(mobNo);
					}else {
						text_rno.setText("");
						text_name.setText("");
						text_mobno.setText("");
					}
					
				}catch(SQLException ex) {
					
				}
				
			}
		});
		student_rno_search.setBounds(152, 21, 148, 22);
		student_rno_search.setColumns(10);
		panel_1.add(student_rno_search);
		
		JButton btnNewButton_1 = new JButton("Update");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String rno,name,mobNo,sno;
				
				rno = text_rno.getText();
				name = text_name.getText();
				mobNo = text_mobno.getText();
				sno = student_rno_search.getText();
				
				try {
					pst = conn.prepareStatement("update students set rollno=?,name=?,mobile=? where rollno=?");
					pst.setString(1,rno);
					pst.setString(2,name);
					pst.setString(3,mobNo);
					pst.setString(4,sno);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record Updated Successfully !");
					table_load();
					text_rno.setText("");
					text_name.setText("");
					text_mobno.setText("");
					text_name.requestFocus();
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1.setBounds(482, 445, 94, 41);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sno;
				
				sno = student_rno_search.getText();
				
				try {
					pst = conn.prepareStatement("delete from students where rollno = ?");
		
					pst.setString(1,sno);
					pst.executeUpdate();
					JOptionPane.showConfirmDialog(null,"Do you wanr to delete Record !");
					table_load();
					text_rno.setText("");
					text_name.setText("");
					text_mobno.setText("");
					text_name.requestFocus();
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
				
			}
		});
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_2.setBounds(596, 445, 94, 41);
		frame.getContentPane().add(btnNewButton_2);
	}
}
