package com.victorlucas.cursomc.services;

import com.victorlucas.cursomc.domain.*;
import com.victorlucas.cursomc.domain.enums.EstadoPagamento;
import com.victorlucas.cursomc.domain.enums.Perfil;
import com.victorlucas.cursomc.domain.enums.TipoCliente;
import com.victorlucas.cursomc.domain.itemPedido.ItemPedido;
import com.victorlucas.cursomc.domain.pagamentos.Pagamento;
import com.victorlucas.cursomc.domain.pagamentos.PagamentoComBoleto;
import com.victorlucas.cursomc.domain.pagamentos.PagamentoComCartao;
import com.victorlucas.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void instantiateTestDatabase() throws ParseException {
        /*Categorias*/
        Categoria cat1 = new Categoria("Informática");
        Categoria cat2 = new Categoria( "Escritório");
        Categoria cat3 = new Categoria("Cama Mesa e Banho");
        Categoria cat4 = new Categoria("Eletrônicos");
        Categoria cat5 = new Categoria("Jardinagem");
        Categoria cat6 = new Categoria("Decoração");
        Categoria cat7 = new Categoria("Perfumaria");


        /*Produtos*/
        Produto p1 = new Produto( "Computador",2000.00);
        Produto p2 = new Produto("Impressora",800.00);
        Produto p3 = new Produto("Mouse",80.00);
        Produto p4 = new Produto("Mesa de escritório", 300.00);
        Produto p5 = new Produto("Toalha", 50.00);
        Produto p6 = new Produto("Colcha", 200.00);
        Produto p7 = new Produto("TV true color", 1200.00);
        Produto p8 = new Produto("Roçadeira", 800.00);
        Produto p9 = new Produto("Abajour", 100.00);
        Produto p10 = new Produto("Pendente", 180.00);
        Produto p11 = new Produto("Shampoo", 90.00);

        /*Categorias de Produtos*/
        cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));
        cat2.getProdutos().addAll(Arrays.asList(p2, p4));
        cat3.getProdutos().addAll(Arrays.asList(p5, p6));
        cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProdutos().addAll(Arrays.asList(p8));
        cat6.getProdutos().addAll(Arrays.asList(p9, p10));
        cat7.getProdutos().addAll(Arrays.asList(p11));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));
        p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p4.getCategorias().addAll(Arrays.asList(cat2));
        p5.getCategorias().addAll(Arrays.asList(cat3));
        p6.getCategorias().addAll(Arrays.asList(cat3));
        p7.getCategorias().addAll(Arrays.asList(cat4));
        p8.getCategorias().addAll(Arrays.asList(cat5));
        p9.getCategorias().addAll(Arrays.asList(cat6));
        p10.getCategorias().addAll(Arrays.asList(cat6));
        p11.getCategorias().addAll(Arrays.asList(cat7));

        /*Estado*/

        Estado est1 = new Estado("Minas Gerais");
        Estado est2 = new Estado("São Paulo");

        /*Cidade*/
        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null,"São Paulo",est2);
        Cidade c3 = new Cidade(null,"Campinas",est2);

        /*Cliente*/
        Cliente cli1 = new Cliente(null,
                "Victor Lucas",
                "victorlucas.ao02@gmail.com",
                "03172859207",
                TipoCliente.PESSOAFISICA,
                bCryptPasswordEncoder.encode("123")); //Senha vai pro banco já encodada.
        cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));

        Cliente cli2 = new Cliente(null,
                "Evandro Junior",
                "admin@center92.com",
                "64257293000170",
                TipoCliente.PESSOAFISICA,
                bCryptPasswordEncoder.encode("123"));
        cli2.getTelefones().addAll(Arrays.asList("12863323","98838393"));
        cli2.addPerfil(Perfil.ADMIN);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2,c3));

        /*Endereço*/
        Endereco e1 = new Endereco(null, "Rua Flores","300","Apto 203","Jardim","38220834",cli1,c1);
        Endereco e2 = new Endereco(null, "Avenida Matos","150","Sala 800","Centro","38777012",cli1,c2);
        Endereco e3 = new Endereco(null, "Avenida Brasil","24","próximo à parada de ônibus","Centro","38977012",cli2,c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
        cli2.getEnderecos().addAll(Arrays.asList(e3));

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
        p3.getItens().addAll(Arrays.asList(ip2) );

        /*Repositórios*/
        categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
        estadoRepository.saveAll(Arrays.asList(est1,est2));
        cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
        clienteRepository.saveAll(Arrays.asList(cli1, cli2));
        enderecoRepository.saveAll(Arrays.asList(e1,e2, e3));
        pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
        itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
    }
}
