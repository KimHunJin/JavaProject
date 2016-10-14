import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class login extends Frame implements ActionListener {
	JFrame f;
	Panel p1,p2,p3,p4,p5,p6,p7,p;
	Label l1,l2,L;
	JTextField tf;
	JPasswordField jpf;
	Button btn1,btn2;
	public login() {
		f = new JFrame("�� �� ��");
				
		f.setSize(150,100);
		f.setVisible(true);
		f.setLocation(400, 350);
		
		p1 = new Panel(new FlowLayout(FlowLayout.LEFT,5,5));
		p2 = new Panel(new FlowLayout(FlowLayout.LEFT,5,5));
		p3 = new Panel(new FlowLayout(FlowLayout.LEFT,5,5));
		p4= new Panel();
		p5 = new Panel();
		p6 = new Panel();
		p7 = new Panel();
		p = new Panel();
		
		L = new Label("�� �� ��");
		l1 = new Label("ID");
		l2 = new Label("Password");
		
		tf = new JTextField(15);
		jpf = new JPasswordField(15);
		
		btn1 = new Button("Ȯ��");
		btn2 = new Button("���");
		
		p.add(L);
		
		p1.add(tf);
		
		p2.add(jpf);
		
		p3.setLayout(new GridLayout(2,1));
		p3.add(l1);
		p3.add(l2);
		
		p4.setLayout(new GridLayout(2,1));
		p4.add(p1);
		p4.add(p2);
		
		p5.setLayout(new BorderLayout());
		
		p6.add(btn1);
		p6.add(btn2);
		
		p7.setLayout(new BorderLayout());
		p7.add(p3,"West");
		p7.add(p4,"Center");
		p7.add(p5,"South");
		
		f.add(p,"North");
		f.add(p7,"Center");
		f.add(p6,"South");
		f.pack();
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String str = e.getActionCommand();
		SQL_select sql_si = new SQL_select();
		sql_si.connectDatabase();
		if(str.equals("Ȯ��")) {  // Ȯ�� ��ư�� Ŭ���� ���
			
			int count_id = 0;
			String pwd;
			try {
				count_id = Integer.parseInt(sql_si.select(tf.getText()));
				pwd =sql_si.select_pwd(tf.getText());
				if(tf.getText().equals("")) {  // id�� �Է����� ���� ���
					JOptionPane.showMessageDialog(this, "���̵� �Է��ϼ���.");
				}
				else if(jpf.getText().equals("")) {  // ��й�ȣ�� �Է����� ���� ���
					JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է��ϼ���.");
				}
				else {
					if(count_id < 1) {  // id�� ���� ���
						JOptionPane.showMessageDialog(this, "���� ���̵� �Դϴ�.");
					}
					else if(jpf.getText().equals(pwd)) {  // ��й�ȣ�� ���� ���
						
						JOptionPane.showMessageDialog(this, "�α��� ����!");					
						Main_Home mh_login = new Main_Home(sql_si.select_id(tf.getText()));
						sql_si.close();
						f.dispose();
						
					}
					else {  // ��й�ȣ�� Ʋ�� ���
						
						JOptionPane.showMessageDialog(this, "��й�ȣ�� �߸��Ǿ����ϴ�.");
			
					}
				}
				
			} catch (ClassNotFoundException | SQLException a) {
				// TODO Auto-generated catch block
				a.printStackTrace();
			}
		}
		else if(str.equals("���")) {  // ��� ��ư�� Ŭ���� ���
			sql_si.close();
			f.dispose();
			Main_Home mh = new Main_Home(null);		
		}		
	}
}
