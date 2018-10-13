package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaDeConexaoSapweb {
	private static FabricaDeConexaoSapweb instancia = new FabricaDeConexaoSapweb();
	private static String usuario;
	private static String senha;

	public static FabricaDeConexaoSapweb getInstancia() {
		return instancia; // ... e retorno aqui--->>>>>>>.
	}

	public Connection getConnection() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		try {
			// return  DriverManager.getConnection("jdbc:oracle:thin:@//egestao.trt1.jus.br:1521/egestao.trt1.jus.br",
			// "app","app");
			// return DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe", "app","app");

			/*return DriverManager.getConnection(
					"jdbc:oracle:thin:@//bdoracle.trt1.jus.br:1521/sapdianp.trt1.jus.br",		"roterdam", "jaws");
			*/
			
			//produção
			return DriverManager.getConnection(
					"jdbc:oracle:thin:@//racstg3-vip:1521/saptrt.trt1.jus.br",		"roterdam", "jaws");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getUsuario() {
		return usuario;
	}

	public static void setUsuario(String usuario) {
		FabricaDeConexaoSapweb.usuario = usuario;
	}

	public static String getSenha() {
		return senha;
	}

	public static void setSenha(String senha) {
		FabricaDeConexaoSapweb.senha = senha;
	}

}
