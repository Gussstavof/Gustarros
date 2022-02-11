package com.gusscarros.core.client.model;

import com.gusscarros.core.endereco.model.Adress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
public class ClientDto {

    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String creditCard;
    private String gender;
    private Adress adress;


}
