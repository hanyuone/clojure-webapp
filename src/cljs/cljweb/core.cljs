(ns cljweb.core
  (:require [reagent.core :as reagent]
            [cljweb.webpage :as webpage]))

(enable-console-print!)

(defn page []
  (webpage/page))

(defn reload []
  (reagent/render [page]
    (js/document.getElementById "app")))

(defn ^:export main []
  (reload))