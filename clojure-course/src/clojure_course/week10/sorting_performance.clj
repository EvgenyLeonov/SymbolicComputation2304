(ns clojure-course.week10.sorting_performance
  (:require [clojure-course.week10.comparison_based_sorting :as routines])
  (:require [clojure-course.week10.sorting_large_dataset :as data])
  )

(println "=== RANDOM VECTOR (COMPARISON BASED) ===")
(def original_vector data/large_vector)
(println "original vector size =" (count original_vector))
(println "Selection Sort =>")
(time (routines/selection_sort_routines original_vector))
(println "Bubble Sort =>")
(time (routines/bubble_sort_routines original_vector))
(println "Insertion Sort =>")
(time (routines/insertion_sort_routines original_vector))

(println "=== ALMOST SORTED VECTOR (COMPARISON BASED) ===")
(def original_vector_a_s data/large_vector_almost_sorted)
(println "original vector size =" (count original_vector_a_s))
(println "Selection Sort =>")
(time (routines/selection_sort_routines original_vector_a_s))
(println "Bubble Sort =>")
(time (routines/bubble_sort_routines original_vector_a_s))
(println "Insertion Sort =>")
(time (routines/insertion_sort_routines original_vector_a_s))





