package org.jander.estoque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.jander.estoque.dto.LucroDTO;
import org.jander.estoque.dto.ProdutoDTO;
import org.jander.estoque.dto.ProdutoRelatorioDTO;
import org.jander.estoque.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/produtos")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacao realizada com sucesso"),
        @ApiResponse(responseCode = "201", description = "Operacao realizada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
        @ApiResponse(responseCode = "404", description = "Dados Invalidos")})
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Cria um novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })
    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO novoProduto = produtoService.criarProduto(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }
    @Operation(summary = "Atualiza um produto já existente um novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoAtualizado = produtoService.atualizarProduto(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(summary = "Busca um  produto Pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> obterProdutoPorId(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.obterProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @Operation (summary = "Lista produtos por tipo")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ProdutoRelatorioDTO>> listarProdutosPorTipo(@PathVariable String tipo) {
        List<ProdutoRelatorioDTO> produtos = produtoService.relatorioPorTipo(tipo);
        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        produtoService.removerProdutoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        List<ProdutoDTO> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}/financeiro")
    public ResponseEntity<LucroDTO> consultarLucro(@PathVariable Long id) {
        LucroDTO lucro = produtoService.calcularLucro(id);
        return ResponseEntity.ok(lucro);
    }
}