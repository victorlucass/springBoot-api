package com.victorlucas.cursomc;

import com.victorlucas.cursomc.domain.*;
import com.victorlucas.cursomc.domain.enums.EstadoPagamento;
import com.victorlucas.cursomc.domain.enums.TipoCliente;
import com.victorlucas.cursomc.domain.itemPedido.ItemPedido;
import com.victorlucas.cursomc.domain.pagamentos.Pagamento;
import com.victorlucas.cursomc.domain.pagamentos.PagamentoComBoleto;
import com.victorlucas.cursomc.domain.pagamentos.PagamentoComCartao;
import com.victorlucas.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    public void run(String... args) throws Exception {

        /*Categorias*/
        Categoria cat1 = new Categoria("Informática");
        Categoria cat2 = new Categoria( "Escritório");

        /*Produtos*/
        Produto p1 = new Produto( "Computador",2000.00);
        Produto p2 = new Produto("Impressora",800.00);
        Produto p3 = new Produto("Mouse",80.00);

        /*Categorias de Produtos*/
        cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));

        /*Estado*/

        Estado est1 = new Estado("Minas Gerais");
        Estado est2 = new Estado("São Paulo");

        /*Cidade*/
        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null,"São Paulo",est2);
        Cidade c3 = new Cidade(null,"Campinas",est2);

        /*Cliente*/
        Cliente cli1 = new Cliente(null, "Maria Silva","maria@gmail.com","36378912377", TipoCliente.PESSOAFISICA);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2,c3));
        cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));

        /*Endereço*/
        Endereco e1 = new Endereco(null, "Rua Flores","300","Apto 203","Jardim","38220834",cli1,c1);
        Endereco e2 = new Endereco(null, "Avenida Matos","150","Sala 800","Centro","38777012",cli1,c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1,e2));

        /*Pedido*/

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        Pedido ped1 = new Pedido(null,sdf.parse("30/09/2020 10:32"),e1,cli1);
        Pedido ped2 = new Pedido(null, sdf.parse("30/10/2020 11:30"),e2,cli1);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,sdf.parse("20/10/2020 00:00"),null);

        ped1.setPagamento(pagto1);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));

        /*Itens Pedidos*/
        ItemPedido ip1 = new ItemPedido(ped1,p1,0.00,1,2000.00);
        ItemPedido ip2 = new ItemPedido(ped1,p3,0.00,2,80.00);
        ItemPedido ip3 = new ItemPedido(ped2,p2,100.00,1,800.00);

        ped1.getItens().addAll(Arrays.asList(ip1,ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        /*Repositórios*/
        categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
        estadoRepository.saveAll(Arrays.asList(est1,est2));
        cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1,e2));
        pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
        itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));






    }
}
