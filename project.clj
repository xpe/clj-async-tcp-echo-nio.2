(defproject echo-byte "0.1.0-SNAPSHOT"
  :description "Basic TCP echo client/server in Clojure using Java 7 NIO.2"
  :url "https://github.com/bluemont/clj-async-tcp-echo-nio.2"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev
             {:source-paths ["dev"]
              :dependencies [[org.clojure/tools.namespace "0.2.4"]]}})
