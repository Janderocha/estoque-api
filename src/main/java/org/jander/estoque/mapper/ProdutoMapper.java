package org.jander.estoque.mapper;

import org.jander.estoque.dto.ProdutoDTO;
import org.jander.estoque.model.Produto;

public class ProdutoMapper {
    public static ProdutoDTO toDTO(Produto produto) {
        if (produto == null) return null;
        return new ProdutoDTO(
                produto.getId(),
                produto.getCodigo(),
                produto.getDescricao(),
                produto.getTipo(),
                produto.getValor(),
                produto.getQuantidadeEstoque()
        );
    }

    public static Produto toEntity(ProdutoDTO dto) {
        if (dto == null) return null;
        return new Produto(
                dto.id(),
                dto.codigo(),
                dto.descricao(),
                dto.tipo(),
                dto.valor(),
                dto.quantidadeEstoque()
        );
    }
}