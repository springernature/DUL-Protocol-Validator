package com.springernature.dul

import java.net.URL
import java.time.ZonedDateTime
import java.util.UUID

import com.springernature.dul.DULJsonProtocol._
import org.json.JSONObject
import org.scalatest.{FlatSpec, Matchers}
import spray.json._

import scala.util.Try

class PayloadSpec extends FlatSpec with Matchers {

  behavior of "Payload"

  it should "deserialize a Json" in {

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

    val payload = json.parseJson.convertTo[Payload]

    val expectedMessage = Message(
      transactionId = "a1a0fbd7-d280-4ab3-94fe-b060758aa28b",
      transactionDateTime = ZonedDateTime.parse("2017-06-06T12:28:57.001Z"),
      transactionType = TransactionType.Request,
      transactionAccessType = TransactionAccess.Type.Controlled,
      transactionAccessMethod = TransactionAccess.Method.Regular,
      userAgent = "Not Robot/Crawler",
      sessionID = "7bda1ef4a68d03aa8eb8c9aba4366a512fffbbc613322e93535c0cdb679a5272",
      referringURL = Some(new URL("http://url.com/referrer")),
      encodedIP = "7bda1ef4a68d03aa8eb8c9aba4366a512fffbbc613322e93535c0cdb679a5272",
      IPClassC = IPClassC(10.toByte, 20.toByte, 30.toByte),
      itemPlatform = "ThePlatform",
      itemId = ItemID(ItemID.ItemTypeDOI, DOI("10.1186", "s13326-018-0176-y")),
      articleVersion = Some("v2"),
      orgID = Some(List("1234"))
    )

    val expectedPayload = Payload(UUID.fromString("e583eca0-fdf4-45ff-8c8e-2c3ce1196ea7"),
      MessageType.Transaction,
      new URL("http://url.com/callback"),
      expectedMessage)

    payload shouldBe expectedPayload
  }

  it should "serialize to Json" in {
    val message = Message(
      transactionId = "12312",
      transactionDateTime = ZonedDateTime.parse("2018-07-06T14:17:50.452Z"),
      transactionType = TransactionType.Request,
      transactionAccessType = TransactionAccess.Type.Controlled,
      transactionAccessMethod = TransactionAccess.Method.Regular,
      userAgent = "foo/bar",
      sessionID = "1231231",
      referringURL = Some(new URL("http://foo.com/bar")),
      encodedIP = "1231231",
      IPClassC = IPClassC(123.toByte, 21.toByte, 121.toByte),
      itemPlatform = "foo",
      itemId = ItemID(ItemID.ItemTypeDOI, DOI("23", "232")),
      articleVersion = Some("1"),
      orgID = None
    )

    val uuid = UUID.randomUUID()

    val payload = Payload(uuid, MessageType.Transaction, new URL("http://bar.com/foo"), message)

    val expectedPayload = s"""{
         |  "uuid": "$uuid",
         |  "message-type": "counter-transaction",
         |  "callback": "http://bar.com/foo",
         |  "message": {
         |    "User_Agent": "foo/bar",
         |    "Item_ID": {
         |      "type": "DOI",
         |      "value": "23/232"
         |    },
         |    "Article_Version": "1",
         |    "Transaction_Access_Type": "Controlled",
         |    "Transaction_DateTime": "2018-07-06T14:17:50.452Z",
         |    "Referring_URL": "http://foo.com/bar",
         |    "IP_ClassC": "123.21.121",
         |    "Item_Platform": "foo",
         |    "Transaction_ID": "12312",
         |    "Encoded_IP": "1231231",
         |    "Session_ID": "1231231",
         |    "Transaction_Type": "Request",
         |    "Transaction_Access_Method": "Regular"
         |  }
         |}""".stripMargin

    payload.toJson.prettyPrint shouldBe expectedPayload

  }

  it should "generate a JSON which is validated against the schema" in {
    val message = Message(
      transactionId = "12312",
      transactionDateTime = ZonedDateTime.parse("2018-07-06T14:17:50.452Z"),
      transactionType = TransactionType.Request,
      transactionAccessType = TransactionAccess.Type.Controlled,
      transactionAccessMethod = TransactionAccess.Method.Regular,
      userAgent = "foo/bar",
      sessionID = "1231231",
      referringURL = Some(new URL("http://foo.com/bar")),
      encodedIP = "1231231",
      IPClassC = IPClassC(123.toByte, 21.toByte, 121.toByte),
      itemPlatform = "foo",
      itemId = ItemID(ItemID.ItemTypeDOI, DOI("23", "232")),
      articleVersion = Some("1"),
      orgID = None
    )

    val uuid = UUID.randomUUID()

    val payload = Payload(uuid, MessageType.Transaction, new URL("http://bar.com/foo"), message)

    val validationResult: Try[Unit] = Try {
      JSONSchemaValidatorUtils.schemaValidator.validate(new JSONObject(payload.toJson.compactPrint))
    }
    validationResult.isSuccess shouldBe true
  }
}
