organization := "com.thoughtworks"

version := "1.0.0"

name := "hardposit"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:reflectiveCalls",
  "-Xsource:2.13"
)
scalaVersion := "2.13.8"
addCompilerPlugin(
  "edu.berkeley.cs" % "chisel3-plugin" % "3.5.3" cross CrossVersion.full
)

libraryDependencies += "edu.berkeley.cs" %% "chiseltest" % "0.5.3" % "test"
// Provide a managed dependency on X if -DXVersion="" is supplied on the command line.
// The following are the current "release" versions.
val defaultVersions = Map(
  "chisel3" -> "3.5.+",
  "chisel-iotesters" -> "2.+"
)
libraryDependencies ++= (Seq("chisel3", "chisel-iotesters").map { dep: String =>
  "edu.berkeley.cs" %% dep % sys.props
    .getOrElse(dep + "Version", defaultVersions(dep))
})

// Recommendations from http://www.scalatest.org/user_guide/using_scalatest_with_sbt
Test / logBuffered := false

// Disable parallel execution when running tests.
//  Running tests in parallel on Jenkins currently fails.
parallelExecution in Test := false
