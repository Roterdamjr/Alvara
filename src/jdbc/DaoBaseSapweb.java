package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DaoBaseSapweb {
	protected ResultSet rs=null;
	protected PreparedStatement stmt;	
	protected Connection connection;
	
	public DaoBaseSapweb(){
		FabricaDeConexaoSapweb fabrica = FabricaDeConexaoSapweb.getInstancia();
		connection=fabrica.getConnection();

		
	}
	
	protected void fechaRs(){
		try {
			rs.close();
			//stmt.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	protected ResultSet executaBusca (String query) throws Exception{ 

		System.out.println(query);
		PreparedStatement stmt;
				
		try {
			stmt = connection.prepareStatement(query);
			rs=stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(query); 
		return rs;
	}
	

	
}
