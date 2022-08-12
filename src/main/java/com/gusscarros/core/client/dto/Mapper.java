package com.gusscarros.core.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.endereco.infra.AdressInfra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    @Autowired
    private AdressInfra adressInfra;

    public Client toClient(ClientDto clientDto){
        return Client.builder()
                .name(clientDto.getName())
                .cpf(clientDto.getCpf())
                .birthDate(clientDto.getBirthDate())
                .creditCard(clientDto.getCreditCard())
                .gender(clientDto.getGender())
                .adress(adressInfra.validationAdress(clientDto.getAdress()))
                .build();
    }

    public ClientDto toClientDto(Client client){
        return   ClientDto.builder()
                .name(client.getName())
                .cpf(client.getCpf())
                .birthDate(client.getBirthDate())
                .creditCard(client.getCreditCard())
                .gender(client.getGender())
                .adress(adressInfra.validationAdress(client.getAdress()))
                .build();
    }

    public List<ClientDto> convertListDto(List<Client> clients){
        return clients.stream().map(ClientDto::new).collect(Collectors.toList());
    }

    public ClientPatchDto cpfAndStatus(Client client){
        return ClientPatchDto.builder()
                .cpf(client.getCpf())
                .status(client.isStatus())
                .build();
    }
}
