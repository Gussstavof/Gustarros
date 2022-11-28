package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.entity.Client;
import com.gusscarros.core.client.constraints.AgeValidator;
import com.gusscarros.core.address.entity.Address;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Getter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    @NotBlank(message = "Name is empty")
    private String name;

    @CPF(message = "invalid CPF")
    private String cpf;

    @DateTimeFormat(pattern = "yyyy-dd-MM")
    @NotNull
    @AgeValidator
    private LocalDate birthDate;

    @NotBlank
    @CreditCardNumber(message = "Invalid credit card number")
    private String creditCard;

    @NotBlank
    private String gender;

    private boolean status;

    private Address address;

    public ClientDto(Client client) {
        this.name = client.getName();
        this.cpf = client.getCpf().substring(0,3).concat(".***.***-**");
        this.birthDate = client.getBirthDate();
        this.creditCard = client.getCreditCard().substring(12,16);
        this.gender = client.getGender();
        this.address = client.getAddress();
    }

}
