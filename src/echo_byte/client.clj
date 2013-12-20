(ns echo-byte.client
  (:refer-clojure :exclude (read))
  (:require
    [echo-byte.core :as core])
  (:import
    [java.nio ByteBuffer]
    [java.nio.channels AsynchronousSocketChannel CompletionHandler]))

(set! *warn-on-reflection* true)

(defn connect-handler
  []
  (proxy [CompletionHandler] []
    (completed
      [r a]
      nil)
    (failed
      [e a]
      (.printStackTrace ^Throwable e))))

(defn connect
  [host port]
  (let [sa (core/socket-address host port)]
    (doto (AsynchronousSocketChannel/open)
      (.connect sa nil (connect-handler)))))

(declare read)

(defn read-handler
  [asc]
  (proxy [CompletionHandler] []
    (completed
      [n buf]
      (read asc)
      (let [coll (seq (.array ^ByteBuffer buf))]
          (print (format "%d " (first coll)))))
    (failed
      [e buf]
      (.printStackTrace ^Throwable e))))

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
      [e a]
      (.printStackTrace ^Throwable e))))

(defn write
  "Sends a ByteBuffer (buf) to connection. Returns"
  [^AsynchronousSocketChannel asc buf]
  (.write asc buf nil (write-handler asc)))
