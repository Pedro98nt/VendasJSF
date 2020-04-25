package br.edu.faculdadedelta.vendasjsf.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.faculdadedelta.vendasjsf.dao.ProdutoDAO;
import br.edu.faculdadedelta.vendasjsf.modelo.Produto;

@FacesConverter(value = "produtoConverter")
public class ProdutoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		
		if (valor != null) {
			ProdutoDAO dao = new ProdutoDAO();
			try {
				return dao.pesquisarPorId(Long.valueOf(valor));
			} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor) {
		
		if (valor != null) {
			return String.valueOf(((Produto)valor).getId());
		}
		
		return null;
	}

}
