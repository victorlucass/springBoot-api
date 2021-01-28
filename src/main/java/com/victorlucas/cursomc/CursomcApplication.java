package com.victorlucas.cursomc;

import com.victorlucas.cursomc.domain.*;
import com.victorlucas.cursomc.domain.enums.TipoCliente;
import com.victorlucas.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

    @Override
    public void run(String... args) throws Exception {

        /*Categorias*/
        Categoria cat1 = new Categoria("Informática");
        Categoria cat2 = new Categoria( "Escritório");
        categoriaRepository.saveAll(Arrays.asList(cat1,cat2));

        /*Produtos*/
        Produto p1 = new Produto( "Computador",2000.00);
        Produto p2 = new Produto("Impressora",800.00);
        Produto p3 = new Produto("Mouse",80.00);
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3));

        /*Categorias de Produtos*/
        cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));

        /*Estado*/

        Estado est1 = new Estado("Minas Gerais");
        Estado est2 = new Estado("São Paulo");
        estadoRepository.saveAll(Arrays.asList(est1,est2));

        /*Cidade*/
        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null,"São Paulo",est2);
        Cidade c3 = new Cidade(null,"Campinas",est2);
        cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));

        /*Cliente*/
        Cliente cli1 = new Cliente(null, "Maria Silva","maria@gmail.com","36378912377", TipoCliente.PESSOAFISICA);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2,c3));
        cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));

        /*Endereço*/
        Endereco e1 = new Endereco(null, "Rua Flores","300","Apto 203","Jardim","38220834",cli1,c1);
        Endereco e2 = new Endereco(null, "Avenida Matos","150","Sala 800","Centro","38777012",cli1,c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1,e2));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1,e2));
        

    }
}
