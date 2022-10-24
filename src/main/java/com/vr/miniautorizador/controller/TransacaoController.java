package com.vr.miniautorizador.controller;

import com.vr.miniautorizador.dto.TransacaoDTO;
import com.vr.miniautorizador.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoRepository repository;

    @PostMapping
    public ResponseEntity<TransacaoDTO> efetivarTransacao(@Valid @RequestBody TransacaoDTO transacaoDTO) {
        return new ResponseEntity<TransacaoDTO>(transacaoDTO, HttpStatus.CREATED);
    }
}
