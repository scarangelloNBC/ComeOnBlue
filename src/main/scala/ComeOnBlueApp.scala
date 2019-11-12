import java.time.LocalDate

object ComeOnBlueApp extends App {
  val allGamesForDay = GameUtil.getAllGames(LocalDate.parse("2019-04-05"))
  val allPitchesForDay = allGamesForDay.map {
    case dayURL: String =>
      GameUtil.getAllPitchesFromGame(dayURL)
  }

  val allObjects = allPitchesForDay.flatMap {
    _.filter(_.isDefined).map(_.get)
  }

  println(allObjects.mkString("\n"))
}
