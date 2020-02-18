import "source-map-support/register";
import { Java, Polyglot } from "graalts";
import { bold } from "chalk";

// I'm using globalThis but you don't need to if you are
// running js code in a top-level script like this one.

// If you were to import a script that accesses the graal objects, you would
// use globalThis like below in that script because node's "require"
// sets "global" to something different than "global"--yeah that's confusing.

if (typeof globalThis.Java === "undefined") {
  console.log("Graal Java object is not defined.");
  throw new Error("GraalVM is required for JVM interop.");
}

function foo() {
  return "foo is king!";
}

// me lazy so using any
const SPlugin: any = globalThis.Java.type("app.SPlugin");
const JPlugin: any = globalThis.Java.type("app.JPlugin");

// polyglot bindings are *not* toplevel bindings
globalThis.Polyglot.export("foo", foo);

const msg = `
** Test Messages **
Java: ${JPlugin.run()}
Dotty/Scala 3: ${SPlugin.run({ kind: bold("Node"), value: 10 })}
  `;

console.log("Program output", msg);
