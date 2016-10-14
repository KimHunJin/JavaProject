import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class JAVA_Project extends JFrame implements ActionListener {

	/**
	 * @param args
	 */
	static String[][] data;
	JTextField jtf = new JTextField();
	JScrollPane jsp;
	JLabel jlb = new JLabel();
	JButton jbt = new JButton("�˻�");
	String[] genre = {"��ü","R"
			+ "PG","FPS","SIMULATION","SPORTS","AOS","ACTION","RACING","ARCADE","RTS","BOARD","MUSCI","SANDBOX"};
	String[] distributor = {"��ü","�ؽ�","���ڵ�","�ݸ���","���̾�","����","������","��������Ʈ","�����̵�","�������Ʈ","�����̹�","�Ǹ�","���ü���Ʈ","����Ʈ���","����Ʈ��",
			"�Ѱ���","���������","�������� ���ͷ�Ƽ��","����","T3 �������̸�Ʈ","���̵�ƼƼ ������" };
	String[] title = {"���Ӹ�","OS","CPU","�뷮","RAM","Graphics Card","�帣","��޻�"};
	JComboBox jcb_genre = new JComboBox(genre);
	JComboBox jcb_dis = new JComboBox(distributor);
	JTable tb;
	int i=0, j=0;
	JAVA_Project(String user_id) throws SQLException {
		super("SPEC COM");
		addWindowListener(new WindowAdapter() {  // â�� ���� �Ǹ� db ����
			SQL_select sql_c = new SQL_select();
			public void windowActivated(WindowEvent e) {
				
				sql_c.connectDatabase();
			}
			
			public void windowClosing(WindowEvent e) {
				sql_c.close();
			}
		});	
		if(user_id == null) {  // user�� ���̵� null�� ��� ���Ƿ� A��� ����
			jlb.setText("A");
		}
		else {
			jlb.setText(user_id);
		}
		String[] name = {"���Ӹ�", "�帣", "��޻� �̸�"};
		SQL_select sql_db = new SQL_select();
		sql_db.connectDatabase();
		
		DefaultTableModel model = new DefaultTableModel(sql_db.selectDatabase(),name) {  // db���� ������ ������ ���̺� ����
			
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};

		tb = new JTable(model);
		jsp = new JScrollPane(tb);

		tb.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {  // ���콺 Ŭ�� �̺�Ʈ
				if(e.getClickCount()==2) {  // ����Ŭ�� ���� ��� ���̺��� ���� �޾ƿ� �� ���� ù�� ° ���� ���� data�� �Է�
					JTable tb = (JTable)e.getSource();
					int row = tb.getSelectedRow();

					String data = (String)tb.getValueAt(row, 0);
					if(row!=-1) {  // ���õ� ���� ���� ��� ���� ���� ������ �̵�
						try {
							Detail_Form df = new Detail_Form(data,jlb.getText().toString());
							df.setVisible(true);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		setSize(580,600);
		setLayout(null);
		setLocation(865,300);
		
		
		jcb_genre.setBounds(0, 0, 150, 30);
		jcb_dis.setBounds(155, 0, 150, 30);
		jtf.setBounds(310, 0, 170, 30);
		jbt.setBounds(485, 0, 75, 30);
		tb.setBounds(0, 50, 560, 500);
		jsp.setBounds(0, 50, 560, 500);
		
		
		add(jcb_genre);
		add(jcb_dis);
		add(jtf);
		add(jbt);
		add(jsp);
		setVisible(true);
		
		jbt.addActionListener(this);

	}
	@Override
	public void actionPerformed(ActionEvent e) {  // �˻� ��ư Ŭ�� ��
		// TODO Auto-generated method stub
		SQL_select sql = new SQL_select();
		sql.connectDatabase();
		
		String[] name = {"���Ӹ�", "�帣", "��޻� �̸�"};
		
		DefaultTableModel model = (DefaultTableModel) tb.getModel();
		int rowNum = model.getRowCount();
		for(int i=rowNum-1; i >= 0; i--) {  // ��� �˻� ����Ʈ ����
			model.removeRow(i);
		}
		try {
			if(jtf.getText() == "") {
				model.setDataVector(sql.selectDatabase(), name);  // ���� �� ���� ���� ��� ��� �����͸� ������
			}
			else {
				model.setDataVector(sql.select_game(jtf.getText()), name);  // ���� ���� ��� �� �����Ϳ� �´� ������ ������
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql.close();
	}

}
