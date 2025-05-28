package org.jander.estoque.service;

import org.jander.estoque.dto.MovimentacaoDTO;
import org.jander.estoque.enums.TipoMovimentacao;
import org.jander.estoque.enums.TipoProduto;
import org.jander.estoque.model.Movimentacao;
import org.jander.estoque.model.Produto;
import org.jander.estoque.repository.MovimentacaoRepository;
import org.jander.estoque.repository.ProdutoRepository;
import org.jander.estoque.mapper.MovimentacaoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimentacaoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @InjectMocks
    private MovimentacaoService movimentacaoService;

    @Mock
    private MovimentacaoMapper movimentacaoMapper;

    @Test
    void movimentarProduto_entrada_deveAtualizarEstoque() {
        Produto produto = new Produto(1L, "Produto Teste", "Desc", TipoProduto.MOVEL, 100.0, 10);
        MovimentacaoDTO dto = new MovimentacaoDTO(1L, 1L, TipoMovimentacao.ENTRADA.name(), 5, 10.0, LocalDateTime.now());
        Movimentacao movimentacao = new Movimentacao(1L, TipoMovimentacao.ENTRADA, produto,0,  LocalDateTime.now(), 5);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(movimentacaoRepository.save(any(Movimentacao.class))).thenReturn(movimentacao);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        MovimentacaoDTO resultado = movimentacaoService.movimentarProduto(dto);

        assertEquals(15, produto.getQuantidadeEstoque());
        assertNotNull(resultado);
        verify(movimentacaoRepository).save(any(Movimentacao.class));
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void movimentarProduto_saida_deveAtualizarEstoque() {
        Produto produto = new Produto(1L, "Produto Teste", "Desc", null, 100.0, 10);
        MovimentacaoDTO dto = new MovimentacaoDTO(1L, 1L, "SAIDA", 5, 150.0, LocalDateTime.now());
        Movimentacao movimentacao = new Movimentacao(1L, TipoMovimentacao.SAIDA,produto, 150, LocalDateTime.now(), 3);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(movimentacaoRepository.save(any(Movimentacao.class))).thenReturn(movimentacao);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        MovimentacaoDTO resultado = movimentacaoService.movimentarProduto(dto);

        assertEquals(5, produto.getQuantidadeEstoque());
        assertNotNull(resultado);
        verify(movimentacaoRepository).save(any(Movimentacao.class));
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void movimentarProduto_saidaEstoqueInsuficiente_deveLancarExcecao() {
        Produto produto = new Produto(1L, "Produto Teste", "Desc", TipoProduto.MOVEL, 100.0, 2);
        MovimentacaoDTO dto = new MovimentacaoDTO(1L, 1L, TipoMovimentacao.SAIDA.name(), 5, 1.0, LocalDateTime.now());
        Movimentacao movimentacao = new Movimentacao(1L, TipoMovimentacao.SAIDA,produto, 5, LocalDateTime.now(),5);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        assertThrows(IllegalArgumentException.class, () -> movimentacaoService.movimentarProduto(dto));
        verify(movimentacaoRepository, never()).save(any());
        verify(produtoRepository, never()).save(any());
    }
}