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

}
