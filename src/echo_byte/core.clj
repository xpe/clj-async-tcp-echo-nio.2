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
