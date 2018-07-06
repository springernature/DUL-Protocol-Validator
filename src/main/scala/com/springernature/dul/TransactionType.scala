package com.springernature.dul

sealed trait TransactionType {
  val value: String
}

object TransactionType {

  case object Denied extends TransactionType {
    override val value: String = "Access-Denied"
  }

  case object Request extends TransactionType {
    override val value: String = "Request"
  }

}
