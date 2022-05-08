package com.bence.mate.model.json;

import com.bence.mate.model.Customer;

import javax.json.Json;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;

public class CustomerAdapter implements JsonbAdapter<Customer, JsonValue> {

    @Override
    public JsonValue adaptToJson(Customer customer) {
        return Json.createValue(customer.getProfileName());
    }

    @Override
    public Customer adaptFromJson(JsonValue jsonValue) {
        return new Customer(((JsonString) jsonValue).getString());
    }
}
