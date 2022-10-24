package com.vr.miniautorizador.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Transacao {

    public Transacao(Cartao cartao, Double valor) {
        this.cartao = cartao;
        this.valor = valor;
        this.dataTransacao = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double valor;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    private LocalDateTime dataTransacao;

}
