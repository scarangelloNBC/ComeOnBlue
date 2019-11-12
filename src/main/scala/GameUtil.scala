import java.time.LocalDate

import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.browser._

import scala.xml.{Node, XML}

object GameUtil {


  def getAllGames(date: LocalDate): Seq[String] = {
    val baseUrl = s"http://gd2.mlb.com/components/game/mlb/year_${date.getYear}/month_${"%02d".format(date.getMonthValue)}/day_${"%02d".format(date.getDayOfMonth)}"
    val browser = JsoupBrowser()
    val htmlDoc = browser.get(baseUrl)
    val allLinks = htmlDoc >> elementList("a")
    val gids = allLinks.map(_ >> allText("a")).filter(_.contains("gid"))
    gids.map {
      case gid: String =>
        s"$baseUrl/$gid"
    }
  }

  def getAllPitchesFromGame(gameLink: String): Seq[Option[PitchInfo]] = {
    try {
      val xmlDoc = XML.load(s"${gameLink}game_events.xml")
      val pitches = xmlDoc \\ "pitch"
      pitches.map {
        case pitch: Node =>
          if((pitch \ "@des").nonEmpty && (pitch \ "@sz_top").nonEmpty && (pitch \ "@sz_bot").nonEmpty && (pitch \ "@px").nonEmpty && (pitch \ "@pz").nonEmpty &&
            ((pitch \ "@des").toString == "Ball" || (pitch \ "@des").toString == "Called Strike")) {
            Some(PitchInfo((pitch \\ "@des").toString, (pitch \\ "@sz_top").toString.toDouble, (pitch \\ "@sz_bot").toString.toDouble, (pitch \ "@px").toString.toDouble, (pitch \ "@pz").toString.toDouble))
          }
          else {
            None
          }
      }
    }
    catch {
      case exception: Throwable =>
        exception.printStackTrace
        Seq(None)
    }
  }

}
