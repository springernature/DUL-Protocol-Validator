package com.springernature.dul

import org.everit.json.schema.ValidationException
import org.json.JSONObject
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

class SchemaSpec extends FlatSpec with Matchers {

  behavior of "JSON Schema"

  it should "validate correct JSON" in {
    val json =
      """
        |{
        |  "uuid": "e583eca0-fdf4-45ff-8c8e-2c3ce1196ea7",
        |  "message-type": "counter-transaction",
        |  "callback": "http://url.com/callback",
        |  "message": {
        |    "Transaction_ID": "a1a0fbd7-d280-4ab3-94fe-b060758aa28b",
        |    "Transaction_DateTime": "2017-06-06T12:28:57.001Z",
        |    "Transaction_Type": "Request",
        |    "Transaction_Access_Type": "Controlled",
        |    "Transaction_Access_Method": "Regular",
        |    "User_Agent": "Not Robot/Crawler",
        |    "Session_ID": "7bda1ef4a68d03aa8eb8c9aba4366a512fffbbc613322e93535c0cdb679a5272",
        |    "Referring_URL": "http://url.com/referrer",
        |    "Encoded_IP": "7bda1ef4a68d03aa8eb8c9aba4366a512fffbbc613322e93535c0cdb679a5272",
        |    "IP_ClassC": "10.20.30",
        |    "Item_Platform": "ThePlatform",
        |    "Item_ID": {
        |      "type": "DOI",
        |      "value": "10.1186/s13326-018-0176-y"
        |    },
        |    "Article_Version": "v2",
        |    "Org_ID": ["1234"]
        |  }
        |}
      """.stripMargin

    val validationResult: Try[Unit] = Try {
      JSONSchemaValidatorUtils.schemaValidator.validate(new JSONObject(json))
    }
    validationResult.isSuccess shouldBe true
  }

  it should "fail validation when JSON is not correct" in {

    val validationResult: Try[Unit] = Try {
      JSONSchemaValidatorUtils.schemaValidator.validate(new JSONObject(
        """{
          |  "foo": "bar"
          |}
        """.stripMargin))
    }
    validationResult.isFailure shouldBe true
  }

  it should "validate give hints about what's wrong on the JSON" in {
    val jsonWrongTransactionType =
      """
        |{
        |  "uuid": "e583eca0-fdf4-45ff-8c8e-2c3ce1196ea7",
        |  "message-type": "counter-transaction",
        |  "callback": "http://url.com/callback",
        |  "message": {
        |    "Transaction_ID": "a1a0fbd7-d280-4ab3-94fe-b060758aa28b",
        |    "Transaction_DateTime": "2017-06-06T12:28:57.001Z",
        |    "Transaction_Type": "BAD ONE",
        |    "Transaction_Access_Type": "Controlled",
        |    "Transaction_Access_Method": "Regular",
        |    "User_Agent": "Not Robot/Crawler",
        |    "Session_ID": "7bda1ef4a68d03aa8eb8c9aba4366a512fffbbc613322e93535c0cdb679a5272",
        |    "Referring_URL": "http://url.com/referrer",
        |    "Encoded_IP": "7bda1ef4a68d03aa8eb8c9aba4366a512fffbbc613322e93535c0cdb679a5272",
        |    "IP_ClassC": "10.20.30",
        |    "Item_Platform": "ThePlatform",
        |    "Item_ID": {
        |      "type": "DOI",
        |      "value": "10.1186/s13326-018-0176-y"
        |    },
        |    "Article_Version": "v2",
        |    "Org_ID": ["1234"]
        |  }
        |}
      """.stripMargin

    val caught = intercept[ValidationException] {
      JSONSchemaValidatorUtils.schemaValidator.validate(new JSONObject(jsonWrongTransactionType))
    }

    caught.getMessage should startWith ("#/message/Transaction_Type")
  }
}
