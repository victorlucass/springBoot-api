package com.victorlucas.cursomc.services;

import com.victorlucas.cursomc.domain.Cidade;
import com.victorlucas.cursomc.domain.Cliente;
import com.victorlucas.cursomc.domain.Endereco;
import com.victorlucas.cursomc.domain.enums.Perfil;
import com.victorlucas.cursomc.domain.enums.TipoCliente;
import com.victorlucas.cursomc.dto.ClienteDTO;
import com.victorlucas.cursomc.dto.ClienteNewDTO;
import com.victorlucas.cursomc.exceptions.AuthorizationExpection;
import com.victorlucas.cursomc.exceptions.DataIntegrityException;
import com.victorlucas.cursomc.exceptions.ObjectNotFoundException;
import com.victorlucas.cursomc.repositories.ClienteRepository;
import com.victorlucas.cursomc.repositories.EnderecoRepository;
import com.victorlucas.cursomc.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*GET*/
    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findById (Integer id){

        UserSpringSecurity user = UserService.authenticated();

        if(user == null || (!user.hasRole(Perfil.ADMIN) && !id.equals(user.getId()))){
            throw new AuthorizationExpection("Acesso negado!");
        }

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
    @Transactional
    public Cliente save(ClienteNewDTO dto){
        Cliente cliente = fromCliente(dto);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return clienteRepository.save(cliente);
    }

    /*PUT*/
    public Cliente update(Integer id, ClienteDTO dto){
        Cliente obj = findById(id); //Pega o obj no banco e faz a validação.
        fromCliente(dto, obj); // Transformando DTO em Cliente.
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
    private Cliente fromCliente(ClienteDTO dto, Cliente cliente){
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        return cliente;
    }
    public Cliente fromCliente(ClienteNewDTO dto){
        Cliente cliente = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(),
                TipoCliente.toEnum(dto.getTipoCliente()),
                bCryptPasswordEncoder.encode(dto.getSenha()));
        Cidade cidade = new Cidade(dto.getCidadeId(), null, null);
        Endereco endereco = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCep(), cliente, cidade);

        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(dto.getTelefone1());

        if (dto.getTelefone2() != null){
            cliente.getTelefones().add(dto.getTelefone2());
        }
        if (dto.getTelefone3() != null){
            cliente.getTelefones().add(dto.getTelefone3());
        }

        return cliente;
    }
}
