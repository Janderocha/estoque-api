package org.jander.estoque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.jander.estoque.dto.LucroDTO;
import org.jander.estoque.dto.ProdutoRelatorioDTO;
import org.jander.estoque.model.Movimentacao;
import org.jander.estoque.model.Produto;
import org.jander.estoque.service.EstoqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @Operation(summary = "Cria um novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/produtos")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        Produto novoProduto = estoqueService.criarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @Operation(summary = "Atualiza um produto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/produtos/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        produto.setId(id);
        Produto produtoAtualizado = estoqueService.criarProduto(produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(summary = "Obtém um produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> obterProdutoPorId(@PathVariable Long id) {
        Produto produto = estoqueService.obterProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @Operation(summary = "Lista produtos por tipo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso")
    })
    @GetMapping("/produtos/tipo/{tipo}")
    public ResponseEntity<List<ProdutoRelatorioDTO>> listarProdutosPorTipo(@PathVariable String tipo) {
        List<ProdutoRelatorioDTO> produtos = estoqueService.relatorioPorTipo(tipo);
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Remove um produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        estoqueService.removerProdutoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista todos os produtos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso")
    })
    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = estoqueService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Movimenta um produto (entrada/saída)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimentação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/movimentacoes")
    public ResponseEntity<?> movimentarProduto(@RequestBody Movimentacao movimentacao) {
        try {
            Movimentacao novaMovimentacao = estoqueService.movimentarProduto(movimentacao);
            return ResponseEntity.ok(novaMovimentacao);
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }catch (DateTimeParseException ex) {
            return ResponseEntity.badRequest().body("Data inválida");
        }
    }

    @Operation(summary = "Consulta o lucro de um produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lucro consultado com sucesso")
    })
    @GetMapping("/produtos/{id}/financeiro")
    public ResponseEntity<LucroDTO> consultarLucro(@PathVariable Long id) {
        LucroDTO lucro = estoqueService.calcularLucro(id);
        return ResponseEntity.ok(lucro);
    }
}