package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.endereco.model.Adress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientGetDtoTest {

    @InjectMocks
    ClientGetDto clientGetDto;

    private  Adress adress;

    @BeforeEach
    public void setup(){
        Adress adress = Adress.builder()
                .cep("03245110")
                .numero("77")
                .build();
    }

    @Test
    void convertListDto() {
        List<Client> clients = Collections.singletonList(new Client()
                .setName("Gustavo")
                .setAdress(adress)
                .setBirthDate(LocalDate.parse("2003-11-12"))
                .setCpf("56040769025")
                .setCreditCard("5245759559334078")
                .setGender("masculino"));

       var clientsGetdto = ClientGetDto.convertListDto(clients);
        assertEquals(clientsGetdto.set(0, clientGetDto).getCpf(), "560.***.***-**");

    }

    @Test
    void buildClientGetDto(){
        Adress adress = Adress.builder()
                .cep("03245110")
                .numero("77")
                .build();
        clientGetDto = ClientGetDto.builder()
                .name("Gustavo")
                .adress(adress)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();
        Assertions.assertEquals(clientGetDto.getName(), "Gustavo");
    }
}