import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class register extends JFrame implements ActionListener {
	JFrame f;
	Panel p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p;
	Label l1,l2,l3,l4,l5,l6,L;
	JTextField tf1,tf2,tf3,tf4;
	JPasswordField jpf1,jpf2;
	Button btn1,btn2,btn3;
	public register(){
		f = new JFrame ("ȸ �� �� ��");
		f.setSize(400,100);
		f.setVisible(true);
		f.setLocation(400, 350);
		
		p1 = new Panel(new FlowLayout(FlowLayout.LEFT,5,5));
		p2 = new Panel(new FlowLayout(FlowLayout.LEFT,5,5));
		p3 = new Panel(new FlowLayout(FlowLayout.LEFT,5,5));
		p4 = new Panel(new FlowLayout(FlowLayout.LEFT,5,5));
		p5 = new Panel(new FlowLayout(FlowLayout.LEFT,5,5));
		p6 = new Panel(new FlowLayout(FlowLayout.LEFT,5,5));
		p7 = new Panel(new FlowLayout(FlowLayout.LEFT,5,5));
		p8 = new Panel();
		p9 = new Panel();
		p10 = new Panel();
		p11 = new Panel();
		p = new Panel();
		
		L = new Label("ȸ �� �� ��");
		l1 = new Label("ID");
		l2 = new Label("��й�ȣ");
		l3 = new Label("��й�ȣ Ȯ��");
		l5 = new Label("�̸���");
		
		tf1 = new JTextField(15);
		tf2 = new JTextField(5);
		tf3 = new JTextField(15);
		tf4 = new JTextField(15);
		
		jpf1 = new JPasswordField(15);
		jpf2 = new JPasswordField(15);
		
		btn1 = new Button("�ߺ�üũ");
		btn2 = new Button("���� �Ϸ�");
		btn3 = new Button("���� ���");
		
		p.add(L);
		
		p1.add(tf1);
		p1.add(btn1);
		
		p2.add(jpf1);
		
		p3.add(jpf2);
		
		p6.add(tf4);
		
		p7.setLayout(new GridLayout(4,1));
		p7.add(l1);
		p7.add(l2);
		p7.add(l3);
		p7.add(l5);
		
		p8.setLayout(new GridLayout(4,1));
		p8.add(p1);
		p8.add(p2);
		p8.add(p3);
		p8.add(p6);
		
		p9.setLayout(new BorderLayout());
		
		p10.add(btn2);
		p10.add(btn3);
		
		p11.setLayout(new BorderLayout());
		p11.add(p7,"West");
		p11.add(p8,"Center");
		p11.add(p9,"South");
		
		f.add(p,"North");
		f.add(p11,"Center");
		f.add(p10,"South");
		f.pack();
		
		btn2.addActionListener(this);
		btn1.addActionListener(this);
		btn3.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		SQL_select sql_si = new SQL_select();
		sql_si.connectDatabase();
		
		String a = arg0.getActionCommand();
		if(a.equals("���� �Ϸ�")) {  // ���� �Ϸ� ��ư�� ���� ���
			
			if(tf1.getText().equals("")) {  // ���̵� �Է����� �ʾ��� ���
				JOptionPane.showMessageDialog(this, "���̵� �Է��ϼ���.");
			} else if(jpf1.getText().equals("") || jpf2.getText().equals("")) {  // �н����带 �Է����� �ʾ��� ���
				JOptionPane.showMessageDialog(this, "�н����带 �Է��ϼ���.");
			}
			else {
			
			
				String pass = jpf1.getText();
			
				String pass_collect = jpf2.getText();
			
				if(pass.equals(pass_collect)) {  // ��й�ȣ�� ��й�ȣ Ȯ���� �ٸ� ���
					System.out.println(a);
				
					int count = 0;
					try {
						count = Integer.parseInt(sql_si.count());  // ȸ���� ���� ������.
						count++;  // ȸ�� ��ȣ�� ���� count ����
						sql_si.add(tf1.getText());
						sql_si.radd(tf1.getText(),jpf1.getText(),tf4.getText(),count);
					} catch(ClassNotFoundException e) {
						e.printStackTrace();
					} catch(SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "���� ����!");
					Main_Home mh_r = new Main_Home(null); // ���� ���� �� ���� ȭ������ ���ư�
					sql_si.close();
					f.dispose();
				}
				else {
					JOptionPane.showMessageDialog(this, "��й�ȣ�� �ٽ� �Է��Ͻʽÿ�.");
				}
			}
		}
		else if(a.equals("�ߺ�üũ")) {  // �ߺ�üũ ��ư�� Ŭ������ ���
			
			int count = 0;
			try {
				count = Integer.parseInt(sql_si.select(tf1.getText())); // ���̵�� �˻��� �Ͽ� �� ���̵��� ������ ������.
				if(count>=1) { // �ߺ��� ���̵� ���� ���
					JOptionPane.showMessageDialog(this, "�ߺ��� ���̵� �Դϴ�.");
				}
				else {  // ���� ���
					JOptionPane.showMessageDialog(this, "��밡���� ���̵� �Դϴ�.");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(a.equals("���� ���")) {  // ���� ��� ��ư�� Ŭ������ ���
			sql_si.close();
			f.dispose();
			Main_Home mh_r = new Main_Home(null);
			
		}
	}
}
