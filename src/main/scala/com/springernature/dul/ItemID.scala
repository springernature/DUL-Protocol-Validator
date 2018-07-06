package com.springernature.dul

import com.springernature.dul.ItemID.ItemType

object ItemID {

  sealed trait ItemType {
    val value: String
  }

  case object ItemTypeDOI extends ItemType {
    override val value: String = "DOI"
  }

  val values: Seq[ItemType] = Seq(ItemTypeDOI)

}


case class ItemID(`type`: ItemType, value: DOI) {

}
