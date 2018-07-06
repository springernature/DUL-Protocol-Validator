package com.springernature.dul

import java.net.InetAddress

import org.scalatest.{FlatSpec, Matchers, OptionValues}

class IPClassCSpec extends FlatSpec with Matchers with OptionValues {

  behavior of "IPClassC"

  it should "convert an InetAddress to IPClassC" in {
    val inet = InetAddress.getByAddress(Array(123.toByte,234.toByte,2.toByte,1.toByte))

    IPClassC(inet).toString shouldBe "123.234.2"
  }

  it should "convert a String to a class C IP" in {
    val validIP = "142.223.1.2"
    val invalidIP = "443.234.270.236"

    IPClassC.toClassC(validIP).value.toString shouldBe "142.223.1"
    IPClassC.toClassC(invalidIP) shouldBe None
  }

  it should "create Ip Class C directly" in {
    IPClassC(123.toByte, 234.toByte, 2.toByte).toString shouldBe "123.234.2"
  }

  it should "equalize equivalent IP Class C" in {
    val one = InetAddress.getByAddress(Array(123.toByte,234.toByte,2.toByte,1.toByte))
    val other = InetAddress.getByAddress(Array(123.toByte,234.toByte,2.toByte,132.toByte))

    IPClassC(one) shouldBe IPClassC(other)
  }
}
