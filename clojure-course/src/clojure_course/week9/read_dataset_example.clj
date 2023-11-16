(ns clojure-course.week9.read_dataset_example)

(defn read_superheroes_dataset []
  (with-open [rdr (clojure.java.io/reader "src/clojure_course/week9/superheroes_dataset.txt")]
    (reduce conj [] (line-seq rdr)))
  )

; DEBUG
;(for [line (read_superheroes_dataset)]  (println line)  )
