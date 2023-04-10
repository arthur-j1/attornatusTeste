package br.com.apiclientes.desafio;

import br.com.apiclientes.desafio.entity.Cliente;
import br.com.apiclientes.desafio.entity.Endereco;
import br.com.apiclientes.desafio.exceptions.ClienteNotFoundException;
import br.com.apiclientes.desafio.repository.ClienteRepository;
import br.com.apiclientes.desafio.repository.EnderecoRepository;
import br.com.apiclientes.desafio.service.ClienteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
//@DataJpaTest
public class ClienteServiceTest {

    @TestConfiguration
    static class ClienteServiceTestConfiguration {
        @Bean
        public ClienteService clienteService() {
            return new ClienteService();
        }
    }


    @Autowired
    ClienteService clienteService;
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
        enderecoRepository.save(endereco);


        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("rua x");
        endereco2.setCep("12345-678");
        endereco2.setCidade("campina grande");
        endereco2.setNumero("100");
        endereco2.setPrincipal(true);
        endereco2.setId(2L);
        enderecoRepository.save(endereco2);

        Endereco endereco3 = new Endereco();
        endereco3.setLogradouro("rua a");
        endereco3.setCep("87654-321");
        endereco3.setCidade("campina grande");
        endereco3.setNumero("777");
        endereco3.setPrincipal(false);
        endereco3.setId(3L);
        enderecoRepository.save(endereco3);




        Cliente cliente = new Cliente();
        cliente.setNome("davi");
        cliente.setDataNascimento(LocalDate.of(2002, 8, 20));

        List<Endereco> enderecosCliente1 = new ArrayList<>();
        enderecosCliente1.add(endereco);
        enderecosCliente1.add(endereco3);
        cliente.setEnderecos(enderecosCliente1);
        endereco.setCliente(cliente);
        endereco3.setCliente(cliente);
        clienteRepository.save(cliente);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("fulano");
        cliente2.setDataNascimento(LocalDate.of(1990, 4, 15));
        cliente2.setEnderecos(List.of(endereco2));

        endereco2.setCliente(cliente2);
        clienteRepository.save(cliente2);

        Cliente cliente3 = new Cliente();
        cliente3.setNome("cliente3");
        cliente3.setDataNascimento(LocalDate.of(2000, 3, 3));
        cliente3.setEnderecos(new ArrayList<Endereco>());
        clienteRepository.save(cliente3);

        Cliente cliente4 = new Cliente();
        cliente4.setNome("cliente4");
        cliente4.setDataNascimento(LocalDate.of(1995, 2, 2));
        cliente4.setEnderecos(new ArrayList<Endereco>());
        clienteRepository.save(cliente4);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.findById(2L)).thenReturn(Optional.of(cliente2));
        when(clienteRepository.findById(3L)).thenReturn(Optional.of(cliente3));
        when(clienteRepository.findById(4L)).thenReturn(Optional.of(cliente4));
        when(clienteRepository.findAll()).thenReturn(List.of(cliente,cliente2,cliente3,cliente4));

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(enderecoRepository.findById(2L)).thenReturn(Optional.of(endereco2));
        when(enderecoRepository.findById(3L)).thenReturn(Optional.of(endereco3));

    }


    @Test
//    @Transactional
    public void testFindById() {
        Cliente resultado = clienteService.findById(1L);


        assertTrue(resultado.getEnderecos().size() == 2);
        assertEquals("davi", resultado.getNome());
        assertEquals("00000-000", resultado.getEnderecos().get(0).getCep());
        assertEquals("rua rio de janeiro", resultado.getEnderecos().get(0).getLogradouro());
        assertEquals("campina grande", resultado.getEnderecos().get(0).getCidade());
        assertEquals("000", resultado.getEnderecos().get(0).getNumero());;
        assertEquals(LocalDate.of(2002, 8, 20),resultado.getDataNascimento());

    }

    @Test
    public void testFindByIdInexistente() {

        when(clienteRepository.findById(5L)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.findById(5L);
        });

        String expectedMessage = "Cliente com o ID "+ 5L +" não encontrado!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindAll() {

        List<Cliente> clientes = clienteService.findAll();

        assertEquals(4, clientes.size());

        Cliente cliente1 = clientes.get(0);
        assertEquals("davi", cliente1.getNome());
        assertEquals(LocalDate.of(2002, 8, 20), cliente1.getDataNascimento());
        assertEquals(2, cliente1.getEnderecos().size());

        Cliente cliente2 = clientes.get(1);
        assertEquals("fulano", cliente2.getNome());
        assertEquals(LocalDate.of(1990, 4, 15), cliente2.getDataNascimento());
        assertEquals(1, cliente2.getEnderecos().size());

        Cliente cliente3 = clientes.get(2);
        assertEquals("cliente3", cliente3.getNome());
        assertEquals(LocalDate.of(2000, 3, 3), cliente3.getDataNascimento());
        assertEquals(0, cliente3.getEnderecos().size());

        Cliente cliente4 = clientes.get(3);
        assertEquals("cliente4", cliente4.getNome());
        assertEquals(LocalDate.of(1995, 2, 2), cliente4.getDataNascimento());
        assertEquals(0, cliente4.getEnderecos().size());
    }

    @Test
    public void testGetEnderecoPrincipal(){
        Endereco endereco1 =  clienteService.getEnderecoPrincipal(1L);

        Endereco endereco = new Endereco();
        endereco.setLogradouro("rua rio de janeiro");
        endereco.setCep("00000-000");
        endereco.setCidade("campina grande");
        endereco.setNumero("000");
        endereco.setPrincipal(true);


        assertEquals(endereco1.getCep(),endereco.getCep());
        assertEquals(endereco1.getCidade(),endereco.getCidade());
        assertEquals(endereco1.getLogradouro(),endereco.getLogradouro());
        assertEquals(endereco1.getNumero(),endereco.getNumero());

    }

    @Test
    public void testSave () {

        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua 1");
        endereco.setCidade("São Paulo");
        endereco.setPrincipal(true);
        cliente.setEnderecos(List.of(endereco));

        when(clienteRepository.save(cliente)).thenReturn(cliente);
        clienteService.save(cliente);

        Mockito.verify(clienteRepository, Mockito.times(1)).save(cliente);
    }

    @Test
    public void testGetEnderecos(){
        List<Endereco> enderecos = clienteService.getEnderecos(1L);

        Endereco endereco = new Endereco();
        endereco.setLogradouro("rua rio de janeiro");
        endereco.setCep("00000-000");
        endereco.setCidade("campina grande");
        endereco.setNumero("000");
        endereco.setPrincipal(true);

        Endereco endereco1 = new Endereco();
        endereco1.setLogradouro("rua a");
        endereco1.setCep("87654-321");
        endereco1.setCidade("campina grande");
        endereco1.setNumero("777");
        endereco1.setPrincipal(false);

        assertEquals(enderecos.size(), 2);
        assertEquals(enderecos.get(0).getNumero(),endereco.getNumero());
        assertEquals(enderecos.get(0).getCep(),endereco.getCep());
        assertEquals(enderecos.get(0).getCidade(),endereco.getCidade());
        assertEquals(enderecos.get(0).getLogradouro(),endereco.getLogradouro());
        assertEquals(enderecos.get(1).getNumero(),endereco1.getNumero());
        assertEquals(enderecos.get(1).getCep(),endereco1.getCep());
        assertEquals(enderecos.get(1).getCidade(),endereco1.getCidade());
        assertEquals(enderecos.get(1).getLogradouro(),endereco1.getLogradouro());

    }

    @Test
    public void testAtualizarClienteSemEndereco(){
        Cliente clienteBanco = clienteService.findById(1L);
        Endereco enderecoAntigo = new Endereco();
        enderecoAntigo.setLogradouro("rua rio de janeiro");
        enderecoAntigo.setCep("00000-000");
        enderecoAntigo.setCidade("campina grande");
        enderecoAntigo.setNumero("000");
        enderecoAntigo.setPrincipal(true);

        Endereco enderecoAntigo1 = new Endereco();
        enderecoAntigo1.setLogradouro("rua a");
        enderecoAntigo1.setCep("87654-321");
        enderecoAntigo1.setCidade("campina grande");
        enderecoAntigo1.setNumero("777");
        enderecoAntigo1.setPrincipal(false);

        Cliente cliente = new Cliente();
        cliente.setNome("arthur");
        cliente.setDataNascimento(LocalDate.of(2010,10,10));
        System.out.println(clienteBanco);


        clienteService.updateCliente(1L,cliente);
        Cliente clienteAtualizado = clienteService.findById(1L);
        System.out.println(clienteBanco);
        System.out.println(clienteAtualizado);

        assertEquals(clienteAtualizado.getNome(),cliente.getNome());
        assertEquals(clienteAtualizado.getDataNascimento(),cliente.getDataNascimento());
        assertEquals(clienteAtualizado.getEnderecos().get(0).getNumero(),enderecoAntigo.getNumero());
        assertEquals(clienteAtualizado.getEnderecos().get(0).getCep(),enderecoAntigo.getCep());
        assertEquals(clienteAtualizado.getEnderecos().get(0).getCidade(),enderecoAntigo.getCidade());
        assertEquals(clienteAtualizado.getEnderecos().get(0).getLogradouro(),enderecoAntigo.getLogradouro());
        assertEquals(clienteAtualizado.getEnderecos().get(1).getNumero(),enderecoAntigo1.getNumero());
        assertEquals(clienteAtualizado.getEnderecos().get(1).getCep(),enderecoAntigo1.getCep());
        assertEquals(clienteAtualizado.getEnderecos().get(1).getCidade(),enderecoAntigo1.getCidade());
        assertEquals(clienteAtualizado.getEnderecos().get(1).getLogradouro(),enderecoAntigo1.getLogradouro());

    }

    @Test
    public void testAtualizarClienteComEndereco(){
        Endereco enderecoNovo = new Endereco();
        enderecoNovo.setLogradouro("rua b");
        enderecoNovo.setCep("11111-111");
        enderecoNovo.setCidade("campina grande");
        enderecoNovo.setNumero("111");
        enderecoNovo.setPrincipal(true);

        Cliente cliente = new Cliente();
        cliente.setNome("arthur");
        cliente.setDataNascimento(LocalDate.of(2010,10,10));
        cliente.setEnderecos(List.of(enderecoNovo));
        enderecoNovo.setCliente(cliente);

       clienteService.updateCliente(1L,cliente);

        Cliente clienteAtualizado = clienteService.findById(1L);

        Endereco enderecoAtualizado = clienteService.findById(1L)
                .getEnderecos().stream().filter(endereco -> endereco.getLogradouro()=="rua b").findFirst().get();

        assertEquals(clienteAtualizado.getNome(),cliente.getNome());
        assertEquals(clienteAtualizado.getDataNascimento(),cliente.getDataNascimento());
        assertTrue(enderecoAtualizado.equals(enderecoNovo));


    }
    @Test
    public void testSetEnderecoPrincipal(){
        Cliente cliente = clienteService.findById(1L);
        assertTrue(cliente.getEnderecos().get(0).isPrincipal());
        System.out.println(cliente);


        clienteService.setEnderecoPrincipal(1L,3L);

        cliente = clienteService.findById(1L);

        assertFalse(cliente.getEnderecos().get(0).isPrincipal());
        assertTrue(cliente.getEnderecos().get(1).isPrincipal());
    }
    @Test
    public void testGetEnderecosClienteNotFound() {
        Long clienteId = 1L;

        Mockito.when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ClienteNotFoundException.class, () -> clienteService.getEnderecos(clienteId));
    }

    @Test
    public void testFindEnderecoById(){
        Endereco enderecoBanco = clienteService.findEnderecoById(1L,1L);

        Endereco enderecoEsperado = new Endereco();
        enderecoEsperado.setLogradouro("rua rio de janeiro");
        enderecoEsperado.setCep("00000-000");
        enderecoEsperado.setCidade("campina grande");
        enderecoEsperado.setNumero("000");
        enderecoEsperado.setPrincipal(true);

        assertEquals(enderecoEsperado.getCep(),enderecoBanco.getCep());
        assertEquals(enderecoEsperado.getLogradouro(),enderecoBanco.getLogradouro());
        assertEquals(enderecoEsperado.getCidade(),enderecoBanco.getCidade());
        assertEquals(enderecoEsperado.getLogradouro(),enderecoBanco.getLogradouro());
        assertTrue(enderecoBanco.isPrincipal());
    }

    @Test
    public void testDeleteById(){
        clienteService.deleteClienteById(1L);

        Mockito.verify(clienteRepository, Mockito.times(1)).deleteById(1L);
    }

}
