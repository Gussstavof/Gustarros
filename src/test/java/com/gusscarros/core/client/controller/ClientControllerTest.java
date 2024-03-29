package com.gusscarros.core.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gusscarros.core.client.models.request.ClientRequest;
import com.gusscarros.core.client.models.response.ClientResponse;
import com.gusscarros.core.client.exception.NotFoundException;
import com.gusscarros.core.client.service.ClientService;
import com.gusscarros.core.address.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    Pageable pageable;
    ClientRequest clientRequest;
    ClientResponse clientResponse;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(clientController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .build();
        Address address = Address.builder()
                .cep("03245110")
                .numero("277")
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
                .name("Ferreira")
                .address(address)
                .birthDate(LocalDate.parse("2003-11-12"))
                .cpf("560.***.***-**")
                .creditCard("5245759559334078")
                .gender("masculino")
                .build();
    }


    @Test
    void save() throws Exception {
        when(clientService.save(any()))
                .thenReturn("560.***.***-**");

        String response = """
                  {
                    "message": "CREATED",
                    "cpf": "560.***.***-**"
                  }
                """;

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @DisplayName("Get_clients_when_status_is_true_and_return_status_200")
    void getAll() throws Exception {
        when(clientService.getAll(pageable))
                .thenReturn(new PageImpl<>(Collections.singletonList(clientResponse)));

        mockMvc.perform(get("/clients/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Search_client_by_name_and_return_status_200")
    void findByName() throws Exception {
        doReturn(List.of(clientResponse))
                .when(clientService)
                .searchByName("Gustavo");

        mockMvc.perform(get("/clients/searchname?name=Gustavo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(clientResponse))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Throw_exception_NameNotFound")
    void NameNotFound() throws Exception {
        when(clientService.searchByName(Mockito.any()))
                .thenThrow(new NotFoundException("Name not found"));

        mockMvc.perform(get("/clients/searchname?name=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("")))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Search_client_by_cpf_and_return_status_200")
    void findByCpf() throws Exception {
        when(clientService.searchByCpf("56040769025"))
                .thenReturn(clientResponse);

        mockMvc.perform(get("/clients/searchcpf?cpf=56040769025")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("search_Throw_exception_CpfNotFound")
    void cpfNotFound() throws Exception {
        when(clientService.searchByCpf(Mockito.any()))
                .thenThrow(new NotFoundException("CPF not found"));

        mockMvc.perform(get("/clients/searchcpf?cpf=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("update_client_and_return_201")
    void update() throws Exception {
        when(clientService.update(clientRequest, "56040769025"))
                .thenReturn(clientResponse);

        mockMvc.perform(put("/clients/56040769025")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("update_client_without_cpf_in_database_and_return_400")
    void updateCpfNotFound() throws Exception {
        when(clientService.update(clientRequest, "56040769026"))
                .thenThrow(new NotFoundException("CPF not found"));

        mockMvc.perform(put("/clients/56040769026")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @DisplayName("change_client's_status_to_false_and_return_status_200")
    void updateStatus() throws Exception {
        when(clientService.updateStatus(false, "56040769025"))
                .thenReturn(clientResponse);

        mockMvc.perform(patch("/clients/56040769025/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("update_Throw_exception_CpfNotFound")
    void updateStatusCpfNotFound() throws Exception {
        when(clientService.updateStatus(Boolean.parseBoolean(Mockito.any()), Mockito.any()))
                .thenThrow(new NotFoundException("CPF not found"));

        mockMvc.perform(patch("/clients/00000/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Search_client_by_cpf_and_change_client's_status_to_false")
    void updateStatusChange() throws Exception {
        when(clientService.updateStatus(false, "56040769025"))
                .thenReturn(clientResponse);

        String response = """
                  {
                    "cpf": "560.***.***-**",
                    "status": "false"
                  }
                """;

        mockMvc.perform(patch("/clients/56040769025/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("Delete_client_by_cpf_and_return_status_200")
    void deleteClientByCpf() throws Exception {
        doNothing()
                .when(clientService)
                .deleteByCpf("56040769025");

        mockMvc.perform(delete("/clients/56040769025")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}