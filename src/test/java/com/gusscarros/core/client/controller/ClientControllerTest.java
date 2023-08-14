package com.gusscarros.core.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gusscarros.core.client.dto.ClientPatchDto;
import com.gusscarros.core.client.dto.ClientDto;
import com.gusscarros.core.client.dto.Mapper;
import com.gusscarros.core.client.dto.request.ClientRequest;
import com.gusscarros.core.client.dto.response.ClientResponse;
import com.gusscarros.core.client.exception.NotFoundException;
import com.gusscarros.core.client.entity.Client;
import com.gusscarros.core.client.service.ClientService;
import com.gusscarros.core.client.constraints.AgeValidation;
import com.gusscarros.core.address.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private ClientController clientController;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @MockBean
    ClientService clientService;

    @Mock
    Mapper mapper;

    @Mock
    AgeValidation ageValidation;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Pageable pageable;

    ClientDto clientDto;

    List<Client> clients;

    ClientPatchDto clientPatchDto;

    @BeforeEach
    public void setup(){

        mockMvc = MockMvcBuilders
                .standaloneSetup(clientController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .build();

        Address address = Address.builder()
                .cep("03245110")
                .numero("277")
                .build();

        clientDto = ClientDto.builder()
                .name("Ferreira")
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

        clients = Collections.singletonList(new Client()
                .setName("Gustavo")
                .setAddress(address)
                .setBirthDate(LocalDate.parse("2003-11-12"))
                .setCpf("56040769025")
                .setCreditCard("5245759559334078")
                .setGender("Masculino"));
    }


    @Test
    void save() throws Exception {
        when(clientService.saveClient(any()))
                .thenReturn("560.***.***-**");

        String response = """
                     {
                       "message": "CREATED",
                       "cpf": "560.***.***-**"
                     }
                   """;


        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @DisplayName("Get_clients_when_status_is_true_and_return_status_200")
    void getAll() throws Exception {
        when(clientService.allClient(pageable))
                .thenReturn(new PageImpl<>(Collections.singletonList(clientDto)));

        mockMvc.perform(get("/clients/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Search_client_by_name_and_return_status_200")
    void findByName() throws Exception{
        doReturn(mapper.convertListDto(clients))
                .when(clientService)
                .searchName("Gustavo");

        mockMvc.perform(get("/clients/searchname?name=Gustavo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clients)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @DisplayName("Throw_exception_NameNotFound")
    void NameNotFound() throws Exception{
        when(clientService.searchName(Mockito.any()))
                .thenThrow(new NotFoundException("Name not found"));

        mockMvc.perform(get("/clients/searchname?name=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clients)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Search_client_by_cpf_and_return_status_200")
    void findByCpf() throws Exception{
        when(clientService.searchCpf("56040769025"))
                .thenReturn(clientDto);

        mockMvc.perform(get("/clients/searchcpf?cpf=56040769025")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("search_Throw_exception_CpfNotFound")
    void cpfNotFound() throws Exception{
        when(clientService.searchCpf(Mockito.any()))
                .thenThrow(new NotFoundException("CPF not found"));

        mockMvc.perform(get("/clients/searchcpf?cpf=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("update_client_and_return_201")
    void update() throws Exception {
        when(clientService.clientUpdate(clientDto, "56040769025"))
                .thenReturn(clientDto);

        mockMvc.perform(put("/clients/56040769025")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("update_client_without_cpf_in_database_and_return_400")
    void updateCpfNotFound() throws Exception {
        when(clientService.clientUpdate(clientDto, "56040769026"))
                .thenThrow(new NotFoundException("CPF not found"));

        mockMvc.perform(put("/clients/56040769026")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @DisplayName("change_client's_status_to_false_and_return_status_200")
    void updateStatus() throws Exception {
        when(clientService.clientUpdateStatus(false,"56040769025"))
                .thenReturn(clientPatchDto);

        mockMvc.perform(patch("/clients/56040769025/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPatchDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("update_Throw_exception_CpfNotFound")
    void updateStatusCpfNotFound() throws Exception {
        when(clientService.clientUpdateStatus(Boolean.parseBoolean(Mockito.any()),Mockito.any()))
                .thenThrow(new NotFoundException("CPF not found"));

        mockMvc.perform(patch("/clients/00000/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPatchDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Search_client_by_cpf_and_change_client's_status_to_false")
    void updateStatusChange() throws Exception {
        when(clientService.clientUpdateStatus(false, "56040769025"))
                .thenReturn(clientPatchDto);
        mockMvc.perform(patch("/clients/56040769025/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPatchDto)))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":false,\"cpf\":\"56040769025\"}"));
    }

    @Test
    @DisplayName("Delete_client_by_cpf_and_return_status_200")
    void deleteClientByCpf() throws Exception {
        doNothing().when(clientService).clientDelete("56040769025");

        mockMvc.perform(delete("/clients/56040769025")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}