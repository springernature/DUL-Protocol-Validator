package com.springernature.dul

import java.lang.{Byte => JByte}
import java.net.InetAddress

import scala.util.Try

case object IPClassC {
   def apply(ip: InetAddress): IPClassC = {
     new IPClassC(ip.getAddress()(0), ip.getAddress()(1), ip.getAddress()(2))
   }

  def toClassC(text: String): Option[IPClassC] = {
    val parts = text.split('.').map(t => Try{t.toShort}.getOrElse(-1.toShort)).filter(num => num >= 0 && num < 255)
    if (parts.length == 4) {
      Some(IPClassC(parts(0).toByte, parts(1).toByte, parts(2).toByte))
    } else None
  }
}

case class IPClassC(first: Byte, second: Byte, third: Byte) {
  override def toString: String = {
    s"${JByte.toUnsignedInt(first)}.${JByte.toUnsignedInt(second)}.${JByte.toUnsignedInt(third)}"
  }
}
