package com.vr.miniautorizador.dto;

import com.vr.miniautorizador.model.Cartao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartaoDTO {

    public CartaoDTO(Cartao cartao) {
        this.numero = cartao.getNumero();
        this.senha = cartao.getSenha();
    }

    @NotBlank(message = "Necessário informar o número do cartão")
    private String numero;

    @NotBlank(message = "Necessário informar a senha do cartão")
    private String senha;

    public Cartao gerarCartaoInicial() {
        return new Cartao(numero, senha, 500D);
    }

}
