(ns clojure-course.microtasks.microtask003.solutions.microtask003)

(defn get_number_of_ancestors
  [generation]
  (cond
    ; Mr. Furball has two parents
    (= generation 1) 2
    :else (* 2 (get_number_of_ancestors (dec generation)) )
    )
  )

(def number_of_generations_for_century (quot 100 3))
(println "number_of_generations_for_century=" number_of_generations_for_century)
(println "total number of the cat's ancestors =" (get_number_of_ancestors number_of_generations_for_century))
