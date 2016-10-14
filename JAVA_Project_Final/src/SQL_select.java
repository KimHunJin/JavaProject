import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQL_select {
	Connection con = null;
	Statement stmt = null;
	ResultSet rset = null;
	PreparedStatement ptmt;
	String str1 = new String();
	String str2 = new String();
	
	String url = "jdbc:mysql://localhost/final";
	String id = "KimHunJin";
	String pass = "mosquito";
	
	
	public SQL_select() {  // db ����
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Class Loading Complete");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void connectDatabase() {  // db ����
		try {
			con = DriverManager.getConnection(url, id, pass);
			stmt = con.createStatement();
			System.out.println("Make Connet Complete");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String select_gamecount() throws SQLException {  // ���� ����� ���ڸ� ���� �������� ���̺��� ����� ���� ���
		String count_spec = "select count(*) as count from `���ӻ��`";
		stmt = con.createStatement();
		rset = stmt.executeQuery(count_spec);
		
		while(rset.next()) {
			str2 = rset.getString("count");
		}
		System.out.println(str2);
		return str2;
	}
	
	public String[][] favorite(String name) throws SQLException {  // ȸ����ȣ�� ���ڸ� ���� �������� ���̺��� ����� ���� ���
		
		String count_fv = "SELECT COUNT(*) AS COUNT FROM `���ã��` WHERE `ȸ����ȣ` = (SELECT `ȸ����ȣ` FROM `ȸ��` WHERE ID=\'"+name+"\');";
		stmt = con.createStatement();
		rset = stmt.executeQuery(count_fv);
		
		while(rset.next()) {
			str2 = rset.getString("count");
		}
		
		String[][] GameTable = new String[Integer.parseInt(str2)][2];
		
		// ȸ���� ID�� ���ã�⿡�� ���Ӹ��� �˻�
		String fv_id = "SELECT `���Ӹ�` FROM `���ã��` WHERE `ȸ����ȣ` = (SELECT `ȸ����ȣ` FROM `ȸ��` WHERE ID=\'"+name+"\');";
		int i=0;
		try {
			rset = stmt.executeQuery(fv_id);
			while(rset.next()) {
				GameTable[i][0] = i+1+"";
				GameTable[i][1] = rset.getString("���Ӹ�");
				i++;
			}
		} catch (SQLException e) {
		e.printStackTrace();			
		}
		rset.close();
		return GameTable;
	}
	
	// id�� ��ġ�ϴ� ȸ����ȣ�� ã�Ƽ� ���ã�⿡ ����
	public void select_num(String id, String game) throws SQLException {
		
		String count_num = "select `ȸ����ȣ` from `ȸ��` where `ID`= \'" + id + "\'";
		stmt = con.createStatement();
		rset = stmt.executeQuery(count_num);
		
		while(rset.next()) {
			str2 = rset.getString("ȸ����ȣ");
		}
		
		String insert_fv = "insert into `���ã��` values(" + str2 + ",\'" + game + "\')";
		stmt.executeUpdate(insert_fv);
	}
	
	// ���ӻ���� ���ڸ� ���� ���̺��� �������� ����.
	public String[][] selectDatabase() throws SQLException {
		
		String count_spec = "select count(*) as count from `���ӻ��`";
		stmt = con.createStatement();
		rset = stmt.executeQuery(count_spec);
		
		while(rset.next()) {
			str2 = rset.getString("count");
		}
		
		String[][] GameTable = new String[Integer.parseInt(str2)][3];
		
		String select = "SELECT `���Ӹ�`,`�帣`,`��޻� �̸�` FROM `���� ���̺�`";
		int i =0;
		try {
			rset = stmt.executeQuery(select);
			while(rset.next()) {
				GameTable[i][0] = rset.getString("���Ӹ�");
				GameTable[i][1] = rset.getString("�帣");
				GameTable[i][2] = rset.getString("��޻� �̸�");
				i++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		rset.close();
		return GameTable;
	}
	
	// alpa ���� �Է� �޾� ���Ӹ��� �Է¹��� alpa�� ���� �˻��Ͽ� ���̺�� ������
	public String[][] select_game(String alpa) throws SQLException {
		
		String count_spec = "select count(*) as count from `���ӻ��` where `���Ӹ�` like \'" + alpa + "%\'";
		stmt = con.createStatement();
		rset = stmt.executeQuery(count_spec);
		
		while(rset.next()) {
			str2 = rset.getString("count");
		}
		
		String[][] GameTable = new String[Integer.parseInt(str2)][3];
		
		String select = "SELECT `���Ӹ�`,`�帣`,`��޻� �̸�` FROM `���� ���̺�` where `���Ӹ�` like \'" + alpa + "%\'";
		int i =0;
		try {
			rset = stmt.executeQuery(select);
			while(rset.next()) {
				GameTable[i][0] = rset.getString("���Ӹ�");
				GameTable[i][1] = rset.getString("�帣");
				GameTable[i][2] = rset.getString("��޻� �̸�");
				i++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		rset.close();
		return GameTable;
	}
	
	// id�� �Է¹޾� ���� ���̺��� �Է¹��� id�� ���� �޾ƿ�. �ߺ�üũ�� ���
	public String select(String sql_id) throws ClassNotFoundException, SQLException {
		
		String k = "select count(*) as count from `����`  where(`ID`=\"" + sql_id + "\")";
		rset = stmt.executeQuery(k);
		
		while(rset.next()) {
			str1 = rset.getString("count");
		}
		
		rset.close();
		return str1;
	}
	
	// �Է��� �޾ƿͼ� id�� pass ���� ����. ��й�ȣ Ȯ���� ���� ���
	public String select_pwd(String sql_id) throws ClassNotFoundException, SQLException {
		
		String k = "select pass from `ȸ��`  where(`id`=\"" + sql_id + "\")";
		rset = stmt.executeQuery(k);
		
		while(rset.next()) {
			str1 = rset.getString("pass");
		}
		
		rset.close();
		return str1;
	}
	
	// ���� ���̺��� ���ڸ� ��. ȸ����ȣ�� ������ų �������� ���
	public String count() throws ClassNotFoundException, SQLException {
		
		String select = "select count(*) as count from `����`";
		stmt = con.createStatement();
		rset = stmt.executeQuery(select);
		
		while(rset.next()) {
			str1 = rset.getString("count");
		}
		
		rset.close();
		return str1;
	}
	
	// ���ѿ� �Է¹޾ƿ� id�� �����ϰ�, ����� 0���� ��
	public void add(String string0) throws ClassNotFoundException, SQLException {
		stmt = con.createStatement();
		String s = "insert into `����` values(\"" + string0 + "\"," + 0 +")";
		System.out.println(s);
		stmt.executeUpdate(s);
	}
	
	// ȸ�� ���̺� �Է� �޾ƿ� ID, PWD, EMAIL, ȸ����ȣ�� ����
	public void radd(String a, String b, String c,int count) throws ClassNotFoundException, SQLException {
		String str_query = "insert into `ȸ��` values(\"" + a + "\"," + "\"" + b + "\"," + "\"" + c + "\"," + "\"" +"\"," + count +")";
		stmt.executeUpdate(str_query);

	}
	// ���ӻ�翡 �޾ƿ� ���Ӹ�, CPU, OS, HDD, RAM, Graphics �� ����
	public void sadd (String string0, String string1, String string2, String string3, String string4, String string5) throws ClassNotFoundException, SQLException {
		java.sql.PreparedStatement ps_1 = con.prepareStatement("insert  into `���ӻ��` values(?,?,?,?,?,?)");
		ps_1.setString(1, string0);
		ps_1.setString(2, string1);
		ps_1.setString(3, string2);
		ps_1.setString(4, string3);
		ps_1.setString(5, string4);
		ps_1.setString(6, string5);
		ps_1.executeUpdate();
	}
	
	// ���� ���̺� �޾ƿ� ���Ӹ�, �帣, ��޻�, Ű���� ����. �̹��� url�� ������ ���Ͽ� null ���� ����
	public void gadd (String a, String b, String c, String d, int count) throws ClassNotFoundException, SQLException {
		String str_query = "insert into `���� ���̺�` values(\"" + a + "\"," + "\"" + b + "\"," + "\"" + c + "\"," + "\"" + d + "\"," + count +")";
		System.out.println(str_query);
		stmt.executeUpdate(str_query);
	}
	
	// key ���̺� ������ ������ ��ȣ�� ���Ӹ��� ����. Ű���� �߰��� ���� ���� ����.
	public void kadd (int count, String b) throws ClassNotFoundException, SQLException {
		String str_query = "insert into `key` values(" + count  + ",\"" + b + "\")";
		System.out.println(str_query);
		stmt.executeUpdate(str_query);
	}
	
	// ���� ���̺�� ���� ����� �����Ͽ� ���Ӹ��� �˻��Ͽ� �� ���� �迭�� ����
	public String[] select_all(String data) throws ClassNotFoundException,SQLException {
		String select = "SELECT * FROM `���� ���̺�` NATURAL JOIN `���ӻ��`  WHERE `���� ���̺�`.`���Ӹ�` = \'" + data + "\'";
		stmt = con.createStatement();
		rset = stmt.executeQuery(select);
		
		String[] spec = new String[10];
		while(rset.next()) {
			spec[0] = rset.getString("���Ӹ�");
			spec[1] = rset.getString("�帣");
			spec[2] = rset.getString("��޻� �̸�");
			spec[3] = rset.getString("Ű��");
			spec[4] = rset.getString("CPU");
			spec[5] = rset.getString("OS");
			spec[6] = rset.getString("HDD");
			spec[7] = rset.getString("RAM");
			spec[8] = rset.getString("Graphics");
			spec[9] = rset.getString("�̹���");
		}
		return spec;
	}
	
	// ID�� ã�ƿ�. �α��ο� ���
	public String select_id(String data) throws ClassNotFoundException, SQLException {
		String user_id = new String();
		String select = "SELECT ID FROM `����`WHERE ID = \'" + data + "\'";
		stmt = con.createStatement();
		rset = stmt.executeQuery(select);
		
		while(rset.next()) {
			user_id = rset.getString("ID");
		}
		return user_id;
		
	}
	
	// ������ ���
	public void del(String name) throws ClassNotFoundException, SQLException {
		stmt = con.createStatement();
		stmt.executeUpdate("DELETE FROM `key` WHERE `Ű��` = (SELECT `Ű��` FROM `���� ���̺�` WHERE `���Ӹ�`="+"\""+name+"\")");
		stmt.executeUpdate("DELETE FROM `���� ���̺�` WHERE `���Ӹ�`="+"\""+name+"\"");
        stmt.executeUpdate("DELETE FROM `���ӻ��` WHERE `���Ӹ�`="+"\""+name+"\"");
	}
	
	// ������ ���
	public void update(String a, String b, String c, String d, String e, String f, String g, String name) throws ClassNotFoundException, SQLException {
		stmt = con.createStatement();
		stmt.executeUpdate("UPDATE `���� ���̺�` SET `��޻� �̸�` = " + "\'" + a +"\'" + ", `�帣` = " + "\'" + b +"\'" + " WHERE `���Ӹ�` = "  +  "\'" + name +"\'");
		stmt.executeUpdate("UPDATE `���ӻ��` SET `CPU` = " + "\'" + d +"\'" +", `OS` = " + "\'" + c +"\'" + ", `HDD` = " + "\'" + e +"\'" + ", `RAM` =" + "\'" + f +"\'" + ", `Graphics` = " + "\'" + g +"\'" + "WHERE `���Ӹ�` = " + "\'" + name +"\'");
	}
	
	// db�� ����.
	public void close() {
		try {
			con.close();
			stmt.close();
			//rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Close All");
	}
}
