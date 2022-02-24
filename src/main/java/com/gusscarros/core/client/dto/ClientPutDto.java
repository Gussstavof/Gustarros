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
public class ClientPutDto {
    @NotBlank(message = "Nome inválido")
    private String name;

    @NotBlank
    @CreditCardNumber(message = "Número Do cartão de crédito Inválido")
    private String creditCard;

    private Adress adress;

    public ClientPutDto(Client client) {
        this.name = client.getName();
        this.creditCard = client.getCreditCard().substring(12,16);
        this.adress = client.getAdress();
    }

    public Client convert(){
        return new Client(name.toUpperCase(Locale.ROOT), creditCard,adress);
    }
}
