import java.time.LocalDate

object ComeOnBlueApp extends App {
    val allGamesForDay = GameUtil.getAllGames(LocalDate.parse("2019-10-30")).sortWith {
    case (gameInfo1: GameInfo, gameInfo2: GameInfo) =>
      gameInfo1.pitches.length > gameInfo2.pitches.length
  }
  println(allGamesForDay.map(_.stringFormat).mkString(""))
}
