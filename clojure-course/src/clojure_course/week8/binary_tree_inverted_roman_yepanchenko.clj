(ns clojure-course.week8.binary-tree-inverted-roman-yepanchenko)
(defrecord Node [name children])

(def nodes (Node. 4 [(Node. 2 [(Node. 1 []) (Node. 3 [])]) (Node. 5 [(Node. 6 []) (Node. 7 [])])]))

(defn process
  [node]

  (let
    [
     nodeTemp (atom nil)
     childTemp (atom nil)
     childrenTemp (atom [])
     ]

    (reset! nodeTemp node)

    (if (= (count (:children @nodeTemp)) 2)
      (do

        (reset! childTemp (get (:children @nodeTemp) 0))

        (reset! childrenTemp (assoc @childrenTemp 0 (process (get (:children @nodeTemp) 1))))
        (reset! childrenTemp (assoc @childrenTemp 1 (process @childTemp)))

        (reset! nodeTemp (assoc @nodeTemp :children @childrenTemp))
        )
      )

    @nodeTemp
    )
  )

(defn print_binary_tree_routines
  [node]

  (when (some? node)

    (let [left_node (get (:children node) 0)
          right_node (get (:children node) 1)
          ]
      ;(print (:name node))
      (when (some? left_node)
        (print (:name left_node))
        )

      (when (some? right_node)
        (print (:name right_node))
        )

      (print_binary_tree_routines left_node)
      (print_binary_tree_routines right_node)
      )
    )
  )

(defn print_binary_tree
  [root_node]
  (print (:name root_node))
  (print_binary_tree_routines root_node)
  )

(print "old: ")
(print_binary_tree nodes)
(println "")
(print "updated ")
(print_binary_tree (process nodes))
