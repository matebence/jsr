package com.bence.mate.model;

import com.bence.mate.model.json.PrivateVisibilityStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbVisibility;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonbVisibility(value = PrivateVisibilityStrategy.class)
public class Customer {

    @Getter
    @Setter
    @JsonbTransient
    private long customerId;

    @Getter
    @Setter
    @JsonbProperty("name")
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @JsonbProperty("profileName")
    private String profileName;

    @JsonbCreator
    public Customer(@JsonbProperty("profileName") String profileName) {
        this.profileName = profileName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profileName='" + profileName + '\'' +
                '}';
    }
}
