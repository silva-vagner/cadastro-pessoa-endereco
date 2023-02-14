package com.example.testepessoas.controller;

import com.example.testepessoas.DTOs.EnderecoDTO;
import com.example.testepessoas.DTOs.NovoEnderecoDTO;
import com.example.testepessoas.controllers.EnderecoController;
import com.example.testepessoas.enums.TipoEndereco;
import com.example.testepessoas.models.Endereco;
import com.example.testepessoas.service.EnderecoService;
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
public class EnderecoControllerTest {
    private MockMvc mockMvc;

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private EnderecoController enderecoController;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(enderecoController).build();
    }

    @Test
    void shouldCreateEndereco() throws Exception {
        var novoEnderecoDTO = new NovoEnderecoDTO(enderecoBuilder());
        var enderecoDTO = new EnderecoDTO(enderecoBuilder());
        String jSonBody = new ObjectMapper().writeValueAsString(novoEnderecoDTO);
        when(enderecoService.salvarEndereco(any(novoEnderecoDTO.getClass()), anyInt())).thenReturn(enderecoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/enderecos?pessoa_id=1")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(jSonBody))
                .andExpect(status().isCreated());

        verify(enderecoService, times(1)).salvarEndereco(any(NovoEnderecoDTO.class), anyInt());
    }

    @Test
    void shouldGetEnderecos() throws Exception {
        var enderecosDTOList = List.of(new EnderecoDTO(enderecoBuilder()));
        String jSonBody = new ObjectMapper().writeValueAsString(enderecosDTOList);

        when(enderecoService.buscarTodosEnderecos()).thenReturn(enderecosDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/enderecos")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(jSonBody))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSetEnderecoAsPrincipalByEnderecoIdAndPessoaId() throws Exception {
        var enderecoDTO = new EnderecoDTO(enderecoBuilder());
        String jSonBody = new ObjectMapper().writeValueAsString(enderecoDTO);

        lenient().when(enderecoService.definirEnderecoPrincipal(anyInt(), anyInt())).thenReturn(enderecoDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/enderecos?endereco_id=1&pessoa_id=1")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(jSonBody))
                .andExpect(status().isOk());
    }

    private Endereco enderecoBuilder(){
        return new Endereco(1, "Rua Teste", "123456", 10, "Cidade", TipoEndereco.PRINCIPAL);
    }
}
