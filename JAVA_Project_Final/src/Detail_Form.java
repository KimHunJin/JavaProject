import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Detail_Form extends JPanel implements ActionListener {
	
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image img_test = null;
	JTextField Gamename = new JTextField();
	JTextField CPU = new JTextField();
	JTextField OS = new JTextField();
	JTextField HDD = new JTextField();
	JTextField RAM = new JTextField();
	JTextField Graphics = new JTextField();
	JTextField URL = new JTextField();
	JTextField Dst = new JTextField();
	JTextField Genre = new JTextField();
	JLabel LGame, LCPU, LOS, LHDD, LRAM, LGraphics, LURL, LImage, GImage, LDst, LGenre,Luser;
	JButton jbt_add, jbt_fav, jbt_del, jbt_up, jbt_close;
	JFrame jf = new JFrame();
	String image_url = new String();
	int click_num = 0;
	public Detail_Form(String data, String user) throws ClassNotFoundException {
		
		setSize(700,700);
		setLayout(null);
		jf.setTitle("세 부 사 항");
		
		img_test = kit.getImage("image/배경3.png");
		//img_test = kit.getImage(getClass().getResource("image/배경3.png"));  // export할 경우 이미지 첨부를 위해 사용

		LGame = new JLabel("게임명 ");
		LDst = new JLabel("배급사");
		LGenre = new JLabel("장르");
		LCPU = new JLabel("CPU");
		LOS = new JLabel("OS");
		LHDD = new JLabel("HDD");
		LRAM = new JLabel("RAM");
		LGraphics = new JLabel("Graphics Card");
		LURL = new JLabel("URL");
		jbt_add = new JButton("삽입");
		jbt_fav = new JButton("즐겨찾기");
		jbt_del = new JButton("삭제");
		jbt_up = new JButton("수정");
		jbt_close = new JButton("닫기");
		GImage = new JLabel();
		
		Luser = new JLabel(user);
		
		LGame.setBounds(200, 30, 100, 30);
		Gamename.setBounds(260, 30, 320, 30);
		LDst.setBounds(200, 70, 100, 30);
		Dst.setBounds(260 ,70 ,320 ,30);
		LGenre.setBounds(200, 110, 100, 30);
		Genre.setBounds(260, 110, 320, 30);
		LOS.setBounds(20, 180, 100, 30);
		OS.setBounds(80, 180, 500, 30);
		LCPU.setBounds(20, 220, 100, 30);
		CPU.setBounds(80, 220, 500, 30);
		LHDD.setBounds(20, 260, 100, 30);
		HDD.setBounds(80, 260, 200, 30);
		LRAM.setBounds(300, 260, 100, 30);
		RAM.setBounds(380, 260, 200, 30);
		LGraphics.setBounds(20, 300, 100, 30);
		Graphics.setBounds(80, 300, 500, 30);
		LURL.setBounds(20, 340, 100, 30);
		URL.setBounds(80, 340, 500, 30);
		jbt_fav.setBounds(20, 380, 130, 30);
		jbt_add.setBounds(20, 380, 130, 30);
		jbt_del.setBounds(300, 380, 130, 30);
		jbt_up.setBounds(160, 380, 130, 30);
		jbt_close.setBounds(440, 380, 130, 30);
		
		
		add(Gamename);	add(OS);		add(CPU);
		add(HDD);		add(RAM);		add(Graphics);
		add(URL);		add(LGame);		add(LCPU);
		add(LOS);		add(LHDD);		add(LRAM);
		add(LGraphics);	add(LURL);		add(jbt_close);
		add(jbt_add);	add(GImage);	add(LDst);
		add(LGenre);	add(Genre);		add(Dst);
		add(jbt_fav);	add(jbt_del);	add(jbt_up);
			
		
		try {
			SQL_select s_a = new SQL_select();	
			s_a.connectDatabase();
			String spec[] = new String[10];
			spec = s_a.select_all(data);  // spec 배열을 선언하고 그 값들을 가져옴
			Gamename.setText(spec[0]);
			OS.setText(spec[5]);
			CPU.setText(spec[4]);
			HDD.setText(spec[6]);
			RAM.setText(spec[7]);
			Graphics.setText(spec[8]);
			image_url = spec[9];
			Dst.setText(spec[2]);
			Genre.setText(spec[1]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon g_img = new ImageIcon(image_url+".jpg");  // 이미지를 가져옴
		//ImageIcon g_img = new ImageIcon(getClass().getResource(image_url+".jpg"));  // export 할 경우 이미지 첨부를 위해 사용
		GImage.setIcon(g_img);
		
		
		GImage.setBounds(20, 20, 150, 150);
		
		// admin, user, guest가 사용할 수 있는 기능이 다름
		if(user.equals("Admin") && data!="A") {
			Gamename.setEditable(false);
			OS.setEditable(false);
			CPU.setEditable(false);
			HDD.setEditable(false);
			RAM.setEditable(false);
			Graphics.setEditable(false);
			Genre.setEditable(false);
			Dst.setEditable(false);
			URL.setEditable(false);
			
			jbt_fav.setVisible(false);
			jbt_add.setVisible(true);
			jbt_up.setVisible(true);
			jbt_del.setVisible(true);
		}
		else if(user.equals("Admin") && data.equals("A")) {
			Gamename.setEditable(true);
			OS.setEditable(true);
			CPU.setEditable(true);
			HDD.setEditable(true);
			RAM.setEditable(true);
			Graphics.setEditable(true);
			Genre.setEditable(true);
			Dst.setEditable(true);
			URL.setEditable(true);
			
			jbt_fav.setVisible(false);
			jbt_add.setVisible(true);
			jbt_up.setVisible(false);
			jbt_del.setVisible(false);
			click_num = 1;
		}
		else if(user == "A") {
			Gamename.setEditable(false);
			OS.setEditable(false);
			CPU.setEditable(false);
			HDD.setEditable(false);
			RAM.setEditable(false);
			Graphics.setEditable(false);
			Genre.setEditable(false);
			Dst.setEditable(false);
			URL.setEditable(false);
			
			jbt_fav.setVisible(false);
			jbt_add.setVisible(false);
			jbt_up.setVisible(false);
			jbt_del.setVisible(false);
		}
		else {
			Gamename.setEditable(false);
			OS.setEditable(false);
			CPU.setEditable(false);
			HDD.setEditable(false);
			RAM.setEditable(false);
			Graphics.setEditable(false);
			Genre.setEditable(false);
			Dst.setEditable(false);
			URL.setEditable(false);
			
			jbt_add.setVisible(false);
			jbt_up.setVisible(false);
			jbt_del.setVisible(false);
		}
		
		
		
		jbt_add.addActionListener(this);
		jbt_close.addActionListener(this);
		jbt_del.addActionListener(this);
		jbt_up.addActionListener(this);
		jbt_fav.addActionListener(this);
		
		jf.setLocation(1200, 350); 
		jf.setPreferredSize(new Dimension(600, 500));
		jf.setVisible(true);
		Container con = jf.getContentPane(); 
		con.add(this); jf.pack();

	}
	// 배경이미지
	protected void paintComponent(Graphics g)     {   
		g.drawImage(img_test, 0, 0, 700, 700, this);   
	}   
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		SQL_select sql = new SQL_select();
		sql.connectDatabase();
		
		if(arg0.getSource().equals(jbt_add)) {  // 추가 버튼을 눌렀을 경우
			if(click_num == 0) {  // 클릭 횟수가 0 이면 입력 가능으로 변경
				Gamename.setEditable(true);
				OS.setEditable(true);
				CPU.setEditable(true);
				HDD.setEditable(true);
				RAM.setEditable(true);
				Graphics.setEditable(true);
				Genre.setEditable(true);
				Dst.setEditable(true);
				URL.setEditable(true);
				click_num++;
			}
			else if(click_num > 0) {  // 클릭 횟수가 0보다 크면 db에 입력
				sql.connectDatabase();	
				try {
					int count = Integer.parseInt(sql.select_gamecount().toString())+1;
					System.out.println(count);
					sql.sadd(Gamename.getText().toString(),CPU.getText().toString(),OS.getText().toString(),HDD.getText().toString(),RAM.getText().toString(),Graphics.getText().toString());
					sql.kadd(count, Gamename.getText().toString());
					sql.gadd(Gamename.getText().toString(), Genre.getText().toString(), null, Dst.getText().toString(), count);
				} catch(ClassNotFoundException e) {
					e.printStackTrace();
				} catch(SQLException e) {
					e.printStackTrace();
				}
				sql.close();
				click_num = 0;
				jf.dispose();
			}
		}
		if(arg0.getSource().equals(jbt_close)) {  //종료 버튼을 눌렀을 경우
			sql.close();
			jf.dispose();
		}
		
		if(arg0.getSource().equals(jbt_up)) {  // 수정 버튼을 눌렀을 경우
			if(click_num == 0) {  // 클릭을 한번 했으면 수정 가능하도록 변경
				Gamename.setEditable(false);
				OS.setEditable(true);
				CPU.setEditable(true);
				HDD.setEditable(true);
				RAM.setEditable(true);
				Graphics.setEditable(true);
				Genre.setEditable(true);
				Dst.setEditable(true);
				URL.setEditable(true);
				click_num++;
			}
			else if(click_num > 0) {  // 두번 하면 수정
				sql.connectDatabase();
				try {
					sql.update(Dst.getText(), Genre.getText(), OS.getText(), CPU.getText(), HDD.getText(), RAM.getText(), Graphics.getText(), Gamename.getText());
					JOptionPane.showMessageDialog(this, Gamename.getText() + "이(가) 수정 되었습니다.");
					sql.close();
					jf.dispose();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(arg0.getSource().equals(jbt_del)) {  // 삭제 버튼을 눌렀을 경우
			try {
				sql.del(Gamename.getText());
				JOptionPane.showMessageDialog(this, Gamename.getText()+"이(가) 삭제 되었습니다.");
				sql.close();
				jf.dispose();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		
		if(arg0.getSource().equals(jbt_fav)) {  // 즐겨찾기 버튼을 눌렀을 경우
			try {
				sql.select_num(Luser.getText(), Gamename.getText());
				JOptionPane.showMessageDialog(this, Gamename.getText()+"이(가) 즐겨찾기에 추가되었습니다.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
