(ns clojure-course.week8.binary-tree-inverted-roman-yepanchenko)

(defrecord Node [name children])

(def nodes (Node. 2 [(Node. 1 [(Node. 5 []) (Node. 9 [])]) (Node. 3 [])]))

(defn process2
  [node1, node2]

  (if (< (:name node1) (:name node2))
    (do
      {
       :root  (:name node2)
       :child (:name node1)
       }
      )

    (do
      {
       :root  (:name node1)
       :child (:name node2)
       }
      )
    )
  )


(defn process
  [node]

  (let
    [
     nodeTemp (atom nil)
     result (atom {:root nil, :child nil})
     childIndex (atom 0)
     processResponse (atom nil)
     hasChanges (atom false)
     ]

    (reset! nodeTemp node)

    (doseq [value (:children @nodeTemp)]

      (reset! processResponse (process value))

      (if (= (:has-changes @processResponse) true)
        (reset! hasChanges true)
        )

      (reset! nodeTemp
              (assoc @nodeTemp :children (assoc (:children @nodeTemp) @childIndex (:node @processResponse)))
              )


      (reset! childIndex (inc @childIndex))
      )

    (reset! childIndex 0)

    (doseq [value (:children @nodeTemp)]

      (reset! result (process2 @nodeTemp value))

      (if (not (= (:root @result) (:name @nodeTemp)))
        (reset! hasChanges true)
        )

      (reset! nodeTemp (assoc @nodeTemp :name (:root @result)))

      (reset! nodeTemp
              (assoc @nodeTemp :children (assoc (:children @nodeTemp) @childIndex (assoc value :name (:child @result))))
              )

      (reset! childIndex (inc @childIndex))
      )

    {:has-changes @hasChanges :node @nodeTemp}
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

(print "old: " )
(print_binary_tree nodes)
(println "")
(let [
      tempNode (atom nil)
      state (atom true)
      counter (atom 0)
      processResponse (atom nil)
      ]

  (reset! tempNode nodes)

  (while (= @state true)
    (reset! processResponse (process @tempNode))


    (reset! tempNode (:node @processResponse))

    (reset! counter (inc @counter))

    (if (= (:has-changes @processResponse) false)
      (reset! state false)
      )

    (println "step" @counter)
    )

  (print "updated:" )
  (print_binary_tree @tempNode)
  )



