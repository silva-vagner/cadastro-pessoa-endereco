package com.example.testepessoas.DTOs;

import com.example.testepessoas.models.Endereco;
import com.example.testepessoas.models.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PessoaDTO {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("data_nasc")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataNasc;

    @JsonProperty("enderecos")
    private List<EnderecoDTO> enderecos;

    public PessoaDTO(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.dataNasc = pessoa.getDataNasc();
        this.enderecos = pessoa.getEnderecos().stream().map(EnderecoDTO::new).toList();
    };

    public PessoaDTO(Integer id, String nome, LocalDate dataNasc, List<EnderecoDTO> enderecos){
        this.id = id;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.enderecos = enderecos;
    };

    public PessoaDTO(){};

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public Pessoa toPessoaModel() {
        var enderecoModelList = enderecos
                .stream()
                .map(enderecoDTO -> new Endereco(enderecoDTO.getId(), enderecoDTO.getLogradouro(),
                        enderecoDTO.getCep(), enderecoDTO.getNumero(), enderecoDTO.getCidade(),
                        enderecoDTO.getTipoEndereco()))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Pessoa(id, nome, dataNasc, enderecoModelList);
    }
}
