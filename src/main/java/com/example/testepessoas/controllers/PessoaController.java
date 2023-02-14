package com.example.testepessoas.controllers;

import com.example.testepessoas.DTOs.NovaPessoaDTO;
import com.example.testepessoas.DTOs.PessoaDTO;
import com.example.testepessoas.repositories.PessoaRepository;
import com.example.testepessoas.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@Schema(description = "Pessoas")
@Tag(name = "Pessoas")
public class PessoaController {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    PessoaService pessoaService;

    @GetMapping
    @Operation(description = "Busca todas as pessoas")
    public ResponseEntity<List<PessoaDTO>> buscarTodasPessoas(){
        var pessoas = pessoaService.buscarTodasPessoas();
        if(pessoas != null){
            return new ResponseEntity<>(pessoas, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/busca-por-id")
    @Operation(description = "Busca pessoa por id")
    public ResponseEntity<PessoaDTO> buscarPessoa(@RequestParam(name = "id") Integer id){
        var pessoa = pessoaService.buscarPessoaPorId(id);
        if(pessoa != null){
            return new ResponseEntity<>(pessoa, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Operation(description = "Cria uma nova pessoa")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> criarPessoa(@RequestBody @Valid NovaPessoaDTO novaPessoaDTO){
        var pessoa = pessoaService.salvarPessoa(novaPessoaDTO);
        if(pessoa != null){
            return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping
    @Operation(description = "Edita uma pessoa")
    public ResponseEntity<?> editarPessoa(@RequestBody PessoaDTO pessoaDTO,
                                          @RequestParam(name = "id") Integer pessoaId){
        var pessoa = pessoaService.editarPessoa(pessoaDTO, pessoaId);
        if(pessoa != null){
            return new ResponseEntity<>(pessoa, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
