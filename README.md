# rpscript
---

## Overview
---
This is a simple Turing-complete programming language written in Java 21.

## Example
---

```kotlin
fun factorial(n) {
    if (n <= 1) return 1
    return n * factorial(n - 1)
}
print('Factorial of 5 is:', factorial(5))
```

## Project structure
---

* `src/` - source code of the interpreter
* `example_programs/` - example programs written in rpscript

## Building and running
---

### Requirements

* Java (21 or higher)
* Maven

### Build

```bash
mvn clean package
```

### Usage

```bash
./rp example_programs/helloworld.rp
```