package br.com.apiclientes.desafio.repository;

import br.com.apiclientes.desafio.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}