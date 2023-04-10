package br.com.apiclientes.desafio.repository;

import br.com.apiclientes.desafio.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
}