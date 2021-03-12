package com.vitor.minhasacoes.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockDTO {

    private Long id;

    @NotNull
    @Size(min=1, max=50)
    private String ticker;

    @NotNull
    private Long carteiraId;
}
