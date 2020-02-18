import scala.sys.process._
import scala.util.Properties._
import sbt.Keys.streams

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val root = project.in(file("."))
.settings(
	name := "graal-node-typescript-dotty",
	description := "Test graal, node, typescript and dotty interop.",
	scalaVersion := "0.22.0-RC1",
	watchSources += baseDirectory.value / "dist" / "driver.js",
	scalacOptions ++= Seq(
		"-Yindent-colons",
		"-indent",
		"-strict",
		"-language:strictEquality"
	)
)

val crazy = taskKey[Unit]("Run graal's node executable with the Compile classpath.")

crazy := {
	val log = streams.value.log
	// compile scala sources first
	(Compile / compile).value
	log.info("Running on change...")
	val classpath = (Compile / compile / fullClasspath).value
	
	val classpath_list = classpath.map(_.data.getAbsolutePath).mkString(scalaPropOrElse("path.separator",":"))
	log.info(s"Classpath $classpath_list")
	Process(
		Seq("node",
		"--jvm",
		"--vm.cp",
		classpath_list,
		"dist/driver.js"),
		None)!
}
