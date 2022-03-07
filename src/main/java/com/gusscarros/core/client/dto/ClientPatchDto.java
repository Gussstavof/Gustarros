package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.validation.CpfValidation;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientPatchDto {

    private boolean status;

    @CPF
    @CpfValidation
    private String cpf;

    public ClientPatchDto(Client client) {
        this.cpf = client.getCpf().substring(0,3).concat(".***.***-**");
        this.status = client.isStatus();
    }

}
