import java.time.LocalDate

object ComeOnBlueApp extends App {
  val allGamesForDay = GameUtil.getAllGames(LocalDate.parse("2019-10-30"))
  val allPitchesForDay = allGamesForDay.map {
    case dayURL: String =>
      GameUtil.getAllPitchesFromGame(dayURL)
  }

  val allObjects = allPitchesForDay.flatMap {
    _.filter(_.isDefined).map(_.get)
  }

  println(allObjects.mkString("\n"))
  println(allObjects.length)
  val badPitches = allObjects.filter {
    case pitch: PitchInfo =>
      PitchUtil.badPitch(pitch)
  }
  println(badPitches.mkString("\n"))
  println(badPitches.length)

}
