package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DaoBasePJe2G {
	protected ResultSet rs=null;
	protected PreparedStatement stmt;	
	protected Connection connection;
	
	public DaoBasePJe2G(){
		try{
			FabricaDeConexaoPJe2G fabrica = FabricaDeConexaoPJe2G.getInstancia();
			connection=fabrica.getConnection();
		} catch (Exception e) {
			System.out.println("errodaobase");
			//e.printStackTrace();
			throw new RuntimeException(e);
		}			
		
	}
	
	protected void fechaRs(){
		try {
			rs.close();
			//stmt.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	protected ResultSet executaBusca(String query){

		//System.out.println(query);
		PreparedStatement stmt;
				
		try {
			stmt = connection.prepareStatement(query);
			rs=stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	

	
}
