scalaVersion := "3.9.0-RC4"

scalacOptions ++= {
  if(sys.props.get("parallelism") == Some("true")) {
    Seq("-Ybackend-parallelism:16")
  } else {
    Nil
  }
}

Compile / sourceGenerators += Def.task[Seq[File]] {
  val dir = (Compile / sourceManaged).value
  val max = 1000
  (1 to max).map { n =>
    val f = dir / s"A${n}.scala"
    IO.write(
      f,
      s"""
      |package example
      |
      |case class A${n}(
      |  x1: List[A${(n - 1) max 1}.B],
      |  x2: List[A${(n + 1) min max}.B],
      |)
      |
      |object A${n} {
      |  case class B(value: Int)
      |}
      |""".stripMargin
    )
    f
  }
}.taskValue
