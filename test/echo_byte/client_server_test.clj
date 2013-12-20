(ns echo-byte.client-server-test
  (:require [clojure.test :refer :all]
            [echo-byte.core :as core]
            [echo-byte.server :as server]
            [echo-byte.client :as client]))

(deftest client-server-test
  (testing "client + server"
    (let [host "127.0.0.1"
          port 9517
          s (server/bind host port)
          _ (server/accept s)
          c (client/connect host port)]
      (client/write c (core/rand-byte-buffer 100000)))))
