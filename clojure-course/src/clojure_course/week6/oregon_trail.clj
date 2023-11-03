(ns clojure-course.week6.oregon_trail
  (:require [clojure-course.week6.graph_common_funcs :as funcs])
  )

(defrecord Node [name])

; days -- how many days takes this piece of route
; supplies -- how much supplies requires this piece of route
(defrecord Edge [id node1 node2 supplies days])

(def all_nodes
  [
   (Node. "A")
   (Node. "B")
   (Node. "C")
   (Node. "D")
   ]
  )

(def all_edges
  [
   (Edge. 1 "A" "B" -30 7)
   (Edge. 2 "A" "C" -20 10)
   (Edge. 3 "B" "D" -20 4)
   (Edge. 3 "A" "D" -20 4)
   ; (Edge. 4 "C" "D" -10 5)
   ]
  )

(defn print_movement_history
  [node_name
   movements_history]
  (println "history for" node_name ":" @movements_history)
  ;  (reverse @movements_history)
  )

(def all_routes (atom []))
(def start_node_name "A")

(defn dfs_graph
  [
   current_node
   movements_history
   ]
  (let [edges (funcs/get_edges_for_node current_node all_edges)
        current_node_name (:name current_node)
        local_history movements_history
        ]
    (println current_node_name "is visited")
    (swap! local_history conj current_node_name)
    (print_movement_history current_node_name local_history)

    (doseq [edge edges
            :let [
                  child_node (funcs/get_another_node_for_edge current_node edge all_nodes)
                  child_node_name (:name child_node)
                  ]
            :when (not (.contains @local_history child_node_name))
            ]
      (println "edge=" (:node1 edge) "<->" (:node2 edge)" Let's go from" current_node_name "to" child_node_name)
      (swap! local_history conj (dfs_graph child_node local_history))
      (println "recursion returns to" current_node_name)
      (when (= current_node_name start_node_name)
        ; save movement history to collection of routes
        (swap! all_routes conj local_history)
        ; set the history empty for other routes
        (reset! movements_history (atom []))
        (println "Route saved" all_routes)
        )
      )
    local_history
    )
  )

(defn find_all_routes
  [start_node_name
   end_node_name
   ]
  (let [start_node (funcs/get_node start_node_name all_nodes)]


    )


  )

(dfs_graph (funcs/get_node start_node_name all_nodes) (atom []))
(doseq [route @all_routes]
  (println @route)

  )



