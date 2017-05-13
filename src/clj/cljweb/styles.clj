(ns cljweb.styles
  (:require [garden.def :refer [defstyles]]
            [garden.units :as u] 
            [garden.stylesheet :refer [at-import]]))

(defstyles style
  [:html
   {:height (u/percent 100)
    :width (u/percent 100)}]
  [:body
   {:font-family "\"Lucida Console\""
    :height (u/percent 100)}]
  [:p
   {:color "green"}]
  [:div#app
   {:position "relative"
    :height (u/percent 100)}]
  [:div#page
   {:position "absolute"
    :top (u/percent 40)
    :left (u/percent 50)
    :transform "translate(-50%, -50%)"}])
