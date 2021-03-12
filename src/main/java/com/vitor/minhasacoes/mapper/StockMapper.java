package com.vitor.minhasacoes.mapper;


import com.vitor.minhasacoes.dto.StockDTO;

import com.vitor.minhasacoes.entity.Stock;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface StockMapper {
    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    Stock toModel(StockDTO stockDTO);

    @Mapping(source = "carteira.id", target = "carteiraId")
    StockDTO toDTO(Stock stock);
}
