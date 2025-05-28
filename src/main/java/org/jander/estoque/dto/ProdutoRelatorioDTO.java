package org.jander.estoque.dto;

import java.math.BigDecimal;

public record ProdutoRelatorioDTO(
        Long id,
        String descricao,
        String codigo,
        String tipo,
        Integer quantidadeEstoque,
        Integer quantidadeVendida,
        BigDecimal valorTotalEstoque,
        BigDecimal valorTotalVendido
) {}