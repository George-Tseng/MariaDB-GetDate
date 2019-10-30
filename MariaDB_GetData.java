import java.sql.*;
/*
 * 如果用Eclipse執行，請在放置本檔案的Java Project的Properties->Resources->Text file encoding改成「Other」，然後選UTF-8
 * (請根據你要連的DB的設定進行變更，Windows下Eclipse預設會是MS950)。不從Window->Preference->
 * workspace裡的Text file encoding改是避免連帶影響到其他本來用預設編碼跑的Java程式。
 */
/*
 * 另外記得下載好對應DB的JDBC Driver(為jar檔)，一樣在放置本檔案的Java Project的Properties->Java Build Path->Libraries
 * ->選擇「Add External Jars」將對應的jar檔加入到Eclipse中。
 */
public class MariaDB_GetData {
	
	public static void main(String[] args) {
		Connection dbConnection = null;
	    Statement dbStatement = null;
	    ResultSet dbResultSet = null;
	    String sqlCommand="SELECT * FROM employee";//要使用的SQL指令，可再利用switch來實做多種功能的切換
	    final String dbDriver="org.mariadb.jdbc.Driver";//JDBC Driver，依DB種類而有所不同
	    final String dbUrl="jdbc:mariadb://localhost:3306/mydb";//localhost前與DB種類有關，localhost後與設計者的設定有關
	    final String dbUser="javaAppUser";//如為localhost，須先在DB建立對應的使用者名稱
	    final String dbPassword="4321";//對應的密碼，可以修改為接受輸入
	    
	  //載入JDBC Driver
	    try{
	    	Class.forName(dbDriver);	
	    }
	    catch(Exception e) {
	    	System.out.println("無法載入驅動程式");
	        return;
	    }
	    
	  //建立資料連結和Statement物件
	    try {
	    	dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	    	if(dbConnection != null)
	    		System.out.println("建立Connection物件成功!");
	    	
	    	dbStatement = dbConnection.createStatement();
	    	if(dbStatement != null)
	    		System.out.println("建立Statement物件成功!");
	    }
	    catch(SQLException e) {
	    	System.out.println("與資料來源連結錯誤! " + "\n連結標的資料表:"+dbUrl+"\n使用者："+dbUser+"\n密碼"+dbPassword);
	    	System.out.println(e.getMessage());
	    	if(dbConnection != null) {
	    		try {
	    			dbConnection.close();//結束Connection
	    		}
	    		catch( SQLException e2 ) {
	    			
	    		}
	    	}
	    	return;
	    }
	    
	    //讀取SQL指令的執行結果，如果目標資料表不存在或這裡的讀取格式與DB端不相符，都會執行catch中的內容
	    try {
	    	dbResultSet = dbStatement.executeQuery(sqlCommand);
	    	while(dbResultSet.next()){
	    		System.out.print(dbResultSet.getString("num"));
	    		System.out.print("\t");
	    		System.out.print(dbResultSet.getString("name"));
	    		System.out.print("\t");
	    		System.out.print(dbResultSet.getString("sex"));
	    		System.out.print("\t");
	    		System.out.print(dbResultSet.getDate("birth"));
	    		System.out.print("\t");
	    		System.out.print(dbResultSet.getByte("age"));
	    		System.out.print("\t");
	    		System.out.print(dbResultSet.getString("address"));
	    		System.out.println("");
	    	}
	    }
	    catch(SQLException e) {
	    	System.out.println("出現錯誤！");
	    }
	    finally {
	    	try {
	    		dbStatement.close();//結束Statement
	    		dbConnection.close();//結束Connection
	    	}
	    	catch(SQLException e ) {
	    		
	    	}
	    }
	}
}
