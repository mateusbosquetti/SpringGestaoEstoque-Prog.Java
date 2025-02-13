package net.weg.gestaoestoque2025.Controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.weg.gestaoestoque2025.DTO.Request.ProdutoPostRequestDTO;
import net.weg.gestaoestoque2025.DTO.Response.ProdutoResponseDTO;
import net.weg.gestaoestoque2025.Entity.Produto;
import net.weg.gestaoestoque2025.Service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {

    private ProdutoService service;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> postProduto(@RequestBody @Valid ProdutoPostRequestDTO produtoPostRequestDTO) {
        try {
            return new ResponseEntity<>(service.adicionarProduto(produtoPostRequestDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Produto>> getProdutoPage(Pageable pageable) {
        try {
            return new ResponseEntity<>(service.listarProduto(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getProduto() {
        try {
            return new ResponseEntity<>(service.listarProdutoAntigo(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getProdutoById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(service.buscarProduto(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> putProduto(@RequestBody @Valid ProdutoPostRequestDTO produtoPostRequestDTO, @PathVariable int id) {
        try {
            return new ResponseEntity<>(service.atualizarProduto(produtoPostRequestDTO, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable int id) {
        try {
            service.excluirProduto(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
}
