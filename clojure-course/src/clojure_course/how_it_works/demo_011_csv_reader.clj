(ns clojure-course.how-it-works.demo-011-csv-reader

  )

(require '[clojure.data.csv :as csv]
         '[clojure.java.io :as io])

(with-open [reader (io/reader "in-file.csv")]
  (doall
    (csv/read-csv reader)))


