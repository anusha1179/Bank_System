package JDBC2;

import java.sql.ResultSet;

public interface AccountsDAO {
	
	String createAcc(String id,String name,double balance);
	String credit(String id,double amt);
	String debit(String id,double amt);
	double getBalance(String id);
	String deleteAcc(String id);
	ResultSet getAccounts();
}
