package br.com.apiclientes.desafio.service;


import br.com.apiclientes.desafio.entity.Cliente;
import br.com.apiclientes.desafio.entity.Endereco;
import br.com.apiclientes.desafio.exceptions.ClienteNotFoundException;
import br.com.apiclientes.desafio.exceptions.EnderecoNotFoundException;
import br.com.apiclientes.desafio.repository.ClienteRepository;
import br.com.apiclientes.desafio.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class EnderecoService {


    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ClienteRepository clienteRepository;



    public List<Endereco> findAll(){
        return enderecoRepository.findAll();
    }

    public Endereco findById(Long id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if (endereco.isPresent()){
            return endereco.get();
        }else {
            throw new EnderecoNotFoundException(id);
        }

    }

    public Endereco createEndereco(Long clienteId, Endereco endereco) {
        Optional<Cliente> clienteBanco = clienteRepository.findById(clienteId);
        if (clienteBanco.isPresent()) {
//            if (endereco.getCep().length() != 8){
//                throw new CepInvalidoException();
//            }
            if (clienteBanco.get().getEnderecos() != null){
                clienteBanco.get().adicionarEndereco(endereco);
            }else{
                clienteBanco.get().setEnderecos(List.of(endereco));
            }
            endereco.setCliente(clienteBanco.get());
            return enderecoRepository.save(endereco);
        }else {
            throw new ClienteNotFoundException(clienteId);
        }
    }



    public void deleteEnderecoComAutenticacao(Long clienteId,Long enderecoId){
        Cliente clienteBanco = clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNotFoundException(clienteId));
        Endereco endereco = clienteBanco.getEnderecos().stream().
                filter(endereco1 -> endereco1.getId().equals(enderecoId)).findFirst().
                orElseThrow(() -> new EnderecoNotFoundException(enderecoId));
        enderecoRepository.deleteById(enderecoId);
    }
    public void deleteEnderecoById(Long id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new EnderecoNotFoundException(id));
        enderecoRepository.deleteById(id);
    }


}