package com.victorlucas.cursomc.domain.pagamentos;

import com.victorlucas.cursomc.domain.Pedido;
import com.victorlucas.cursomc.domain.enums.EstadoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PagamentoComCartao extends Pagamento{
    private Integer numeroDeParcela;

    public PagamentoComCartao (Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcela){
        super(id, estado, pedido );
        this.numeroDeParcela = numeroDeParcela;
    }

}
