package com.gusscarros.core.client.validation;

import com.gusscarros.core.client.dto.ClientDto;
import com.gusscarros.core.client.dto.request.ClientRequest;
import org.springframework.stereotype.Component;

@Component
public interface CreateValidation {
     void validator(ClientRequest clientRequest);
}
