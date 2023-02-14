package com.example.testepessoas.controller;

import com.example.testepessoas.DTOs.NovaPessoaDTO;
import com.example.testepessoas.DTOs.PessoaDTO;
import com.example.testepessoas.controllers.PessoaController;
import com.example.testepessoas.enums.TipoEndereco;
import com.example.testepessoas.models.Endereco;
import com.example.testepessoas.models.Pessoa;
import com.example.testepessoas.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class PessoaControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(pessoaController).build();
    }

    @Test
    void shouldCreatePessoa() throws Exception {
        var novaPessoaDTO = new NovaPessoaDTO(buildPessoa());
        var pessoa = buildPessoa();
        String jSonBody = new ObjectMapper().writeValueAsString(novaPessoaDTO);
        when(pessoaService.salvarPessoa(any(novaPessoaDTO.getClass()))).thenReturn(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(jSonBody))
                .andExpect(status().isCreated());

        verify(pessoaService, times(1)).salvarPessoa(any(NovaPessoaDTO.class));
    }

    @Test
    void shouldGetPessoas() throws Exception {
        var pessoasDTOList = List.of(new PessoaDTO(buildPessoa()));
        String jSonBody = new ObjectMapper().writeValueAsString(pessoasDTOList);

        when(pessoaService.buscarTodasPessoas()).thenReturn(pessoasDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/pessoas")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(jSonBody))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetPessoaById() throws Exception {
        var pessoaDTO = new PessoaDTO(buildPessoa());
        String jSonBody = new ObjectMapper().writeValueAsString(pessoaDTO);

        when(pessoaService.buscarPessoaPorId(anyInt())).thenReturn(pessoaDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/pessoas/busca-por-id?id=1")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(jSonBody))
                .andExpect(status().isOk());
    }

    @Test
    void shouldEditPessoaById() throws Exception {
        var pessoaDTO = new PessoaDTO(buildPessoa());
        String jSonBody = new ObjectMapper().writeValueAsString(pessoaDTO);

        lenient().when(pessoaService.editarPessoa(any(), anyInt())).thenReturn(buildPessoa());

        mockMvc.perform(MockMvcRequestBuilders.get("/pessoas?id=1")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(jSonBody))
                .andExpect(status().isOk());
    }

    private Pessoa buildPessoa(){
        var endereco = new Endereco(1, "Rua Teste", "12345678", 10, "Cidade", TipoEndereco.PRINCIPAL);
        return new Pessoa(1, "nome", LocalDate.now(), List.of(endereco));
    }
}
