(ns clojure-course.how-it-works.demo-011-csv-reader
  (:gen-class)
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io])
  )

(defn open_csv
  [full_path]
  (with-open [file (io/reader full_path)]
    (-> file
        (slurp)
        (csv/read-csv))))
(def csv-file (open_csv "src/clojure_course/how_it_works/test.csv"))


