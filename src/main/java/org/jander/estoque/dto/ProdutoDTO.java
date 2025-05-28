package org.jander.estoque.dto;

import org.jander.estoque.enums.TipoProduto;

public record ProdutoDTO(
        Long id,
        String codigo,
        String descricao,
        TipoProduto tipo,
        double valor,
        Integer quantidadeEstoque
) {}