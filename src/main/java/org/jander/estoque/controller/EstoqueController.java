package org.jander.estoque.controller;

import org.jander.estoque.model.Movimentacao;
import org.jander.estoque.model.Produto;
import org.jander.estoque.repository.MovimentacaoRepository;
import org.jander.estoque.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {


    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public EstoqueController(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    @PostMapping("/produto")
     public Produto criarProduto(@RequestBody Produto produto) {
         return produtoRepository.save(produto);
     }
    @GetMapping("/produto/{id}")
    public Produto obterProdutoPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
    }
    @GetMapping("/produtos")
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();

    }

    @PostMapping("/movimentacao")
    public Movimentacao criarMovimentacao(@RequestBody Movimentacao movimentacao) {
        return movimentacaoRepository.save(movimentacao);

    }
}
