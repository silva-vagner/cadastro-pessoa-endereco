package com.example.testepessoas.controllers;

import com.example.testepessoas.DTOs.EnderecoDTO;
import com.example.testepessoas.DTOs.NovoEnderecoDTO;
import com.example.testepessoas.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@Schema(description = "Enderecos")
@Tag(name = "Endereços")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @GetMapping
    @Operation(description = "Busca todos os endereços")
    public ResponseEntity<List<EnderecoDTO>> buscarTodosEnderecos(){
        var pessoas = enderecoService.buscarTodosEnderecos();
        if(pessoas != null){
            return new ResponseEntity<>(pessoas, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Operation(description = "Cria um novo endereço")
    public ResponseEntity<EnderecoDTO> criarEndereco(@RequestBody NovoEnderecoDTO novoEnderecoDTO,
                                                         @RequestParam(name = "pessoa_id") Integer pessoaId){
        var endereco = enderecoService.salvarEndereco(novoEnderecoDTO, pessoaId);
        if(endereco != null){
            return new ResponseEntity<>(endereco, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping
    @Operation(description = "Define um endereço cadastrado como principal")
    public ResponseEntity<EnderecoDTO> definirEnderecoPrincipal(@RequestParam(name = "endereco_id") Integer enderecoId,
                                                                @RequestParam(name = "pessoa_id") Integer pessoaId){
        var endereco = enderecoService.definirEnderecoPrincipal(enderecoId, pessoaId);
        if(endereco != null){
            return new ResponseEntity<>(endereco, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
