package com.bence.mate.app;

import com.bence.mate.model.Customer;
import com.bence.mate.model.Order;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbConfig;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.config.PropertyNamingStrategy;

import java.time.LocalDate;

import static java.nio.charset.Charset.forName;

public class JsonB {

    private final static EasyRandomParameters parameters = new EasyRandomParameters().seed(123L)
            .objectPoolSize(10)
            .randomizationDepth(10)
            .stringLengthRange(5, 50)
            .charset(forName("UTF-8"))
            .collectionSizeRange(1, 10)
            .ignoreRandomizationErrors(true)
            .overrideDefaultInitialization(false)
            .dateRange(LocalDate.now(), LocalDate.of(2023,1,1));

    private final static EasyRandom easyRandom = new EasyRandom(parameters);

    public static void main(String args[]) {
        Order easyRandomOrder = easyRandom.nextObject(Order.class);
        Customer customer = new Customer("ecneb");

        customer.setCustomerId(1);
        customer.setLastName("mate");
        customer.setFirstName("bence");

        easyRandomOrder.setCustomer(customer);
        //easyRandomOrder.setItems(null);

        JsonbConfig jsonbConfig = new JsonbConfig()
                .withPropertyNamingStrategy(PropertyNamingStrategy.CASE_INSENSITIVE)
                .withStrictIJSON(true)
                .withNullValues(false);

        Jsonb jsonb = JsonbBuilder.create(jsonbConfig);
        String json = jsonb.toJson(easyRandomOrder);
        Order order = jsonb.fromJson(json, Order.class);

        System.out.println(json);
        System.out.println(order.toString());
    }
}
