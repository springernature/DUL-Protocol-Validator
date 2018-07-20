package com.springernature.dul

import org.everit.json.schema.Schema
import org.everit.json.schema.loader.SchemaLoader
import org.json.{JSONObject, JSONTokener}

object JSONSchemaValidatorUtils {


  val schemaValidator: Schema = {
    val inputStream = getClass.getResourceAsStream("/payload.schema.json")

    val rawSchema = new JSONObject(new JSONTokener(inputStream))
    SchemaLoader.load(rawSchema)
  }

}
