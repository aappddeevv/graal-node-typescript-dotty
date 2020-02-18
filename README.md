# graal.js, node, typescript and dotty

Combined graal.js, node, typescript and dotty test and writing a "plugin".

Obviously, you must make sure you run this with graal. See the .jvmopts for parameters
you need to use graal when bundled your jdk is bundled with graal.
Or you can use graal directly by setting up your environment appropriately, for
example, using sdkman and explicitly use a graal release.

A .jvmopts is included in the project directory that should activate graal if you run a jvm
bundle with graal. I ran this on graal explicitly so if the .jvmopts is not quite right for you,
please adjust.

You _must_ run on graal >= 20.0.0 if you import npm packages that have methods called "static."

Run `node --show-version:graalvm` to observe that node is running on graal.

# Running

- Make sure graal node is the default: `node --show-version:graalvm`
- Install node dependencies: `npm i`
  - Graal's node is slow for install npm packages.
- Compile the typescript program: `npx tsc`
- Force npm chalk to output terminal codes: `export FORCE_COLOR=3`
- Run the node program driver.ts via the sbt command: `sbt crazy`.

`crazy` is an alias that compiles the dotty/java sources and runs
node. You must ensure that the graal "node" is configured to be used
in the same shell that you are running sbt in.

The sbt task sets node (the graal version) to run with the proper classpath. You could
also setup a task to dump the classpath and run node from your shell CLI. You may also choose to
bundle the dotty program into a fatjar with all dotty (scala 2.13, dotty and your project
class files) included and put the fatjar on the classpath.

# Development

Start dotty via `sbt` and using the `launchIDE` command.

1. Run the code generator in sbt using `~crazy` which will watch all source
   files for changes including `driver.js` the output of typescript compilation.
2. Run the typescript compiler, tsc, in another shell or in a vs code shell to
   continuously update the typescript program.

```sh
npx tsc -watch
```

You can also put all the typescript resources under src/main/js and change tsconfig.json
to includes that directory. Or you can add a `tsc` step to the `crazy` command and
watch `driver.ts` but all of this is up to you.

# Misc

Starting up graal on the jvm takes awhile before js code runs faster. If you are
just starting a node process and doing some IO multiple times, you will pay this
startup price, which is large, multiple times. Expect the polyglut process to
take several seconds to really start.

Links:

- graal API to access values from JS; https://www.graalvm.org/truffle/javadoc/index.html?org/graalvm/polyglot/Context.html
- dotty structural types: https://dotty.epfl.ch/docs/reference/changed-features/structural-types.html
- latest graal releases including binaries: https://github.com/graalvm/graalvm-ce-builds/releases

sdkman may or may not have the latest release available through its publishing mechanism.

# License

MIT license.
