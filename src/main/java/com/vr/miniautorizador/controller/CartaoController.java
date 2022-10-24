package com.vr.miniautorizador.controller;

import com.vr.miniautorizador.dto.CartaoDTO;
import com.vr.miniautorizador.model.Cartao;
import com.vr.miniautorizador.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("cartoes")
public class CartaoController {

    @Autowired
    private CartaoRepository repository;

    @PostMapping
    public ResponseEntity<CartaoDTO> criarCartao(@Valid @RequestBody CartaoDTO cartaoDTO) {

        Optional<Cartao> cartaoOptional = repository.findByNumero(cartaoDTO.getNumero());
        if (cartaoOptional.isPresent()) {
            throw new IllegalArgumentException("Número do cartão já existente.");
        }

        repository.save(cartaoDTO.gerarCartaoInicial());
        return new ResponseEntity<CartaoDTO>(cartaoDTO, HttpStatus.CREATED);
    }

    @GetMapping("{numeroCartao}")
    public ResponseEntity<Object> obterSaldoCartao(@PathVariable String numeroCartao) {
        return ResponseEntity.ok().body(numeroCartao);
    }

}
