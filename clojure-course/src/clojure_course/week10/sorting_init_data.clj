(ns clojure-course.week10.sorting_init_data)

(def vector_to_sort [8 4 1 9])
(defn swap [original_vector index1 index2]
  ; assoc vec index replacement
  (assoc original_vector index2 (get original_vector index1) index1 (get original_vector index2)))

;DEBUG
;(println (swap [1 2 3] 0 1))