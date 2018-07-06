package com.springernature.dul

object MessageType {

  case object Transaction extends MessageType {
    override val value: String = "counter-transaction"
  }

  case object Summary extends MessageType {
    override val value: String = "counter-summary"
  }
}

sealed trait MessageType {
  val value: String
}
