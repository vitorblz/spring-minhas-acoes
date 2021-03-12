package com.vitor.minhasacoes.controller;

import com.vitor.minhasacoes.builder.CarteiraDTOBuilder;
import com.vitor.minhasacoes.dto.CarteiraDTO;
import com.vitor.minhasacoes.exception.CarteiraNaoEncontradaException;
import com.vitor.minhasacoes.service.CarteiraService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static org.hamcrest.core.Is.is;

import static com.vitor.minhasacoes.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CarteiraControllerTest {

    private static final long VALID_CARTEIRA_ID = 1L;
    private static final long INVALID_CARTEIRA_ID = 2L;

    private MockMvc mockMvc;

    private static final String API_URL = "/api/v1/cateiras";

    @Mock
    private CarteiraService carteiraService;

    @InjectMocks
    private CarteiraController carteiraController;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(carteiraController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPostIsCalledThenACarteiraIsCreated() throws Exception {
        // given
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();

        //when
        when(carteiraService.criarCarteira(carteiraDTO)).thenReturn(carteiraDTO);

        mockMvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(carteiraDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is(carteiraDTO.getNome())));

    }

    @Test
    void whenPostIsCalledWithoutNomeThenAnErrorIsReturned() throws Exception {
        // given
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();
        carteiraDTO.setNome(null);

        mockMvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(carteiraDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenGETIsCalledWithValidCarteiraThenOkStatusIsReturned() throws Exception {
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();

        when(carteiraService.findByNome(carteiraDTO.getNome())).thenReturn(carteiraDTO);

        mockMvc.perform(get(API_URL + "/" + carteiraDTO.getNome())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(carteiraDTO.getNome())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredThenNotFoundStatusIsReturned() throws Exception {
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();

        when(carteiraService.findByNome(carteiraDTO.getNome())).thenThrow(CarteiraNaoEncontradaException.class);

        mockMvc.perform(get(API_URL + "/" + carteiraDTO.getNome())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListaDeCarteirasForChamadaStatusOkEhRetornado() throws Exception {
        CarteiraDTO carteiraDTO = CarteiraDTOBuilder.builder().build().toCarteiraDTO();

        when(carteiraService.listaTodas()).thenReturn(Collections.singletonList(carteiraDTO));

        mockMvc.perform(get(API_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is(carteiraDTO.getNome())));
    }

    @Test
    void whenDELETEEhChamadoComIDValidoNoContentEhRetornado() throws Exception {
        doNothing().when(carteiraService).deleteById(VALID_CARTEIRA_ID);

        mockMvc.perform(delete(API_URL + "/" + VALID_CARTEIRA_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(carteiraService, times(1)).deleteById(VALID_CARTEIRA_ID);
    }

    @Test
    void whenDELETEEhChamadoComIdInvalidoThenNotFoundStatusEhRetornado() throws Exception {
        doThrow(CarteiraNaoEncontradaException.class).when(carteiraService).deleteById(INVALID_CARTEIRA_ID);

        mockMvc.perform(delete(API_URL + "/" + INVALID_CARTEIRA_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
