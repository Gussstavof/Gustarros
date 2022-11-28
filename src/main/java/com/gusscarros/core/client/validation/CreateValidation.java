package com.gusscarros.core.client.validation;

import com.gusscarros.core.client.dto.ClientDto;
import org.springframework.stereotype.Component;

@Component
public interface CreateValidation {
     void validator(ClientDto clientDto);
}
