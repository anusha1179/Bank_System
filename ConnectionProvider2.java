package JDBC2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionProvider2 {
	private static Properties p;
	static
	{
		p=new Properties();
	}
	
	public static Connection connect(String user,String password)
	{
		Connection con=null;
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			
			p.setProperty("user",user);
			p.setProperty("password", password);
			
			con=DriverManager.getConnection(url,p);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
}
