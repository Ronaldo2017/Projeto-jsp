package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception {

		if (objeto.isNovo()) {

			String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES ( ?, ?, ?, ?);";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());

			statement.execute();
			connection.commit();
		} else {
			String sql = "UPDATE public.model_login SET login=?, senha=?, nome=?, email=? WHERE id = " + ""
					+ objeto.getId() + ";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, objeto.getLogin());
			statement.setString(2, objeto.getSenha());
			statement.setString(3, objeto.getNome());
			statement.setString(4, objeto.getEmail());
			statement.executeUpdate();
			connection.commit();
		}
		return this.consultaUsuario(objeto.getLogin());
	}

	public ModelLogin consultaUsuario(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper('" + login + "')";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {// se tem resultatdo no bd

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));

		}
		return modelLogin;
	}

	public boolean validaLogin(String login) throws Exception {
		String sql = "SELECT COUNT(1) > 0 as existe FROM model_login WHERE upper(login) = upper('" + login + "')";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		resultSet.next();// para entrar nos resultados
		return resultSet.getBoolean("existe");

	}
	
	
	public void deletarUser(String idUser) throws Exception{
		String sql = "DELETE FROM model_login WHERE id = ?;";
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, Long.parseLong(idUser));
		prepareSql.executeUpdate();
		
		connection.commit();
	}

}
