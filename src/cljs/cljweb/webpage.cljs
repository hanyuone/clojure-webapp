(ns cljweb.webpage
  (:require [reagent.core :as reagent]
            [cljweb.gen-prob :as prob]))

(def current-input (reagent/atom 0))

(defn parse-int [string]
  (cond
    (or (zero? (count string)) (= string "-")) 0
    (= (last string) "-") (- 0 (js/parseInt string))
    :else (js/parseInt string)))  

(defn problem-input []
  [:div#problem-input
   [:input#negate-input
    {:type "button"
     :value "-"
     :on-click #(reset! current-input (- 0 @current-input))}]
   [:input#number-entry
    {:type "text"
     :value @current-input
     :on-change #(reset! current-input
                   (-> % .-target .-value parse-int))
     :on-key-press (fn [e]
                     (when (= (.-charCode e) 13)
                       (.preventDefault e)
                       (reset! current-input 0)
                       (prob/check-answer @current-input)))}]
   [:input#backspace
    {:type "button"
     :value "<-"
     :on-click #(reset! current-input
                  (parse-int
                    (subs (str @current-input) 1)))}]])       

(defn page []
  (prob/random-problem)
  [:div#page
     [:p#current-problem @prob/problem]
     [problem-input]])