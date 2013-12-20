clj-async-tcp-echo-nio.2
========================

An asychrononous TCP echo client/server in Clojure using Java 7 NIO.2.

The Java documentation is thorough, if not necessarily the best source of
examples. You can get the background you need with [AsynchronousSocketChannel][ASC], [ByteBuffer][BB], [AsynchronousServerSocketChannel][ASSC], and [CompletionHandler][CH].

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

In another REPL, start a client:

```clj
(def c (client/connect "127.0.0.1" 9500))
(time (client/write c (core/rand-byte-buffer 100000)))
```

## License

Copyright 2013 David James

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
