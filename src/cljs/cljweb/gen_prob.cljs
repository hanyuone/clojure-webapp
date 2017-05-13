(ns cljweb.gen-prob
  (:require [clojure.string :as string]
            [reagent.core :as reagent]
            [ajax.core :refer [GET POST]]))

;; Predefined atoms
(def problem (reagent/atom ""))
(def list-problem (reagent/atom ""))
(def counter (reagent/atom 0))
(def current-time (reagent/atom (.getTime (js/Date.))))

;; Problem generation stuff

;; Checks if an item is in a collection,
;; basically a redefinition of "some".
(defn in [item coll]
  (some #{item} coll))

;; Checks if two operators need brackets.
(defn brackets? [op-1 op-2]
  (let [high-imp ["*" "/"]
        low-imp ["+" "-"]]
    (if (and (in op-1 low-imp) (in op-2 high-imp))
      false true)))

;; Turns a problem into a string.
(defn problem->string [problem]
  (let [split-prob (vec (rest (string/split problem #" ")))]
    (loop [operators (subvec split-prob 0 3)
           numbers (subvec split-prob 4)
           prev-operator ""
           final-string (str (nth split-prob 3))]
      (if (zero? (count operators)) final-string
        (recur
          (vec (rest operators))
          (vec (rest numbers))
          (first operators)
          (str
            (if
              (and
                (= (count final-string) 1)
                (brackets? prev-operator (first operators)))
              final-string
              (str "(" final-string ")"))
            (first operators)
            (first numbers)))))))

;; Handler for GET request
(defn question-handler [response]
  (swap! counter inc)
  (if (= @counter 1)
    (do
      (reset! problem (problem->string (str response)))
      (reset! list-problem (str response))))) 

(defn random-problem []
  (GET "/random"
    {:handler question-handler}))

;; Checking answers
(defn answer-handler [response]
  (reset! current-time (.getTime (js/Date.)))
  (reset! counter 0)
  (random-problem))

(defn check-answer [answer]
  (let [real-answer (first (string/split problem #" "))]
    (println real-answer)
    (if (= (int real-answer) answer)
      (let [new-time (.getTime (js/Date.))
            millis-diff
            (/ (- new-time @current-time) (* 1000 3600 24))]
        (POST "/time"
          {:params {:q-time millis-diff}
           :handler answer-handler}))
      (println "Wrong answer!"))))
