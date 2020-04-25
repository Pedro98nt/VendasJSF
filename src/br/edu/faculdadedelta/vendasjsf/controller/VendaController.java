package br.edu.faculdadedelta.vendasjsf.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.faculdadedelta.vendasjsf.dao.VendaDAO;
import br.edu.faculdadedelta.vendasjsf.modelo.Produto;
import br.edu.faculdadedelta.vendasjsf.modelo.Venda;
import br.edu.faculdadedelta.vendasjsf.util.FacesUtil;

@ManagedBean
@SessionScoped
public class VendaController {

	private Venda venda = new Venda();
	private VendaDAO dao = new VendaDAO();
	private Produto produtoSelecionado = new Produto();

	private static final String PAGINA_CADASTRO_VENDA = "cadastroVenda.xhtml";
	private static final String PAGINA_LISTA_VENDA = "listaVenda.xhtml";
	
	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public void limparCampos() {
		venda = new Venda();
		produtoSelecionado = new Produto();
	}

	public String salvar() {
		try {
			if (venda.getId() == null) {
				venda.setProduto(produtoSelecionado);
				dao.incluir(venda);
				FacesUtil.exibirMensagem("Inclusão realizada com sucesso!");
				limparCampos();
			} else {
				venda.setProduto(produtoSelecionado);
				dao.alterar(venda);
				FacesUtil.exibirMensagem("Alteração realizada com sucesso!");
				limparCampos();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMensagem("Erro ao realizar a operação. " + e.getMessage());
		}

		return PAGINA_CADASTRO_VENDA;
	}
	
	public String excluir() {
		try {
			dao.excluir(venda);
			FacesUtil.exibirMensagem("Exclusão realizada com sucesso!");
			limparCampos();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMensagem("Erro ao realizar a operação. " + e.getMessage());			
		}
		return PAGINA_LISTA_VENDA;
	}
	
	public String editar() {
		produtoSelecionado = venda.getProduto();
		return PAGINA_CADASTRO_VENDA;
	}
	
	public List<Venda> getLista() {
		List<Venda> listaRetorno = new ArrayList<>();
		try {
			listaRetorno = dao.listar();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			FacesUtil.exibirMensagem("Erro ao realizar a operação. " + e.getMessage());			
		}
		return listaRetorno;
	}
}
