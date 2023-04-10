package br.com.apiclientes.desafio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnderecoNotFoundException extends RuntimeException{

    public EnderecoNotFoundException(Long id){
        super("Endereço com o ID "+id+" não encontrado!");
    }
}
