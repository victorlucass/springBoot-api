package com.victorlucas.cursomc.services;

import com.victorlucas.cursomc.domain.Cliente;
import com.victorlucas.cursomc.domain.Pedido;
import com.victorlucas.cursomc.exceptions.ObjectNotFoundException;
import com.victorlucas.cursomc.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido findById(Integer id){
        Optional<Pedido> pedidoSelect = pedidoRepository.findById(id);

        return pedidoSelect.orElseThrow(
                () -> new ObjectNotFoundException("Pedido não encontrado.")
        );
    }

}
