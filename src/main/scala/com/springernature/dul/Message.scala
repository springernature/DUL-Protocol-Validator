package com.springernature.dul

import java.net.URL
import java.time.ZonedDateTime

/*
"Transaction_ID",
        "Transaction_Type",
        "Transaction_DateTime",
        "Transaction_Access_Method",
        "User_Agent",
        "Session_ID",
        "Encoded_IP",
        "IP_ClassC",
        "Item_Platform",
        "Item_ID"
 */

case class Message(transactionId: String,
                   transactionDateTime: ZonedDateTime,
                   transactionType: TransactionType,
                   transactionAccessType: TransactionAccess.Type,
                   transactionAccessMethod: TransactionAccess.Method,
                   userAgent: String,
                   sessionID: String,
                   referringURL: Option[URL],
                   encodedIP: String,
                   IPClassC: IPClassC,
                   itemPlatform: String,
                   itemId: ItemID,
                   articleVersion: Option[String],
                   orgID: Option[List[String]])
