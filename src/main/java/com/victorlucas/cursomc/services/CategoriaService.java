package com.victorlucas.cursomc.services;

import com.victorlucas.cursomc.domain.Categoria;
import com.victorlucas.cursomc.exceptions.ObjectNotFoundException;
import com.victorlucas.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> findAll(){
        List<Categoria> obj = repository.findAll();
        return obj;
    }

    public Categoria findById(Integer id){
        Optional<Categoria> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Erro ao encontra Categoria com id "+
                id + " do tipo " + Categoria.class.getName()));
    }
    //para user o findById ele precisa ser do tipo Optional<tipo>
    //Ao retorna ele precisa do orElse() esse cara vai realizar oq está dentro caso seja nulo.

    public Categoria create(Categoria obj){
        obj.setId(null);
        return repository.save(obj);
    }
}
