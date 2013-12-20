(ns echo-byte.core
  (:import
    [java.net InetSocketAddress]))

(defn socket-address
  [^String host ^Integer port]
  (InetSocketAddress. host port))

(defn byte-buffer
  [coll]
  (-> (map byte coll)
      (byte-array)
      (java.nio.ByteBuffer/wrap)))

(defn rand-byte
  []
  (byte (- (rand-int 256) 128)))

(defn rand-byte-buffer
  [n]
  (-> (repeatedly n rand-byte)
      (byte-array)
      (java.nio.ByteBuffer/wrap)))
