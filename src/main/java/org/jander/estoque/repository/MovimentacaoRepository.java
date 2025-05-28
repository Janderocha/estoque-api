package org.jander.estoque.repository;

import org.jander.estoque.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByProdutoId(Long produtoId);


    @Query("SELECT m from Movimentacao  m WHERE m.produto.codigo = :codigoProduto AND m.tipo = 'SAIDA'")
    List<Movimentacao> findByCodigoProduto(String codigoProduto);

    @Query("SELECT SUM(m.quantidade) FROM Movimentacao m WHERE m.produto.id = :produtoId AND m.tipo = 'SAIDA'")
    Integer findQuantidadeVendidaByProdutoId(@Param("produtoId") Long produtoId);

    @Query("SELECT SUM(m.valor) FROM Movimentacao m WHERE m.produto.id = :produtoId AND m.tipo = 'SAIDA'")
    double findValorVendidaByProdutoId(@Param("produtoId") Long produtoId);
}
