package com.vitor.minhasacoes.service;

import com.vitor.minhasacoes.builder.CarteiraDTOBuilder;
import com.vitor.minhasacoes.builder.StockDTOBuilder;
import com.vitor.minhasacoes.dto.CarteiraDTO;
import com.vitor.minhasacoes.dto.StockDTO;
import com.vitor.minhasacoes.entity.Carteira;
import com.vitor.minhasacoes.entity.Stock;
import com.vitor.minhasacoes.exception.CarteiraJaExisteException;
import com.vitor.minhasacoes.exception.CarteiraNaoEncontradaException;
import com.vitor.minhasacoes.mapper.CarteiraMapper;
import com.vitor.minhasacoes.mapper.StockMapper;
import com.vitor.minhasacoes.repository.CarteiraRepository;

import static org.junit.jupiter.api.Assertions.*;

import com.vitor.minhasacoes.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarteiraServiceTest {

    @Mock
    private CarteiraRepository carteiraRepository;

    @Mock
    private StockRepository stockRepository;

    private CarteiraMapper carteiraMapper = CarteiraMapper.INSTANCE;
    private StockMapper stockMapper = StockMapper.INSTANCE;

    @InjectMocks
    private CarteiraService carteiraService;


    @Test
    void whenCarteiraInformedItShouldBeCreated() throws CarteiraJaExisteException {
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();
        Carteira expectedCarteiraSalva = carteiraMapper.toModel(carteiraDTO);

        when(carteiraRepository.save(expectedCarteiraSalva)).thenReturn(expectedCarteiraSalva);

        CarteiraDTO carteiraSalva = carteiraService.criarCarteira(carteiraDTO);

        assertThat(carteiraDTO.getId(),is(equalTo(carteiraSalva.getId())));
        assertThat(carteiraDTO.getNome(), is(equalTo(carteiraSalva.getNome())));
    }

    @Test
    void whenStockInformedItShouldBeCreated() throws CarteiraNaoEncontradaException {
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();
        Carteira expectedCarteira = carteiraMapper.toModel(carteiraDTO);

        StockDTO stockDto = StockDTOBuilder.builder().build().toStockDTO();
        Stock expectedStock = stockMapper.toModel(stockDto);

        when(carteiraRepository.findById(carteiraDTO.getId())).thenReturn(Optional.of(expectedCarteira));
        when(stockRepository.save(expectedStock)).thenReturn(expectedStock);

        StockDTO stockSalvoDTO = carteiraService.addStock(stockDto);

        assertThat(stockDto.getId(),is(equalTo(stockSalvoDTO.getId())));
        assertThat(stockDto.getTicker(), is(equalTo(stockSalvoDTO.getTicker())));
        assertThat(stockDto.getCarteiraId(), is(equalTo(stockSalvoDTO.getCarteiraId())));
    }

    @Test
    void whenCarteiraAlreadyCreatedExceptionShouldBeThrown(){
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();
        Carteira duplicatedCarteira = carteiraMapper.toModel(carteiraDTO);

        when(carteiraRepository.findByNome(carteiraDTO.getNome())).thenReturn(Optional.of(duplicatedCarteira));

        assertThrows(CarteiraJaExisteException.class, () -> carteiraService.criarCarteira(carteiraDTO));
    }

    @Test
    void whenValidCarteiraNameIsGivenThenReturnACarteira() throws CarteiraNaoEncontradaException {
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();
        Carteira carteiraExpected = carteiraMapper.toModel(carteiraDTO);

        when(carteiraRepository.findByNome(carteiraDTO.getNome())).thenReturn(Optional.of(carteiraExpected));

        CarteiraDTO carteiraEncontrada = carteiraService.findByNome(carteiraDTO.getNome());

        assertThat(carteiraEncontrada, is(equalTo(carteiraDTO)));
    }

    @Test
    void whenNotRegisteredCarteiraIsGivenThenThrowAnException() {
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();

        when(carteiraRepository.findByNome(carteiraDTO.getNome())).thenReturn(Optional.empty());

        assertThrows(CarteiraNaoEncontradaException.class, () -> carteiraService.findByNome(carteiraDTO.getNome()));
    }

    @Test
    void whenListagemCarteirasEhChamadaThenRetornaUmaListaDeCarteiras(){
        // given
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();
        Carteira carteiraExpected = carteiraMapper.toModel(carteiraDTO);

        // when
        when(carteiraRepository.findAll()).thenReturn(Collections.singletonList(carteiraExpected));

        // then
        List<CarteiraDTO> carteiraDTOList = carteiraService.listaTodas();

        assertThat(carteiraDTOList, is(not(empty())));
        assertThat(carteiraDTOList.get(0), is(equalTo(carteiraDTO)));

    }

    @Test
    void whenListagemCarteirasEhChamadaThenRetornaNenhumaCarteira(){
        // when
        when(carteiraRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        // then
        List<CarteiraDTO> carteiraDTOList = carteiraService.listaTodas();

        assertThat(carteiraDTOList, is(empty()));
    }

    @Test
    void whenExclusaoEhChamadaThenUmaCarteiraDeveSerRemovida() throws CarteiraNaoEncontradaException{
        // given
        CarteiraDTO carteiraDeletadaDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();
        Carteira carteiraDeletadaExpected = carteiraMapper.toModel(carteiraDeletadaDTO);
        // when
        when(carteiraRepository.findById(carteiraDeletadaDTO.getId())).thenReturn(Optional.of(carteiraDeletadaExpected));
        doNothing().when(carteiraRepository).deleteById(carteiraDeletadaExpected.getId());

        carteiraService.deleteById(carteiraDeletadaDTO.getId());

        verify(carteiraRepository, times(1)).findById(carteiraDeletadaDTO.getId());
        verify(carteiraRepository, times(1)).deleteById(carteiraDeletadaDTO.getId());
    }



}
