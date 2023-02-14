package com.example.testepessoas.service;

import com.example.testepessoas.DTOs.NovoEnderecoDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {
    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private EnderecoService enderecoService;


    @Test
    void deveSalvarEndereco() {
        var pessoa = buildPessoa();
        var pessoaDTO = new PessoaDTO(pessoa);
        var novoEnderecoDTO = new NovoEnderecoDTO(buildEndereco());

        when(pessoaRepository.save(any())).thenReturn(pessoa);
        when(pessoaService.buscarPessoaPorId(anyInt())).thenReturn(pessoaDTO);
        enderecoService.salvarEndereco(novoEnderecoDTO, pessoa.getId());

        verify(pessoaService, times(1)).buscarPessoaPorId(pessoa.getId());
        verify(pessoaRepository, times(1)).save(any());
    }

    @Test
    void deveBuscarEnderecos() {
        var enderecoList = buildEnderecoList();

        when(enderecoRepository.findAll()).thenReturn(enderecoList);
        var enderecoDTOList = enderecoService.buscarTodosEnderecos();

        assertEquals(2, enderecoDTOList.size());
    }

    @Test
    void deveDefinirEnderecoComoPrincipalPorEnderecoIdEPessoaId() throws Exception {
        var enderecoList = buildEnderecoList();
        var pessoa = buildPessoa();
        pessoa.setEnderecos(enderecoList);
        var pessoaDTO = new PessoaDTO(pessoa);

        when(pessoaService.buscarPessoaPorId(1)).thenReturn(pessoaDTO);
        var enderecoDTO = enderecoService.definirEnderecoPrincipal(2, 1);

        assertEquals(2, enderecoDTO.getId());
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
