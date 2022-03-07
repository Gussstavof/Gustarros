package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.client.validation.AgeValidation;
import com.gusscarros.core.client.validation.CpfValidation;
import com.gusscarros.core.endereco.model.Adress;
import com.gusscarros.core.endereco.validation.AdressValidation;
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
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientPostDto {

    @NotBlank(message = "Nome is empty")
    private String name;

    @CPF(message = "invalid CPF")
    @CpfValidation
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

    @AdressValidation
    private Adress adress;


    public Client build(){
        Client client = new Client()
                .setName(this.name.toUpperCase(Locale.ROOT))
                .setCpf(this.cpf)
                .setBirthDate(this.birthDate)
                .setCreditCard(this.creditCard)
                .setGender(this.gender.toUpperCase(Locale.ROOT))
                .setAdress(this.adress);
        return client;
    }

}
