package br.com.apiclientes.desafio.controller;


import br.com.apiclientes.desafio.entity.Cliente;
import br.com.apiclientes.desafio.entity.Endereco;
import br.com.apiclientes.desafio.service.ClienteService;
import br.com.apiclientes.desafio.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


//Optei por não criar o metodo de atualizar o endereço do cliente,
//ao inves disso fazer o processo de excluir o endereço e criar um novo
@RestController
public class ClienteController {


    @Autowired
    private  ClienteService clienteService;
    @Autowired
    private EnderecoService enderecoService;


    @GetMapping("/clientes")//Aprovado
    public ResponseEntity<List<Cliente>> getAllClientes()   {
        return ResponseEntity.ok(clienteService.findAll());
    }


    @GetMapping("/enderecos")//Aprovado
    public ResponseEntity<List<Endereco>> getAllEnderecos()   {
        return ResponseEntity.ok(enderecoService.findAll());
    }


    @GetMapping("/cliente/{id}")//Aprovado
    public ResponseEntity<Cliente> findClienteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }


    @GetMapping("endereco/{id}")//Aprovado
    public ResponseEntity<Endereco> getEndereco(@PathVariable Long id){
        return ResponseEntity.ok(enderecoService.findById(id));
    }


    @GetMapping("/{clienteId}/enderecos")//Aprovado
    public ResponseEntity<List<Endereco>> getEnderecosClienteById(@PathVariable("clienteId") Long id) {
        return ResponseEntity.ok(clienteService.getEnderecos(id));
    }


    @GetMapping("/{clienteId}/buscar-endereco/{enderecoId}")//Aprovado
    public ResponseEntity<Endereco> getClienteEndereco(@PathVariable Long clienteId, @PathVariable Long enderecoId){
        return ResponseEntity.ok(clienteService.findEnderecoById(clienteId, enderecoId));
    }

    //Essa função busca no banco de dados um endereco com id informado associado ao cliente,
    //caso não haja, retorna o erro EndrecoNotFound ou ClienteNotFound se não tiver nenhum cliente com o id passado
    @GetMapping("/{clienteId}/endereco/principal")//Aprovado
    public ResponseEntity<Endereco> getEnderecoPrincipal(@PathVariable("clienteId") Long id) {
        return ResponseEntity.ok(clienteService.getEnderecoPrincipal(id));
    }


    @PostMapping("/")//Aprovado
    public ResponseEntity<Cliente> saveCliente(@RequestBody @Valid Cliente cliente) {
        return ResponseEntity.ok(clienteService.save(cliente));
    }


    @PostMapping("/{clienteId}/endereco")//Aprovado
    public ResponseEntity<Endereco> saveEndereco(@PathVariable("clienteId") Long id, @RequestBody @Valid Endereco endereco) {
        return ResponseEntity.ok(enderecoService.createEndereco(id, endereco));
    }

    @PostMapping("/{clienteId}/endereco-principal/{enderecoId}")//Aprovado
    public ResponseEntity<Endereco> setEnderecoPrincipal(@PathVariable("clienteId") Long clienteId, @PathVariable Long enderecoId){
        return ResponseEntity.ok(clienteService.setEnderecoPrincipal(clienteId,enderecoId));
    }


    //Decidi não utilizar a anotação @Valid para caso queira atualizar somente um campo do cliente não precisar preencher todos os outros campos
    //E a validação será feita na função usada da classe clienteService
    @PutMapping("/atualizar/{id}")//Aprovado
    public ResponseEntity<Cliente> updateCliente(@PathVariable("id") Long id,
                                                     @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.updateCliente(id, cliente));
    }


    @DeleteMapping("/{id}")//aprovado
    public ResponseEntity<Void> deleteClienteById(@PathVariable Long id) {
        clienteService.deleteClienteById(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{clienteId}delete-endereco/{enderecoId}")//Aprovado
    public ResponseEntity<Void> deleteEnderecoComAutenticacao(@PathVariable Long clienteId, @PathVariable Long enderecoId) {
        enderecoService.deleteEnderecoComAutenticacao(clienteId,enderecoId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete-endereco/{enderecoId}")//Aprovado
    public ResponseEntity<Void> deleteEnderecoById(@PathVariable Long enderecoId){
        enderecoService.deleteEnderecoById(enderecoId);
        return ResponseEntity.noContent().build();
    }



}