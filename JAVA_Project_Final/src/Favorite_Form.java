import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Favorite_Form extends JFrame {

	JTable tb;
	JScrollPane scp;
	
	public Favorite_Form(final String name) throws SQLException {
		super("즐 겨 찾 기");
		
		setSize(300, 430);
		setLocation(100, 350);
		
		addWindowListener(new WindowAdapter() {
			SQL_select fv_db = new SQL_select();
			public void windowActivated(WindowEvent e) {
				
				fv_db.connectDatabase();
			}
			
			public void windowClosing(WindowEvent e) {
				fv_db.close();
			}
		});	
		
		SQL_select fv_db = new SQL_select();
		fv_db.connectDatabase();
		
		String[] title = {"번호","게임명"};
		DefaultTableModel model = new DefaultTableModel(fv_db.favorite(name),title) {  // 즐겨찾기에 추가된 리스트를 가지고옴
			
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		
		tb = new JTable(model);
		scp = new JScrollPane(tb);
		
		tb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {  // 더블 클릭 시 상세 폼으로 넘어감
					JTable tb = (JTable)e.getSource();
					int row = tb.getSelectedRow();

					String data = (String)tb.getValueAt(row, 1);
					if(row!=-1) {
						try {
							Detail_Form df = new Detail_Form(data,name);
							df.setVisible(true);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		tb.setBounds(10, 40, 300, 500);
		scp.setBounds(10, 40, 300, 500);
		
		add(scp);
		
	}
}
