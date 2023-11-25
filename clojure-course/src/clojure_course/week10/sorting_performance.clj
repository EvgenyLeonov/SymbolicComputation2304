(ns clojure-course.week10.sorting_performance
  (:require [clojure-course.week10.sorting_demos1 :as routines])
  (:require [clojure-course.week10.sorting_large_dataset :as data])
  )

(println "original vector size =" (count data/large_vector))
(println "Selection Sort =>")
(time (routines/selection_sort_routines data/large_vector))
(println "Bubble Sort =>")
(time (routines/bubble_sort_routines data/large_vector))
(println "Insertion Sort =>")
(time (routines/insertion_sort_routines data/large_vector))





