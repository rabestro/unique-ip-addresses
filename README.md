# IPv4 addresses

A simple task to count the number of unique IPv4 addresses in a huge file.

## Task Description

Given a simple text file with IPv4 addresses in dotted-decimal notation. One line - one address, like this:
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
java -jar build/libs/ipcounter.jar ip_addresses
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

After a long time processing a huge text file, I got an error message:

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

### The Solution

To read a text file, we use the `Files.lines()` method. This method returns a stream of strings, 
each of which is a textual representation of the IPv4 address. The addresses are then converted 
from their textual representation to integers of type int (4 bytes). These addresses are added 
to a special container, where one bit is used to indicate this number. Since the int type uses 4 bytes, 
our container needs $2^{32}$ bits to indicate the presence of numbers. This is equal to 512 MB. 
This volume is fixed and does not depend on the total number of IPv4 addresses.

The project implements one version of the converter and several variants of the container 
optimized for different amounts of data. In particular, `LongArrayContainer` immediately allocates 
the required 512 MB of data and is optimal for very large amounts of data. Whereas `BitSetContainer` 
allocates the required memory dynamically for the used IP address segments. Thus, if we have IP addresses 
only from certain segments, this will allow us to allocate the minimum required memory. 
The container `DualBitSetContainer` is a particular, slightly more optimized, 
case of the more general `BitSetContainer`.

The following code snippet illustrates the idea behind this solution.

```java
private static long countUnique(Stream<String> ipAddresses) {
    return ipAddresses
                .mapToInt(new IPv4Converter())
                .collect(LongArrayContainer::new, IntContainer::add, IntContainer::addAll)
                .countUnique();
}
```

Here `IntContainer` is the interface of the container, and `LongArrayContainer` is its implementation.

The implementation of the converter is highly optimized and assumes that the IP address in text format is correct. 
The converter does not perform any checks and will return an arbitrary number in case of an incorrect address. 
If additional verification is required, then you can either add a filter to the string stream, 
or implement a converter where there will be an additional check of the address for correctness.

In the catalog `src/jmh` you will find benchmarks where several implementations of converters are considered. 
At the moment, the solution seems to be close to optimal.
