(ns logging-dashboard.dispatcher
  (:require [cljs-flux.dispatcher :refer [dispatcher]]))

(def update-columns  (dispatcher))
(def update-sorting  (dispatcher))
(def update-settings (dispatcher))
(def update-filters  (dispatcher))
(def logs-search     (dispatcher))
