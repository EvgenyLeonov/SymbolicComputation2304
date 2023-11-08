(ns clojure-course.week7.fast-slow-pointers
  (:require [clojure-course.week7.linked_lists :as linked_lists])
  )

(def head (linked_lists/create_singly_list))
(println "head=" head)
(let
  [slow (atom head)
   fast (atom head)
   ]
  (while (and
           (some? @fast)
           (some? (:next_node @fast)))
    (println "slow=" (:name @slow))
    (println "fast=" (:name @fast))
    (reset! slow (:next_node @slow))
    (reset! fast (:next_node (:next_node @fast)))
    )

  (println "middle=" (:name @slow))
  )
