package com.victorlucas.cursomc.services;

import com.victorlucas.cursomc.domain.Categoria;
import com.victorlucas.cursomc.dto.CategoriaDTO;
import com.victorlucas.cursomc.exceptions.DataIntegrityException;
import com.victorlucas.cursomc.exceptions.ObjectNotFoundException;
import com.victorlucas.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<CategoriaDTO> findAll(){
        List<Categoria> lista = repository.findAll();
        List<CategoriaDTO> listDTO = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return listDTO;
    }

    public Categoria findById(Integer id){
        Optional<Categoria> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Erro ao encontra Categoria com id "+
                id + " do tipo " + Categoria.class.getName()));
    }
    //para user o findById ele precisa ser do tipo Optional<tipo>
    //Ao retorna ele precisa do orElse() esse cara vai realizar oq está dentro caso seja nulo.

    public Categoria save(Categoria obj){
        obj.setId(null);
        return repository.save(obj);
    }


    public Categoria update(Categoria categoria){
        return repository.save(categoria);
    }

    public void delete(Integer id) {
        findById(id); //Vai verificar se tem id...
        try{
            repository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não foi possível excluir uma categoria pois há produtos vinculados à ela.");
        }
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }
}
