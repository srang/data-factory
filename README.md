# `data-factory`
Test framework for generating rich objects with dummy data

Inspired and leverages [java-faker](https://github.com/DiUS/java-faker)

## Usage
In your pom.xml, add the following:

```xml
<dependency>
  <groupId>com.github.srang</groupId>
  <artifactId>data-factory</artifactId>
  <version>1.0.3</version>
</dependency> 
```

In your Java code define a class you want to generate for example:

```java
public class Fruit {
    public String name;
    public String description;

    public Fruit(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
```

Then to start generating `Fruit` instances, you'll need to instantiate a `DataFactory`:

```java
DataFactory<Fruit> fruitFactory = new BaseFactory<>(Fruit.class);
```

Then you can start generating objects:

```java
// generate one fruit
Fruit fruit = fruitFactory.generate();

// or generate a list of fruits
List<Fruit> fruits = fruitFactory.generate(6);
```

## Factory Customization

There are a number of ways to customize a `DataFactory`:

```java
// inline field filter
fruitFilter.addFilter(
                (Field field) -> field.getType().equals(String.class) 
                   && field.getName().toLowerCase().contains("name"),
                () -> "Apple");

Fruit apple = fruitFactory.generate();
apple.getName(); // the name is "Apple"

// add custom language/locale
Locale bos =  new Locale("eng","boston"); // colloquialisms
DataFactory<Fruit> bostonFruitFactory = new BaseFactory<>(Fruit.class, bos);
bostonFruitFactory.generate().getName(); // wahtamelon
```

## Nesting and Inheritance

A `DataFactory` can be used with complex objects that have `List` fields, nested objects, and/or inherit fields from parent objects. Going back to the `Fruit` example, let's create a couple supporting objects:

```java
public class FruitBasket {
    public String material;
    public List<Fruit> contents;

    public Fruit(String material, List<Fruit> contents) {
        this.material = material;
        this.contents = contents;
    }
}

public class Berry extends Fruit {
    public Integer seedCount;
    
    public Berry(String name, String description, Integer seedCount) {
        super(name, description);
        this.seedCount = seedCount;
    }
}
```