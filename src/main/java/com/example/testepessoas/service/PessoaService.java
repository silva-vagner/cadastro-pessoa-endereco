package com.example.testepessoas.service;

import com.example.testepessoas.DTOs.NovaPessoaDTO;
import com.example.testepessoas.DTOs.PessoaDTO;
import com.example.testepessoas.models.Pessoa;
import com.example.testepessoas.repositories.EnderecoRepository;
import com.example.testepessoas.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PessoaService {
    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Transactional
    public Pessoa salvarPessoa(NovaPessoaDTO pessoaDTO){
        var pessoa = pessoaDTO.toPessoaModel();
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public List<PessoaDTO> buscarTodasPessoas(){
        var pessoasList = pessoaRepository.findAll();
        var pessoasDTOList = pessoasList
                .stream()
                .map(PessoaDTO::new)
                .toList();
        return pessoasDTOList;
    }

    @Transactional
    public PessoaDTO buscarPessoaPorId(Integer id){
        var pessoa = pessoaRepository.findById(id);
        return pessoa.map(PessoaDTO::new).orElse(null);
    }
    @Transactional
    public Pessoa editarPessoa(PessoaDTO editarPessoaDTO, Integer pessoaId){
        var pessoaAtual = buscarPessoaPorId(pessoaId).toPessoaModel();
        var pessoaEditada = editarPessoaDTO.toPessoaModel();
        if(pessoaAtual != null){
            enderecoRepository.deleteAll(pessoaAtual.getEnderecos());
            pessoaAtual.setEnderecos(pessoaEditada.getEnderecos());
            pessoaAtual.setNome(pessoaEditada.getNome());
            pessoaAtual.setDataNasc(pessoaEditada.getDataNasc());
            return pessoaRepository.save(pessoaAtual);
        }
        return null;
    }


}
