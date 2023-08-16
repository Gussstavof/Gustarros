package com.gusscarros.core.client.validation;

import com.gusscarros.core.client.models.request.ClientRequest;
import org.springframework.stereotype.Component;

@Component
public interface Validation {
     void validator(ClientRequest clientRequest);
}
