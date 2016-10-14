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
	
	
	public SQL_select() {  // db 연결
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Class Loading Complete");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void connectDatabase() {  // db 연결
		try {
			con = DriverManager.getConnection(url, id, pass);
			stmt = con.createStatement();
			System.out.println("Make Connet Complete");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String select_gamecount() throws SQLException {  // 게임 사양의 숫자를 세어 동적으로 테이블을 만들기 위해 사용
		String count_spec = "select count(*) as count from `게임사양`";
		stmt = con.createStatement();
		rset = stmt.executeQuery(count_spec);
		
		while(rset.next()) {
			str2 = rset.getString("count");
		}
		System.out.println(str2);
		return str2;
	}
	
	public String[][] favorite(String name) throws SQLException {  // 회원번호로 숫자를 세어 동적으로 테이블을 만들기 위해 사용
		
		String count_fv = "SELECT COUNT(*) AS COUNT FROM `즐겨찾기` WHERE `회원번호` = (SELECT `회원번호` FROM `회원` WHERE ID=\'"+name+"\');";
		stmt = con.createStatement();
		rset = stmt.executeQuery(count_fv);
		
		while(rset.next()) {
			str2 = rset.getString("count");
		}
		
		String[][] GameTable = new String[Integer.parseInt(str2)][2];
		
		// 회원의 ID로 즐겨찾기에서 게임명을 검색
		String fv_id = "SELECT `게임명` FROM `즐겨찾기` WHERE `회원번호` = (SELECT `회원번호` FROM `회원` WHERE ID=\'"+name+"\');";
		int i=0;
		try {
			rset = stmt.executeQuery(fv_id);
			while(rset.next()) {
				GameTable[i][0] = i+1+"";
				GameTable[i][1] = rset.getString("게임명");
				i++;
			}
		} catch (SQLException e) {
		e.printStackTrace();			
		}
		rset.close();
		return GameTable;
	}
	
	// id가 일치하는 회원번호를 찾아서 즐겨찾기에 삽입
	public void select_num(String id, String game) throws SQLException {
		
		String count_num = "select `회원번호` from `회원` where `ID`= \'" + id + "\'";
		stmt = con.createStatement();
		rset = stmt.executeQuery(count_num);
		
		while(rset.next()) {
			str2 = rset.getString("회원번호");
		}
		
		String insert_fv = "insert into `즐겨찾기` values(" + str2 + ",\'" + game + "\')";
		stmt.executeUpdate(insert_fv);
	}
	
	// 게임사양의 숫자를 세어 테이블을 동적으로 만듬.
	public String[][] selectDatabase() throws SQLException {
		
		String count_spec = "select count(*) as count from `게임사양`";
		stmt = con.createStatement();
		rset = stmt.executeQuery(count_spec);
		
		while(rset.next()) {
			str2 = rset.getString("count");
		}
		
		String[][] GameTable = new String[Integer.parseInt(str2)][3];
		
		String select = "SELECT `게임명`,`장르`,`배급사 이름` FROM `게임 테이블`";
		int i =0;
		try {
			rset = stmt.executeQuery(select);
			while(rset.next()) {
				GameTable[i][0] = rset.getString("게임명");
				GameTable[i][1] = rset.getString("장르");
				GameTable[i][2] = rset.getString("배급사 이름");
				i++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		rset.close();
		return GameTable;
	}
	
	// alpa 값을 입력 받아 게임명이 입력받은 alpa인 것을 검색하여 테이블로 보여줌
	public String[][] select_game(String alpa) throws SQLException {
		
		String count_spec = "select count(*) as count from `게임사양` where `게임명` like \'" + alpa + "%\'";
		stmt = con.createStatement();
		rset = stmt.executeQuery(count_spec);
		
		while(rset.next()) {
			str2 = rset.getString("count");
		}
		
		String[][] GameTable = new String[Integer.parseInt(str2)][3];
		
		String select = "SELECT `게임명`,`장르`,`배급사 이름` FROM `게임 테이블` where `게임명` like \'" + alpa + "%\'";
		int i =0;
		try {
			rset = stmt.executeQuery(select);
			while(rset.next()) {
				GameTable[i][0] = rset.getString("게임명");
				GameTable[i][1] = rset.getString("장르");
				GameTable[i][2] = rset.getString("배급사 이름");
				i++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		rset.close();
		return GameTable;
	}
	
	// id를 입력받아 권한 테이블에서 입력받은 id의 수를 받아옴. 중복체크에 사용
	public String select(String sql_id) throws ClassNotFoundException, SQLException {
		
		String k = "select count(*) as count from `권한`  where(`ID`=\"" + sql_id + "\")";
		rset = stmt.executeQuery(k);
		
		while(rset.next()) {
			str1 = rset.getString("count");
		}
		
		rset.close();
		return str1;
	}
	
	// 입력을 받아와서 id로 pass 값을 얻어옴. 비밀번호 확인을 위해 사용
	public String select_pwd(String sql_id) throws ClassNotFoundException, SQLException {
		
		String k = "select pass from `회원`  where(`id`=\"" + sql_id + "\")";
		rset = stmt.executeQuery(k);
		
		while(rset.next()) {
			str1 = rset.getString("pass");
		}
		
		rset.close();
		return str1;
	}
	
	// 권한 테이블의 숫자를 셈. 회원번호를 증가시킬 목적으로 사용
	public String count() throws ClassNotFoundException, SQLException {
		
		String select = "select count(*) as count from `권한`";
		stmt = con.createStatement();
		rset = stmt.executeQuery(select);
		
		while(rset.next()) {
			str1 = rset.getString("count");
		}
		
		rset.close();
		return str1;
	}
	
	// 권한에 입력받아온 id를 삽입하고, 등급을 0으로 줌
	public void add(String string0) throws ClassNotFoundException, SQLException {
		stmt = con.createStatement();
		String s = "insert into `권한` values(\"" + string0 + "\"," + 0 +")";
		System.out.println(s);
		stmt.executeUpdate(s);
	}
	
	// 회원 테이블에 입력 받아온 ID, PWD, EMAIL, 회원번호를 삽입
	public void radd(String a, String b, String c,int count) throws ClassNotFoundException, SQLException {
		String str_query = "insert into `회원` values(\"" + a + "\"," + "\"" + b + "\"," + "\"" + c + "\"," + "\"" +"\"," + count +")";
		stmt.executeUpdate(str_query);

	}
	// 게임사양에 받아온 게임명, CPU, OS, HDD, RAM, Graphics 를 삽입
	public void sadd (String string0, String string1, String string2, String string3, String string4, String string5) throws ClassNotFoundException, SQLException {
		java.sql.PreparedStatement ps_1 = con.prepareStatement("insert  into `게임사양` values(?,?,?,?,?,?)");
		ps_1.setString(1, string0);
		ps_1.setString(2, string1);
		ps_1.setString(3, string2);
		ps_1.setString(4, string3);
		ps_1.setString(5, string4);
		ps_1.setString(6, string5);
		ps_1.executeUpdate();
	}
	
	// 게임 테이블에 받아온 게임명, 장르, 배급사, 키값을 삽입. 이미지 url은 구현을 못하여 null 값을 삽입
	public void gadd (String a, String b, String c, String d, int count) throws ClassNotFoundException, SQLException {
		String str_query = "insert into `게임 테이블` values(\"" + a + "\"," + "\"" + b + "\"," + "\"" + c + "\"," + "\"" + d + "\"," + count +")";
		System.out.println(str_query);
		stmt.executeUpdate(str_query);
	}
	
	// key 테이블에 생성한 게임의 번호와 게임명을 삽입. 키워드 추가는 아직 구현 못함.
	public void kadd (int count, String b) throws ClassNotFoundException, SQLException {
		String str_query = "insert into `key` values(" + count  + ",\"" + b + "\")";
		System.out.println(str_query);
		stmt.executeUpdate(str_query);
	}
	
	// 게임 테이블과 게임 사양을 조인하여 게임명을 검색하여 그 값을 배열에 삽입
	public String[] select_all(String data) throws ClassNotFoundException,SQLException {
		String select = "SELECT * FROM `게임 테이블` NATURAL JOIN `게임사양`  WHERE `게임 테이블`.`게임명` = \'" + data + "\'";
		stmt = con.createStatement();
		rset = stmt.executeQuery(select);
		
		String[] spec = new String[10];
		while(rset.next()) {
			spec[0] = rset.getString("게임명");
			spec[1] = rset.getString("장르");
			spec[2] = rset.getString("배급사 이름");
			spec[3] = rset.getString("키값");
			spec[4] = rset.getString("CPU");
			spec[5] = rset.getString("OS");
			spec[6] = rset.getString("HDD");
			spec[7] = rset.getString("RAM");
			spec[8] = rset.getString("Graphics");
			spec[9] = rset.getString("이미지");
		}
		return spec;
	}
	
	// ID를 찾아옴. 로그인에 사용
	public String select_id(String data) throws ClassNotFoundException, SQLException {
		String user_id = new String();
		String select = "SELECT ID FROM `권한`WHERE ID = \'" + data + "\'";
		stmt = con.createStatement();
		rset = stmt.executeQuery(select);
		
		while(rset.next()) {
			user_id = rset.getString("ID");
		}
		return user_id;
		
	}
	
	// 삭제에 사용
	public void del(String name) throws ClassNotFoundException, SQLException {
		stmt = con.createStatement();
		stmt.executeUpdate("DELETE FROM `key` WHERE `키값` = (SELECT `키값` FROM `게임 테이블` WHERE `게임명`="+"\""+name+"\")");
		stmt.executeUpdate("DELETE FROM `게임 테이블` WHERE `게임명`="+"\""+name+"\"");
        stmt.executeUpdate("DELETE FROM `게임사양` WHERE `게임명`="+"\""+name+"\"");
	}
	
	// 수정에 사용
	public void update(String a, String b, String c, String d, String e, String f, String g, String name) throws ClassNotFoundException, SQLException {
		stmt = con.createStatement();
		stmt.executeUpdate("UPDATE `게임 테이블` SET `배급사 이름` = " + "\'" + a +"\'" + ", `장르` = " + "\'" + b +"\'" + " WHERE `게임명` = "  +  "\'" + name +"\'");
		stmt.executeUpdate("UPDATE `게임사양` SET `CPU` = " + "\'" + d +"\'" +", `OS` = " + "\'" + c +"\'" + ", `HDD` = " + "\'" + e +"\'" + ", `RAM` =" + "\'" + f +"\'" + ", `Graphics` = " + "\'" + g +"\'" + "WHERE `게임명` = " + "\'" + name +"\'");
	}
	
	// db를 닫음.
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
