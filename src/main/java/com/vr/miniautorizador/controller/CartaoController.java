package com.vr.miniautorizador.controller;

import com.vr.miniautorizador.dto.CartaoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("cartoes")
public class CartaoController {

    @PostMapping
    public ResponseEntity<CartaoDTO> criarCartao(@Valid @RequestBody CartaoDTO cartaoDTO) {
        System.out.println(cartaoDTO);
        return new ResponseEntity<CartaoDTO>(cartaoDTO, HttpStatus.CREATED);
    }

}
