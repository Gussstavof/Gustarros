package com.gusscarros.core.client.dto;

import com.gusscarros.core.client.model.Client;
import com.gusscarros.core.endereco.model.Adress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Locale;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientPostDto {

    @NotBlank(message = "Nome inválido")
    private String name;

    @CPF(message = "CPF inválido")
    private String cpf;

    @DateTimeFormat(pattern = "yyyy-dd-MM")
    @NotNull
    private LocalDate birthDate;

    @NotBlank
    @CreditCardNumber(message = "Número Do cartão de crédito Inválido")
    private String creditCard;

    @NotBlank
    private String gender;

    private Adress adress;

    public  Client convert(){
        return new Client(name.toUpperCase(Locale.ROOT), cpf, birthDate, creditCard, gender.toUpperCase(Locale.ROOT),adress);
    }
}
