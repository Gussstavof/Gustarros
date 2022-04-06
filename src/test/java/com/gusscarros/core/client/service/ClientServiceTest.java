package com.gusscarros.core.client.service;

import com.gusscarros.core.client.dto.ClientGetDto;
import com.gusscarros.core.client.dto.ClientPatchDto;
import com.gusscarros.core.client.dto.ClientPostDto;
import com.gusscarros.core.client.dto.ClientPutDto;
import com.gusscarros.core.client.exception.ExceptionNotFound;
import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.endereco.infra.AdressInfra;
import com.gusscarros.core.endereco.model.Adress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    AdressInfra adressInfra;

    @Mock
    ClientRepository repository;

    private   ClientPostDto clientPostDto;

    private ClientPutDto clientPutDto;

    private ClientPatchDto clientPatchDto;

    private ClientGetDto clientGetDto;

    private Adress adress;

    private Adress adressUpdate;

    @BeforeEach
    public  void setup(){

        adress = Adress.builder()
                .cep("03245110")
                .numero("277")
                .build();
        adressUpdate = Adress.builder()
                .cep("03220100")
                .numero("90")
                .build();
        clientPostDto = ClientPostDto.builder()
                .name("Ferreira")
                .adress(adress)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();
        clientPutDto = ClientPutDto.builder()
                .adress(adressUpdate)
                .creditCard("5339474401406150")
                .name("Gustavo")
                .build();
    }


    private ClientPostDto clientSave(){
        return  clientService.saveClient(clientPostDto);
    }

    @Test
    public void saveClientTest(){
        when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());
        when(adressInfra.validationAdress(adress))
                .thenReturn(adress);

        assertSame( clientService.saveClient(clientPostDto), clientPostDto);
    }


    @Test
    public void getAllStatusTrueTest(){
         var client = clientSave();

        when(repository.findByStatusTrue()).
                thenReturn(Collections.singletonList(client.build()));

        assertTrue(clientService.allClient().size()>0);
    }

    @Test
    public void getByCpfTest(){
       var client = clientSave();

        when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        var clientCpf = clientService.searchCpf("56040769025");

        assertEquals(clientCpf.getName(), "FERREIRA");
    }

    @Test
    public void getByCpfNotFoundTest(){
        when(repository.findByCpf(Mockito.any()))
                .thenThrow(new ExceptionNotFound("CPF not found"));

        assertThrows(ExceptionNotFound.class,() -> clientService.searchCpf("00000000000"));
    }

    @Test
    public void getByNameTest(){
        var client = clientSave();

        when(repository.findByNameContains(Mockito.any())).
                thenReturn(Collections.singletonList(client.build()));

        var clientName = clientService.searchName("FER");

        assertEquals(clientName.set(0, clientGetDto).getName(), "FERREIRA");
    }

    @Test
    public void getByNameNotFound(){
        when(repository.findByCpf(Mockito.any()))
                .thenThrow(new ExceptionNotFound("Name not found"));

        assertThrows(ExceptionNotFound.class,() -> clientService.searchName("Gus"));
    }

    @Test
    public void updateClientTest(){
        var client = clientSave();

        when(repository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());
        when(adressInfra.validationAdress(adressUpdate))
                .thenReturn(adressUpdate);

        var clientUpdate = clientService.clientUpdate(clientPutDto, "56040769025");

        assertSame("Gustavo", clientUpdate.getName());
    }

    @Test
    public void updateStatusTest(){
        var client = clientSave();

        when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());

        var clientStatus = clientService.clientUpdateStatus(false,"56040769025");

        assertFalse(clientStatus.isStatus());

    }

    @Test
    public void deleteClient(){
        Client client = new Client()
                .setId("1")
                .setName("Gustavo")
                .setAdress(adress)
                .setBirthDate(LocalDate.parse("2003-11-12"))
                .setCpf("56040769025")
                .setCreditCard("5245759559334078")
                .setGender("masculino");
        when(repository.findByCpf(client.getCpf()))
                .thenReturn(Optional.of(client));
        doNothing().when(repository).deleteById(client.getId());


        clientService.clientDelete(client.getCpf());
        verify(repository).deleteById(client.getId());

    }

}