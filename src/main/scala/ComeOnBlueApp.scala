import java.time.LocalDate

object ComeOnBlueApp extends App {
  println(GameUtil.getAllPitchesFromGame(GameUtil.getAllGames(LocalDate.parse("2019-04-05")).head)
    .get
    .filter(_.isDefined)
    .mkString("\n"))
}
