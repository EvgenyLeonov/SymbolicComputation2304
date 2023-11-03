(ns clojure-course.week6.oregon_trail
  (:require [clojure-course.week6.graph_common_funcs :as funcs])
  (:require [clojure-course.week6.oregon-trail-simple-dataset :as simple-dataset])
  (:require [clojure.string :only [index-of]])
  )

(defn string_merge_routines
  [path_as_string target_node_name]
  (let [result (atom path_as_string)]
    ; if we don't need to merge anything, just return result
    (doseq [node_name path_as_string
            :when (not= node_name target_node_name)
            :let [index_node_name (index-of path_as_string node_name 0)
                  index_node_name_next (index-of path_as_string node_name (inc index_node_name))
                  ]
            :when (and (some? index_node_name) (some? index_node_name_next) (> index_node_name_next index_node_name))
            ]
      ;(println "merge" node_name "; index1=" index_node_name "; index2="index_node_name_next)
      (reset! result
              (string_merge_routines
                (str (subs path_as_string 0 index_node_name) (subs path_as_string index_node_name_next))
                target_node_name
                )
              )
      )

    @result
    )
  )

(defn folding_routines [route_vector target_node_name]
  (let [path (subvec route_vector 0 (.lastIndexOf route_vector target_node_name))
        path_as_string (apply str path)
        node_names (atom [])
        ]
    (swap! node_names conj (string_merge_routines path_as_string target_node_name))
    (when (.contains path target_node_name)
      (swap! node_names conj (folding_routines path target_node_name))
      )
    @node_names
    )
  )

(defn folding [folding_vector target_node_name]
  (let [edges_for_route (atom [])
        all_node_chains (folding_routines folding_vector target_node_name)
        _ (println "all_node_chains=" all_node_chains)
        ]
    )
  )

(defn dfs_graph
  [
   current_node
   movements_history
   target_node_name
   ]
  (let [edges (funcs/get_edges_for_node current_node simple-dataset/all_edges)
        current_node_name (:name current_node)
        ]
    (println current_node_name "is visited")
    (swap! movements_history conj current_node_name)
    (println "history for" current_node_name ":" @movements_history)

    (doseq [edge edges
            :let [
                  child_node (funcs/get_another_node_for_edge current_node edge simple-dataset/all_nodes)
                  child_node_name (:name child_node)
                  is_this_target_node (= child_node_name target_node_name)
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
          (dfs_graph child_node movements_history target_node_name)
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
   target_node_name
   ]
  (let [all_routes (atom [])
        edges_for_all_routes (atom [])
        start_node_children (funcs/get_children_for_node start_node_name simple-dataset/all_edges simple-dataset/all_nodes)
        ]
    ; BFS approach
    (doseq [child_node start_node_children
            :let [child_node_name (:name child_node)]
            ]
      (println "dive from start node" start_node_name "to" child_node_name)
      (swap! all_routes conj
             (dfs_graph child_node (atom [start_node_name]) target_node_name)
             )
      )
    ; folding for all routes
    (doseq [route @all_routes
            ; if a route doesn't lead us to the target, let's abandon it
            :when (.contains @route target_node_name)
            ]
      (swap! edges_for_all_routes conj (folding @route target_node_name))
      )


    ; return all routes we found
    @edges_for_all_routes
    )
  )

(def all_routes (find_all_routes simple-dataset/start_node_name simple-dataset/target_node_name))
(println "ALL ROUTES TO TARGET:")
(doseq [route all_routes
        :when (.contains @route simple-dataset/target_node_name)
        ]
  (println @route)
  )

; DEBUG
;(def vect1 ["A" "B" "D" "C" "D" "E" "G" "E" "D" "F" ])
;(def vect1_str (apply str vect1))
;(def vect2 ["A" "B" "D" "C" "D" "E"  ])
;(def vect2_str (apply str vect2))
;(println "merged string 1=" (string_merge_routines vect1_str "G"))
;(println "merged string 2=" (string_merge_routines vect2_str "G"))





