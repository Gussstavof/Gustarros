package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.validation.AgeValidator;
import com.gusscarros.core.client.validation.CpfValidator;
import com.gusscarros.core.endereco.model.Adress;
import com.gusscarros.core.endereco.validation.AdressValidator;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Locale;

@Builder
@Getter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientPostDto{

    @NotBlank(message = "Name is empty")
    private String name;

    @CPF(message = "invalid CPF")
    @CpfValidator
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

    @AdressValidator
    private Adress adress;

}
