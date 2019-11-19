import java.time.LocalDate

import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.browser._

import scala.xml.{Elem, NodeSeq, XML}

object GameUtil {

  def getAllGames(date: LocalDate): Seq[GameInfo] = {
    val baseUrl = s"http://gd2.mlb.com/components/game/mlb/year_${date.getYear}/month_${"%02d".format(date.getMonthValue)}/day_${"%02d".format(date.getDayOfMonth)}"
    val browser = JsoupBrowser()
    val htmlDoc = browser.get(baseUrl)
    val allLinks = htmlDoc >> elementList("a")
    val gids = allLinks.map(_ >> allText("a")).filter(_.contains("gid"))
    gids.map {
      case gid: String =>
        val baseURL = s"$baseUrl/$gid"
        val playersDoc = XML.load(s"${baseURL}players.xml")
        val gameEventsDoc = XML.load(s"${baseURL}game_events.xml")
        val homeTeam = getTeam(playersDoc, "home")
        val awayTeam = getTeam(playersDoc, "away")
        val homeUmp = getHomeUmp(playersDoc)
        val pitches = PitchUtil.getAllPitchesFromGame(gameEventsDoc).filter(_.isDefined).map(_.get)
        val filteredPitches = pitches.filter {
          case pitch: PitchInfo =>
            PitchUtil.badPitch(pitch)
        }
        GameInfo(homeTeam, awayTeam, homeUmp, filteredPitches, baseURL)
    }
  }

  def getTeam(xmlDoc: Elem, teamType: String): String = {
    val teams = xmlDoc \\ "team"
    val specificTeam = teams.filter{case node: NodeSeq => (node \ "@type").toString == teamType}
    (specificTeam \\ "@name").toString
  }

  def getHomeUmp(xmlDoc: Elem): String = {
    val umps = xmlDoc \\ "umpire"
    val homeUmp = umps.filter{case node: NodeSeq => (node \ "@position").toString == "home"}
    (homeUmp \\ "@name").toString
  }
}
