package com.victorlucas.cursomc.services;

import com.victorlucas.cursomc.domain.Cliente;
import com.victorlucas.cursomc.dto.ClienteDTO;
import com.victorlucas.cursomc.exceptions.DataIntegrityException;
import com.victorlucas.cursomc.exceptions.ObjectNotFoundException;
import com.victorlucas.cursomc.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /*GET*/
    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findById (Integer id){
        Optional<Cliente> clienteSelect = clienteRepository.findById(id);
        return clienteSelect.orElseThrow(
                () -> new ObjectNotFoundException("Cliente com o id: " + id +" do tipo "+Cliente.class.getName()+" não existe, ou não encontrado.")
        );
    }

    public Page<Cliente>findPage(Integer page, Integer linePerPage, String direction, String orderBy){
        PageRequest pageRequest = PageRequest.of(page, linePerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    /*POST*/
    public Cliente save(Cliente cliente){
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }

    /*PUT*/
    public Cliente update(Integer id, ClienteDTO dto){
        Cliente obj = findById(id); //Pega o obj no banco e faz a validação.
        fromCliente(dto, obj); // Transformando DTO em Cliente.
        obj.setId(id); // Seta o id para fazer o update
        return clienteRepository.save(obj); //Salva no banco
    }

    /*DELETE*/
    public void delete(Integer id){
        findById(id);
        try {
            clienteRepository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new DataIntegrityException("Não foi possível deletar esse cliente.");
        }
    }

    /*AUXILIAR*/
    public Cliente fromCliente(ClienteDTO dto, Cliente cliente){
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        return cliente;
    }
}
