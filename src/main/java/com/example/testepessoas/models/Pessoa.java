package com.example.testepessoas.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Pessoa {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "nome")
    private String nome;


    @Column(name = "data_nasc")
    @JsonProperty("data_nasc")
    private LocalDate dataNasc;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="pessoa_id", nullable=false)
    private List<Endereco> enderecos;

    public Pessoa(Integer id, String nome, LocalDate dataNasc, List<Endereco> enderecos) {
        this.id = id;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.enderecos = enderecos;
    }

    public Pessoa(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
