(ns cljweb.routes
  (:require [clojure.java.io :as io]
            [compojure.core :refer [ANY GET PUT POST DELETE defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [response]]))

(defn read-nth-line
    "Read line-number from the given text file. The first line has the number 1."
    [file line-number]
    (with-open [rdr (io/reader file)]
      (nth (line-seq rdr) line-number)))

(defroutes routes
  (GET "/" _
    (-> "public/index.html"
        io/resource
        io/input-stream
        response
        (assoc :headers {"Content-Type" "text/json; charset=utf-8"})))
  (GET "/random" []
    (let [random-int (rand-int 2830)]
      (read-nth-line "resources/public/questions.txt" random-int)))
  (POST "/time" req
    (let [q-time (get (:params req) :q-time)]
      (spit "resources/public/times.txt" q-time :append true)
      q-time))
  (resources "/"))
