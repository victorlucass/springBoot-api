package com.victorlucas.cursomc.repositories;

import com.victorlucas.cursomc.domain.pagamentos.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
