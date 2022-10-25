package com.vr.miniautorizador.repository;

import com.vr.miniautorizador.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query(value = "LOCK TABLES transacao WRITE, cartao WRITE", nativeQuery = true)
    @Modifying
    void lockTables();

    @Query(value = "UNLOCK TABLES", nativeQuery = true)
    @Modifying
    void releaseTables();

}
