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
		f = new JFrame("로 그 인");
				
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
		
		L = new Label("로 그 인");
		l1 = new Label("ID");
		l2 = new Label("Password");
		
		tf = new JTextField(15);
		jpf = new JPasswordField(15);
		
		btn1 = new Button("확인");
		btn2 = new Button("취소");
		
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
		if(str.equals("확인")) {  // 확인 버튼을 클릭할 경우
			
			int count_id = 0;
			String pwd;
			try {
				count_id = Integer.parseInt(sql_si.select(tf.getText()));
				pwd =sql_si.select_pwd(tf.getText());
				if(tf.getText().equals("")) {  // id를 입력하지 않은 경우
					JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
				}
				else if(jpf.getText().equals("")) {  // 비밀번호를 입력하지 않은 경우
					JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요.");
				}
				else {
					if(count_id < 1) {  // id가 없는 경우
						JOptionPane.showMessageDialog(this, "없는 아이디 입니다.");
					}
					else if(jpf.getText().equals(pwd)) {  // 비밀번호가 맞은 경우
						
						JOptionPane.showMessageDialog(this, "로그인 성공!");					
						Main_Home mh_login = new Main_Home(sql_si.select_id(tf.getText()));
						sql_si.close();
						f.dispose();
						
					}
					else {  // 비밀번호가 틀린 경우
						
						JOptionPane.showMessageDialog(this, "비밀번호가 잘못되었습니다.");
			
					}
				}
				
			} catch (ClassNotFoundException | SQLException a) {
				// TODO Auto-generated catch block
				a.printStackTrace();
			}
		}
		else if(str.equals("취소")) {  // 취소 버튼을 클릭할 경우
			sql_si.close();
			f.dispose();
			Main_Home mh = new Main_Home(null);		
		}		
	}
}
