package com.springernature.dul


import java.net.URL
import java.time.format.DateTimeFormatter
import java.time.{ZoneId, ZonedDateTime}

import spray.json._

import scala.util.Try

object DULJsonProtocol extends DefaultJsonProtocol {

  implicit object TransactionTypeFormat extends RootJsonFormat[TransactionType] {

    val errorMessage = s"TransactionType expected. Accepted values ${TransactionType.values.map(_.value).mkString(",")}"

    override def write(obj: TransactionType): JsValue = JsString(obj.value)

    override def read(json: JsValue): TransactionType = json match {
      case JsString(value) => TransactionType.values.find(_.value == value).getOrElse(deserializationError(errorMessage))
      case _ => deserializationError(errorMessage)
    }
  }

  implicit object TransactionAccessMethodFormat extends RootJsonFormat[TransactionAccess.Method] {
    val errorMessage = s"TransactionAccessMethod expected. Accepted values ${TransactionAccess.Method.values.map(_.value).mkString(",")}"

    override def read(json: JsValue): TransactionAccess.Method = json match {
      case JsString(value) => TransactionAccess.Method.values.find(_.value == value).getOrElse(deserializationError(errorMessage))
      case _ => deserializationError(errorMessage)
    }

    override def write(obj: TransactionAccess.Method): JsValue = JsString(obj.value)
  }

  implicit object TransactionAccessTypeFormat extends RootJsonFormat[TransactionAccess.Type] {
    val errorMessage = s"TransactionAccessType expected. Accepted values ${TransactionAccess.Type.values.map(_.value).mkString(",")}"

    override def read(json: JsValue): TransactionAccess.Type = json match {
      case JsString(value) => TransactionAccess.Type.values.find(_.value == value).getOrElse(deserializationError(errorMessage))
      case _ => deserializationError(errorMessage)
    }

    override def write(obj: TransactionAccess.Type): JsValue = JsString(obj.value)
  }

  implicit object MessageTypeFormat extends RootJsonFormat[MessageType] {
    val errorMessage = s"MessageType expected. Accepted values ${MessageType.values.map(_.value).mkString(",")}"

    override def read(json: JsValue): MessageType = json match {
      case JsString(value) => MessageType.values.find(_.value == value).getOrElse(deserializationError(errorMessage))
      case _ => deserializationError(errorMessage)
    }

    override def write(obj: MessageType): JsValue = JsString(obj.value)
  }


  implicit object IPClassCFormat extends RootJsonFormat[IPClassC] {

    override def read(json: JsValue): IPClassC = json match {
      case JsString(value) => IPClassC.toClassC(value).getOrElse(deserializationError(s"The value $value doesn't look like an IP"))
      case _ => deserializationError("IPClassC expected")
    }

    override def write(obj: IPClassC): JsValue = JsString(obj.toString)
  }


  implicit object ZonedDateTimeFormat extends RootJsonFormat[ZonedDateTime] {

    val dateTimeFormat: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME


    override def read(json: JsValue): ZonedDateTime = json match {
      case JsString(value) => Try{ZonedDateTime.parse(value)}.getOrElse(deserializationError("Wrong format for a timestamp"))
      case _ => deserializationError("Date Time expected")
    }

    override def write(obj: ZonedDateTime): JsValue = JsString(obj.withZoneSameInstant(ZoneId.of("UTC")).format(dateTimeFormat))
  }

  implicit object URLFormat extends RootJsonFormat[URL] {
    override def write(obj: URL): JsValue = JsString(obj.toExternalForm)

    override def read(json: JsValue): URL = json match {
      case JsString(value) => new URL(value)
      case _ => deserializationError("URL expected")
    }
  }

  implicit object ItemTypeFormat extends RootJsonFormat[ItemID.ItemType] {

    override def write(obj: ItemID.ItemType): JsValue = JsString(obj.value)

    override def read(json: JsValue): ItemID.ItemType = json match {
      case JsString(value) => ItemID.values.find(_.value == value).getOrElse(deserializationError("DOI expected"))

      case _ => deserializationError("DOI expected")
    }
  }

  implicit object DOIFormat extends RootJsonFormat[DOI] {
    override def write(obj: DOI): JsValue = JsString(obj.toString)

    override def read(json: JsValue): DOI = json match {
      case JsString(value) => DOI.parse(value).getOrElse(deserializationError("Expected a DOI value"))
      case _ => deserializationError("DOI value expected")
    }
  }

  implicit val ItemIDFormat: RootJsonFormat[ItemID] = jsonFormat2(ItemID.apply)

  implicit val MessageFormat: RootJsonFormat[Message] = jsonFormat(Message,
    "Transaction_ID",
    "Transaction_DateTime",
    "Transaction_Type",
    "Transaction_Access_Type",
    "Transaction_Access_Method",
    "User_Agent",
    "Session_ID",
    "Referring_URL",
    "Encoded_IP",
    "IP_ClassC",
    "Item_Platform",
    "Item_ID",
    "Article_Version",
    "Org_ID"
  )

}
