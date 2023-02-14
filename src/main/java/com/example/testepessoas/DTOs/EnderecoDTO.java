package com.example.testepessoas.DTOs;

import com.example.testepessoas.enums.TipoEndereco;
import com.example.testepessoas.models.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

public class EnderecoDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("logradouro")
    private String logradouro;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("numero")
    private int numero;

    @JsonProperty("cidade")
    private String cidade;

    @JsonProperty("tipo_endereco")
    private TipoEndereco tipoEndereco;

    public EnderecoDTO(){};

    public EnderecoDTO(Endereco endereco){
        this.id = endereco.getId();
        this.logradouro = endereco.getLogradouro();
        this.cep = endereco.getCEP();;
        this.numero = endereco.getNumero();
        this.cidade = endereco.getCidade();
        this.tipoEndereco = endereco.getTipoEndereco();
    };

    public EnderecoDTO(Integer id, String logradouro, String cep, int numero, String cidade, TipoEndereco tipoEndereco){
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.tipoEndereco = tipoEndereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
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

    public TipoEndereco getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(TipoEndereco tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    public Endereco toEnderecoModel(){
        return new Endereco(id, logradouro, cep, numero, cidade, tipoEndereco);
    }
}
