package com.vitor.minhasacoes.repository;

import com.vitor.minhasacoes.entity.Carteira;
import com.vitor.minhasacoes.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByTicker(String ticker);
}
