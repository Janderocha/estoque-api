package org.jander.estoque.repository;

import org.jander.estoque.enums.TipoProduto;
import org.jander.estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

     List<Produto> findByTipo(TipoProduto tipo);
}
