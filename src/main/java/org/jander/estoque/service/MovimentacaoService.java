package org.jander.estoque.service;

import org.jander.estoque.dto.MovimentacaoDTO;
import org.jander.estoque.enums.TipoMovimentacao;
import org.jander.estoque.mapper.MovimentacaoMapper;
import org.jander.estoque.model.Movimentacao;
import org.jander.estoque.model.Produto;
import org.jander.estoque.repository.MovimentacaoRepository;
import org.jander.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoService {
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public MovimentacaoService(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public MovimentacaoDTO movimentarProduto(MovimentacaoDTO movimentacaoDTO) {
        Produto produto = produtoRepository.findById(movimentacaoDTO.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + movimentacaoDTO.produtoId()));
        Movimentacao movimentacao = MovimentacaoMapper.toEntity(movimentacaoDTO, produto);
        if (movimentacao.getTipo() == TipoMovimentacao.ENTRADA) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + movimentacao.getQuantidade());
            movimentacaoRepository.save(movimentacao);
            produtoRepository.save(produto);
        } else if (movimentacao.getTipo() == TipoMovimentacao.SAIDA) {
            if (produto.getQuantidadeEstoque() >= movimentacao.getQuantidade()) {
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - movimentacao.getQuantidade());
                movimentacaoRepository.save(movimentacao);
                produtoRepository.save(produto);
            } else {
                throw new IllegalArgumentException("Quantidade insuficiente no estoque");
            }
        }
        return MovimentacaoMapper.toDTO(movimentacao);
    }
}