package com.springernature.dul

sealed trait MessageType {
  val value: String
}

object MessageType {

  case object Transaction extends MessageType {
    override val value: String = "counter-transaction"
  }

  case object Summary extends MessageType {
    override val value: String = "counter-summary"
  }

  val values: Seq[MessageType] = Seq(Transaction, Summary)
}
