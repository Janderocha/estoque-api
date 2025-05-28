package org.jander.estoque.service;

import org.jander.estoque.dto.LucroDTO;
import org.jander.estoque.dto.ProdutoRelatorioDTO;
import org.jander.estoque.enums.TipoMovimentacao;
import org.jander.estoque.enums.TipoProduto;
import org.jander.estoque.model.Movimentacao;
import org.jander.estoque.model.Produto;
import org.jander.estoque.repository.MovimentacaoRepository;
import org.jander.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstoqueServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarProduto_deveSalvarEretornarProduto() {
        Produto produto = new Produto(1L, "Produto Teste", 10);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto salvo = estoqueService.criarProduto(produto);

        assertNotNull(salvo);
        assertEquals("Produto Teste", salvo.getDescricao());
        verify(produtoRepository).save(produto);
    }

    @Test
    void removerProdutoPorId_deveChamarDeleteById() {
        estoqueService.removerProdutoPorId(1L);
        verify(produtoRepository).deleteById(1L);
    }

    @Test
    void obterProdutoPorId_existente_deveRetornarProduto() {
        Produto produto = new Produto(1L, "Produto Teste", 10);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Produto resultado = estoqueService.obterProdutoPorId(1L);

        assertEquals(produto, resultado);
    }

    @Test
    void obterProdutoPorId_inexistente_deveLancarExcecao() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> estoqueService.obterProdutoPorId(1L));
        assertTrue(ex.getMessage().contains("Produto não encontrado"));
    }

    @Test
    void listarProdutos_deveRetornarLista() {
        List<Produto> produtos = List.of(new Produto(1L, "A", 1), new Produto(2L, "B", 2));
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> resultado = estoqueService.listarProdutos();

        assertEquals(2, resultado.size());
    }

    @Test
    void listarProdutosPorTipo_deveFiltrarPorTipo() {
        Produto p1 = new Produto(1L, "A", 1);
        p1.setTipo(TipoProduto.ELETRODOMESTICO);
        Produto p2 = new Produto(2L, "B", 2);
        p2.setTipo(TipoProduto.MOVEL);
        when(produtoRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Produto> resultado = estoqueService.listarProdutosPorTipo("MOVEL");

        assertEquals(1, resultado.size());
        assertEquals("MOVEL", resultado.get(0).getTipo());
    }

    @Test
    void criarMovimentacao_deveSalvarMovimentacao() {
        Movimentacao mov = new Movimentacao();
        when(movimentacaoRepository.save(any(Movimentacao.class))).thenReturn(mov);

        Movimentacao salvo = estoqueService.criarMovimentacao(mov);

        assertNotNull(salvo);
        verify(movimentacaoRepository).save(mov);
    }

    @Test
    void movimentarProduto_entrada_deveAdicionarEstoque() {
        Produto produto = new Produto(1L, "A", 5);
        Movimentacao mov = new Movimentacao();
        mov.setTipo(TipoMovimentacao.ENTRADA);
        mov.setQuantidade(3);
        mov.setProduto(produto);

        when(movimentacaoRepository.save(any())).thenReturn(mov);
        when(produtoRepository.save(any())).thenReturn(produto);

        Movimentacao resultado = estoqueService.movimentarProduto(mov);

        assertEquals(8, produto.getQuantidadeEstoque());
    }

    @Test
    void movimentarProduto_saida_deveRemoverEstoque() {
        Produto produto = new Produto(1L, "A", 5);
        Movimentacao mov = new Movimentacao();
        mov.setTipo(TipoMovimentacao.SAIDA);
        mov.setQuantidade(3);
        mov.setProduto(produto);

        when(movimentacaoRepository.save(any())).thenReturn(mov);
        when(produtoRepository.save(any())).thenReturn(produto);

        Movimentacao resultado = estoqueService.movimentarProduto(mov);

        assertEquals(2, produto.getQuantidadeEstoque());
    }

    @Test
    void movimentarProduto_saida_quantidadeInsuficiente_deveLancarExcecao() {
        Produto produto = new Produto(1L, "A", 2);
        Movimentacao mov = new Movimentacao();
        mov.setTipo(TipoMovimentacao.SAIDA);
        mov.setQuantidade(3);
        mov.setProduto(produto);

        assertThrows(IllegalArgumentException.class, () -> estoqueService.movimentarProduto(mov));
    }

    @Test
    void calcularLucro_deveRetornarLucroDTO() {
        Movimentacao entrada = new Movimentacao();
        entrada.setTipo(TipoMovimentacao.ENTRADA);
        entrada.setValor(10.0);
        entrada.setQuantidade(2);

        Movimentacao saida = new Movimentacao();
        saida.setTipo(TipoMovimentacao.SAIDA);
        saida.setValor(20.0);
        saida.setQuantidade(1);

        when(movimentacaoRepository.findByProdutoId(1L)).thenReturn(List.of(entrada, saida));

        LucroDTO lucro = estoqueService.calcularLucro(1L);

        assertEquals((20.0*1)-(10.0*2), lucro.getLucro());
        assertEquals(1, lucro.getQuantidadeTotalSaida());
    }

    @Test
    void relatorioPorTipo_todos_deveRetornarListaDTO() {
        Produto p = new Produto(1L, "A", 5);
        p.setTipo(TipoProduto.ELETRODOMESTICO);
        p.setValor(10.0);
        when(produtoRepository.findAll()).thenReturn(List.of(p));
        when(movimentacaoRepository.findQuantidadeVendidaByProdutoId(1L)).thenReturn(2);

        List<ProdutoRelatorioDTO> relatorio = estoqueService.relatorioPorTipo("TODOS");

        assertEquals(1, relatorio.size());
        assertEquals("A", relatorio.get(0).descricao());
    }

    @Test
    void relatorioPorTipo_filtrado_deveRetornarListaDTO() {
        Produto p = new Produto(1L, "A", 5);
        p.setTipo(TipoProduto.MOVEL);
        p.setValor(10.0);
        when(produtoRepository.findByTipo(TipoProduto.MOVEL)).thenReturn(List.of(p));
        when(movimentacaoRepository.findQuantidadeVendidaByProdutoId(1L)).thenReturn(2);

        List<ProdutoRelatorioDTO> relatorio = estoqueService.relatorioPorTipo("MERCADORIA");

        assertEquals(1, relatorio.size());
        assertEquals("A", relatorio.get(0).descricao());
    }
}