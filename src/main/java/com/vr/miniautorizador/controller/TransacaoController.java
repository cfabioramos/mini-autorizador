package com.vr.miniautorizador.controller;

import com.vr.miniautorizador.dto.TransacaoDTO;
import com.vr.miniautorizador.model.Cartao;
import com.vr.miniautorizador.repository.CartaoRepository;
import com.vr.miniautorizador.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<TransacaoDTO> efetivarTransacao(@Valid @RequestBody TransacaoDTO transacaoDTO) {

        Cartao cartaoModel = validaDadosCartao(transacaoDTO);

        validaSaldoCartao(transacaoDTO, cartaoModel);

        // Aqui obtenho o lock exclusivo de escrita de ambas as tabelas (transacao e cartao)
        /*
            Outra regra que pode ser adicionada
            para garantir que duas transações disparadas ao mesmo tempo
            não causem problemas relacionados à concorrência é um insert select
            na tabela transacao
         */
        repository.lockTables();

        repository.save(transacaoDTO.gerarTransacao(cartaoModel, transacaoDTO.getValor()));

        debitaSaldoCartao(transacaoDTO, cartaoModel);

        repository.releaseTables();

        return new ResponseEntity<TransacaoDTO>(transacaoDTO, HttpStatus.CREATED);
    }

    private Cartao validaDadosCartao(TransacaoDTO transacaoDTO) {
        Optional<Cartao> cartaoOptional =
                cartaoRepository.findByNumero(transacaoDTO.getNumeroCartao());
        Cartao cartaoModel = cartaoOptional.orElseThrow(() ->
                new IllegalArgumentException("CARTAO_INEXISTENTE"));

        cartaoOptional = cartaoRepository.findByNumeroAndSenha(
                transacaoDTO.getNumeroCartao(), transacaoDTO.getSenhaCartao());
        cartaoModel = cartaoOptional.orElseThrow(() ->
                new IllegalArgumentException("SENHA_INVALIDA"));
        return cartaoModel;
    }

    private void validaSaldoCartao(TransacaoDTO transacaoDTO, Cartao cartaoModel) {
        if (cartaoModel.getSaldo() < transacaoDTO.getValor()) {
            throw new IllegalArgumentException("SALDO_INSUFICIENTE");
        }
    }

    private void debitaSaldoCartao(TransacaoDTO transacaoDTO, Cartao cartaoModel) {
        cartaoModel.setSaldo(cartaoModel.getSaldo() - transacaoDTO.getValor());
        cartaoRepository.save(cartaoModel);
    }
}
