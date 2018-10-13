package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class FabricaDeConexaoPJe2G {
	private static FabricaDeConexaoPJe2G instancia = new FabricaDeConexaoPJe2G();
	private static String usuario;
	private static String senha;
	
	public static FabricaDeConexaoPJe2G getInstancia() {  
		return instancia; // ... e retorno aqui--->>>>>>>.
	}  
	
	public Connection getConnection(){

		try{
			Class.forName("org.postgresql.Driver");
		}catch(Exception e){
			throw new RuntimeException(e);
		} 
		
		try{
			return DriverManager.getConnection("jdbc:postgresql://10.1.75.59:5434/eg_2grau",	"postgres","pje123456");
			//return DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe",	"app","app");
					

		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	public static String getUsuario() {
		return usuario;
	}

	public static void setUsuario(String usuario) {
		FabricaDeConexaoPJe2G.usuario = usuario;
	}

	public static String getSenha() {
		return senha;
	}

	public static void setSenha(String senha) {
		FabricaDeConexaoPJe2G.senha = senha;
	}
	
	
}
