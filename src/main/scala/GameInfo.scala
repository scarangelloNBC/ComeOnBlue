case class GameInfo(homeTeam: String, awayTeam: String, homeUmp: String, pitches: Seq[PitchInfo], baseURL: String) {
  def stringFormat(): String = {
    val separator = "--------------------------------------------"
    s"$separator\nGAME INFO:\n${awayTeam} at ${homeTeam}\nHome plate umpire is ${homeUmp}\nNumber of poorly called pitches: ${pitches.length}\n" +
      s"Pitches:\n${pitches.map(_.stringFormat).mkString("\n")}\nAll game info can be found at ${baseURL}\n$separator\n"
  }
}
