package com.springernature.dul

object TransactionAccess {
  sealed trait Method {
    val value: String
  }

  object Method {

    case object TDM extends Method {
      override val value: String = "TDM"
    }

    case object Regular extends Method {
      override val value: String = "Regular"
    }

    val values: Seq[TransactionAccess.Method] = Seq(TDM, Regular)
  }

  sealed trait Type {
    val value: String
  }

  object Type {

    case object Controlled extends Type {
      override val value: String = "Controlled"
    }

    case object OAGold extends Type {
      override val value: String = "OA_Gold"
    }

    case object OtherFreeToRead extends Type {
      override val value: String = "Other_Free_to_Read"
    }

    val values: Seq[TransactionAccess.Type] = Seq(Controlled, OAGold, OtherFreeToRead)
  }
}




