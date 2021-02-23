package com.victorlucas.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.victorlucas.cursomc.domain.enums.TipoCliente;
import com.victorlucas.cursomc.dto.ClienteDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Cliente implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String email;

    private String CpfOuCnpj;

    private Integer tipoCliente;

    @JsonIgnore
    private String senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL) //CascadeType.ALL é para forçar a exclusão do endereço junto com o cliente quando chamado método.
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "TELEFONE")
    private Set<String> telefones = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipoCliente, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        CpfOuCnpj = cpfOuCnpj;
        this.tipoCliente = (tipoCliente == null) ? null : tipoCliente.getNumber();
        this.senha = senha;
    }

    public Cliente(Integer id, String nome, String email){
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public TipoCliente getTipoCliente() {
        return TipoCliente.toEnum(tipoCliente);
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente.getNumber();
    }
}
