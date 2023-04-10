package br.com.apiclientes.desafio.config;

import br.com.apiclientes.desafio.entity.Cliente;
import br.com.apiclientes.desafio.entity.Endereco;
import br.com.apiclientes.desafio.repository.ClienteRepository;
import br.com.apiclientes.desafio.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Profile("local")
@Component
public class LocalConfig implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;


    @Override
    public void run(String... args) throws Exception {
        Endereco endereco1 = new Endereco();
        endereco1.setCep("58414030");
        endereco1.setLogradouro("rua espirito santo");
        endereco1.setCidade("campina grande");
        endereco1.setNumero("686");
        endereco1.setPrincipal(true);

        Endereco endereco2 = new Endereco();
        endereco2.setCep("00000000");
        endereco2.setLogradouro("avenida das figueiras");
        endereco2.setCidade("campina grande");
        endereco2.setNumero("100");
        endereco2.setPrincipal(false);

        Endereco endereco3 = new Endereco();
        endereco3.setCep("10101100");
        endereco3.setLogradouro("rua dos cascalhos");
        endereco3.setCidade("campina grande");
        endereco3.setNumero("79");
        endereco3.setPrincipal(false);

        Endereco endereco4 = new Endereco();
        endereco4.setCep("50710390");
        endereco4.setLogradouro("rua sao paulo");
        endereco4.setCidade("campina grande");
        endereco4.setNumero("301");
        endereco4.setPrincipal(true);

        Cliente cliente1 = new Cliente();
        cliente1.setNome("Arthur");
        cliente1.setDataNascimento(LocalDate.of(2002, 8, 20));
        cliente1.setEnderecos(List.of(endereco1, endereco2));

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Gabriel");
        cliente2.setDataNascimento(LocalDate.of(1990, 7, 10));
        cliente2.setEnderecos(List.of(endereco3, endereco4));

        endereco1.setCliente(cliente1);
        endereco2.setCliente(cliente1);
        endereco3.setCliente(cliente2);
        endereco4.setCliente(cliente2);

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        enderecoRepository.saveAll(List.of(endereco1,endereco2,endereco3,endereco4));

    }
}
