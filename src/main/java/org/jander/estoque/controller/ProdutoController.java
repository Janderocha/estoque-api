//package org.jander.estoque.controller;
//
//import org.jander.estoque.dto.ProdutoRelatorioDTO;
//import org.jander.estoque.dto.ProdutoRequestDTO;
//import org.jander.estoque.service.EstoqueService;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/produtos")
//public class ProdutoController {
//
//    private final EstoqueService estoqueService;
//
//    public ProdutoController(EstoqueService estoqueService) {
//        this.estoqueService = estoqueService;
//    }
//
//    @PostMapping
//    public ProdutoRelatorioDTO criar(@RequestBody @Valid ProdutoRequestDTO dto) {
//        return estoqueService.criarProduto(dto);
//    }
//
//    @PutMapping("/{id}")
//    public ProdutoRelatorioDTO atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO dto) {
//        return estoqueService.atualizarProduto(id, dto);
//    }
//
//    @GetMapping("/{id}")
//    public ProdutoRelatorioDTO obterPorId(@PathVariable Long id) {
//        return estoqueService.obterProdutoPorId(id);
//    }
//
//    @GetMapping
//    public List<ProdutoRelatorioDTO> listar() {
//        return estoqueService.listarProdutos();
//    }
//
//    @DeleteMapping("/{id}")
//    public void remover(@PathVariable Long id) {
//        estoqueService.removerProdutoPorId(id);
//    }
//}