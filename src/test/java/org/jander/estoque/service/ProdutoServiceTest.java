package org.jander.estoque.service;

import org.jander.estoque.dto.ProdutoDTO;
import org.jander.estoque.enums.TipoProduto;
import org.jander.estoque.model.Produto;
import org.jander.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void criarProduto_deveRetornarProdutoDTO() {
        ProdutoDTO dto = new ProdutoDTO(1L, "Produto Teste", "Descrição", TipoProduto.MOVEL, 100.0, 10);
        Produto produto = new Produto(1L, "Produto Teste", "Descrição", TipoProduto.MOVEL, 100.0, 10);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoDTO resultado = produtoService.criarProduto(dto);

        assertNotNull(resultado);
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void obterProdutoPorId_deveRetornarProdutoDTO() {
        Produto produto = new Produto();
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoDTO resultado = produtoService.obterProdutoPorId(1L);

        assertNotNull(resultado);
        verify(produtoRepository).findById(1L);
    }

    @Test
    void atualizarProduto_deveRetornarProdutoDTO() {
        Produto produto = new Produto(1L, "Produto Teste", "Descrição", TipoProduto.MOVEL, 100.0, 10);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoDTO resultado = produtoService.atualizarProduto(1L, new ProdutoDTO(1L, "Produto Teste", "Descrição", TipoProduto.MOVEL, 100.0, 10));

        assertNotNull(resultado);
        verify(produtoRepository).save(any(Produto.class));
    }
}