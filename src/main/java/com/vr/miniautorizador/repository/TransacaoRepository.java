package com.vr.miniautorizador.repository;

import com.vr.miniautorizador.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Cartao, Long> {
}
