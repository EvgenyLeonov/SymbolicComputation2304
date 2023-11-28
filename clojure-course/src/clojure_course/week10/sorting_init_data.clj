(ns clojure-course.week10.sorting_init_data)

(def vector_to_sort [8 4 1 9])
(def vector_to_sort2 [5 4 5 5 1 1 3])
(def vector_to_sort3 [28 50 32 22 41 12])

(defn swap [original_vector index1 index2]
  ; assoc vec index replacement
  (assoc original_vector index2 (get original_vector index1) index1 (get original_vector index2)))

(defn replace_item [original_vector index new_value]
  (assoc original_vector index new_value)
  )

;DEBUG
;(println (swap [1 2 3] 0 1))