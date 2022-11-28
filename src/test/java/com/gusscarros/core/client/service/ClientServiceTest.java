package com.gusscarros.core.client.service;

import com.gusscarros.core.client.dto.*;
import com.gusscarros.core.client.exception.NotFoundException;
import com.gusscarros.core.client.entity.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.validation.CreateValidation;
import com.gusscarros.core.address.infra.AddressInfra;
import com.gusscarros.core.address.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    AddressInfra addressInfra;

    @Mock
    ClientRepository repository;

    @Mock
    Mapper mapper;

    @Mock
    List<CreateValidation> createValidations;

    private ClientDto clientDto;

    private ClientDto clientPutDto;

    private ClientPatchDto clientPatchDto;

    private Client client;

    private Address address;

    private Address addressUpdate;

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
        clientDto = ClientDto.builder()
                .name("Ferreira")
                .address(address)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();
        clientPutDto = ClientDto.builder()
                .name("Gustavo")
                .address(addressUpdate)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5339474401406150")
                .gender("masculino")
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

        clientPatchDto = ClientPatchDto.builder()
                .cpf("56040769025")
                .status(false)
                .build();
    }

    @Test
    public void saveClientTest(){
        clientService.saveClient(clientDto);
        when(repository.save(client))
                .thenReturn(client);
        when(addressInfra.validationAdress(address))
                .thenReturn(address);
        when(mapper.toClientDto(client))
                .thenReturn(clientDto);
        when(mapper.toClient(clientDto))
                .thenReturn(client);
        doNothing().when(createValidations)
                .forEach(createValidation -> createValidation.validator(clientDto));
        var result = clientService.saveClient(clientDto);
        assertSame( result, clientDto);
    }

    @Test
    public void getAllStatusTrueTest(){
         var clients = Collections.singletonList(client);
         var clientsDto = Collections.singletonList(clientDto);

        when(repository.findByStatusTrue())
                .thenReturn(clients);
        when(mapper.convertListDto(clients))
                .thenReturn(clientsDto);

       assertTrue(clientService.allClient().contains(clientDto));
    }

    @Test
    public void getByCpfTest(){
       var cpf = "56040769025";

       when(repository.findByCpf(cpf))
               .thenReturn(Optional.ofNullable(client));
       when(mapper.toClientDto(client))
               .thenReturn(clientDto);

        var clientCpf = clientService.searchCpf("56040769025");

        assertSame(clientCpf, clientDto);
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
                .thenThrow(new NotFoundException("Name not found"));

        assertThrows(NotFoundException.class,
                () -> clientService.searchName("Gus"));
    }

    @Test
    public void updateClientTest(){
        when(repository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(client));
        when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client));
        when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());
        when(addressInfra.validationAdress(addressUpdate))
                .thenReturn(addressUpdate);
        when(mapper.toClientDto(client))
                .thenReturn(clientPutDto);

        var clientUpdate = clientService.clientUpdate(clientPutDto, "56040769025");

        assertSame(clientUpdate, clientPutDto);
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
                .setAddress(address)
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