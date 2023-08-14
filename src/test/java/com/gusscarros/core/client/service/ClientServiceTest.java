package com.gusscarros.core.client.service;

import com.gusscarros.core.client.models.request.ClientRequest;
import com.gusscarros.core.client.models.response.ClientResponse;
import com.gusscarros.core.client.exception.NotFoundException;
import com.gusscarros.core.client.models.entity.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.validation.CreateValidation;
import com.gusscarros.core.address.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {
    @InjectMocks
    ClientService clientService;
    @Mock
    ClientRepository repository;
    @Mock
    List<CreateValidation> createValidations;
    ClientRequest clientRequest;
    ClientResponse clientResponse;
    Client client;
    Address address;
    Address addressUpdate;
    Pageable pageable;

    @BeforeEach
    public  void setup(){

        address = Address.builder()
                .cep("03245110")
                .numero("277")
                .build();
        addressUpdate = Address.builder()
                .cep("03220100")
                .numero("90")
                .build();

        client = Client.builder()
                .id("1")
                .name("Gustavo")
                .address(address)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();

        clientRequest = ClientRequest.builder()
                .name("Ferreira")
                .address(address)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();

        clientResponse = ClientResponse.builder()
                .name("Gustavo")
                .address(address)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("560.***.***-**")
                .creditCard("4078")
                .gender("masculino")
                .build();
    }

    @Test
    public void saveClientTest(){
        when(repository.save(clientRequest.toClient()))
                .thenReturn(client);
        doNothing().when(createValidations)
                .forEach(createValidation -> createValidation.validator(clientRequest));

        var result = clientService.saveClient(clientRequest);

        assertEquals("560.***.***-**", result);
    }

    @Test
    public void getAllStatusTrueTest(){
         Page<Client> clients = new PageImpl<>(Collections.singletonList(client));

        when(repository.findByStatusTrue(pageable))
                .thenReturn(clients);

        var result = clientService.allClient(pageable);

        assertInstanceOf(Page.class, result);
        assertTrue(result.stream()
               .anyMatch(element -> element.equals(clientResponse))
        );

    }

    @Test
    public void getByCpfTest(){
       var cpf = "56040769025";

       when(repository.findByCpf(cpf))
               .thenReturn(Optional.ofNullable(client));

        var result = clientService.searchCpf("56040769025");

        assertEquals(clientResponse, result);
    }

    @Test
    public void getByCpfNotFoundTest(){
        when(repository.findByCpf(Mockito.any()))
                .thenThrow(new NotFoundException("CPF not found"));

        assertThrows(NotFoundException.class,
                () -> clientService.searchCpf("00000000000"));
    }

    @Test
    public void getByNameTest(){

        when(repository.findByNameContains("Fer")).
                thenReturn(List.of(client));

        var clientName = clientService.searchName("Fer");

        assertTrue(clientName.contains(clientResponse));
    }

    @Test
    public void getByNameNotFound(){
        when(repository.findByCpf(Mockito.any()))
                .thenThrow(new NotFoundException("Name not found"));

        assertThrows(NotFoundException.class,
                () -> clientService.searchName("Gus"));
    }

    @Test
    public void updateClientTest(){

        var clientPut = ClientRequest.builder()
                .name("Gusstavo")
                .address(addressUpdate)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();

        clientResponse.setName("Gusstavo");
        clientResponse.setAddress(addressUpdate);

        when(repository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(client));
        when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client));
        when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());

        var result = clientService.clientUpdate(clientPut, "56040769025");

        assertEquals(clientResponse, result);
        assertInstanceOf(ClientResponse.class, result);
    }

    @Test
    public void updateStatusTest(){

        doReturn(Optional.ofNullable(client))
                .when(repository)
                .findByCpf("56040769025");
        when(repository.save(Mockito.any()))
                .thenReturn(client);

        var result = clientService.clientUpdateStatus(false,"56040769025");

        assertFalse(result.isStatus());
        assertEquals(clientResponse.getName(), result.getName());
    }

    @Test
    public void deleteClient(){
        Client client = new Client()
                .setId("1")
                .setName("Gustavo")
                .setAddress(address)
                .setBirthDate(LocalDate.parse("2003-11-12"))
                .setCpf("56040769025")
                .setCreditCard("5245759559334078")
                .setGender("masculino");

        when(repository.findByCpf(client.getCpf()))
                .thenReturn(Optional.of(client));
        doNothing()
                .when(repository)
                .deleteById(client.getId());

        clientService.clientDelete(client.getCpf());
        verify(repository).deleteById(client.getId());
    }
}