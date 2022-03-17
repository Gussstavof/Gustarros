package com.gusscarros.core.client.service;

import com.gusscarros.core.client.dto.ClientGetDto;
import com.gusscarros.core.client.dto.ClientPatchDto;
import com.gusscarros.core.client.dto.ClientPostDto;
import com.gusscarros.core.client.dto.ClientPutDto;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.endereco.infra.AdressInfra;
import com.gusscarros.core.endereco.model.Adress;
import org.junit.jupiter.api.Assertions;
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


    //POST
    @Test
    public void saveClientTest(){
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());
        Mockito.when(adressInfra.validationAdress(adress))
                .thenReturn(adress);

        Assertions.assertSame( clientService.saveClient(clientPostDto), clientPostDto);
       // Assertions.assertSame("Rua Mandubirá", clientSave().getAdress().getLogradouro());

    }


    @Test
    public void getAllStatusTrueTest(){
         var client = clientSave();

        Mockito.when(repository.findByStatusTrue()).
                thenReturn(Collections.singletonList(client.build()));

        Assertions.assertTrue(clientService.allClient().size()>0);
    }

    @Test
    public void getByCpfTest(){
       var client = clientSave();

        Mockito.when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        var clientCpf = clientService.searchCpf("56040769025");

        Assertions.assertEquals(clientCpf.getName(), "FERREIRA");
    }

    @Test
    public void getByNameTest(){
        var client = clientSave();

        Mockito.when(repository.findByNameContains(Mockito.any())).
                thenReturn(Collections.singletonList(client.build()));

        var clientName = clientService.searchName("FER");

        Assertions.assertEquals(clientName.set(0, clientGetDto).getName(), "FERREIRA");
    }

    @Test
    public void updateClientTest(){
        var client = clientSave();

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        Mockito.when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());
        Mockito.when(adressInfra.validationAdress(adressUpdate))
                .thenReturn(adressUpdate);

        var clientUpdate = clientService.clientUpdate(clientPutDto, "56040769025");

        Assertions.assertSame("Gustavo", clientUpdate.getName());
    }

    @Test
    public void updateStatusTest(){
        var client = clientSave();

        Mockito.when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());

        var clientStatus = clientService.clientUpdateStatus(false,"56040769025");

        Assertions.assertFalse(clientStatus.isStatus());

    }

}
