package JDBC2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import JDBC1.ConnectionProvider;

public class AccountsDAOImpl implements AccountsDAO{

	private static Connection con;


	public AccountsDAOImpl(String user,String password) {
		con=ConnectionProvider.connect(user, password);
	}
	public static Connection getConnection()
	{
		return con;
	}

	@Override
	public String createAcc(String id,String name,double balance) {

		String insertQuery="INSERT INTO ACCOUNTS(ID,NAME,BALANCE) VALUES(?,?,?)";

		try {
			PreparedStatement pstmt=con.prepareStatement(insertQuery);

			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setDouble(3, balance);

			int result=pstmt.executeUpdate();
			if(result>0)
			{
				return "Account created successfully.";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Error occured! Please try again.";
	}

	@Override
	public String credit(String id,double amt) {

		double currentBal=this.getBalance(id);
		currentBal+=amt;
		try
		{
			String query="UPDATE ACCOUNTS SET BALANCE=? WHERE ID=?";
			PreparedStatement pt=con.prepareStatement(query);
			pt.setDouble(1,currentBal);
			pt.setString(2, id);

			int result=pt.executeUpdate();
			if(result>0)
			{
				return "Rs."+amt+" is credited to the a/c "+id;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "Error occurred! Please try again";
	}

	@Override
	public String debit(String id,double amt) {

		String query="UPDATE ACCOUNTS SET BALANCE=? WHERE ID=?";
		double currentBal=this.getBalance(id);
		if(currentBal>amt)
		{
			currentBal-=amt;
			try {
				PreparedStatement pt=con.prepareStatement(query);

				pt.setDouble(1,currentBal);
				pt.setString(2, id);
				int result=pt.executeUpdate();
				if(result>0)
				{
					return "Rs."+amt+" is debited from the a/c "+id;
				}
				return "Error occurred! Please try again";
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
		return "Insufficient balance";

	}

	@Override
	public double getBalance(String id) {
		String query="SELECT BALANCE FROM ACCOUNTS WHERE ID=?";
		double balance=0;
		try {
			PreparedStatement stmt=con.prepareStatement(query);
			stmt.setString(1,id);

			ResultSet rs=stmt.executeQuery();
			if(rs.next())
			{
				balance=rs.getDouble(1);
				return balance;
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String deleteAcc(String id) {
		String query="DELETE FROM ACCOUNTS WHERE ID=?";
		try {
			PreparedStatement pt=con.prepareStatement(query);
			pt.setString(1,id);
			int result=pt.executeUpdate();
			if(result>0)
			{
				return "A/c "+id+" is deleted successfully.";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "A/c doesn't exists";

	}

	@Override
	public ResultSet getAccounts() {
		String query="SELECT * FROM ACCOUNTS";
		ResultSet rs=null;
		try
		{
			PreparedStatement pt=con.prepareStatement(query);
			 rs=pt.executeQuery();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}


}
