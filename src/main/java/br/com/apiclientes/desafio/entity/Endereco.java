package br.com.apiclientes.desafio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String logradouro;
    @Column(nullable = false)
    @NotBlank
    private String cep;
    @Column(nullable = false)
    @NotBlank
    private String numero;
    @Column(nullable = false)
    @NotBlank
    private String cidade;
    @ManyToOne
    @JoinColumn(name = "cliente")
    @JsonBackReference
    private Cliente cliente;
    @Column(name = "endereco_principal")
    private boolean principal;


    public String toString(){
        return "ID : " + this.getId() +
                ", Logradouro : " + this.getLogradouro() +
                ", N°  : " + this.getNumero() +
                ", CEP: " + this.getCep() +
                ", Cidade: " + this.getCidade()+
                ", Pricipal: "+this.isPrincipal();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(getId(), endereco.getId()) &&
                Objects.equals(getLogradouro(), endereco.getLogradouro()) &&
                Objects.equals(getCep(), endereco.getCep()) &&
                Objects.equals(getNumero(), endereco.getNumero()) &&
                Objects.equals(getCidade(), endereco.getCidade()) &&
                Objects.equals(getCliente(), endereco.getCliente()) &&
                isPrincipal() == endereco.isPrincipal();
    }



    @Override
    public int hashCode() {
        return Objects.hash(getCliente(), getId(), getLogradouro(), getCep(), getNumero(), getCidade());
    }





}