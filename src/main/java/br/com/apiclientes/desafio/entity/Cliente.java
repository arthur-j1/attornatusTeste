package br.com.apiclientes.desafio.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false,unique = true)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    @NotNull
    private LocalDate dataNascimento;


    @Column()
    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Endereco> enderecos;


    public Endereco consultaEnderecoPrincipal(){
        Endereco enderecoPrincipal = new Endereco();
        for (Endereco enderecoLista: enderecos) {
            if (enderecoLista.isPrincipal()){
                enderecoPrincipal = enderecoLista;
            }
        }
        return enderecoPrincipal;
    }


    public void adicionarEndereco(Endereco endereco){
        this.enderecos.add(endereco);
    }



    @Override
    public String toString(){

        String enderecoStr = this.getEnderecos().stream()
                .map(Endereco::toString)
                .collect(Collectors.joining(", "));

        return "ID : " + this.getId() + ", Nome : " + this.getNome() +
                ", Data de nascimento : " + this.getDataNascimento() +
                ", Endereços : " + enderecoStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cliente cliente = (Cliente) o;
        return id != null && Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
