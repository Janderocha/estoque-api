//package org.jander.estoque.controller;
//
//import org.jander.estoque.dto.LucroDTO;
//import org.jander.estoque.dto.ProdutoRelatorioDTO;
//import org.jander.estoque.model.Movimentacao;
//import org.jander.estoque.model.Produto;
//import org.jander.estoque.repository.MovimentacaoRepository;
//import org.jander.estoque.repository.ProdutoRepository;
//import org.jander.estoque.service.EstoqueService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "http://localhost:5173")
//@RestController
//@RequestMapping("/movimentacao")
//public class MovimentacaoController {
//
//
//    private final ProdutoRepository produtoRepository;
//    private final MovimentacaoRepository movimentacaoRepository;
//    private final EstoqueService estoqueService;
//
//    public MovimentacaoController(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository, EstoqueService estoqueService) {
//        this.produtoRepository = produtoRepository;
//        this.movimentacaoRepository = movimentacaoRepository;
//        this.estoqueService = estoqueService;
//    }
//
//    @PostMapping("/produto")
//     public Produto criarProduto(@RequestBody Produto produto) {
//         return estoqueService.criarProduto(produto);
//     }
//
//     @PutMapping("/produto/{id}")
//     public Produto atualizarProduto(@RequestBody Produto produto) {
//         return estoqueService.criarProduto(produto);
//     }
//    @GetMapping("/produtos/id/{id}")
//    public Produto obterProdutoPorId(@PathVariable Long id) {
//        return estoqueService.obterProdutoPorId(id);
//    }
//
//    @GetMapping("/produtos/tipo/{tipo}")
//    public List<ProdutoRelatorioDTO> listarProdutosPorTipo(@PathVariable String tipo) {
//        return estoqueService.relatorioPorTipo(tipo);
//    }
//
//    @DeleteMapping("/produto/delete/{id}")
//    public void removerProduto(@PathVariable Long id) {
//         estoqueService.removerProdutoPorId(id);
//    }
//
//
//
//    @GetMapping("/produtos")
//    public List<Produto> listarProdutos() {
//        return estoqueService.listarProdutos();
//    }
//
//    @PostMapping("/movimentacao")
//    public Movimentacao movimentarProduto(@RequestBody Movimentacao movimentacao) {
//        return estoqueService.movimentarProduto(movimentacao);
//
//    }
//
//   @GetMapping("/financeiro/{id}")
//   public LucroDTO consultarLucro(@PathVariable Long id) {
//       return estoqueService.calcularLucro(id);
//   }
//
//
//
//
//}
