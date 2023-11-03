(ns clojure-course.week6.oregon_trail
  (:require [clojure-course.week6.graph_common_funcs :as funcs])
  (:require [clojure-course.week6.oregon-trail-simple-dataset :as simple-dataset])
  )

(defn print_movement_history
  [node_name
   movements_history]
  (println "history for" node_name ":" @movements_history)
  )

(defn dfs_graph
  [
   current_node
   movements_history
   end_node_name
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
                  is_this_target_node (= child_node_name end_node_name)
                  ]
            :when (not (and (not is_this_target_node)
                            (.contains @movements_history child_node_name)
                            ))
            ]

      ; if we reached destination, we don't need to search after the target node
      (if (true? is_this_target_node)
        (do
          (swap! movements_history conj child_node_name)

          (println "we reached the target node" child_node_name)

          )
        (do
          (println "edge=" (:node1 edge) "<->" (:node2 edge) " Let's go from" current_node_name "to" child_node_name)
          (dfs_graph child_node movements_history end_node_name)
          (println "recursion returns to" current_node_name)
          )
        )
      (swap! movements_history conj current_node_name)
      )
    movements_history
    )
  )

(defn find_all_routes
  [start_node_name
   end_node_name
   ]
  (let [;start_node (funcs/get_node start_node_name all_nodes)
        all_routes (atom [])
        ;start_node_edges (funcs/get_edges_for_node start_node all_edges)
        start_node_children (funcs/get_children_for_node start_node_name simple-dataset/all_edges simple-dataset/all_nodes)
        ]
    ; BFS approach
    ;(println "all edges for start node:" start_node_edges)
    (doseq [child_node start_node_children
            :let [child_node_name (:name child_node)]
            ]
      (println "dive from start node" start_node_name "to" child_node_name)
      (swap! all_routes conj
             (dfs_graph child_node (atom [start_node_name]) end_node_name)
             )
      ;(println "route after " child_node_name ":" @all_routes_2)
      )

    ; return all routes we found
    @all_routes
    )
  )

(def start_node_name "A")
(def target_node_name "E")
(def all_routes (find_all_routes start_node_name target_node_name))
(println "ALL ROUTES TO TARGET:")
(doseq [route all_routes
        :when (.contains @route target_node_name)
        ]
  (println @route)

  )



