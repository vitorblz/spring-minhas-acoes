package com.vitor.minhasacoes.builder;

import com.vitor.minhasacoes.dto.CarteiraDTO;
import com.vitor.minhasacoes.dto.StockDTO;
import com.vitor.minhasacoes.entity.Stock;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class CarteiraDTOBuilder {
    @Builder.Default
    private  Long id = 1l;

    @Builder.Default
    private String nome =  "Principal";

    private List<StockDTO> stocks;

    public CarteiraDTO toCarteiraDTO(){
        return new CarteiraDTO(id,nome,stocks);
    }

}
