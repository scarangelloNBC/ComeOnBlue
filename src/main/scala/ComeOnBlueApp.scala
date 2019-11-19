import java.time.LocalDate

object ComeOnBlueApp extends App {
  val allGamesForDay = GameUtil.getAllGames(LocalDate.parse("2019-05-20"))
  println(allGamesForDay.map(_.stringFormat).mkString(""))
}
