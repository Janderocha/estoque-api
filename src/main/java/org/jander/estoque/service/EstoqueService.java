package org.jander.estoque.service;

import org.jander.estoque.dto.LucroDTO;
import org.jander.estoque.dto.ProdutoRelatorioDTO;
import org.jander.estoque.enums.TipoProduto;
import org.jander.estoque.enums.TipoMovimentacao;
import org.jander.estoque.model.Movimentacao;
import org.jander.estoque.model.Produto;
import org.jander.estoque.repository.MovimentacaoRepository;
import org.jander.estoque.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EstoqueService {
     private final ProdutoRepository produtoRepository;
     private final MovimentacaoRepository movimentacaoRepository;

    public EstoqueService(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }


    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void removerProdutoPorId(Long id) {
        produtoRepository.deleteById(id);
    }

    public Produto obterProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
    }

    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }


    public List<Produto> listarProdutosPorTipo(String tipo) {
        return produtoRepository.findAll().stream()
                .filter(produto -> produto.getTipo().equals(tipo))
                .toList();
    }

    public Movimentacao criarMovimentacao(Movimentacao movimentacao) {
        return movimentacaoRepository.save(movimentacao);
    }


    public Movimentacao movimentarProduto(Movimentacao movimentacao) {
        Produto produto = movimentacao.getProduto();
        if (movimentacao.getTipo() == TipoMovimentacao.ENTRADA) {
            // Lógica para adicionar produto ao estoque
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + movimentacao.getQuantidade());
            movimentacaoRepository.save(movimentacao);
            produtoRepository.save(produto);
        } else if (movimentacao.getTipo() == TipoMovimentacao.SAIDA) {
            // Lógica para remover produto do estoque
            if (produto.getQuantidadeEstoque() >= movimentacao.getQuantidade()) {
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - movimentacao.getQuantidade());
                movimentacaoRepository.save(movimentacao);
                produtoRepository.save(produto);
            } else {
                throw new IllegalArgumentException("Quantidade insuficiente no estoque");
            }
        }
        return movimentacao;
    }


    public LucroDTO calcularLucro(Long idProduto) {
        List<Movimentacao> movimentacoes = movimentacaoRepository.findByProdutoId(idProduto);

        double totalEntradas = movimentacoes.stream()
                .filter(mov -> mov.getTipo() == TipoMovimentacao.ENTRADA)
                .mapToDouble(mov -> mov.getValor() * mov.getQuantidade())
                .sum();

        double totalSaidas = movimentacoes.stream()
                .filter(mov -> mov.getTipo() == TipoMovimentacao.SAIDA)
                .mapToDouble(mov -> mov.getValor() * mov.getQuantidade())
                .sum();

        int quantidadeTotalSaida = movimentacoes.stream()
                .filter(mov -> mov.getTipo() == TipoMovimentacao.SAIDA)
                .mapToInt(Movimentacao::getQuantidade)
                .sum();

        double lucro = totalSaidas - totalEntradas;

        return new LucroDTO(lucro, quantidadeTotalSaida);
    }

    @Transactional(readOnly = true)
    public List<ProdutoRelatorioDTO> relatorioPorTipo(String tipo) {
        List<Produto> produtos;
        if(tipo.equals("TODOS")) {
            produtos = produtoRepository.findAll();
        }else{
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

                    BigDecimal totalVendido =  qtdVenda != null
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
}
