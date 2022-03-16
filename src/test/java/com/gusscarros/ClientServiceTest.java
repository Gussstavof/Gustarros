package com.gusscarros;

import com.gusscarros.core.client.dto.ClientGetDto;
import com.gusscarros.core.client.dto.ClientPatchDto;
import com.gusscarros.core.client.dto.ClientPostDto;
import com.gusscarros.core.client.dto.ClientPutDto;
import com.gusscarros.core.client.exception.ExceptionNotFound;
import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.service.ClientService;
import com.gusscarros.core.endereco.infra.AdressInfra;
import com.gusscarros.core.endereco.model.Adress;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.plaf.PanelUI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@Log4j2
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

    @BeforeEach
    public  void setup(){

        adress = new Adress();
        Adress adressUpdate = new Adress();


        clientPostDto = ClientPostDto.builder()
                .name("Ferreira")
                .adress(adress.setCep("03245110"))
                .adress(adress.setNumero("277"))
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();

        clientPutDto = ClientPutDto.builder()
                .adress(adressUpdate.setCep("03220-100"))
                .adress(adressUpdate.setNumero("90"))
                .creditCard("5339474401406150")
                .name("Gustavo")
                .build();

    }

    private void mockMethods(){
        Mockito.when(repository.save(Mockito.any())).thenReturn(Mockito.any());
        Mockito.when(adressInfra.validationAdress(adress)).thenReturn(adress);
    }


    //POST
    @Test
    public void saveClientTest(){
       mockMethods();

        Assertions.assertSame(clientService.saveClient(clientPostDto), clientPostDto);
    }

    @Test
    public void getAllStatusTrueTest(){
        var client = clientService.saveClient(clientPostDto);

        Mockito.when(repository.findByStatusTrue()).
                thenReturn(Collections.singletonList(client.build()));

        Assertions.assertTrue(clientService.allClient().size()>0);
    }

    @Test
    public void getByCpfTest(){
       var client = clientService.saveClient(clientPostDto);

        Mockito.when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        var clientCpf = clientService.searchCpf("56040769025");

        Assertions.assertEquals(clientCpf.getName(), "FERREIRA");
    }


    @Test
    public void getByName(){
        var client = clientService.saveClient(clientPostDto);

        Mockito.when(repository.findByNameContains(Mockito.any())).
                thenReturn(Collections.singletonList(client.build()));
        var clientName = clientService.searchName("FER");

        Assertions.assertEquals(clientName.set(0, clientGetDto).getName(), "FERREIRA");
    }

    @Test
    public void updateClientTest(){
        var client = clientService.saveClient(clientPostDto);

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        Mockito.when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());

        var clientUpdate = clientService.clientUpdate(clientPutDto, "56040769025");

        Assertions.assertSame("Gustavo", clientUpdate.getName());
    }

    @Test
    public void updateStatusTest(){
        var client = clientService.saveClient(clientPostDto);

        Mockito.when(repository.findByCpf(Mockito.any()))
                .thenReturn(Optional.ofNullable(client.build()));
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(Mockito.any());

        var clientStatus = clientService.clientUpdateStatus(false,"56040769025");

        Assertions.assertFalse(clientStatus.isStatus());

    }

}
