package com.springernature.dul

import org.scalatest.{FlatSpec, Matchers, OptionValues}

class DOISpec extends FlatSpec with Matchers with OptionValues {

  behavior of "DOI"

  it should "let create a class using prefix and suffix" in {
    val preffix = "foo"
    val suffix = "bar"
    val doi = DOI(preffix, suffix)

    doi.toString shouldBe s"$preffix/$suffix"
  }

  it should "parse correct DOI strings" in {
    val doiString = "10.1186/s13326.018.0176.y"

    DOI.parse(doiString).value.toString shouldBe doiString
  }

  it should "parse correct DOI strings ignoring case" in {
    val doiString = "10.1186/S13326.018.0176.Y"

    DOI.parse(doiString).value.toString shouldBe doiString.toLowerCase
  }

  it should "parse early DOI strings" in {
    val earlyDOI = "10.1002/6.!@#$%^&*()_+=][{}\\/-y"

    DOI.parse(earlyDOI).value.toString shouldBe earlyDOI.toLowerCase
  }

  it should "parse different legacy DOI formats" in {
    val legacy1DOI = "10.1234/3-3X34<foo:2r>3.4.a;2"
    val legacy2DOI = "10.1021/ab234"
    val legacy3DOI = "10.1207/foo123&3_3"

    DOI.parse(legacy1DOI).value.toString shouldBe legacy1DOI.toLowerCase
    DOI.parse(legacy2DOI).value.toString shouldBe legacy2DOI.toLowerCase
    DOI.parse(legacy3DOI).value.toString shouldBe legacy3DOI.toLowerCase
  }

  it should "not parse incorrect DOI Strings" in {
    val wrongDOI = "xx.23/s32.323"

    DOI.parse(wrongDOI) shouldBe None
  }

}
