package br.edu.faculdadedelta.vendasjsf.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.faculdadedelta.vendasjsf.dao.ProdutoDAO;
import br.edu.faculdadedelta.vendasjsf.modelo.Produto;
import br.edu.faculdadedelta.vendasjsf.util.FacesUtil;

@ManagedBean
@SessionScoped
public class ProdutoController {

	private Produto produto = new Produto();
	private ProdutoDAO dao = new ProdutoDAO();
	
	private static final String PAGINA_CADASTRO_PRODUTO = "cadastroProduto.xhtml";
	private static final String PAGINA_LISTA_PRODUTO = "listaProduto.xhtml";
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void limparCampos() {
		produto = new Produto();
	}
	
	public String salvar() {
		try {
			if (produto.getId() == null) {
				dao.incluir(produto);
				FacesUtil.exibirMensagem("Inclusão realizada com sucesso!");
				limparCampos();
			} else {
				dao.alterar(produto);
				FacesUtil.exibirMensagem("Alteração realizada com sucesso!");
				limparCampos();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMensagem("Erro ao realizar a operação: " + e.getMessage());
		}
		return PAGINA_CADASTRO_PRODUTO;
	}
	
	public String excluir() {
		try {
			dao.excluir(produto);
			FacesUtil.exibirMensagem("Exclusão realizada com sucesso!");
			limparCampos();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMensagem("Erro ao realizar a operação: " + e.getMessage());			
		}
		return PAGINA_LISTA_PRODUTO;
	}
	
	public String editar() {
		return PAGINA_CADASTRO_PRODUTO;
	}
	
	public List<Produto> getLista() {
		List<Produto> listaRetorno = new ArrayList<>();
		try {
			listaRetorno = dao.listar();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMensagem("Erro ao realizar a operação: " + e.getMessage());			
		}
		return listaRetorno;
	}
}
