package com.victorlucas.cursomc.services;

import com.victorlucas.cursomc.domain.Cliente;
import com.victorlucas.cursomc.exceptions.ObjectNotFoundException;
import com.victorlucas.cursomc.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll(){
        List<Cliente> allCliente = clienteRepository.findAll();
        return allCliente;
    }

    public Cliente findById (Integer id){
        Optional<Cliente> clienteSelect = clienteRepository.findById(id);
        return clienteSelect.orElseThrow(
                () -> new ObjectNotFoundException("Cliente com o id: " + id +" do tipo "+Cliente.class.getName()+" não existe, ou não encontrado.")
        );
    }

}
