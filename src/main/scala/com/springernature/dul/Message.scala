package com.springernature.dul

import java.net.URL
import java.time.ZonedDateTime

case class Message(transactionId: String,
                   transactionDateTime: ZonedDateTime,
                   transactionType: TransactionType,
                   transactionAccessType: TransactionAccess.Type,
                   transactionAccessMethod: TransactionAccess.Method,
                   userAgent: String,
                   sessionID: String,
                   referringURL: URL,
                   encodedIP: String,
                   IPClassC: IPClassC,
                   itemPlatform: String,
                   itemId: ItemID,
                   articleVersion: String,
                   orgID: Array[String])
