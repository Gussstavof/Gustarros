package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.validation.AgeValidation;
import com.gusscarros.core.endereco.model.Adress;
import com.gusscarros.core.endereco.validation.AdressValidation;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Locale;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientPostDto {

    @NotBlank(message = "Nome is empty")
    private String name;

    @CPF(message = "invalid CPF")
    private String cpf;

    @DateTimeFormat(pattern = "yyyy-dd-MM")
    @NotNull
    @AgeValidation
    private LocalDate birthDate;

    @NotBlank
    @CreditCardNumber(message = "Invalid credit card number")
    private String creditCard;

    @NotBlank
    private String gender;

    @AdressValidation(message = "Invalid CEP")
    private Adress adress;

    public  Client convert(){
        return new Client(name.toUpperCase(Locale.ROOT), cpf, birthDate, creditCard, gender.toUpperCase(Locale.ROOT),adress);
    }
}
