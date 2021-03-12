package com.vitor.minhasacoes.builder;

import com.vitor.minhasacoes.dto.StockDTO;
import lombok.Builder;

@Builder
public class StockDTOBuilder {
    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String ticker =  "ITUB4";

    private final Long carteiraId = 1L;

    public StockDTO toStockDTO(){
        return new StockDTO(id,ticker,carteiraId);
    }

}
