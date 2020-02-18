// plugin for scala called from node.js via graal

package app
import org.graalvm.polyglot._

case class JSValue(o: Value) extends Selectable {
    def selectDynamic(name: String): Value = o.getMember(name)
}

type ASTNode = JSValue { 
    val kind: Value
    val value: Value
}

val context = Context.getCurrent()
val jsBindings = context.getBindings("js")
val jsPolyBindings = context.getPolyglotBindings()

object SPlugin {
    def run(value: Value): String = {
        println(s"raw object $value (note string value has terminal codes from chalk in it)")
        println(s"""kind member ${value.getMember("kind")}""")
        val j = JSValue(value).asInstanceOf[ASTNode]
        println(s"from Selectable ASTNode ${j.kind.asString()}, ${j.value.asInt()}")

        println("Trying to access polyglot (not the top-level language) bindings")
        val foo = jsPolyBindings.getMember("foo")
        println(s"js function foo object from polyglot bindings: '$foo'")
        val x = foo.execute()
        println(s"foo result '${x.asString()}'")

        "Code from dotty !" 
    }
}