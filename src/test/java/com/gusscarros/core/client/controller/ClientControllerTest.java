package com.gusscarros.core.client.controller;

import com.gusscarros.core.client.dto.ClientPostDto;
import com.gusscarros.core.client.dto.ClientPutDto;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.service.ClientService;
import com.gusscarros.core.endereco.infra.AdressInfra;
import com.gusscarros.core.endereco.model.Adress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ClientController clientController;

    @Mock
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    AdressInfra adressInfra;

    private ClientPostDto clientPostDto;
    private Adress adress;

    @BeforeEach
    public void setup(){

        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        adress = Adress.builder()
                .cep("03245110")
                .numero("277")
                .build();

        clientPostDto = ClientPostDto.builder()
                .name("Ferreira")
                .adress(adress)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();


    }


    @Test
    void save() throws Exception {

        Mockito.when(clientService.saveClient(clientPostDto))
                .thenReturn(clientPostDto);
        Mockito.when(clientRepository.existsByCpf(Mockito.any()))
                .thenReturn(true);
        Mockito.when(adressInfra.validationAdress(clientPostDto.getAdress()))
                .thenReturn(clientPostDto.getAdress());
        var json = """
                {
                    "name": "Gustavo Ferreira Alves",
                    "cpf": "56040769025",
                    "birthDate": "2003-11-12T02:00:00.000Z",
                    "creditCard": "5245759559334078",
                    "gender": "MASCULINO",
                    "status": true,
                    "adress": {
                        "cep": "03245-110",
                        "numero": "285"
                    }
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void getAll() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findByCpf() {
    }

    @Test
    void update() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void delete() {
    }
}