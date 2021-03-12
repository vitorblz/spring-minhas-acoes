package com.vitor.minhasacoes.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarteiraNaoEncontradaException extends Exception{

    public CarteiraNaoEncontradaException(String nome){
        super(String.format("Carteira %s não encontrada",nome));
    }

    public CarteiraNaoEncontradaException(Long id) {
        super(String.format("Carteira com id %s não foi encontrada.", id));
    }

}
