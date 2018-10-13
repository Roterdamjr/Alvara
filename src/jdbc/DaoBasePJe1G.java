package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DaoBasePJe1G {
	protected ResultSet rs=null;
	protected PreparedStatement stmt;	
	protected Connection connection;
	


	public DaoBasePJe1G(){
		FabricaDeConexaoPJe1G fabrica = FabricaDeConexaoPJe1G.getInstancia();
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
