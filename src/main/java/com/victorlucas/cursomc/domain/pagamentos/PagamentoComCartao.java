package com.victorlucas.cursomc.domain.pagamentos;

import com.fasterxml.jackson.annotation.JsonTypeName;
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
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento{
    private Integer numeroDeParcelas;

    public PagamentoComCartao (Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas){
        super(id, estado, pedido );
        this.numeroDeParcelas = numeroDeParcelas;
    }

}
