package com.victorlucas.cursomc.repositories;

import com.victorlucas.cursomc.domain.itemPedido.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
