
case class PitchInfo(pitchResult: String, sz_top: Double, sz_bot: Double, px: Double, pz: Double, inning: Int, atBatResult: String) {
  def stringFormat(): String = {
    val sep = "------------------------------------"
    s"\t$sep\n\tInning: $inning\n\tPitch Ruling: $pitchResult\n\tX Distance From Center Plate: $px\n\tZ Distance From Center Plate: $pz\n\tStrike Zone Z Limits: $sz_bot, $sz_top\n\tAt Bat Result: $atBatResult\n\t$sep"
  }
}
