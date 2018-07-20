# DUL Protocol Validator

## Description
This project holds the JSON Schema, as well as a Scala implementation of the forementioned schema, which can be used to
validate messages exchanged between Publishers and Content services. This project is known as Distributed Usage Logging
(_DUL_ from now on). To read more about it, please check [Project DUL](https://www.crossref.org/community/project-dul/)

The JSON Schema will make sure the message looks like a correct message. Due to the nature of the values of the content
itself, it can't be ensured that the content is correct. For example, for DOIs it can only be assured that it looks like
a DOI, but not that is a valid one, or that exists at all.
On the other hand the Scala implementation of the JSON Schema is more restrictive and will guarantee the validity of the
message. These classes are meant be used as [Data Transfer Objects](https://en.wikipedia.org/wiki/Data_transfer_object)
 (_DTO_ from now on) and might not necessarily fit any further purpose except for communicating between producers and 
 consumers of usage data. 

### JSON Schema
The schema is generated using Draft 7 of the Standard (last at the moment of writing).
See [JSON Schema Project Page](http://json-schema.org/) for further info.

### Case Classes
The project also provides the corresponding `case classes` to read from or write to JSON. Thanks to a powerful type
system, the `case classes` are more strict than the JSON schema allowing to create only semantically correct messages. 

## Dependencies
The dependencies of this project are:
* [spray-json](https://github.com/spray/spray-json)

## Usage

There are two different ways to use this project:

### JSON Schema Only
Pick the JSON Schema validation tool of your taste and point the schema location to
[https://github.com/springernature/DUL-Protocol-Validator/blob/master/src/main/resources/payload.schema.json](https://github.com/springernature/DUL-Protocol-Validator/blob/master/src/main/resources/payload.schema.json)

You can find a list of different available JSON Schema validation tools here: 
[JSON Schema Validators](http://json-schema.org/implementations.html#validators).

### DTOs
In order to have access to the _DTOs_ modelling the _DUL_ protocol, you need first to add a dependency
to this project. Currently, only source code dependency is supported. Here you can find an example on how to do so:
```scala 
lazy val dulProtocolValidator = RootProject(uri("git://github.com/springernature/DUL-Protocol-Validator.git"))

lazy val myProject = project in file("my-project").dependsOn(dulProtocolValidator)
```
Where `myProject` is your own project definition.

After this, you are ready to use `Payload` and `Message` classes.

#### Case Class to JSON
To serialize the a `Payload` to JSON you need to write:
```scala
import spray.json._
import com.springernature.dul.DULJsonProtocol._

val payload = ???

val jsonAST = payload.toJson

jsonAST.prettyPrint // To pretty print the JSON with newlines and spaces
jsonAST.compactPrint // To print the JSON without spaces neither newlines
```

#### JSON to Case Class
To deserialize a JSON into a `Payload` class you need to write:
```scala
import spray.json._
import com.springernature.dul.DULJsonProtocol._

val json = ??? // This is a Payload JSON message

val payload = json.parseJson.convertTo[Payload]
```

## Development

Before submitting any pull requests, please ensure that you have adhered to the 
[contribution guidelines](https://github.com/springernature/DUL-Protocol-Validator/blob/master/CONTRIBUTING.md).

To clone the repository:
```
git@github.com:springernature/DUL-Protocol-Validator.git
```
   
To compile the library:
```
sbt compile
```

To run the tests:
```
sbt test
```

# License

LGPL v3.0

Copyright © 2018 Springer Nature
