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
		f = new JFrame ("회 원 가 입");
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
		
		L = new Label("회 원 가 입");
		l1 = new Label("ID");
		l2 = new Label("비밀번호");
		l3 = new Label("비밀번호 확인");
		l5 = new Label("이메일");
		
		tf1 = new JTextField(15);
		tf2 = new JTextField(5);
		tf3 = new JTextField(15);
		tf4 = new JTextField(15);
		
		jpf1 = new JPasswordField(15);
		jpf2 = new JPasswordField(15);
		
		btn1 = new Button("중복체크");
		btn2 = new Button("가입 완료");
		btn3 = new Button("가입 취소");
		
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
		if(a.equals("가입 완료")) {  // 가입 완료 버튼을 누를 경우
			
			if(tf1.getText().equals("")) {  // 아이디를 입력하지 않았을 경우
				JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
			} else if(jpf1.getText().equals("") || jpf2.getText().equals("")) {  // 패스워드를 입력하지 않았을 경우
				JOptionPane.showMessageDialog(this, "패스워드를 입력하세요.");
			}
			else {
			
			
				String pass = jpf1.getText();
			
				String pass_collect = jpf2.getText();
			
				if(pass.equals(pass_collect)) {  // 비밀번호와 비밀번호 확인이 다를 경우
					System.out.println(a);
				
					int count = 0;
					try {
						count = Integer.parseInt(sql_si.count());  // 회원의 수를 가져옴.
						count++;  // 회원 번호를 위한 count 증가
						sql_si.add(tf1.getText());
						sql_si.radd(tf1.getText(),jpf1.getText(),tf4.getText(),count);
					} catch(ClassNotFoundException e) {
						e.printStackTrace();
					} catch(SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "가입 성공!");
					Main_Home mh_r = new Main_Home(null); // 가입 성공 시 메인 화면으로 돌아감
					sql_si.close();
					f.dispose();
				}
				else {
					JOptionPane.showMessageDialog(this, "비밀번호를 다시 입력하십시오.");
				}
			}
		}
		else if(a.equals("중복체크")) {  // 중복체크 버튼을 클릭했을 경우
			
			int count = 0;
			try {
				count = Integer.parseInt(sql_si.select(tf1.getText())); // 아이디로 검색을 하여 그 아이디의 개수를 가져옴.
				if(count>=1) { // 중복된 아이디가 있을 경우
					JOptionPane.showMessageDialog(this, "중복된 아이디 입니다.");
				}
				else {  // 없을 경우
					JOptionPane.showMessageDialog(this, "사용가능한 아이디 입니다.");
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(a.equals("가입 취소")) {  // 가입 취소 버튼을 클릭했을 경우
			sql_si.close();
			f.dispose();
			Main_Home mh_r = new Main_Home(null);
			
		}
	}
}
