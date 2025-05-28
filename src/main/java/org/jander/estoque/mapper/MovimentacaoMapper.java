package org.jander.estoque.mapper;

import org.jander.estoque.dto.MovimentacaoDTO;
import org.jander.estoque.model.Movimentacao;
import org.jander.estoque.model.Produto;

public class MovimentacaoMapper {
    public static MovimentacaoDTO toDTO(Movimentacao mov) {
        if (mov == null) return null;
        return new MovimentacaoDTO(
            mov.getId(),
            mov.getProduto() != null ? mov.getProduto().getId() : null,
            mov.getTipo() != null ? mov.getTipo().name() : null,
            mov.getQuantidade(),
            mov.getValor(),
            mov.getData()
        );
    }

    public static Movimentacao toEntity(MovimentacaoDTO dto, Produto produto) {
        if (dto == null) return null;
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setId(dto.id());
        movimentacao.setProduto(produto);
        movimentacao.setTipo(dto.tipo() != null ? org.jander.estoque.enums.TipoMovimentacao.valueOf(dto.tipo()) : null);
        movimentacao.setQuantidade(dto.quantidade());
        movimentacao.setValor(dto.valor());
        movimentacao.setData(dto.data());
        return movimentacao;
    }
}