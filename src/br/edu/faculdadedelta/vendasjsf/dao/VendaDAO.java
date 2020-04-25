package br.edu.faculdadedelta.vendasjsf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.vendasjsf.modelo.Produto;
import br.edu.faculdadedelta.vendasjsf.modelo.Venda;
import br.edu.faculdadedelta.vendasjsf.util.Conexao;

public class VendaDAO {

	public void incluir(Venda venda) 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "INSERT INTO vendas (quantidade, data_venda, id_produto) "
				+ " VALUES (?,?,?) ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, venda.getQuantidade());
		ps.setDate(2, new java.sql.Date(venda.getDataVenda().getTime()));
		ps.setLong(3, venda.getProduto().getId());
		
		ps.executeUpdate();
		
		Conexao.fecharConexao(ps, conn, null);
	}
	
	public void alterar(Venda venda) throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "UPDATE vendas "
				+ " SET quantidade = ?, "
				+ " data_venda = ?, "
				+ " id_produto =  ? "
				+ " WHERE id_venda = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, venda.getQuantidade());
		ps.setDate(2, new java.sql.Date(venda.getDataVenda().getTime()));
		ps.setLong(3, venda.getProduto().getId());
		ps.setLong(4, venda.getId());
		
		ps.executeUpdate();
		
		Conexao.fecharConexao(ps, conn, null);
		
	}
	
	public void excluir(Venda venda) 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "DELETE FROM vendas WHERE id_venda = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, venda.getId());
		
		ps.executeUpdate();
		
		Conexao.fecharConexao(ps, conn, null);
	}
	
	public List<Venda> listar() 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT "
				+ " v.id_venda AS idVenda,"
				+ " v.quantidade AS quantidadeVenda, "
				+ " v.data_venda AS dataVenda, "
				+ " p.id_produto AS idProduto, "
				+ " p.nome AS nomeProduto, "
				+ " p.descricao AS descricaoProduto, "
				+ " p.data_cadastro AS dataCadastroProduto, "
				+ " p.valor AS valorProduto "
				+ " FROM vendas v "
				+ " INNER JOIN produtos p ON v.id_produto = p.id_produto";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Venda> listaRetorno = new ArrayList<>();
		while (rs.next()) {
			Venda venda = new Venda();
			venda.setId(rs.getLong("idVenda"));
			venda.setQuantidade(rs.getInt("quantidadeVenda"));
			venda.setDataVenda(rs.getDate("dataVenda"));
			
			Produto produto = new Produto();
			produto.setId(rs.getLong("idProduto"));
			produto.setNome(rs.getString("nomeProduto").trim());
		    produto.setDescricao(rs.getString("descricaoProduto").trim());
		    produto.setValor(rs.getDouble("valorProduto"));
		    produto.setDataCadastro(rs.getDate("dataCadastroProduto"));
		    
		    venda.setProduto(produto);
		    
		    listaRetorno.add(venda);
		}
		
		Conexao.fecharConexao(ps, conn, rs);
		
		return listaRetorno;
	}
	
/*	public List<Venda> listar() 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT * FROM vendas ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Venda> listaRetorno = new ArrayList<>();
		while (rs.next()) {
			Venda venda = new Venda();
			venda.setId(rs.getLong("idVenda"));
			venda.setQuantidade(rs.getInt("quantidadeVenda"));
			venda.setDataVenda(rs.getDate("dataVenda"));
			
		    ProdutoDAO dao = new ProdutoDAO();
		    
		    venda.setProduto(dao.pesquisarPorId(rs.getLong("id_produto")));
			
		    listaRetorno.add(venda);
		}
		
		Conexao.fecharConexao(ps, conn, rs);
		
		return listaRetorno;
	} */
}
