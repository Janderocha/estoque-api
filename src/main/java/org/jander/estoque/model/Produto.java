package org.jander.estoque.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jander.estoque.enums.TipoProduto;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codigo;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TipoProduto tipo;
    private double valor;
    private Integer quantidadeEstoque;


    public Produto(long l, String produtoTeste, int i) {
    }



}
