package com.vitor.minhasacoes.repository;

import com.vitor.minhasacoes.entity.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    Optional<Carteira> findByNome(String nome);
}
