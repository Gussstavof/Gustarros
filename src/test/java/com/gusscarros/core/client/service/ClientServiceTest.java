package com.gusscarros.core.client.service;

import com.gusscarros.core.client.dto.*;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
public class ClientServiceTest {
    // I'll fix these tests!!!!!!!!

    @InjectMocks
    ClientService clientService;

    @Mock
    AdressInfra adressInfra;

    @Mock
    ClientRepository repository;

    @Mock
    Mapper mapper;

    private ClientDto clientDto;

    private ClientDto clientSave;

    private ClientDto clientPutDto;

    private ClientPatchDto clientPatchDto;

    private Client client;

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
        clientDto = ClientDto.builder()
                .name("Ferreira")
                .adress(adress)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();
        clientPutDto = ClientDto.builder()
                .name("Gustavo")
                .adress(adressUpdate)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5339474401406150")
                .gender("masculino")
                .build();
        client = Client.builder()
                .id("1")
                .name("Gustavo")
                .adress(adress)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();

        clientPatchDto = ClientPatchDto.builder()
                .cpf("56040769025")
                .status(false)
                .build();

        clientSave = clientService.saveClient(clientDto);
    }

    @Test
    public void saveClientTest(){
        clientService.saveClient(clientDto);
        when(repository.save(client))
                .thenReturn(Mockito.any());
        when(adressInfra.validationAdress(adress))
                .thenReturn(adress);
        when(mapper.toClientDto(client))
                .thenReturn(clientDto);
        when(mapper.toClient(clientDto))
                .thenReturn(client);

        assertSame( clientService.saveClient(clientDto)
                , clientDto);
    }


    @Test
    public void getAllStatusTrueTest(){
         var clients = Collections.singletonList(client);
         var clientsDto = Collections.singletonList(clientDto);

        when(repository.findByStatusTrue())
                .thenReturn(clients);
        when(mapper.convertListDto(clients))
                .thenReturn(clientsDto);
       assertTrue(clientService.allClient().size()>0);
    }

    @Test
    public void getByCpfTest(){
       var cpf = "56040769025";

       when(repository.findByCpf(cpf))
               .thenReturn(Optional.ofNullable(client));
       when(mapper.toClientDto(client))
               .thenReturn(clientDto);
        var clientCpf = clientService.searchCpf("56040769025");


        assertEquals(clientCpf.getName(), "Ferreira");
    }

    @Test
    public void getByCpfNotFoundTest(){
        when(repository.findByCpf(Mockito.any()))
                .thenThrow(new ExceptionNotFound("CPF not found"));

        assertThrows(ExceptionNotFound.class,() -> clientService.searchCpf("00000000000"));
    }

    @Test
    public void getByNameTest(){
        var clients = Collections.singletonList(mapper.toClient(clientDto));
        var clientsDto = Collections.singletonList(clientDto);

        when(repository.findByNameContains("Fer")).
                thenReturn(clients);
        when(mapper.convertListDto(clients))
               .thenReturn(clientsDto);

        var clientName = clientService.searchName("Fer");

        assertTrue(clientName.contains(clientDto));
    }

    @Test
    public void getByNameNotFound(){
        when(repository.findByCpf(Mockito.any()))
                .thenThrow(new ExceptionNotFound("Name not found"));

        assertThrows(ExceptionNotFound.class,() -> clientService.searchName("Gus"));
    }

    @Test
    public void updateClientTest(){

        when(repository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(client));
        when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client));
        when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());
        when(adressInfra.validationAdress(adressUpdate))
                .thenReturn(adressUpdate);
        when(mapper.toClientDto(client))
                .thenReturn(clientPutDto);

        var clientUpdate = clientService.clientUpdate(clientPutDto, "56040769025");

        var name = clientUpdate.getName();

        assertSame("Gustavo", clientUpdate.getName());
    }

    @Test
    public void updateStatusTest(){

        doReturn(Optional.ofNullable(client))
                .when(repository)
                .findByCpf("56040769025");
        when(repository.save(Mockito.any()))
                .thenReturn(client);
        doReturn(clientPatchDto)
                .when(mapper)
                .cpfAndStatus(client);

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