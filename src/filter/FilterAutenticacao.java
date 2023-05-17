package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class FilterAutenticacao
 * 
 * INTERCEPTA TODAS AS REQUISI�OES QUE VIEREM DO PROJETO OU MAPEAMENTO
 */
@WebFilter("/FilterAutenticacao")
public class FilterAutenticacao implements Filter {

 
    public FilterAutenticacao() {
        
    }


	public void destroy() {
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}