package com.vr.miniautorizador.controller;

import com.vr.miniautorizador.dto.TransacaoDTO;
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

    @PostMapping
    public ResponseEntity<TransacaoDTO> criarCartao(@Valid @RequestBody TransacaoDTO transacaoDTO) {
        System.out.println(transacaoDTO);
        return new ResponseEntity<TransacaoDTO>(transacaoDTO, HttpStatus.CREATED);
    }
}
