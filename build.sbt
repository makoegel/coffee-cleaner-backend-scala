val Http4sVersion = "0.18.9"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"
val circeVersion = "0.9.3"
val flywayVersion = "5.1.1"
val h2Version = "1.4.196"
val doobieVersion = "0.5.3"

lazy val root = (project in file("."))
  .settings(
    organization := "io.github.makoegel",
    name := "coffee-cleaner-backend-scala",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.5",
    libraryDependencies ++= Seq(
      "com.h2database" % "h2" % h2Version,
      "org.flywaydb" % "flyway-core" % flywayVersion,
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "org.tpolecat" %% "doobie-core" % doobieVersion,
      "org.tpolecat" %% "doobie-hikari" % doobieVersion,
      "org.tpolecat" %% "doobie-refined" % doobieVersion,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      // Optional for auto-derivation of JSON codecs
      "io.circe" %% "circe-generic" % circeVersion,
      // Optional for string interpolation to JSON model
      "io.circe" %% "circe-literal" % circeVersion,
      // test dependencies
      "org.specs2" %% "specs2-core" % Specs2Version % Test,
      "org.tpolecat" %% "doobie-specs2" % doobieVersion % Test
    )
  )

