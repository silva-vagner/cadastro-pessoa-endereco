package com.example.testepessoas.service;

import com.example.testepessoas.DTOs.PessoaDTO;
import com.example.testepessoas.enums.TipoEndereco;
import com.example.testepessoas.models.Endereco;
import com.example.testepessoas.models.Pessoa;
import com.example.testepessoas.repositories.EnderecoRepository;
import com.example.testepessoas.repositories.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {
    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private PessoaService pessoaService;


    @Test
    void deveBuscarPessoaPorId() {
        var pessoa = buildPessoa();

        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        var pessoaDTO = pessoaService.buscarPessoaPorId(1);

        assertEquals(pessoaDTO.getId(), 1);
    }

    @Test
    void deveEditarPessoa() {
        var pessoaAtual = buildPessoa();
        var dadosPessoa = buildPessoa();
        dadosPessoa.setNome("Nome Editado");
        var pessoaEditadaDTO = new PessoaDTO(dadosPessoa);

        when(pessoaRepository.findById(pessoaAtual.getId())).thenReturn(Optional.of(pessoaAtual));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(dadosPessoa);
        Pessoa pessoaEditada = pessoaService.editarPessoa(pessoaEditadaDTO, 1);

        assertEquals("Nome Editado", pessoaEditada.getNome());
    }

    private Endereco buildEndereco(){
        return new Endereco(1, "Rua Teste", "123456", 10, "Cidade", TipoEndereco.PRINCIPAL);
    }

    private List<Endereco> buildEnderecoList(){
        var endereco1 = new Endereco(1, "Rua 1", "12345", 10, "Cidade 1", TipoEndereco.PRINCIPAL);
        var endereco2 = new Endereco(2, "Rua 2", "54321", 9, "Cidade 2", TipoEndereco.ALTERNATIVO);
        return Arrays.asList(endereco1, endereco2);
    }

    private Pessoa buildPessoa(){
        var enderecos = List.of(buildEndereco());
        return new Pessoa(1, "Nome Pessoa", LocalDate.now(), enderecos);
    }
}
