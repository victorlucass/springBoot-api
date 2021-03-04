package com.victorlucas.cursomc.services;

import com.victorlucas.cursomc.domain.Cliente;
import com.victorlucas.cursomc.domain.Pedido;
import com.victorlucas.cursomc.domain.enums.EstadoPagamento;
import com.victorlucas.cursomc.domain.itemPedido.ItemPedido;
import com.victorlucas.cursomc.domain.pagamentos.PagamentoComBoleto;
import com.victorlucas.cursomc.exceptions.AuthorizationExpection;
import com.victorlucas.cursomc.exceptions.ObjectNotFoundException;
import com.victorlucas.cursomc.repositories.ItemPedidoRepository;
import com.victorlucas.cursomc.repositories.PagamentoRepository;
import com.victorlucas.cursomc.repositories.PedidoRepository;
import com.victorlucas.cursomc.security.UserSpringSecurity;
import com.victorlucas.cursomc.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido findById(Integer id){
        Optional<Pedido> pedidoSelect = pedidoRepository.findById(id);

        return pedidoSelect.orElseThrow(
                () -> new ObjectNotFoundException("Pedido não encontrado.")
        );
    }

    @Transactional
    public Pedido save(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.findById(pedido.getCliente().getId()));
        pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if (pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto bol = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(bol, pedido.getInstante());
        }
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());
        for(ItemPedido ip : pedido.getItens()){
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.findById(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(pedido);
        }
        itemPedidoRepository.saveAll(pedido.getItens());
        emailService.sendOrderConfirmationHtmlEmail(pedido);
        return pedido;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        UserSpringSecurity user = UserService.authenticated();
        if (user == null){
            throw new AuthorizationExpection("Acesso negado!");
        }
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(orderBy), direction);
        Cliente cliente = clienteService.findById(user.getId());
        return pedidoRepository.findByCliente(cliente, pageRequest);
    }

}
