# Tabular

Prints simple tabular output in console.

## Build

Run command

```
$ ./gradlew build
```

will generates jar archive on `./build/libs`.

## Sample Code

Code:
```java
Tabular t = new Tabular();
t.setHeader(new Object[] { "Name", "Age", "Gender", "Score" });
t.addRow(new Object[] { "Alice", 23, "Female", 15 });
t.addRow(new Object[] { "Bob",   30, "Male",   892 });
t.addRow(new Object[] { "Carol", 25, "Female", 1059 });

System.out.println(t);
```

Output:
```
Name  Age Gender Score
Alice  23 Female    15
Bob    30 Male     892
Carol  25 Female  1059
```

## Try with maven repos

```groovy
repositories {
    maven { url 'https://stmy.github.io/mvn/' }
}

dependencies {
    compile 'com.github.stmy:tabular:0.1.1'
}
```

## License

Apache License, Version 2.0.