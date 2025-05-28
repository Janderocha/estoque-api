package org.jander.estoque.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRelatorioDTO {
    private Long id;
    private String descricao;
    private String codigo;
    private String tipo;
    private Integer quantidadeEstoque;
    private Integer quantidadeVendida;
    private BigDecimal valorTotalEstoque;
    private BigDecimal valorTotalVendido;
}