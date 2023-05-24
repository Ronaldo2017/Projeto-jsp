package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	private static String url = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String password = "admin";
	private static Connection connection = null;

	// retorna a conexao existente
	public static Connection getConnection() {
		return connection;
	}

	// chama a classe direta
	static {
		conectar();
	}

	public SingleConnectionBanco() {
		// quando tiver uma instancia vai conectar
		conectar();
	}

	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");// carrega o driver de conexao do banco
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);// nao efetua alteracoes no banco sem nosso comando
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
