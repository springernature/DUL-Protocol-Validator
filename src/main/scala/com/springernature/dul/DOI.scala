package com.springernature.dul

object DOI {
  def parse(text: String): Option[DOI] = {
    val modernDOI = """^(10\.\d{4,9})\/([-._;()/:a-z0-9]+)$""".r
    val earlyDOI = """^(10\.1002)\/([^\s]+)$""".r
    val legacy1DOI = """^(10\.\d{4})\/(\d+-\d+x?\d+\d+<[\d\w]+:[\d\w]*>\d+\.\d+\.\w+;\d)$""".r
    val legacy2DOI = """^(10\.1021)\/(\w\w\d+)$""".r
    val legacy3DOI = """^(10\.1207)\/([\w\d]+\&\d+_\d+)$""".r

    text.toLowerCase match {
      case modernDOI(prefix, suffix) => Some(DOI(prefix, suffix))
      case earlyDOI(prefix, suffix) => Some(DOI(prefix, suffix))
      case legacy1DOI(prefix, suffix) => Some(DOI(prefix, suffix))
      case legacy2DOI(prefix, suffix) => Some(DOI(prefix, suffix))
      case legacy3DOI(prefix, suffix) => Some(DOI(prefix, suffix))
      case _ => None

    }
  }
}

case class DOI(prefix: String, suffix: String) {
  override def toString: String = s"$prefix/$suffix"
}

