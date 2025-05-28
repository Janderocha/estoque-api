package org.jander.estoque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.jander.estoque.dto.ProdutoRelatorioDTO;
import org.jander.estoque.model.Movimentacao;
import org.jander.estoque.model.Produto;
import org.jander.estoque.repository.MovimentacaoRepository;
import org.jander.estoque.repository.ProdutoRepository;
import org.jander.estoque.service.EstoqueService;
import org.springframework.web.bind.annotation.*;
import org.jander.estoque.dto.LucroDTO;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/estoque")
public class EstoqueController {


    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final EstoqueService estoqueService;

    public EstoqueController(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository, EstoqueService estoqueService) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.estoqueService = estoqueService;
    }
    @Operation(summary = "Cria um novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/produto")
     public Produto criarProduto(@RequestBody Produto produto) {
         return estoqueService.criarProduto(produto);
     }

    @Operation(summary = "atualiza um  produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
     @PutMapping("/produto/{id}")
     public Produto atualizarProduto(@RequestBody Produto produto) {
         return estoqueService.criarProduto(produto);
     }
    @GetMapping("/produtos/id/{id}")
    public Produto obterProdutoPorId(@PathVariable Long id) {
        return estoqueService.obterProdutoPorId(id);
    }

    @GetMapping("/produtos/tipo/{tipo}")
    public List<ProdutoRelatorioDTO> listarProdutosPorTipo(@PathVariable String tipo) {
        return estoqueService.relatorioPorTipo(tipo);
    }

    @DeleteMapping("/produto/delete/{id}")
    public void removerProduto(@PathVariable Long id) {
         estoqueService.removerProdutoPorId(id);
    }



    @GetMapping("/produtos")
    public List<Produto> listarProdutos() {
        return estoqueService.listarProdutos();
    }

    @PostMapping("/movimentacao")
    public Movimentacao movimentarProduto(@RequestBody Movimentacao movimentacao) {
        return estoqueService.movimentarProduto(movimentacao);

    }

   @GetMapping("/financeiro/{id}")
   public LucroDTO consultarLucro(@PathVariable Long id) {
       return estoqueService.calcularLucro(id);
   }




}
