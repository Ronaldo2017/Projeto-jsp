package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnectionBanco;

/**
 * Servlet Filter implementation class FilterAutenticacao
 * 
 * Filter = INTERCEPTA TODAS AS REQUISIÇOES QUE VIEREM DO PROJETO OU MAPEAMENTO
 * 
 * Tudo que vier da pasta principal vai ser interceptado
 */
@WebFilter(urlPatterns = { "/principal/*" })
public class FilterAutenticacao implements Filter {

	//declara o objeto connection = chama a conexao com o bd
	private static Connection connection;

	public FilterAutenticacao() {

	}

	// encerra os processo quando o servidor é parado
	// mataria os processos de conecao com o banco
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// intercepta as requisicoes e as respostas no sistema
	// tutdo que fize no sistema vai passar por aqui
	// ex: validacao de autenticacao
	// commit, rolback de transacoes do banco
	// validar e fazer redirecionamento de paginas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			// pegar a sessao do usuario
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String usuarioLogado = (String) session.getAttribute("usuario");
			String urlParaAutenticar = req.getServletPath();// url que esta sendo acessada

			// validar se esta logado, senao vai para tela de login
			// nao esta logado
			if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return;// para a execucao e manda para o login
			} else {
				// deixa o processo do software continuar
				chain.doFilter(request, response);
			}

			//comitar as alteracoes no bd
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	// inicia os procecessos ou recursos quando o servidor sobe o projeto
	// iniciar a conexao com o banco
	public void init(FilterConfig fConfig) throws ServletException {
		//chama a classe de conexao
		connection = SingleConnectionBanco.getConnection();
	}

}
