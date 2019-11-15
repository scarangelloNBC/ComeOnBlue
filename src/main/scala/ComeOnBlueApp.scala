import java.time.LocalDate

object ComeOnBlueApp extends App {
  val allGamesForDay = GameUtil.getAllGames(LocalDate.parse("2019-10-30"))
  println(allGamesForDay.map(_.stringFormat).mkString(""))
}
