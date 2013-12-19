(ns echo-byte.client
  (:refer-clojure :exclude (send))
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
      [r a]
      (println "connect-handler completed")
      )
    (failed
      [e a]
      (println "connect-handler failed")
      (.printStackTrace ^Throwable e))))

(defn connect
  [host port]
  (let [sa (core/socket-address host port)]
    (doto (AsynchronousSocketChannel/open)
      (.connect sa nil (connect-handler)))))

(defn send-handler
  [ch]
  (proxy [CompletionHandler] []
    (completed
      [r a]
      (println "send-handler completed"))
    (failed
      [e a]
      (println "send-handler failed")
      (.printStackTrace ^Throwable e))))

(defn send
  "Sends a ByteBuffer (buf) to connection. Returns"
  [^AsynchronousSocketChannel ch buf]
  (.write ch buf nil (send-handler ch)))
