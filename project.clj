(defproject logging-dashboard "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0-alpha5"]
                 [compojure "1.3.2"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-defaults "0.1.4"]
                 [org.clojure/clojurescript "0.0-2069"]
                 [domina "1.0.3"]]
  :plugins [[lein-ring "0.8.13"] [lein-cljsbuild "1.0.0"]]
  :source-paths ["src/clj" "src/cljs"]
  :ring {:handler logging-dashboard.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}}

  :cljsbuild {:builds
              [{:source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/logging-dashboard.js"
                           :optimizations :whitespace
                           :pretty-print true}}]})
