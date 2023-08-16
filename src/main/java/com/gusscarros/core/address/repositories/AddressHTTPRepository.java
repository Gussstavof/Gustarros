package com.gusscarros.core.address.repositories;

import com.gusscarros.core.address.entity.Address;

public interface AddressHTTPRepository {
    Address validationAddress(Address address);
}
