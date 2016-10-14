import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Main_Home extends JFrame implements ActionListener {
	// ������Ʈ ����
	JButton jbt_select = new JButton("�˻�");
	JButton jbt_login = new JButton("�α���");
	JButton jbt_singup = new JButton("ȸ������");
	JButton jbt_add = new JButton("����");
	JButton jbt_logout = new JButton("�α׾ƿ�");
	JButton jbt_favorite = new JButton("���ã��");
	JButton jbt_exit = new JButton("����");
	JLabel jlb_id = new JLabel();
	JLabel hello1 = new JLabel("SPEC COM");
	JLabel hello2 = new JLabel("Specification Comparison");
	static String id = new String();
	
	public Main_Home(String string) {
		
		super("SPEC COM");
		id = string;
		
		addWindowListener(new WindowAdapter() {
			SQL_select sql_c = new SQL_select();
			
			public void windowActivated(WindowEvent e) {
				sql_c.connectDatabase(); // â�� ���� �Ǹ� db�� ����
			}
			
			public void windowClosing(WindowEvent e) {  // â�� ���� �� db ���� ����
				sql_c.close();
				System.exit(0);
			}
		});	
		
		setLayout(null);
		setLocation(400,350);
		setSize(465,430);
	
		hello1.setBounds(60, 50, 400, 60);
		hello2.setBounds(80, 110, 300, 40);
		
		hello1.setFont(new Font("�������",Font.BOLD,62));
		hello2.setFont(new Font("�������",Font.BOLD,23));
		hello2.setForeground(Color.ORANGE);
		hello1.setForeground(Color.MAGENTA);
		
		jlb_id.setBounds(250, 250, 200, 30);
		jbt_select.setBounds(10, 300, 100, 30);
		jbt_login.setBounds(120, 300, 100, 30);
		jbt_logout.setBounds(120, 300, 100, 30);
		jbt_singup.setBounds(230, 300, 100, 30);
		jbt_favorite.setBounds(230, 300, 100, 30);
		jbt_add.setBounds(340, 300, 100, 30);
		jbt_exit.setBounds(340, 340, 100, 30);
		
		add(jbt_select); add(jbt_login);
		add(jbt_singup); add(jbt_add);
		add(jbt_logout); add(jbt_favorite);
		add(jbt_exit);	 add(hello1); add(hello2);
		
		jbt_favorite.setVisible(false);
		jbt_logout.setVisible(false);
		jbt_add.setVisible(false);
		setVisible(true);
		if(string == null) {  // id�� null �� ���
			jlb_id.setText("");
		}
		else if(string != null) {  // null�� �ƴ� ���
			jlb_id.setText(string+"�� ȯ���մϴ�.");
			jlb_id.setFont(new Font("�������",Font.BOLD,14));
			if(string.equals("Admin")) {
				jbt_add.setVisible(true);
			}
			jbt_logout.setVisible(true);
			jbt_favorite.setVisible(true);
			jbt_login.setVisible(false);
			jbt_singup.setVisible(false);
		}
		add(jlb_id);
		
		jbt_select.addActionListener(this);
		jbt_login.addActionListener(this);
		jbt_singup.addActionListener(this);
		jbt_add.addActionListener(this);
		jbt_exit.addActionListener(this);
		jbt_logout.addActionListener(this);
		jbt_favorite.addActionListener(this);
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Main_Home main = new Main_Home(null);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {  // �̺�Ʈ
		// TODO Auto-generated method stub
		SQL_select sql_make = new SQL_select();
		sql_make.connectDatabase();
		if(e.getSource().equals(jbt_select)) {  // �˻� ��ư�� �������
			try {
				if(id == "A") {  // id�� null �� ��� �ӽ÷� ���̵� a�� ��.
					JAVA_Project java = new JAVA_Project("A");
				}
				else { 
					sql_make.close();
					JAVA_Project java = new JAVA_Project(id);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource().equals(jbt_login)) {  // �α��� ��ư�� ���� ���
			login log = new login();
			this.dispose();
			sql_make.close();
			
		}
		if(e.getSource().equals(jbt_singup)) {  // ȸ������ ��ư�� ���� ���
			register reg = new register();
			this.dispose();
			sql_make.close();
		}
		if(e.getSource().equals(jbt_add)) {  // ���� ��ư�� ���� ���
			try {
				Detail_Form df = new Detail_Form("A","Admin");  // null ���� admin ������ ����
				df.setVisible(true);
				sql_make.close();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource().equals(jbt_exit)) {  // ���� ��ư�� ���� ���
			sql_make.close();
			System.exit(0);	
		}
		if(e.getSource().equals(jbt_logout)) {  // �α׾ƿ� ��ư�� ���� ���
			sql_make.close();
			this.dispose();
			Main_Home mh_log = new Main_Home(null);
		}
		if(e.getSource().equals(jbt_favorite)) {  // ���ã�� ��ư�� ���� ���
				
			try {
				Favorite_Form fvf;
				fvf = new Favorite_Form(id);
				fvf.setVisible(true);
				sql_make.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
	}
}
