package com.example.testepessoas.models;

import com.example.testepessoas.enums.TipoEndereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Endereco {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "numero")
    private int numero;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "tipo_endereco")
    @JsonProperty("tipo_endereco")
    private TipoEndereco tipoEndereco;

    public Endereco(Integer id, String logradouro, String cep, int numero, String cidade, TipoEndereco tipoEndereco) {
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.tipoEndereco = tipoEndereco;
    }

    public Endereco(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCEP() {
        return cep;
    }

    public void setCEP(String cep) {
        this.cep = cep;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setTipoEndereco(TipoEndereco tipoEndereco){
        this.tipoEndereco = tipoEndereco;
    }

    public TipoEndereco getTipoEndereco(){
        return tipoEndereco;
    }
}
