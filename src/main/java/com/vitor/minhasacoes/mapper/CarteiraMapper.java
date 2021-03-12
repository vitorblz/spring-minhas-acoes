package com.vitor.minhasacoes.mapper;

import com.vitor.minhasacoes.dto.CarteiraDTO;
import com.vitor.minhasacoes.entity.Carteira;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(uses = StockMapper.class)
public interface CarteiraMapper {
    CarteiraMapper INSTANCE = Mappers.getMapper(CarteiraMapper.class);

    Carteira toModel(CarteiraDTO carteiraDTO);
    CarteiraDTO toDTO(Carteira carteira);
}
