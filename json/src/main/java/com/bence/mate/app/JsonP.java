package com.bence.mate.app;

import com.bence.mate.model.Order;
import com.bence.mate.model.InventoryItem;

import java.util.Map;
import javax.json.Json;
import java.util.HashMap;
import java.io.StringReader;
import java.io.StringWriter;
import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonPointer;
import java.time.LocalDateTime;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriterFactory;
import javax.json.bind.JsonbBuilder;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonGenerator;

public class JsonP {

    private final static String JSON = "{\"date\":\"2022-10-27\",\"items\":[{\"catalogItemId\":-167885730524958550,\"inventoryItemId\":-5106534569952410475,\"name\":\"dpHYZGhtgdntugzvvKAXLhMLlNgNfZBdyFGRajVfJ\",\"quantity\":4672433029010564658},{\"catalogItemId\":-3581075550420886390,\"inventoryItemId\":-7216359497931550918,\"name\":\"NonEnOinZjUfzQhdgLLfDTDGspDbQvBQYuxiXXVytGCx\",\"quantity\":-2298228485105199876},{\"catalogItemId\":1326634973105178603,\"inventoryItemId\":-5237980416576129062,\"name\":\"zVllpgTJKhRQqqszYLYdvDhtAsLghPXAgtbpr\",\"quantity\":-3758321679654915806},{\"catalogItemId\":-1694783153133139413,\"inventoryItemId\":-7771300887898959616,\"name\":\"XPZkhnfL\",\"quantity\":2746989241534039508},{\"catalogItemId\":1210033231312349320,\"inventoryItemId\":-457112246358890037,\"name\":\"TBSXsPBOxmQInyIvpRgmgQsYEKkAAAryjCRhLTuh\",\"quantity\":1282378635546458216},{\"catalogItemId\":7830028867000074426,\"inventoryItemId\":1674084292433445352,\"name\":\"nTodUewZQqaZErUaofGvthLoyPLDADY\",\"quantity\":-2317076407365535282},{\"catalogItemId\":7314076204812145092,\"inventoryItemId\":-7837950195727116076,\"name\":\"zxWoaMAzEEplqjJjNBgpTmxxpIoQMODRhfGEfXIoTtOmc\",\"quantity\":4765075071605135204}],\"customer\":{\"lastName\":\"mate\",\"name\":\"bence\",\"profileName\":\"ecneb\"},\"orderId\":-5106534569952410475,\"storeName\":\"eOMtThyhVNLWUZNRcBaQKxIyedUsFwdkelQbx\",\"price\":\"0.72\"}";

    public static void main(String args[]) {
        objectModelReader();
        objectModelWriter();
        streamModelEvents();

        generator();
        patch();
    }

    private static void objectModelReader() {
        try (JsonReader jsonReader = Json.createReader(new StringReader(JSON))) {

            JsonObject jsonObject = jsonReader.readObject();

            JsonObjectBuilder jsonTrackingObjectBuilder = Json.createObjectBuilder()
                    .add("orderDate", LocalDateTime.now().toString());

            jsonObject = Json.createObjectBuilder(jsonObject)
                    .add("tracking", jsonTrackingObjectBuilder.build()).build();

            JsonArrayBuilder jsonStatusArrayBuilder = Json.createArrayBuilder()
                    .add(Json.createObjectBuilder().add("status", "RECEIVED").build())
                    .add(Json.createObjectBuilder().add("status", "SENT_FOR_PROCESSING").build());

            jsonTrackingObjectBuilder.add("statuses", jsonStatusArrayBuilder.build());
            System.out.println(jsonObject.toString());
        }
    }

    private static void objectModelWriter() {
        try (JsonReader jsonReader = Json.createReader(new StringReader(JSON))) {

            JsonObject jsonObject = jsonReader.readObject();
            Map<String, Boolean> configMap = new HashMap<>();
            configMap.put(JsonGenerator.PRETTY_PRINTING, true);

            JsonWriterFactory jsonWriterFactory = Json.createWriterFactory(configMap);
            StringWriter stringWriter = new StringWriter();

            try (JsonWriter writer = jsonWriterFactory.createWriter(stringWriter)) {
                writer.writeObject(jsonObject);
                System.out.println(stringWriter.toString());
            }
        }
    }

    private static void streamModelEvents() {
        try (JsonParser jsonParser = Json.createParser(new StringReader(JSON))) {

            while (jsonParser.hasNext()) {
                JsonParser.Event event = jsonParser.next();

                switch (event) {
                    case KEY_NAME:
                        System.out.println(jsonParser.getString());
                        break;
                    case VALUE_STRING:
                        System.out.println(jsonParser.getString());
                        break;
                    case VALUE_NUMBER:
                        System.out.println(jsonParser.getLong());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static void generator() {
        Order order = JsonbBuilder.create().fromJson(JSON, Order.class);
        StringWriter writer = new StringWriter();

        try (JsonGenerator generator = Json.createGenerator(writer)) {

            generator.writeStartObject();

            generator.write("orderId", order.getOrderId());
            generator.write("storeName", order.getStoreName());

            generator.writeStartArray("items");

            for (InventoryItem item : order.getItems()) {
                generator.writeStartObject();
                generator.write("name", item.getName());
                generator.write("quantity", item.getQuantity());
                generator.writeEnd();
            }
            generator.writeEnd();

            generator.writeStartObject("customer");
            generator.write("firstName", order.getCustomer().getFirstName());
            generator.write("lastName", order.getCustomer().getLastName());

            generator.writeEnd();
            generator.writeEnd();

            generator.flush();

            System.out.println(writer.toString());
        }
    }

    private static void patch() {
        try (JsonReader jsonReader = Json.createReader(new StringReader(JSON))) {

            JsonObject jsonObject = jsonReader.readObject();

            JsonPointer pointer = Json.createPointer("/items/0/quantity");

            long quantity = Long.parseLong(pointer.getValue(jsonObject).toString());

            System.out.println("Quantity: " + quantity);

            jsonObject = Json.createPatchBuilder()
                    .add("/promo", "double")
                    .replace("/items/0/quantity", Long.toString(quantity * 2L))
                    .remove("/storeName").build().apply(jsonObject);

            System.out.println(jsonObject);
        }
    }
}
