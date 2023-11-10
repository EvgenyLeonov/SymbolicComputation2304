(ns clojure-course.week7.bfs_queue
  (:require [clojure-course.week7.binary_tree :as binary_tree])
  )

(def queue (atom []))

; inserts node at the first position (FIFO)
(defn queue_add
  [node_to_add]
  (when (some? node_to_add)
    (if (empty? @queue)
      (reset! queue (conj @queue node_to_add))
      (reset! queue (cons node_to_add @queue))
      )
    )
  )

; removes the last item (FIFO)
(defn queue_remove_item
  []
  (reset! queue (into [] (drop-last @queue)))
  )

(defn queue_not_empty []
  (not (empty? @queue))
  )

(defn queue_next_item
  []
  (when queue_not_empty
    (last @queue)
    )
  )

(defn bfs_visit_all_nodes
  [root_node]
  (queue_add root_node)
  (while (queue_not_empty)
    (let [current_node (queue_next_item)
          left_node (:left_node current_node)
          right_node (:right_node current_node)
          ]
      (println (:name current_node) "is visited")
      (println "left:" (:name left_node))
      (println "right:" (:name right_node))
      (queue_add left_node)
      (queue_add right_node)
      (queue_remove_item)
      )
    )

  )

(bfs_visit_all_nodes (binary_tree/create_tree))