package org.jander.estoque.controller;

import org.jander.estoque.dto.MovimentacaoDTO;
import org.jander.estoque.service.MovimentacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping
    public ResponseEntity<MovimentacaoDTO> movimentarProduto(@RequestBody MovimentacaoDTO movimentacaoDTO) {
        MovimentacaoDTO novaMovimentacao = movimentacaoService.movimentarProduto(movimentacaoDTO);
        return ResponseEntity.ok(novaMovimentacao);
    }
}