package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.entity.Client;
import com.gusscarros.core.address.infra.AddressInfra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    @Autowired
    private AddressInfra addressInfra;

    public Client toClient(ClientDto clientDto){
        return Client.builder()
                .name(clientDto.getName())
                .cpf(clientDto.getCpf())
                .birthDate(clientDto.getBirthDate())
                .creditCard(clientDto.getCreditCard())
                .gender(clientDto.getGender())
                .address(addressInfra.validationAdress(clientDto.getAddress()))
                .build();
    }

    public ClientDto toClientDto(Client client){
        return   ClientDto.builder()
                .name(client.getName())
                .cpf(client.getCpf())
                .birthDate(client.getBirthDate())
                .creditCard(client.getCreditCard())
                .gender(client.getGender())
                .address(addressInfra.validationAdress(client.getAddress()))
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
