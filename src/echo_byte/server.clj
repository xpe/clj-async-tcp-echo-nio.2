(ns echo-byte.server
  "Server code, using Java 7 NIO.2.

  Abbreviations:
  a    = attachment
  asc  = AsynchronousSocketChannel
  assc = AsynchronousServerSocketChannel
  sa   = socket address (InetSocketAddress)"
  (:refer-clojure :exclude (read))
  (:require
    [echo-byte.core :as core])
  (:import
    [java.nio ByteBuffer]
    [java.nio.channels AsynchronousSocketChannel
     AsynchronousServerSocketChannel CompletionHandler]))

(defn bind
  "Returns an AsynchronousServerSocketChannel."
  [host port]
  (let [assc (AsynchronousServerSocketChannel/open)
        sa (core/socket-address host port)]
    (.bind assc sa)))

(declare read)

(defn write-handler
  [asc]
  (proxy [CompletionHandler] []
    (completed
      [n a]
      (println "write-handler completed")
      (println "n:" n "a:" a)
      (read asc))
    (failed
      [e a]
      (println "write-handler failed")
      (.printStackTrace ^Throwable e))))

(defn read-handler
  [asc]
  (proxy [CompletionHandler] []
    (completed
      [n rbuf]
      (println "read-handler completed")
      (println "n:" n "rbuf:" (seq (.array rbuf)))
      (let [wbuf (.duplicate rbuf)]
        (.rewind wbuf)
        (.write asc wbuf nil (write-handler asc))))
    (failed
      [e rbuf]
      (println "read-handler failed")
      (.printStackTrace ^Throwable e))))

(defn read
  [asc]
  (let [buf (ByteBuffer/allocate 1)]
    (.read asc buf buf (read-handler asc))))

(declare accept)

(defn accept-handler
  "Inside the CompletionHandler, AsynchronousSocketChannel"
  [assc]
  (proxy [CompletionHandler] []
    (completed
      [asc a]
      (accept assc)
      (println "accept-handler completed")
      (read asc))
    (failed
      [e a]
      (accept assc)
      (println "accept-handler failed")
      (.printStackTrace ^Throwable e))))

(defn accept
  "Accepts an AsynchronousServerSocketChannel. Returns nil."
  [assc]
  (println "accept")
  (.accept assc nil (accept-handler assc)))
