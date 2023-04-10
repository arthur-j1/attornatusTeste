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

@RestController
public class ClienteController {


    @Autowired
    private  ClienteService clienteService;
    @Autowired
    private EnderecoService enderecoService;


    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes()   {
        return ResponseEntity.ok(clienteService.findAll());
    }


    @GetMapping("/enderecos")
    public ResponseEntity<List<Endereco>> getAllEnderecos()   {
        return ResponseEntity.ok(enderecoService.findAll());
    }


    @GetMapping("/cliente/{id}")
    public ResponseEntity<Cliente> findClienteById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }


    @GetMapping("endereco/{id}")
    public ResponseEntity<Endereco> getEndereco(@PathVariable Long id){
        return ResponseEntity.ok(enderecoService.findById(id));
    }


    @GetMapping("/{clienteId}/enderecos")
    public ResponseEntity<List<Endereco>> getEnderecosClienteById(@PathVariable("clienteId") Long id) {
        return ResponseEntity.ok(clienteService.getEnderecos(id));
    }


    @GetMapping("/{clienteId}/buscar-endereco/{enderecoId}")
    public ResponseEntity<Endereco> getClienteEndereco(@PathVariable Long clienteId, @PathVariable Long enderecoId){
        return ResponseEntity.ok(clienteService.findEnderecoById(clienteId, enderecoId));
    }


    @GetMapping("/{clienteId}/endereco/principal")
    public ResponseEntity<Endereco> getEnderecoPrincipal(@PathVariable("clienteId") Long id) {
        return ResponseEntity.ok(clienteService.getEnderecoPrincipal(id));
    }


    @PostMapping("/")
    public ResponseEntity<Cliente> saveCliente(@RequestBody @Valid Cliente cliente) {
        return ResponseEntity.ok(clienteService.save(cliente));
    }


    @PostMapping("/{clienteId}/endereco")
    public ResponseEntity<Endereco> saveEndereco(@PathVariable("clienteId") Long id, @RequestBody @Valid Endereco endereco) {
        return ResponseEntity.ok(enderecoService.createEndereco(id, endereco));
    }

    @PostMapping("/{clienteId}/endereco-principal/{enderecoId}")/
    public ResponseEntity<Endereco> setEnderecoPrincipal(@PathVariable("clienteId") Long clienteId, @PathVariable Long enderecoId){
        return ResponseEntity.ok(clienteService.setEnderecoPrincipal(clienteId,enderecoId));
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable("id") Long id,
                                                     @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.updateCliente(id, cliente));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClienteById(@PathVariable Long id) {
        clienteService.deleteClienteById(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{clienteId}delete-endereco/{enderecoId}")
    public ResponseEntity<Void> deleteEnderecoComAutenticacao(@PathVariable Long clienteId, @PathVariable Long enderecoId) {
        enderecoService.deleteEnderecoComAutenticacao(clienteId,enderecoId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete-endereco/{enderecoId}")
    public ResponseEntity<Void> deleteEnderecoById(@PathVariable Long enderecoId){
        enderecoService.deleteEnderecoById(enderecoId);
        return ResponseEntity.noContent().build();
    }

}
