package com.bence.mate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.time.LocalDate;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbNumberFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonbPropertyOrder(
        {
                "date",
                "items",
                "customer",
                "orderId",
                "storeName"
        }
)
@JsonbNillable(value = false)
public class Order {

    @Getter
    @Setter
    private long orderId;

    @Getter
    @Setter
    private String storeName;

    @Getter
    @Setter
    //@JsonbTypeAdapter(CustomerAdapter.class)
    private Customer customer;

    @Getter
    @Setter
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDate date;

    @Getter
    @Setter
    @JsonbProperty(nillable = true)
    private List<InventoryItem> items;

    @Getter
    @Setter
    @JsonbNumberFormat(locale = "en_US", value = "#0.00")
    private Double price;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", storeName='" + storeName + '\'' +
                ", customer=" + customer +
                ", date=" + date +
                ", items=" + items +
                ", price=" + price +
                '}';
    }
}
