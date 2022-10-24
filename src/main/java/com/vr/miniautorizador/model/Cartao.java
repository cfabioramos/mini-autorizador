package com.vr.miniautorizador.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(
        name = "CARTAO",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"numero"})
)
public class Cartao {

    public Cartao(String numero, String senha) {
        this.numero = numero;
        this.senha = senha;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String numero;

    private String senha;

    private Double saldo;

}
