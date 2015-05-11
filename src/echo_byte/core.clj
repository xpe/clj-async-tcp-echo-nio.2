(ns echo-byte.core
  (:import
   [java.net InetSocketAddress]))

(defn socket-address
  "Returns a InetSocketAddress for supplied host and port."
  [^String host ^Integer port]
  (InetSocketAddress. host port))

(defn byte-buffer
  "Returns a ByteBuffer containing a collection. Each item of the
  collection must be an integer ranging from -128 to 127."
  [coll]
  (-> (map byte coll)
      (byte-array)
      (java.nio.ByteBuffer/wrap)))

(defn rand-byte
  "Returns a random byte."
  []
  (byte (- (rand-int 256) 128)))

(defn rand-byte-buffer
  "Returns a ByteBuffer of length n with random contents."
  [n]
  (-> (repeatedly n rand-byte)
      (byte-array)
      (java.nio.ByteBuffer/wrap)))
