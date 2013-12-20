clj-async-tcp-echo-nio.2
========================

An asynchrononous TCP echo client/server in Clojure using Java 7 NIO.2.

The Java documentation is thorough, if not necessarily the best source of
examples. You can get the background you need with
[AsynchronousSocketChannel][ASC],
[ByteBuffer][BB],
[CompletionHandler][CH], and
[AsynchronousServerSocketChannel][ASSC].

[ASC]: http://docs.oracle.com/javase/7/docs/api/java/nio/channels/AsynchronousSocketChannel.html
[BB]: http://docs.oracle.com/javase/7/docs/api/java/nio/ByteBuffer.html
[ASSC]: http://docs.oracle.com/javase/7/docs/api/java/nio/channels/AsynchronousServerSocketChannel.html
[CH]: http://docs.oracle.com/javase/7/docs/api/java/nio/channels/CompletionHandler.html

## Usage

In one REPL, start a server:

```clj
(def s (server/bind "127.0.0.1" 9500))
(server/accept s)
```

(If you [pick the wrong port][port], you'll get an error.)

In another REPL, start a client:

```clj
(def c (client/connect "127.0.0.1" 9500))
(time (client/write c (core/rand-byte-buffer 100000)))
```

Note that you will see something like:

> "Elapsed time: 127.451 msecs"

And the REPL will return `nil`, but the TCP activity will continue:

```sh
22 99 -101 13 57 -27 69 115 6 -75 103 5 20 -38 -104 -125 127 20 -124 -19 37
88 -91 93 -67 -31 -99 31 95 -12 120 60 -101 125 -110 -109 -113 3 -116 -98
...
-53 63 66 52 75 -65 121 93 106 29 114 118 106 1 -57 -43 94 -125 49 -98 -37
21 64 92 108 -76 38 76 -100 -35 -107 -39 -65 60 27 67 -113 -6
```

That's because we're using asynchronous calls, of course!

Did you [dig the port you chose][dig]?

## Commentary

Asynchronous IO in Java has quite a history, and I'm no historian. I created
this example to demonstrate how to work with Java 7's NIO.2. It seems much
cleaner that its predecessor, NIO.

So, you might try NIO.2 before you assume that you need a layer on top, such
as [Netty].

This example also gives you a nice taste of [callback hell][hell].

[port]: http://www.youtube.com/watch?v=diC-mWSuo6I
[hell]: http://www.youtube.com/watch?v=EuxW7-Nhr6w
[dig]: http://www.youtube.com/watch?v=diC-mWSuo6I#t=1m30
[Netty]: http://netty.io/

## License

Copyright 2013 Bluemont Labs LLC

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
