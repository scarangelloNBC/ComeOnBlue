import scala.xml.{Node, XML}

object PitchUtil {

  private val ballDiameter = 0.25
  private val plateWidth = 1.417

  def badPitch(pitch: PitchInfo): Boolean = {
    pitch.pitchResult match {
      case "Called Strike" =>
        verticalCheck(pitch.sz_top, pitch.sz_bot, pitch.pz, pitch.pitchResult) || horizontalCheck(pitch.px, pitch.pitchResult)
      case "Ball" =>
        verticalCheck(pitch.sz_top, pitch.sz_bot, pitch.pz, pitch.pitchResult) && horizontalCheck(pitch.px, pitch.pitchResult)
    }
  }

  def verticalCheck(topLimit: Double, botLimit: Double, pz: Double, description: String): Boolean = {
    description match {
      case "Called Strike" =>
        pz > (topLimit + (ballDiameter * 2.5)) || pz < (botLimit - (ballDiameter * 2.5))
      case "Ball" =>
        pz < (topLimit - (ballDiameter * 1.5)) && pz > (botLimit + (ballDiameter * 1.5))
    }
  }

  def horizontalCheck(px: Double, description: String): Boolean = {
    description match {
      case "Called Strike" =>
        px.abs > ((plateWidth / 2) + ((ballDiameter * 2.5) / 2))
      case "Ball" =>
        px.abs < ((plateWidth / 2) - ((ballDiameter * 1.5) / 2))
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
