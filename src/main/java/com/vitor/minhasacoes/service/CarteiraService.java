package com.vitor.minhasacoes.service;

import com.vitor.minhasacoes.dto.CarteiraDTO;
import com.vitor.minhasacoes.dto.StockDTO;
import com.vitor.minhasacoes.entity.Carteira;
import com.vitor.minhasacoes.entity.Stock;
import com.vitor.minhasacoes.exception.CarteiraJaExisteException;
import com.vitor.minhasacoes.exception.CarteiraNaoEncontradaException;
import com.vitor.minhasacoes.mapper.CarteiraMapper;
import com.vitor.minhasacoes.mapper.StockMapper;
import com.vitor.minhasacoes.repository.CarteiraRepository;
import com.vitor.minhasacoes.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarteiraService {

    @Autowired
    CarteiraRepository carteiraRepository;

    @Autowired
    StockRepository stockRepository;

    CarteiraMapper carteiraMapper = CarteiraMapper.INSTANCE;
    StockMapper stockMapper = StockMapper.INSTANCE;

    public CarteiraDTO criarCarteira(CarteiraDTO carteiraDTO) throws CarteiraJaExisteException {
        carteiraJaCadastrada(carteiraDTO.getNome());
        Carteira carteira = carteiraMapper.toModel(carteiraDTO);
        Carteira carteiraSalva = carteiraRepository.save(carteira);

        return carteiraMapper.toDTO(carteiraSalva);
    }

    public StockDTO addStock(StockDTO stockDTO) throws CarteiraNaoEncontradaException {
        Optional<Carteira> carteira = carteiraRepository.findById(stockDTO.getCarteiraId());
        if(carteira.isPresent()){
            Stock stock = new Stock();
            stock.setTicker(stockDTO.getTicker());
            stock.setCarteira(carteira.get());
            Stock stockSalvo = stockRepository.save(stock);
            return stockMapper.toDTO(stockSalvo);
        }
        throw new CarteiraNaoEncontradaException(stockDTO.getCarteiraId());
    }

    public CarteiraDTO findByNome(String nome) throws CarteiraNaoEncontradaException {
        Carteira carteira = carteiraRepository.findByNome(nome)
                .orElseThrow(() -> new CarteiraNaoEncontradaException(nome));

        return carteiraMapper.toDTO(carteira);
    }

    public List<CarteiraDTO> listaTodas(){
        List<Carteira> carteira =  carteiraRepository.findAll();
        return carteira.stream()
               .map(_carteira -> {
                   CarteiraDTO carteiraDTO = new CarteiraDTO();
                   carteiraDTO.setId(_carteira.getId());
                   carteiraDTO.setNome(_carteira.getNome());
                   List<StockDTO> stokes = _carteira.getStokes().stream().map(stockMapper::toDTO).collect(Collectors.toList());
                   carteiraDTO.setStocks(stokes);
                   return carteiraDTO;
               }).collect(Collectors.toList());
    }

    public void deletaPorId(Long id) throws CarteiraNaoEncontradaException {
        verificaSeExistePorId(id);
        carteiraRepository.deleteById(id);
    }

    private Carteira verificaSeExistePorId(Long id) throws CarteiraNaoEncontradaException {
        return carteiraRepository.findById(id)
                .orElseThrow(() -> new CarteiraNaoEncontradaException(id));
    }

    private void carteiraJaCadastrada(String nome) throws CarteiraJaExisteException {
        Optional<Carteira> carteira = carteiraRepository.findByNome(nome);
        if (carteira.isPresent())
            throw new CarteiraJaExisteException(nome);
    }


    public void deleteById(Long id) throws CarteiraNaoEncontradaException {
        verificaSeExistePorId(id);
        carteiraRepository.deleteById(id);
    }
}
