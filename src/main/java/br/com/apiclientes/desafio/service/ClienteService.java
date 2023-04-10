package br.com.apiclientes.desafio.service;


import br.com.apiclientes.desafio.entity.Cliente;
import br.com.apiclientes.desafio.entity.Endereco;
import br.com.apiclientes.desafio.exceptions.ClienteNotFoundException;
import br.com.apiclientes.desafio.exceptions.EnderecoNotFoundException;
import br.com.apiclientes.desafio.repository.ClienteRepository;
import br.com.apiclientes.desafio.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.List;

@Service
public class  ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente save(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente findById(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()){
            return cliente.get();
        }else{
            throw new ClienteNotFoundException(id);
        }
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }



    public List<Endereco> getEnderecos(Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) {

            return cliente.get().getEnderecos();

        }else{
            throw new ClienteNotFoundException(clienteId);
        }
    }

    public Endereco getEnderecoPrincipal(Long clienteId){
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()){
            return cliente.get().consultaEnderecoPrincipal();
        }else{
            throw new ClienteNotFoundException(clienteId);
        }

    }
    public Endereco setEnderecoPrincipal(Long clienteId, Long enderecoId) {
        Optional<Cliente> clienteBanco = clienteRepository.findById(clienteId);
        if (clienteBanco.isPresent()) {
            Endereco enderecoSelecionado = clienteBanco.get().getEnderecos().stream().
                    filter(endereco -> endereco.getId().equals(enderecoId)).findFirst().
                    orElseThrow(() -> new EnderecoNotFoundException(enderecoId));

            clienteBanco.get().getEnderecos().forEach(endereco -> endereco.setPrincipal(false));
            enderecoSelecionado.setPrincipal(true);
            enderecoRepository.save(enderecoSelecionado);
            return clienteRepository.findById(clienteId).get().consultaEnderecoPrincipal();

        }else{
            throw new ClienteNotFoundException(clienteId);
        }
    }

    public Cliente updateCliente(Long id,Cliente clienteAtualizado){
        Cliente clienteBanco = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));

        if (clienteAtualizado.getNome() != null){
            clienteBanco.setNome(clienteAtualizado.getNome());
        }if (clienteAtualizado.getDataNascimento() != null){
            clienteBanco.setDataNascimento(clienteAtualizado.getDataNascimento());
        }if (clienteAtualizado.getEnderecos() != null){
            for(Endereco endereco : clienteAtualizado.getEnderecos()){
                clienteBanco.adicionarEndereco(endereco);
                endereco.setCliente(clienteBanco);
                enderecoRepository.save(endereco);
            }
        }
        return clienteRepository.save(clienteBanco);
    }


    public void deleteClienteById(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
        clienteRepository.deleteById(id);
    }

    public Endereco findEnderecoById(Long clienteId, Long enderecoId){
        Cliente cliente = clienteRepository.findById(clienteId).
                orElseThrow(() -> new ClienteNotFoundException(clienteId));
        Endereco endereco = cliente.getEnderecos().stream()
                .filter(enderecoLista -> enderecoLista.getId().equals(enderecoId)).findFirst()
                .orElseThrow(() -> new EnderecoNotFoundException(enderecoId));
        return endereco;
    }
    
}
