package com.vitor.minhasacoes.dto;

import com.vitor.minhasacoes.entity.Stock;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarteiraDTO {

    private Long id;

    @NotNull
    @Size(min=1, max=50)
    private String nome;

    private List<StockDTO> stocks;
}
