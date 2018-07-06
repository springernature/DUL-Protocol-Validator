package com.springernature.dul

import java.net.URL
import java.time.{ZoneId, ZonedDateTime}

import org.scalatest.{FlatSpec, Matchers}

import spray.json._
import DULJsonProtocol._

class MessageSpec extends FlatSpec with Matchers {

  behavior of "Message"

  it should "deserialize a Json" in {

    val json = """
      |{
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
      |    "Item_Platform": "Mendeley",
      |    "Item_ID": {
      |      "type": "DOI",
      |      "value": "10.1186/s13326-018-0176-y"
      |    },
      |    "Article_Version": "v2",
      |    "Org_ID": ["1234"]
      |}
    """.stripMargin

    val message = json.parseJson.convertTo[Message]

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
      itemPlatform = "Mendeley",
      itemId = ItemID(ItemID.ItemTypeDOI, DOI("10.1186", "s13326-018-0176-y")),
      articleVersion = Some("v2"),
      orgID = Some(List("1234"))
    )

    message shouldBe expectedMessage
  }

  it should "serialize a Message to Json" in {
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

    val expectedMessage = """{
                            |  "User_Agent": "foo/bar",
                            |  "Item_ID": {
                            |    "type": "DOI",
                            |    "value": "23/232"
                            |  },
                            |  "Article_Version": "1",
                            |  "Transaction_Access_Type": "Controlled",
                            |  "Org_ID": [],
                            |  "Transaction_DateTime": "2018-07-06T14:17:50.452Z",
                            |  "Referring_URL": "http://foo.com/bar",
                            |  "IP_ClassC": "123.21.121",
                            |  "Item_Platform": "foo",
                            |  "Transaction_ID": "12312",
                            |  "Encoded_IP": "1231231",
                            |  "Session_ID": "1231231",
                            |  "Transaction_Type": "Request",
                            |  "Transaction_Access_Method": "Regular"
                            |}""".stripMargin

    message.toJson.prettyPrint shouldBe expectedMessage

  }
}
