(ns clojure-course.week7.binary_tree)

(defrecord Node [name left_node right_node])

(defn create_tree []
  (let [node1  (Node. "1" nil nil)
        node3  (Node. "3" nil nil)
        node2  (Node. "2" node1 node3)
        node6  (Node. "6" nil nil)
        node7  (Node. "7" nil nil)
        node5  (Node. "5" node6 node7)
        node4  (Node. "4" node2 node5)
        ]
    node4
    )
  )

(defn create_tree_simple []
  (let [node1  (Node. "1" nil nil)
        node3  (Node. "3" nil nil)
        node2  (Node. "2" node1 node3)
        ]
    node2
    )
  )

(defn print_binary_tree_routines
  [node]
  (when (some? node)
    (let [left_node (:left_node node)
          right_node (:right_node node)
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