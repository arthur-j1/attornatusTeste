package br.com.apiclientes.desafio;

import br.com.apiclientes.desafio.entity.Cliente;
import br.com.apiclientes.desafio.entity.Endereco;
import br.com.apiclientes.desafio.exceptions.ClienteNotFoundException;
import br.com.apiclientes.desafio.exceptions.EnderecoNotFoundException;
import br.com.apiclientes.desafio.repository.ClienteRepository;
import br.com.apiclientes.desafio.repository.EnderecoRepository;
import br.com.apiclientes.desafio.service.ClienteService;
import br.com.apiclientes.desafio.service.EnderecoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EnderecoServiceTest {

    @TestConfiguration
    static class ClienteServiceTestConfiguration {
        @Bean
        public ClienteService clienteService() {
            return new ClienteService();
        }
        @Bean
        public EnderecoService enderecoService() {
            return new EnderecoService();
        }
    }


    @Autowired
    ClienteService clienteService;
    @Autowired
    EnderecoService enderecoService;
    @MockBean
    @Autowired
    ClienteRepository clienteRepository;
    @MockBean
    @Autowired
    EnderecoRepository enderecoRepository;

    @Before
    public void setup() {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("rua rio de janeiro");
        endereco.setCep("00000-000");
        endereco.setCidade("campina grande");
        endereco.setNumero("000");
        endereco.setPrincipal(true);
        endereco.setId(1L);

        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("rua x");
        endereco2.setCep("12345-678");
        endereco2.setCidade("campina grande");
        endereco2.setNumero("100");
        endereco2.setPrincipal(false);
        endereco2.setId(2L);

        Cliente cliente = new Cliente();
        cliente.setNome("davi");
        cliente.setDataNascimento(LocalDate.of(2002, 8, 20));
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        enderecos.add(endereco2);
        cliente.setEnderecos(enderecos);
        endereco.setCliente(cliente);
        endereco2.setCliente(cliente);
        clienteRepository.save(cliente);

        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Mockito.when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        Mockito.when(enderecoRepository.findById(2L)).thenReturn(Optional.of(endereco2));

        Mockito.when(enderecoRepository.findAll()).thenReturn(List.of(endereco,endereco2));

    }

    @Test
    public void testFindById(){
        Endereco enderecoBanco = enderecoService.findById(1L);

        assertEquals("00000-000", enderecoBanco.getCep());
        assertEquals("rua rio de janeiro", enderecoBanco.getLogradouro());
        assertEquals("campina grande", enderecoBanco.getCidade());
        assertEquals("000", enderecoBanco.getNumero());
        assertEquals(true, enderecoBanco.isPrincipal());
    }
    @Test
    public void testFindByIdEnderecoInexistente() {

        Mockito.when(enderecoRepository.findById(5L)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(EnderecoNotFoundException.class, () -> {
            enderecoService.findById(5L);
        });

        String expectedMessage = "Endereço com o ID "+ 5L +" não encontrado!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindAll(){
        List<Endereco> enderecos = enderecoService.findAll();

        assertEquals(enderecos.get(0).getLogradouro(),"rua rio de janeiro");
        assertEquals(enderecos.get(0).getCidade(),"campina grande");
        assertEquals(enderecos.get(0).getNumero(),"000");
        assertEquals(enderecos.get(0).getCep(),"00000-000");
        assertTrue(enderecos.get(0).isPrincipal());
        assertEquals(enderecos.get(1).getLogradouro(),"rua x");
        assertEquals(enderecos.get(1).getCidade(),"campina grande");
        assertEquals(enderecos.get(1).getNumero(),"100");
        assertEquals(enderecos.get(1).getCep(),"12345-678");
        assertFalse(enderecos.get(1).isPrincipal());

    }

    @Test
    public void testCreateEndereco(){
        Endereco endereco = new Endereco();
        endereco.setLogradouro("rua a");
        endereco.setCep("87654-321");
        endereco.setCidade("campina grande");
        endereco.setNumero("777");
        endereco.setPrincipal(false);


        Mockito.when(enderecoRepository.save(endereco)).thenReturn(endereco);
        enderecoService.createEndereco(1L,endereco);

        Mockito.verify(enderecoRepository, Mockito.times(1)).save(endereco);
    }

    @Test
    public void testCreateEnderecoParaPrimerioEnderecoDoCliente(){
        Endereco endereco = new Endereco();
        endereco.setLogradouro("rua a");
        endereco.setCep("87654-321");
        endereco.setCidade("campina grande");
        endereco.setNumero("777");
        endereco.setPrincipal(false);

        Cliente cliente = new Cliente();
        cliente.setNome("davi");
        cliente.setDataNascimento(LocalDate.of(2002, 8, 20));
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        cliente.setEnderecos(enderecos);
        endereco.setCliente(cliente);;

        when(clienteRepository.save(cliente)).thenReturn(cliente);
        clienteService.save(cliente);

        Mockito.verify(clienteRepository, Mockito.times(1)).save(cliente);

        when(clienteRepository.findById(5L)).thenReturn(Optional.of(cliente));

        Mockito.when(enderecoRepository.save(endereco)).thenReturn(endereco);
        enderecoService.createEndereco(1L,endereco);

        Mockito.verify(enderecoRepository, Mockito.times(1)).save(endereco);
    }

    @Test
    public void testDeleteEnderecoById(){

        enderecoService.deleteEnderecoById(1L);

        Mockito.verify(enderecoRepository, Mockito.times(1)).deleteById(1L);

    }

//    @Test
//    public void testDeleteEnderecoByIdComIdInexistente(){
//
//        ;
////        when(enderecoRepository.findById(5L)).thenReturn(Optional.empty());
////        enderecoService.deleteEnderecoById(5L);
//
//        Exception exception = Assertions.assertThrows(EnderecoNotFoundException.class, () -> {
//            enderecoService.findById(5L);
//        });
//        String expectedMessage = "Endereço com o ID "+ 5L +" não encontrado!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//
//    }

    @Test
    public void testDeleteEnderecoComAutenticacao() {
        Cliente cliente = clienteRepository.findById(1L).get();

        enderecoService.deleteEnderecoComAutenticacao(1L, 1L);

        Mockito.verify(enderecoRepository, times(1)).deleteById(1L);

        Mockito.when(clienteRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ClienteNotFoundException.class, () -> enderecoService.deleteEnderecoComAutenticacao(2L, 1L));

        Mockito.when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Mockito.when(enderecoRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EnderecoNotFoundException.class, () -> enderecoService.deleteEnderecoComAutenticacao(1L, 3L));
    }






}
