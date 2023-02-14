package com.example.testepessoas.DTOs;

import com.example.testepessoas.models.Endereco;
import com.example.testepessoas.models.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;


public class NovaPessoaDTO {

    @JsonProperty("nome")
    @NotNull
    @NotEmpty
    private String nome;

    @JsonProperty("data_nasc")
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNasc;

    @JsonProperty("enderecos")
    @Valid
    private List<NovoEnderecoDTO> enderecos;

    public NovaPessoaDTO(Pessoa pessoa) {
        this.nome = pessoa.getNome();
        this.dataNasc = pessoa.getDataNasc();
        this.enderecos = pessoa.getEnderecos().stream().map(NovoEnderecoDTO::new).toList();
    };

    public NovaPessoaDTO(String nome, LocalDate dataNasc, List<NovoEnderecoDTO> enderecos){
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.enderecos = enderecos;
    };

    public NovaPessoaDTO(){};

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public List<NovoEnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public Pessoa toPessoaModel() {
        var enderecoList = enderecos
                .stream()
                .map(novoEnderecoDTO -> new Endereco(null, novoEnderecoDTO.getLogradouro(),
                        novoEnderecoDTO.getCep(), novoEnderecoDTO.getNumero(), novoEnderecoDTO.getCidade(),
                        novoEnderecoDTO.getTipoEndereco()))
                .toList();
        return new Pessoa(null, nome, dataNasc, enderecoList);
    }
}
