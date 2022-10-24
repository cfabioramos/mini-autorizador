package com.vr.miniautorizador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransacaoDTO {

    @NotBlank(message = "Necessário informar o número do cartão")
    private String numeroCartao;

    @NotBlank(message = "Necessário informar a senha do cartão")
    private String senhaCartao;

    @NotNull(message = "Necessário informar o valor da transação")
    private Double valor;

}
