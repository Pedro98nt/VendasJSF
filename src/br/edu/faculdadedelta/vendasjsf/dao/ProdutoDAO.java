package br.edu.faculdadedelta.vendasjsf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.vendasjsf.modelo.Produto;
import br.edu.faculdadedelta.vendasjsf.util.Conexao;

public class ProdutoDAO {

	public void incluir(Produto produto) 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "INSERT INTO produtos (nome, descricao, "
				+ " valor, data_cadastro) VALUES (?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, produto.getNome().trim());
		ps.setString(2, produto.getDescricao().trim());
		ps.setDouble(3, produto.getValor());
		ps.setDate(4, new java.sql.Date(produto.getDataCadastro().getTime()));
		
		ps.executeUpdate();
		
		Conexao.fecharConexao(ps, conn, null);
	}
	
	public void alterar(Produto produto) 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "UPDATE produtos "
				+ " SET nome = ?, "
				+ " descricao = ?, "
				+ " valor = ?, "
				+ " data_cadastro = ? "
				+ " WHERE id_produto = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, produto.getNome().trim());
		ps.setString(2, produto.getDescricao().trim());
		ps.setDouble(3, produto.getValor());
		ps.setDate(4, new java.sql.Date(produto.getDataCadastro().getTime()));
		ps.setLong(5, produto.getId());
		
		ps.executeUpdate();
		
		Conexao.fecharConexao(ps, conn, null);
	}
	
	public void excluir(Produto produto) 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "DELETE FROM produtos WHERE id_produto = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setLong(1, produto.getId());
		
		ps.executeUpdate();
		
		Conexao.fecharConexao(ps, conn, null);
		
	}
	
	public Produto pesquisarPorId(Long id) 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT id_produto, nome, descricao, "
				+ " valor, data_cadastro "
				+ " FROM produtos "
				+ " WHERE id_produto = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, id);
		
		ResultSet rs = ps.executeQuery();
		Produto retorno = new Produto();
		if (rs.next()) {
			retorno = popularProduto(rs);
		}
		Conexao.fecharConexao(ps, conn, rs);
		
		return retorno;
	}
	
	
	public List<Produto> listar() 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT id_produto, nome, descricao, valor, data_cadastro "
				+ " FROM produtos";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<Produto> listaRetorno = new ArrayList<>();
		
		while(rs.next()) {
			listaRetorno.add(popularProduto(rs));
		}
		
		Conexao.fecharConexao(ps, conn, rs);
		
		return listaRetorno;
	}
	
	private Produto popularProduto(ResultSet rs) throws SQLException {
		
		Produto produto = new Produto();
		produto.setId(rs.getLong("id_produto"));
		produto.setNome(rs.getString("nome").trim());
		produto.setDescricao(rs.getString("descricao").trim());
		produto.setValor(rs.getDouble("valor"));
		produto.setDataCadastro(rs.getDate("data_cadastro"));
		
		return produto;
	}
}