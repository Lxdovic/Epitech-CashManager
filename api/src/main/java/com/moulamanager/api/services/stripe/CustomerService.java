package com.moulamanager.api.services.stripe;

import com.moulamanager.api.exceptions.stripe.CustomerNotFoundException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CustomerService {

    private static final String CUSTOMER_NOT_FOUND = "Customer not found";

    public Customer findOrCreateCustomer(String email, String username) throws StripeException {
        try {
            return findCustomerByEmail(email);
        } catch (CustomerNotFoundException e) {
            return createCustomer(email, username);
        }
    }

    public Customer findCustomerByEmail(String email) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        CustomerCollection customers = Customer.list(params);
        return customers.getData().stream().findFirst().orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

    }

    public Customer createCustomer(String email, String username) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("name", username);
        return Customer.create(params);
    }
}
