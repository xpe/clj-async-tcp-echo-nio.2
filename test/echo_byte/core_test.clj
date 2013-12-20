(ns echo-byte.core-test
  (:require [clojure.test :refer :all]
            [echo-byte.core :refer :all])
  (:import [java.net InetSocketAddress]
           [java.nio ByteBuffer]))

(deftest socket-address-test
  (testing "socket-address"
    (let [sa (socket-address "127.0.0.1" 9500)]
      (is (instance? InetSocketAddress sa))
      (is (= "127.0.0.1" (.getHostString sa)))
      (is (= "localhost" (.getHostName sa)))
      (is (= 9500 (.getPort sa))))))

(deftest byte-buffer-test
  (testing "byte-buffer"
    (let [coll [2 3 5 7 11]
          bb (byte-buffer coll)]
      (is (= coll (seq (.array bb)))))))

(deftest rand-byte-test
  (testing "rand-byte"
    (let [n 1000
          bs (repeatedly n rand-byte)]
      (is (= n (count (filter #(instance? Byte %) bs)))))))

(deftest rand-byte-buffer-test
  (testing "rand-byte-buffer"
    (let [n 1000
          bs (rand-byte-buffer n)]
      (is (instance? ByteBuffer bs))
      (is (= n (.capacity bs))))))
