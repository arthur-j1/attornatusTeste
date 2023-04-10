//package br.com.apiclientes.desafio;
//
//import br.com.apiclientes.desafio.controller.ClienteController;
//import br.com.apiclientes.desafio.entity.Cliente;
//import br.com.apiclientes.desafio.entity.Endereco;
//import br.com.apiclientes.desafio.repository.ClienteRepository;
//import br.com.apiclientes.desafio.repository.EnderecoRepository;
//import br.com.apiclientes.desafio.service.ClienteService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//public class ClienteControllerTest {
//
//    @InjectMocks
//    private ClienteController clienteController = new ClienteController();
//
//
//    @Mock
//    private ClienteService clienteService = new ClienteService();
//
//    private Cliente cliente;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        cliente = new Cliente();
//        cliente.setNome("Arthur");
//        cliente.setDataNascimento(LocalDate.of(2002,8,20));
//
//        Endereco endereco = new Endereco();
//        endereco.setLogradouro("rua x");
//        endereco.setCep("12345-678");
//        endereco.setCidade("campina grande");
//        endereco.setNumero("100");
//        endereco.setPrincipal(true);
//        endereco.setId(1L);
//        cliente.adicionarEndereco(endereco);
//
//
//
//    }
//
//    @Test
//    public void testSalvarCliente() {
//        setup();
//        var responde = assertDoesNotThrow(() -> clienteController.saveCliente(cliente));
//
////        clienteController.saveCliente(cliente);
//
//    }
//
//
//
//
//
//}
