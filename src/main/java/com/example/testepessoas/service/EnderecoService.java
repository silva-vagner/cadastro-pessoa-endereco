package com.example.testepessoas.service;

import com.example.testepessoas.DTOs.EnderecoDTO;
import com.example.testepessoas.DTOs.NovoEnderecoDTO;
import com.example.testepessoas.enums.TipoEndereco;
import com.example.testepessoas.models.Endereco;
import com.example.testepessoas.repositories.EnderecoRepository;
import com.example.testepessoas.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EnderecoService {
    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    PessoaService pessoaService;

    @Transactional
    public EnderecoDTO salvarEndereco(NovoEnderecoDTO enderecoDTO, Integer pessoaId){
        var pessoa = pessoaService.buscarPessoaPorId(pessoaId).toPessoaModel();
        if(pessoa != null){
            var novoEndereco = enderecoDTO.toEnderecoModel();
            var enderecos = pessoa.getEnderecos();
            var antigosEnderecosIdsList = enderecos.stream().map(Endereco::getId).toList();
            enderecos.add(novoEndereco);
            pessoa.setEnderecos(enderecos);
            var novaPessoa = pessoaRepository.save(pessoa);
            var enderecoInserido = novaPessoa.getEnderecos()
                    .stream()
                    .filter(endereco -> !antigosEnderecosIdsList.contains(endereco.getId()))
                    .findFirst()
                    .orElse(null);
            if(enderecoInserido != null){
                return new EnderecoDTO(enderecoInserido);
            }
        }
        return null;
    }

    @Transactional
    public List<EnderecoDTO> buscarTodosEnderecos(){
        var enderecosList = enderecoRepository.findAll();
        var enderecosDTOList = enderecosList
                .stream()
                .map(EnderecoDTO::new)
                .toList();
        return enderecosDTOList;
    }

    public EnderecoDTO definirEnderecoPrincipal(Integer enderecoId, Integer pessoaId) {
        var pessoa = pessoaService.buscarPessoaPorId(pessoaId).toPessoaModel();
        var enderecosList = pessoa.getEnderecos();
        enderecosList.forEach(endereco -> {
            if(endereco.getId() == enderecoId){
                endereco.setTipoEndereco(TipoEndereco.PRINCIPAL);
            }else{
                endereco.setTipoEndereco(TipoEndereco.ALTERNATIVO);
            }
        });
        var enderecoPrincipal = enderecosList
                .stream()
                .filter(endereco -> endereco.getTipoEndereco().equals(TipoEndereco.PRINCIPAL))
                .findFirst().orElseGet(null);

        enderecoRepository.deleteAll(pessoa.getEnderecos());
        pessoa.setEnderecos(enderecosList);
        pessoaRepository.save(pessoa);

        return new EnderecoDTO(enderecoPrincipal);
    }
}
