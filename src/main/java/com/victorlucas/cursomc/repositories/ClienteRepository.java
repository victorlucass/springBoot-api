package com.victorlucas.cursomc.repositories;

import com.victorlucas.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Transactional(readOnly = true)//readOnly significa que é uma transação de leitura exclusivamente, isso dá mais performace.
    Cliente findByEmail(String field);
}
