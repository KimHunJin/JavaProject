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
	JButton jbt = new JButton("검색");
	String[] genre = {"전체","R"
			+ "PG","FPS","SIMULATION","SPORTS","AOS","ACTION","RACING","ARCADE","RTS","BOARD","MUSCI","SANDBOX"};
	String[] distributor = {"전체","넥슨","블리자드","넷마블","라이엇","스팀","오리진","엔씨소프트","위메이드","윈디소프트","워게이밍","피망","세시소프트","소프트빅뱅","게임트리",
			"한게임","무브게임즈","에이케이 인터렉티브","웹젠","T3 엔터테이먼트","아이덴티티 게임즈" };
	String[] title = {"게임명","OS","CPU","용량","RAM","Graphics Card","장르","배급사"};
	JComboBox jcb_genre = new JComboBox(genre);
	JComboBox jcb_dis = new JComboBox(distributor);
	JTable tb;
	int i=0, j=0;
	JAVA_Project(String user_id) throws SQLException {
		super("SPEC COM");
		addWindowListener(new WindowAdapter() {  // 창이 실행 되면 db 연동
			SQL_select sql_c = new SQL_select();
			public void windowActivated(WindowEvent e) {
				
				sql_c.connectDatabase();
			}
			
			public void windowClosing(WindowEvent e) {
				sql_c.close();
			}
		});	
		if(user_id == null) {  // user의 아이디가 null일 경우 임의로 A라고 지정
			jlb.setText("A");
		}
		else {
			jlb.setText(user_id);
		}
		String[] name = {"게임명", "장르", "배급사 이름"};
		SQL_select sql_db = new SQL_select();
		sql_db.connectDatabase();
		
		DefaultTableModel model = new DefaultTableModel(sql_db.selectDatabase(),name) {  // db에서 정보를 가져와 테이블 생성
			
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};

		tb = new JTable(model);
		jsp = new JScrollPane(tb);

		tb.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {  // 마우스 클릭 이벤트
				if(e.getClickCount()==2) {  // 더블클릭 했을 경우 테이블의 행을 받아와 그 행의 첫번 째 열의 값을 data에 입력
					JTable tb = (JTable)e.getSource();
					int row = tb.getSelectedRow();

					String data = (String)tb.getValueAt(row, 0);
					if(row!=-1) {  // 선택된 행이 있을 경우 세부 사항 폼으로 이동
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
	public void actionPerformed(ActionEvent e) {  // 검색 버튼 클릭 시
		// TODO Auto-generated method stub
		SQL_select sql = new SQL_select();
		sql.connectDatabase();
		
		String[] name = {"게임명", "장르", "배급사 이름"};
		
		DefaultTableModel model = (DefaultTableModel) tb.getModel();
		int rowNum = model.getRowCount();
		for(int i=rowNum-1; i >= 0; i--) {  // 모든 검색 리스트 삭제
			model.removeRow(i);
		}
		try {
			if(jtf.getText() == "") {
				model.setDataVector(sql.selectDatabase(), name);  // 글을 쓴 것이 없을 경우 모든 데이터를 보여줌
			}
			else {
				model.setDataVector(sql.select_game(jtf.getText()), name);  // 글을 썼을 경우 그 데이터에 맞는 정보를 보여줌
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql.close();
	}

}
