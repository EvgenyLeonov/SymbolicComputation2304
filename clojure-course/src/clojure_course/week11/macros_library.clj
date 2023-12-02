(ns clojure-course.week11.macros_library
  (:require [clojure-course.week8.binary_tree_inverted :as binary_tree])
  (:require [clojure-course.week7.binary_tree :as binary_tree_data])
  )

(defmacro say_opinion
  "express opinion about your variable"
  [your_var]
  (let [
        opinions ["pfff... and this is how you call a variable?" "errm... even my grandma can do it better" "nothing personal, but no girl would date someone who calls variables that way"]
        opt (rand-int 3)
        string_to_print (get opinions opt)
        ]
    (println "You named variable as" (name your_var))
    (println string_to_print)
    )
  )

(defrecord Node [name left_node right_node])

(defmacro invert_binary_tree
  "inverts a binary tree and returns its root node"
  [root_node]
  (binary_tree/invert_tree (eval root_node) )
  )

(def this_is_my_variable 100)
(say_opinion this_is_my_variable)

(def original_tree (binary_tree_data/create_tree_simple))
;(binary_tree_data/print_binary_tree original_tree)
(println)
(def inverted_tree (invert_binary_tree original_tree))
;(binary_tree_data/print_binary_tree inverted_tree)
(println)
(macroexpand-1 '(when (7 > 5) "!"))









