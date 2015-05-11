(ns echo-byte.client
  "Client code, using Java 7 NIO.2.

  Abbreviations:
  a    = attachment
  asc  = AsynchronousSocketChannel
  n    = number of bytes
  sa   = socket address (InetSocketAddress)"
  (:refer-clojure :exclude (read))
  (:require
   [echo-byte.core :as core])
  (:import
   [java.nio
    ByteBuffer]
   [java.nio.channels
    AsynchronousSocketChannel
    CompletionHandler]))

(set! *warn-on-reflection* true)

(defn connect-handler
  []
  (proxy [CompletionHandler] []
    (completed
      [_ a]
      nil)
    (failed
      [e a]
      (.printStackTrace ^Throwable e))))

(defn connect
  "Returns a AsynchronousSocketChannel to host and port."
  [host port]
  (let [sa (core/socket-address host port)]
    (doto (AsynchronousSocketChannel/open)
      (.connect sa nil (connect-handler)))))

(declare read)

(defn read-handler
  [asc]
  (proxy [CompletionHandler] []
    (completed
      [n ^ByteBuffer buf]
      (read asc)
      (let [coll (seq (.array buf))]
        (print (format "%d " (first coll)))))
    (failed [^Throwable e buf]
      (.printStackTrace e))))

(defn read
  [^AsynchronousSocketChannel asc]
  (let [buf (ByteBuffer/allocate 1)]
    (.read asc buf buf (read-handler asc))))

(defn write-handler
  [asc]
  (proxy [CompletionHandler] []
    (completed
      [r a]
      (read asc))
    (failed
      [^Throwable e a]
      (.printStackTrace e))))

(defn write
  "Sends a ByteBuffer (buf) to connection. Returns nil."
  [^AsynchronousSocketChannel asc buf]
  (.write asc buf nil (write-handler asc)))
