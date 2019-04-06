# Series Generator

This utility tool consolidating series to be used to determine if a ranges(s) should be allowed or excluded.

## Requirements

Sometimes items cannot be shipped to certain codes, and the rules for these restrictions are stored as a series of ranges of 5-digit codes. 

For example, if the ranges are: `[94133,94133] [94200,94299] [94600,94699]`

Then the item can be shipped to code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299.

Any item might be restricted based on multiple sets of these ranges obtained from multiple sources.

## PROBLEM

Given a collection of 5-digit code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input. 

## NOTES

- The ranges above are just examples, your implementation should work for any set of arbitrary ranges
- Ranges may be provided in arbitrary order
- Ranges may or may not overlap
- Your solution will be evaluated on the correctness and the approach taken, and adherence to coding standards and best practices

## EXAMPLES

If the input = `[94133,94133] [94200,94299] [94600,94699]`
Then the output should be = `[94133,94133] [94200,94299] [94600,94699]`

If the input = `[94133,94133] [94200,94299] [94226,94399]`
Then the output should be = `[94133,94133] [94200,94399]`

## Evaluation Guidelines

Your work will be evaluated against the following criteria:
- Successful implementation
- Efficiency of the implementation
- Design choices and overall code organization
- Code quality and best practices

## Getting Started

After downloading or cloning this repository, any further setup is completely optional. It is possible to use this demo immediately if your environment if configured to use the tools listed below in `Prerequisites`.

### Prerequisites

Your environment will need to be setup to use [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
 and [Gradle](https://gradle.org/install/).

## Building

Gradle is used for building the source code, generating the documentation, executing unit tests, and running the demo application.

```
Run all commands from within the main project folder.
```
### Configure in Eclipse
Import project as gradle project.
Gradle Refresh
Run Application.java


### Gradle tasks

The following Gradle tasks are available:

- build
- clean
- javadoc
- run
- test

#### build

The `build` task will compile all Java classes (including unit tests) and copy the compiled classes and resource file(s) to the `build` folder.

To execute this, run: `gradle build`

#### clean

The `clean` task will remove the `build` folder.

To execute this, run: `gradle clean`

#### javadoc

The `javadoc` task will generate the JavaDocs from source code and copy them into `build/docs`.

To execute this, run: `gradle javadoc`

#### run

The `run` task will execute the main Java class: `com.nisum.seriesgenerator.Application`

To execute this, run: `gradle run`

When the `Application` class is executed, it will:
- read the contents of `build/resources/main/rawRanges.txt`
   ```
   To change the code range definitions, edit the file: src/main/resources/rawRanges.txt

   After editing rawRanges.txt, it is necessary to run the build again.
   ```
- process all of the ranges contained within `rawRanges.txt`
- sort and consolidate all ranges into the minimum number of ranges
- display both the original and consolidated ranges
- optional: Any codes passed in via the command-line will be checked if they are in any of the excluded ranges
   ```
   To add codes to the command-line, run add "-Pexclude=#####,#####" to the build command.

   Example: gradle run -Pexclude=11111,22222,98765
   ```

#### test

The `test` task will execute the unit tests copy the results to the `build/________________________` folder.

To execute this, run: `gradle test`

The unit test report can be seen by opening `/build/reports/tests/test/index.html` with a web browser.

The code coverage report can be seen by opening `/build/reports/jacoco/test/html/index.html` with a web browser.

## Libraries

This project uses the following libraries:
* [JDK 1.8.0.181]
* [Spring Boot 1.3.3-RELEASE]
* [JUnit 4.+]
* [Hamcrest 2.0.0.0]
* [JaCoCo 0.7.1.201405082137]
* [Gradle 2+]

This project was developed using the free Community Edition of Eclipse Java EE IDE for Web Developers.
Version: Photon Release (4.8.0).

## Artifacts Generated
Document : \README.md
Executable Jar : \build\libs\SeriesGenerator.jar
Coverage Report : build\reports\jacoco\test\html\index.html
Test Report : build\reports\tests\index.html
