## JSON-B and JSON-P (JSR 353)

**Work with JSON and Java**
Structure behind it 

```json
{
	"title": "JSON Processing with Java EE"
}
```

```java
class Course {

	@Getter
	@Setter
	private String title;
}
```

JSON with Java is most used in
- RESTful architecture
- Auth
- NoSQL

**JSON-B**
Converting from JSON to java POJOs. Provides serialization and deserialization of Java objects to JSON. Setter methods are used during deserialization and getters method during serilization. If a field is private and doesnt have a public getter method then the property wont be visible in JSON output.

- @JsonbCreator - Is used to tell JSON-B that the Java object has a constructor (a "creator") which can match the fields of a JSON object to the fields of the Java object
- @JsonbDateFormat - A general purpose annotation which can be used to serialize a particular type into a specific format
- @JsonbNillable - (de)serializition of null values enabled by default
- @JsonbNumberFormat - A general purpose annotation which can be used to serialize a particular type into a specific format
- @JsonbProperty - Is used to tell JSON-B that this is a json property with name
- @JsonbPropertyOrder - Sets the order of properties
- @JsonbTransient - Is used to mark a type (class) to be ignored everywhere that type is used.
- @JsonbTypeAdapter - Is used to create alternative option for modifying, adapting data
- @JsonbTypeDeserializer - Is used to create alternative option for deserialization
- @JsonbTypeSerializer - Is used to create alternative option for serialization
- @JsonbVisibility - Is used to create custom visibility strategies

**JSON-P**
Additional support for JSON. JSONPointer for proerty acces with path expression. JSONPatch for modifying JSON with path expression. Generating and parsing JSON data, two programing models:
- Object model
	- Tree represented JSON data
	- Navigate analyze and modify
	- More flexible but slower
- Streaming model
	- Event based parser
	- Less flexible, but faster

**Object model**

Memory based model:
- javax.json.JsonValue
- javax.json.JsonBuilder
- javax.json.JsonReader
- javax.json.JsonWriter

Objects:
- JsonObject is a JSON object (It uses a Map behind, for storing the data in Java)
- JsonArray is a JSON array (It uses a List behind, for storing the data in Java)
- JsonValue String and number
- JsonObject 

Self-describing value types:
- ARRAY
- OBJECT
- STRING
- NUMBER
- TRUE
- FALSE
- NULL

**Streaming Model**

- Read JSON and fire events
	- Start/end of an object
	- Start/end of an array
	- A key name
	- Value tyope string, number boolean, and null value 

**Similarities with Jackson**

Read + Write Annotations:
- @JsonProperty - Is used to tell Jackson that this is a json property with name.
```java
public class Person {

    @JsonProperty("id")
    public long personId = 0;
}
```

- @JsonIgnore - Is used to tell Jackson to ignore a certain property (field) of a Java object.
```java
public class Person {

    @JsonIgnore
    public long personId = 0;
}
```

- @JsonIgnoreProperties - Is used to specify a list of properties of a class to ignore
```java
public class Person {

	@JsonIgnoreProperties({"street", "zip"})
    public Address address;
}
```

- @JsonIgnoreType - Is used to mark a whole type (class) to be ignored everywhere that type is used.
```java
public class Person {

    @JsonIgnoreType
    public static class Address {
        public String streetName  = null;
        public String houseNumber = null;
        public String zipCode     = null;
        public String city        = null;
        public String country     = null;
    }
}
```

- @JsonAutoDetect - Is used to tell Jackson to include properties which are not public, both when reading and writing object
```java
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class Person {

    private long personId;
    public String name;

}
```

- @JsonFormat - A general purpose annotation which can be used to serialize a particular type into a specific format. (For example Date)
```java
public class Person {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
	private Date birthDate;
}
```

Write Annotations:
- @JsonSetter - The value specified inside the @JsonSetter annotation is the name of the JSON field to match to this setter method. Is used to tell Jackson that is should match the name of this setter method to a property name in the JSON data
```java
public class Person {

    @Getter
    private long personId;

    @Getter
    @Setter
    private String name;

    @JsonSetter("id")
    public void setPersonId(long personId) { this.personId = personId; }
}
```

- @JsonAnySetter - The annotation instructs Jackson to call the same setter method for all unrecognized fields in the JSON object. 
```java
public class Bag {

    private Map<String, Object> properties = new HashMap<>();

    @JsonAnySetter
    public void set(String fieldName, Object value){
        this.properties.put(fieldName, value);
    }

    public Object get(String fieldName){
        return this.properties.get(fieldName);
    }
}
```

- @JsonCreator - Is used to tell Jackson that the Java object has a constructor (a "creator") which can match the fields of a JSON object to the fields of the Java object
```java
public class Person {

	@Getter
    private long id;

	@Getter
    private String name;

    @JsonCreator
    public PersonImmutable(
            @JsonProperty("id")  long id,
            @JsonProperty("name") String name  ) {

        this.id = id;
        this.name = name;
    }
}
```

- @JacksonInject - Is used to inject values into the parsed objects, instead of reading those values from the JSON
```java
public class Person {

    @JacksonInject
    public String source = null;
}

InjectableValues inject = new InjectableValues.Std().addValue(String.class, "jenkov.com");
Person personInject = new ObjectMapper().reader(inject)
                        .forType(Person.class)
                        .readValue(new File("data/person.json"));
```

- @JsonDeserialize - Is used to specify a custom de-serializer class for a given field in a Java object.
```java
public class Person {

    @JsonDeserialize(using = OptimizedBooleanDeserializer.class)
    public boolean enabled = false;
}

public class OptimizedBooleanDeserializer
    extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws
        IOException, JsonProcessingException {

        String text = jsonParser.getText();
        if("0".equals(text)) return false;
        return true;
    }
}
```

Read Annotations:
- @JsonInclude -  Tells Jackson only to include properties under certain circumstances.
```java
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Person {

    public long personId;
    public String name;
}
```

- @JsonGetter - Is used to tell Jackson that a certain field value should be obtained from calling a getter method instead of via direct field access.
```java
public class Person {

    private long  personId = 0;

    @JsonGetter("id")
    public long personId() { return this.personId + 1; }

    @JsonSetter("id")
    public void personId(long personId) { this.personId = personId - 1; }

}
```

- @JsonAnyGetter - Annotation enables you to use a Map as container for properties that you want to serialize to JSON
```java
public class Person {

    private Map<String, Object> properties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> properties() {
        return properties;
    }
}
```

- @JsonPropertyOrder - Annotation can be used to specify in what order the fields of your Java object should be serialized into JSON.
```java
@JsonPropertyOrder({"name", "personId"})
public class PersonPropertyOrder {

    public long personId;
    public String name;

}
```

- @JsonRawValue - Annotation tells Jackson that this property value should written directly as it is to the JSON output. So if its a string then without quotation marks. This is of course invalid JSON. We should use only when the string is already a JSON example : public String address = "{'street': "Main"}"
```java
public class Person {

    public long   personId = 0;

    @JsonRawValue
    public String address  = "$#";
}
```

- @JsonValue - Tells Jackson that Jackson should not attempt to serialize the object itself
```java
public class Person {

    public long   personId = 0;
    public String name = null;

    @JsonValue
    public String toJson(){
        return this.personId + "," + this.name;
    }
}
```

- @JsonSerialize - Annotation is used to specify a custom serializer for a field in a Java object.
```java
public class Person {

    public long personId;
    public String name:

    @JsonSerialize(using = OptimizedBooleanSerializer.class)
    public boolean enabled = false;
}

public class OptimizedBooleanSerializer extends JsonSerializer<Boolean> {

    @Override
    public void serialize(Boolean aBoolean, JsonGenerator jsonGenerator, 
        SerializerProvider serializerProvider) 
    throws IOException, JsonProcessingException {

        if(aBoolean){
            jsonGenerator.writeNumber(1);
        } else {
            jsonGenerator.writeNumber(0);
        }
    }
}
```

**Jackson ObjectMapper**

```java
public class Car {
	
	@Getter
	@Setter
    private String color;


	@Getter
	@Setter
    private String type;
}
```

- Java Object to JSON
```java
ObjectMapper objectMapper = new ObjectMapper();
Car car = new Car("yellow", "renault");
objectMapper.writeValue("{\"color\": \"red\", \"type\": \"SUV\"}", car);
```

- JSON to Java Object
```java
String json = "{\"color\": \"red\", \"type\": \"SUV\"}";
Car car = objectMapper.readValue(json, Car.class);	
```

- JSON to Jackson JsonNode
```java
String json =  "{\"color\": \"red\", \"type\": \"SUV\"}";
JsonNode jsonNode = objectMapper.readTree(json);
String color = jsonNode.get("color").asText();
```

- Creating a Java List From a JSON Array String
```java
String jsonCarArray = "[{\"color\": \"red\", \"type\": \"SUV\"}]";
List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});
```

- Creating Java Map From JSON String
```java
String json = "{\"color\": \"red\", \"type\": \"SUV\"}";
Map<String, Object> map objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
```

- Configuring Serialization or Deserialization Feature
```java
objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
Car car = objectMapper.readValue(jsonString, Car.class);

JsonNode jsonNodeRoot = objectMapper.readTree(jsonString);
JsonNode jsonNodeYear = jsonNodeRoot.get("year");
String year = jsonNodeYear.asText();
```

- Creating Custom Serializer or Deserializer
```java
public class CustomCarSerializer extends StdSerializer<Car> {
    
    public CustomCarSerializer() {
        this(null);
    }

    public CustomCarSerializer(Class<Car> t) {
        super(t);
    }

    @Override
    public void serialize(
      Car car, JsonGenerator jsonGenerator, SerializerProvider serializer) {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("car_brand", car.getType());
        jsonGenerator.writeEndObject();
    }
}

public class CustomCarDeserializer extends StdDeserializer<Car> {
    
    public CustomCarDeserializer() {
        this(null);
    }

    public CustomCarDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Car deserialize(JsonParser parser, DeserializationContext deserializer) {
        Car car = new Car();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);
        
        // try catch block
        JsonNode colorNode = node.get("color");
        String color = colorNode.asText();
        car.setColor(color);
        return car;
    }
}

ObjectMapper mapper = new ObjectMapper();
SimpleModule moduleSerializer = new SimpleModule("CustomCarSerializer", new Version(1, 0, 0, null, null, null));
moduleSerializer.addSerializer(Car.class, new CustomCarSerializer());
mapper.registerModule(moduleSerializer);

Car car = new Car("yellow", "renault");
String carJson = mapper.writeValueAsString(car);

SimpleModule moduleDeserializer = new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
moduleDeserializer.addDeserializer(Car.class, new CustomCarDeserializer());
mapper.registerModule(moduleDeserializer);

car = mapper.readValue(carJson, Car.class);
```

- Handling Formats
```java
ObjectMapper objectMapper = new ObjectMapper();
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
objectMapper.setDateFormat(df);
String carAsString = objectMapper.writeValueAsString(request);
```