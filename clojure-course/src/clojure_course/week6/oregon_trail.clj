(ns clojure-course.week6.oregon_trail
  (:require [clojure-course.week6.graph_common_funcs :as funcs])
  (:require [clojure-course.week6.oregon-trail-simple-dataset :as simple-dataset])
  )

(defn print_movement_history
  [node_name
   movements_history]
  (println "history for" node_name ":" @movements_history)
  ;  (reverse @movements_history)
  )

(defn dfs_graph
  [
   current_node
   movements_history
   ]
  (let [edges (funcs/get_edges_for_node current_node simple-dataset/all_edges)
        current_node_name (:name current_node)
        ]
    (println current_node_name "is visited")
    (swap! movements_history conj current_node_name)
    (print_movement_history current_node_name movements_history)

    (doseq [edge edges
            :let [
                  child_node (funcs/get_another_node_for_edge current_node edge simple-dataset/all_nodes)
                  child_node_name (:name child_node)
                  ]
            :when (not (.contains @movements_history child_node_name))
            ]
      (println "edge=" (:node1 edge) "<->" (:node2 edge) " Let's go from" current_node_name "to" child_node_name)
      (dfs_graph child_node movements_history)
      (println "recursion returns to" current_node_name)
      )
    movements_history
    )
  )

(defn find_all_routes
  [start_node_name
   end_node_name
   ]
  (let [;start_node (funcs/get_node start_node_name all_nodes)
        all_routes_2 (atom [])
        ;start_node_edges (funcs/get_edges_for_node start_node all_edges)
        start_node_children (funcs/get_children_for_node start_node_name simple-dataset/all_edges simple-dataset/all_nodes)
        ]
    ; BFS approach
    ;(println "all edges for start node:" start_node_edges)
    (doseq [child_node start_node_children
            :let [child_node_name (:name child_node)]
            ]
      (println "dive from start node" start_node_name "to" child_node_name)
      (swap! all_routes_2 conj
             (dfs_graph child_node (atom [start_node_name]))
             )
      (println "route after " child_node_name ":" @all_routes_2)
      )

    ; return
    @all_routes_2
    )
  )

(def all_routes (find_all_routes "A" nil))

;(dfs_graph (funcs/get_node start_node_name all_nodes) (atom []))
(println "ALL ROUTES:")
(doseq [route all_routes]
  (println @route)

  )



