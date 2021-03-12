package com.vitor.minhasacoes.controller;

import com.vitor.minhasacoes.dto.CarteiraDTO;

import com.vitor.minhasacoes.dto.StockDTO;
import com.vitor.minhasacoes.exception.CarteiraJaExisteException;
import com.vitor.minhasacoes.exception.CarteiraNaoEncontradaException;
import com.vitor.minhasacoes.service.CarteiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cateiras")
public class CarteiraController {

    @Value("${app.nome}")
    public String appNome;

    @Autowired
    CarteiraService carteiraService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarteiraDTO carteiras(@RequestBody @Valid CarteiraDTO carteriraDTO) throws CarteiraJaExisteException {
        return carteiraService.criarCarteira(carteriraDTO);
    }

    @PostMapping("/stocks")
    @ResponseStatus(HttpStatus.CREATED)
    public StockDTO addStock(@RequestBody @Valid StockDTO stockDTO) throws CarteiraNaoEncontradaException {
        return carteiraService.addStock(stockDTO);
    }

    @GetMapping("/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public CarteiraDTO buscaCarteira(@PathVariable String nome) throws CarteiraNaoEncontradaException {
        CarteiraDTO carteiraDTO = carteiraService.findByNome(nome);
        return carteiraDTO;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CarteiraDTO> buscaCarteiras()  {

        return carteiraService.listaTodas();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws CarteiraNaoEncontradaException {
        carteiraService.deleteById(id);
    }
}
