[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=rabestro_codereview-task1-ip-addresses&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=rabestro_codereview-task1-ip-addresses)

# IPv4 addresses

A simple task to count the number of unique IPv4 addresses in a huge file.

## Task Description

Given a simple text file with IPv4 addresses. One line - one address, like this:
```
145.67.23.4
8.34.5.23
89.54.3.124
89.54.3.124
3.45.71.5
...
```
The file size is not limited and can take tens and hundreds of gigabytes. Your task is to count the number of unique addresses in this file, spending as little memory and time as possible. You can download a sample file [here](https://ecwid-vgv-storage.s3.eu-central-1.amazonaws.com/ip_addresses.zip). The file is zipped, and you should unzip it before processing. Please note that the size of unzipped file is about 120Gb.

## How to build and run the project

To build the application you may use this command (Linux/MacOS):

```shell
./gradlew assemble
```

To run using gradle please use a command:

```shell
./gradlew run -q --console=plain --args="ip_addresses"
```

Alternatively, you can run the program using the command:

```shell
java -jar build\libs\ipcounter.jar ip_addresses
```

Please note that to run the application, you should replace `ip_addresses` with a full path to the source text file with IPv4 addresses.

## Run test

Unit tests, integration tests and application functional test are written using the Spock Framework.

Tests can be run with the command: 
```shell
./gradlew test
```

## Benchmarks

```shell
./gradlew jmh
```

### Benchmark results for containers

```text
Benchmark                               (amount)  Mode  Cnt   Score   Error  Units
ContainerBenchmark.dualBitSetContainer        1B  avgt    5  20.188 ± 1.211   s/op
ContainerBenchmark.dualBitSetContainer        1M  avgt    5   0.053 ± 0.002   s/op
ContainerBenchmark.dualBitSetContainer        1K  avgt    5   0.034 ± 0.001   s/op
ContainerBenchmark.longArrayContainer         1B  avgt    5  13.943 ± 0.336   s/op
ContainerBenchmark.longArrayContainer         1M  avgt    5   0.093 ± 0.005   s/op
ContainerBenchmark.longArrayContainer         1K  avgt    5   0.080 ± 0.002   s/op
```

## Solution description

### Naive approach 

The first naive attempt to solve the problem:

```shell
sort -u ips.txt | wc -l
```

After very long time of processing the very huge text file I've got an error message:

```text
sort: write failed: /tmp/sortcQjXmj: No space left on device
0
```

### The naive approach in Java

The same approach, but implemented as a Java program, looks like this:

```java
var path = Path.of("ips.txt");

try (var lines = Files.lines(path)) {
    var unique = lines.distinct().count();    
    System.out.println(unique);
}
```

### Working Solution

The IPv4 addresses converted to int number and then stored in custom container. In order to use Stream API I created a custom collector, and it used to count unique addresses. 
This collector can be configured to be used with different converter and container. This code snippet demonstrates the use of the collector.

```java
var path = Path.of("ips.txt");

try (var lines = Files.lines(path)) {
    var unique = lines.collect(IPv4Collector.countingUnique());    
    System.out.println(unique);
}
```

The project has two general container implementations:
-   BitSetContainer
-   LongArrayContainer

The class DualBitSetContainer was my very first solution for the task and represent a special case of BitSetContainer with level equals 1. 
