package com.example.testepessoas.DTOs;

import com.example.testepessoas.enums.TipoEndereco;
import com.example.testepessoas.models.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class NovoEnderecoDTO {
    @JsonProperty("logradouro")
    @NotNull
    @NotEmpty
    private String logradouro;

    @JsonProperty("cep")
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]{8}", message = "CEP inválido.")
    private String cep;

    @JsonProperty("numero")
    @Min(value = 0)
    private int numero;

    @JsonProperty("cidade")
    @NotNull
    @NotEmpty
    private String cidade;

    @JsonProperty("tipo_endereco")
    @NotNull
    private TipoEndereco tipoEndereco;

    public NovoEnderecoDTO(){};

    public NovoEnderecoDTO(Endereco endereco){
        this.logradouro = endereco.getLogradouro();
        this.cep = endereco.getCEP();;
        this.numero = endereco.getNumero();
        this.cidade = endereco.getCidade();
        this.tipoEndereco = endereco.getTipoEndereco();
    };

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
        return new Endereco(null, logradouro, cep, numero, cidade, tipoEndereco);
    }
}
