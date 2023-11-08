(ns clojure-course.week7.bfs_queue
  (:require [clojure-course.week7.binary_tree :as binary_tree])
  )

(def queue (atom []))

; inserts node at the first position (FIFO)
(defn queue_add
  [node_to_add]
  (when-not (nil? node_to_add)
    (reset! queue (cons node_to_add @queue))
    (println "queue=" @queue)
    )
  )

; removes the last item (FIFO)
(defn queue_remove
  []
  (swap! queue drop-last)
  )

(defn queue_not_empty []
  (empty? queue)
  )

(defn queue_next_item
  []
  (when queue_not_empty
    (get @queue 0)
    )
  )

; DEBUG
;(queue_add "1")
;(queue_add "2")
;(println "queue=" @queue)
;(queue_remove)
;(println "queue=" @queue)

(defn bfs_visit_all_nodes
  [root_node]
  (queue_add root_node)
  (while queue_not_empty
    (let [current_node (queue_next_item)
          ]
      (println (:name current_node) "is visited")
      (queue_add (:left_node current_node))
      (queue_add (:right_node current_node))
      (queue_remove)
      )
    )

  )

(bfs_visit_all_nodes (binary_tree/create_tree))