package com.gusscarros.core.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gusscarros.core.client.dto.ClientGetDto;
import com.gusscarros.core.client.dto.ClientPatchDto;
import com.gusscarros.core.client.dto.ClientPostDto;
import com.gusscarros.core.client.exception.ExceptionNotFound;
import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.repository.ClientRepository;
import com.gusscarros.core.client.service.ClientService;
import com.gusscarros.core.client.service.CpfService;
import com.gusscarros.core.endereco.infra.AdressInfra;
import com.gusscarros.core.endereco.model.Adress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private ClientController clientController;

    @MockBean
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    AdressInfra adressInfra;

    @Mock
    CpfService cpfService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private ClientGetDto clientGetDto;
    private ClientPostDto clientPostDto;
    private List<Client> clients;
    private ClientPatchDto clientPatchDto;
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

        clientGetDto = ClientGetDto.builder()
                .name("Ferreira")
                .adress(adress)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("56040769025")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();

        clientPatchDto = ClientPatchDto.builder()
                .cpf("56040769025")
                .status(true)
                .build();

        clients = Collections.singletonList(new Client()
                .setName("Gustavo")
                .setAdress(adress)
                .setBirthDate(LocalDate.parse("2003-11-12"))
                .setCpf("56040769025")
                .setCreditCard("5245759559334078")
                .setGender("Masculino"));
    }


    @Test
    void save() throws Exception {
        when(clientRepository.existsByCpf(Mockito.any()))
                .thenReturn(true);
        when(adressInfra.validationAdress(Mockito.any()))
                .thenReturn(adress);
        when(clientService.saveClient(clientPostDto))
                .thenReturn(clientPostDto);
        when(cpfService.isValid(Mockito.any(), Mockito.any()))
                .thenReturn(true);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPostDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void getAll() throws Exception {
        when(clientService.allClient())
                .thenReturn(ClientGetDto.convertListDto(clients));

        mockMvc.perform(get("/clients/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clients)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findByName() throws Exception{
        when(clientService.searchName("Gustavo"))
                .thenReturn(ClientGetDto.convertListDto(clients));

        mockMvc.perform(get("/clients/searchname?name=G")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clients)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void NameNotFound() throws Exception{
        when(clientService.searchName(Mockito.any()))
                .thenThrow(new ExceptionNotFound("Name not found"));

        mockMvc.perform(get("/clients/searchname?name=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clients)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void findByCpf() throws Exception{
        when(clientService.searchCpf("56040769025"))
                .thenReturn(clientGetDto);

        mockMvc.perform(get("/clients/searchcpf?cpf=56040769025")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientGetDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void cpfNotFound() throws Exception{
        when(clientService.searchCpf(Mockito.any()))
                .thenThrow(new ExceptionNotFound("CPF not found"));

        mockMvc.perform(get("/clients/searchcpf?cpf=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientGetDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void update() {
    }

    @Test
    void updateStatus() throws Exception {
        when(clientService.clientUpdateStatus(false,"56040769025"))
                .thenReturn(clientPatchDto);

        mockMvc.perform(patch("/clients/56040769025/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPatchDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateStatusCpfNotFound() throws Exception {
        when(clientService.clientUpdateStatus(Boolean.parseBoolean(Mockito.any()),Mockito.any()))
                .thenThrow(new ExceptionNotFound("CPF not found"));

        mockMvc.perform(patch("/clients/00000/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPatchDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateStatusChange() throws Exception {
        when(clientService.clientUpdateStatus(false, "56040769025"))
                .thenReturn( ClientPatchDto.builder()
                        .cpf("56040769025")
                        .status(false)
                        .build());
        mockMvc.perform(patch("/clients/56040769025/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPatchDto)))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":false,\"cpf\":\"56040769025\"}"));
    }

    @Test
    void deleteClientByCpf() throws Exception {
        doNothing().when(clientService).clientDelete("56040769025");

        mockMvc.perform(delete("/clients/56040769025")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPostDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}