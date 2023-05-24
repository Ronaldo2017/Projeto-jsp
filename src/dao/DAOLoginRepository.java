package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	private Connection connection;// objeto de conexao

	public DAOLoginRepository() {
		//quando instanciar esta classe ja tem a conexxao
		connection = SingleConnectionBanco.getConnection();
	}

	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		String sql = "SELECT * FROM model_login WHERE login = ? and senha = ?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());

		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return true;
		}
		return false;

	}

}
