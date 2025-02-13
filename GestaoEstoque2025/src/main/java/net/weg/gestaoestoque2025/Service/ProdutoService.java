package net.weg.gestaoestoque2025.Service;

import lombok.AllArgsConstructor;
import net.weg.gestaoestoque2025.DTO.Request.ProdutoPostRequestDTO;
import net.weg.gestaoestoque2025.DTO.Response.ProdutoResponseDTO;
import net.weg.gestaoestoque2025.Entity.Produto;
import net.weg.gestaoestoque2025.Repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProdutoService {

    private ProdutoRepository repository;
    private CategoriaService categoriaService;
    private FabricanteService fabricanteService;


    public ProdutoResponseDTO adicionarProduto(ProdutoPostRequestDTO produtoPostRequestDTO) {
        Produto produto = repository.save(toEntity(produtoPostRequestDTO));
        return produto.toDTO();
    }

    public ProdutoResponseDTO buscarProduto(int id) {
        Produto produto = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return produto.toDTO();
    }

    public List<ProdutoResponseDTO> listarProdutoAntigo() {
        return repository.findAll().stream().map(Produto::toDTO).collect(Collectors.toList());
    }

    public Page<Produto> listarProduto(Pageable pageable) {
       return repository.findAll(pageable);
    }

    public ProdutoResponseDTO atualizarProduto(ProdutoPostRequestDTO produtoPostRequestDTO, int id) {
        buscarProduto(id);

        Produto produto = toEntity(produtoPostRequestDTO);
        produto.setId(id);
        repository.save(produto);
        return produto.toDTO();
    }

    public void excluirProduto(int id) {
        buscarProduto(id);
        repository.deleteById(id);
    }

    public Produto toEntity(ProdutoPostRequestDTO produtoPostRequestDTO) {
        return Produto.builder()
                .nome(produtoPostRequestDTO.nome())
                .preco(produtoPostRequestDTO.preco())
                .estoque(produtoPostRequestDTO.estoque())
                .dataValidade(produtoPostRequestDTO.dataValidade())
                .descricao(produtoPostRequestDTO.descricao())
                .codigoBarras(produtoPostRequestDTO.codigoBarras())
                .peso(produtoPostRequestDTO.peso())
                .medida(produtoPostRequestDTO.medida())
                .fabricante(fabricanteService.buscarFabricanteEntidade(produtoPostRequestDTO.fabricante_id()))
                .categoria(categoriaService.buscarCategoriaEntidade(produtoPostRequestDTO.categoria_id()))
                .build();
    }
}
