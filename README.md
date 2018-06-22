# DUL Protocol Validator

This project holds the JSON Schema that can be used to validate messages exchanged between Publishers and Content services.
The JSON Schema will make sure the message looks like a correct message. Due to the nature of the values of the content
itself, it can't be ensured that the content is correct. For example, for DOIs it can only be assured that it looks like
a DOI, but not that is a valid one, or that exists at all.

## JSON Schema
The schema is generated using Draft 7 of the Standard (last at the moment of writing).
See [JSON Schema Project Page](http://json-schema.org/) for further info.

## TODO
- Think about add an Avro Schema
- Add Scala classes that map with proper types
