(ns clojure-course.week8.binary_tree_inverted
  (:require [clojure-course.week7.binary_tree :as binary_tree])
)
(defrecord Node [name left_node right_node])

(defn invert_tree [current_node]
  (when (some? current_node)
    (let [
          ;_ (println "current_node" current_node)
          current_node_name (:name current_node)
          left_node (:left_node current_node)
          right_node (:right_node current_node)
          ;_ (println "left_node" (:name left_node))
          ;_ (println "right_node" (:name right_node))
          left_node_inverted (invert_tree left_node)
          right_node_inverted (invert_tree right_node)
          ;_ (println "left_node_inverted" (:name left_node_inverted))
          ;_ (println "right_node_inverted" (:name right_node_inverted))
          ]
      (Node. current_node_name right_node_inverted left_node_inverted )
      )
    )
  )

; uncomment this
;(print "original simple binary tree=")
;(binary_tree/print_binary_tree (binary_tree/create_tree_simple))
;(println)

;(println "inverted binary tree=" (invert_tree (binary_tree/create_tree)))
;(print "inverted simple binary tree=")
;(binary_tree/print_binary_tree (invert_tree (binary_tree/create_tree_simple)))
;(println)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;(print "original binary tree=")
;(binary_tree/print_binary_tree  (binary_tree/create_tree))
;(println)
;(print "inverted binary tree=")
;(binary_tree/print_binary_tree (invert_tree (binary_tree/create_tree)))
;(println)
