import sbt._
object Dependencies {
  object V {
    val ScalaScraper = "2.1.0"
    val Poi = "3.17"
    val XML = "1.0.6"
  }

  val scalaScrapper = Seq(
    "net.ruippeixotog" %% "scala-scraper" % V.ScalaScraper
  )
  val poi = Seq(
    "org.apache.poi" % "poi" % V.Poi,
    "org.apache.poi" % "poi-ooxml" % V.Poi
  )
  val XML = Seq(
    "org.scala-lang.modules" % "scala-xml_2.11" % V.XML
  )
}
