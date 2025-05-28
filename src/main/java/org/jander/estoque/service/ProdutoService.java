package org.jander.estoque.service;

import org.jander.estoque.dto.LucroDTO;
import org.jander.estoque.dto.ProdutoDTO;
import org.jander.estoque.dto.ProdutoRelatorioDTO;
import org.jander.estoque.enums.TipoProduto;
import org.jander.estoque.mapper.ProdutoMapper;
import org.jander.estoque.model.Movimentacao;
import org.jander.estoque.model.Produto;
import org.jander.estoque.repository.MovimentacaoRepository;
import org.jander.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public ProdutoService(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public ProdutoDTO criarProduto(ProdutoDTO produtoDTO) {
        Produto produto = ProdutoMapper.toEntity(produtoDTO);
        Produto salvo = produtoRepository.save(produto);
        return ProdutoMapper.toDTO(salvo);
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produto = ProdutoMapper.toEntity(produtoDTO);
        produto.setId(id);
        Produto salvo = produtoRepository.save(produto);
        return ProdutoMapper.toDTO(salvo);
    }

    @Transactional
    public void removerProdutoPorId(Long id) {
        movimentacaoRepository.deleteAllByProdutoId(id);
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO obterProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
        return ProdutoMapper.toDTO(produto);
    }

    public List<ProdutoDTO> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProdutoRelatorioDTO> relatorioPorTipo(String tipo) {
        List<Produto> produtos;
        if (tipo.equals("TODOS")) {
            produtos = produtoRepository.findAll();
        } else {
            TipoProduto tipoProduto = TipoProduto.valueOf(tipo.toUpperCase());
            produtos = produtoRepository.findByTipo(tipoProduto);
        }

        return produtos.stream()
                .map(p -> {
                    Integer quantidadeVendida = movimentacaoRepository.findQuantidadeVendidaByProdutoId(p.getId());
                    Double valorFornecedor = p.getValor();
                    Integer quantidadeEstoque = p.getQuantidadeEstoque();
                    Integer qtdVenda = quantidadeVendida != null ? quantidadeVendida : 0;

                    BigDecimal totalEstoque = valorFornecedor != null && quantidadeEstoque != null
                            ? BigDecimal.valueOf(valorFornecedor * quantidadeEstoque)
                            : BigDecimal.ZERO;

                    BigDecimal totalVendido = qtdVenda != null
                            ? BigDecimal.valueOf(qtdVenda)
                            : BigDecimal.ZERO;

                    return new ProdutoRelatorioDTO(
                            p.getId(),
                            p.getDescricao(),
                            p.getCodigo(),
                            p.getTipo().name(),
                            p.getQuantidadeEstoque(),
                            qtdVenda,
                            totalEstoque,
                            totalVendido
                    );
                })
                .toList();
    }

    public LucroDTO calcularLucro(Long idProduto) {
        List<Movimentacao> movimentacoes = movimentacaoRepository.findByProdutoId(idProduto);

        double totalEntradas = movimentacoes.stream()
                .filter(mov -> mov.getTipo().name().equals("ENTRADA"))
                .mapToDouble(mov -> mov.getValor() * mov.getQuantidade())
                .sum();

        double totalSaidas = movimentacoes.stream()
                .filter(mov -> mov.getTipo().name().equals("SAIDA"))
                .mapToDouble(mov -> mov.getValor() * mov.getQuantidade())
                .sum();

        int quantidadeTotalSaida = movimentacoes.stream()
                .filter(mov -> mov.getTipo().name().equals("SAIDA"))
                .mapToInt(Movimentacao::getQuantidade)
                .sum();

        double lucro = totalSaidas - totalEntradas;

        return new LucroDTO(lucro, quantidadeTotalSaida);
    }
}