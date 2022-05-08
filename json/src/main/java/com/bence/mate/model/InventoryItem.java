package com.bence.mate.model;

import com.bence.mate.model.json.InventoryDivider;
import com.bence.mate.model.json.InventoryMultiplier;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.json.bind.annotation.JsonbTypeSerializer;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {

    @Getter
    @Setter
    private Long inventoryItemId;

    @Getter
    @Setter
    private Long catalogItemId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @JsonbTypeDeserializer(value = InventoryDivider.class)
    @JsonbTypeSerializer(value = InventoryMultiplier.class)
    private Long quantity;

    @Override
    public String toString() {
        return "InventoryItem{" +
                "inventoryItemId=" + inventoryItemId +
                ", catalogItemId=" + catalogItemId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
