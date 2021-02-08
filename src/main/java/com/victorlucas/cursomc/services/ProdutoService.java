package com.victorlucas.cursomc.services;

import com.victorlucas.cursomc.domain.Categoria;
import com.victorlucas.cursomc.domain.Pedido;
import com.victorlucas.cursomc.domain.Produto;
import com.victorlucas.cursomc.exceptions.ObjectNotFoundException;
import com.victorlucas.cursomc.repositories.CategoriaRepository;
import com.victorlucas.cursomc.repositories.PedidoRepository;
import com.victorlucas.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto findById(Integer id){
        Optional<Produto> pedidoSelect = repo.findById(id);
        return pedidoSelect.orElseThrow(
                () -> new ObjectNotFoundException("Pedido não encontrado.")
        );
    }

    /*public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String direction, String orderBy){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return repo.search(nome, categorias, pageRequest);
    }
    */
    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return repo.search(nome, categorias, pageRequest);
    }
}
