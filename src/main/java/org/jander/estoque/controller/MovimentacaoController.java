package org.jander.estoque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.jander.estoque.dto.MovimentacaoDTO;
import org.jander.estoque.service.MovimentacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/movimentacoes")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operacao realizada com sucesso"),
        @ApiResponse(responseCode = "201", description = "Operacao realizada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
        @ApiResponse(responseCode = "404", description = "Dados Invalidos")})
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @Operation(summary = "Registra uma movimentação de produto")
    @PostMapping
    public ResponseEntity<MovimentacaoDTO> movimentarProduto(@RequestBody MovimentacaoDTO movimentacaoDTO) {
        MovimentacaoDTO novaMovimentacao = movimentacaoService.movimentarProduto(movimentacaoDTO);
        return ResponseEntity.ok(novaMovimentacao);
    }
}