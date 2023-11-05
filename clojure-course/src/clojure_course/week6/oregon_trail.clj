(ns clojure-course.week6.oregon_trail
  (:require [clojure-course.week6.graph_common_funcs :as funcs])
  (:require [clojure-course.week6.oregon_trail_init_data_short :as oregon_trail_short])
  (:require [clojure.string :as string :only [index-of]])
  )

(defn string_merge_routines
  [path_as_string target_node_name]
  (let [result (atom path_as_string)]
    ; if we don't need to merge anything, just return result
    (doseq [node_name path_as_string
            :when (not= node_name target_node_name)
            :let [index_node_name (string/index-of path_as_string node_name 0)
                  index_node_name_next (string/index-of path_as_string node_name (inc index_node_name))
                  ]
            :when (and (some? index_node_name) (some? index_node_name_next) (> index_node_name_next index_node_name))
            ]
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

; auxiliary function for recursive run
(defn folding_routines [route_vector target_node_name]
  (let [path (subvec route_vector 0 (.lastIndexOf route_vector target_node_name))
        path_as_string (apply str path)
        node_names (atom [])
        ]
    (swap! node_names conj (string_merge_routines path_as_string target_node_name))
    (when (.contains path target_node_name)
      (swap! node_names concat (folding_routines path target_node_name))
      )
    @node_names
    )
  )

; typically a route contains unnecessary movements back and forth, so, let's remove them
(defn folding [folding_vector target_node_name all_edges]
  (let [edges_for_route (atom [])
        all_node_chains (folding_routines folding_vector target_node_name)
        _ (println "all_node_chains=" all_node_chains)
        ]
    (doseq [chain all_node_chains
            :let [chain_count (count chain)
                  last_index (dec chain_count)
                  chain_edges (atom [])
                  ]
            :when (>= chain_count 2)
            ]
      (loop [index1 0
             index2 1
             ]
        (when (<= index2 last_index)
          (let [node_name1 (get chain index1)
                node_name2 (get chain index2)
                edge (funcs/get_edge_by_nodes (str node_name1) (str node_name2) all_edges)

                ]
            (swap! chain_edges conj edge)
            )
          (recur (inc index1) (inc index2))
          )
        )
      ; add connection between the last node in the list and target node
      (swap! chain_edges conj
             (funcs/get_edge_by_nodes
               (str (get chain last_index))
               target_node_name
               all_edges)
             )
      (swap! edges_for_route conj @chain_edges)
      )
      @edges_for_route
    )
  )

; the major function performing traverse via graph
(defn dfs_graph
  [
   current_node
   ; we remember the chain of nodes visited so far
   ; this vector transferred in all children nodes
   movements_history
   target_node_name
   all_nodes
   all_edges
   ]
  (let [edges (funcs/get_edges_for_node current_node all_edges)
        current_node_name (:name current_node)
        ]
    (println current_node_name "is visited")
    (swap! movements_history conj current_node_name)
    ; the current node knows the whole route from beginning
    (println "history for" current_node_name ":" @movements_history)

    (doseq [edge edges
            :let [
                  child_node (funcs/get_another_node_for_edge current_node edge all_nodes)
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
          (dfs_graph child_node movements_history target_node_name all_nodes all_edges)
          (println "recursion returns to" current_node_name)
          )
        )
      (swap! movements_history conj current_node_name)
      )
    movements_history
    )
  )

; the main method to find all routes from start_node_name to target_node_name
; note: this is "pure function" -- it used input arguments only and change any external objects
(defn find_all_routes
  [start_node_name
   target_node_name
   all_nodes
   all_edges
   ]
  (let [
        all_routes (atom [])
        ; here we store search results (each route created by list of "legs" = edges)
        edges_for_all_routes (atom [])
        start_node_children (funcs/get_children_for_node start_node_name all_edges all_nodes)
        ]
    ; BFS approach. Let's run search for all children of the root node, and traverse their descendants
    (doseq [child_node start_node_children
            :let [child_node_name (:name child_node)]
            ]
      (println "dive from start node" start_node_name "to" child_node_name)
      ; recursively travers all nodes
      (swap! all_routes conj
             (dfs_graph child_node (atom [start_node_name]) target_node_name all_nodes all_edges)
             )
      )
    ; let's analyse results. they contain unnecessary pieces and algorithm passed some edges beck and forth
    ; we eliminate these pieces => fold the sequence
    ; folding for all routes
    (doseq [route @all_routes
            ; if a route doesn't lead us to the target, let's abandon it
            :when (.contains @route target_node_name)
            ]
      ; we use "concat" instead "conj" overwise result of "folding" function will be added as vector instead of list nodes
      (swap! edges_for_all_routes concat (folding @route target_node_name all_edges))
      )
    ; return all routes we found
    @edges_for_all_routes
    )
  )

; this is the entry point for the program. All routes calculated via find_all_routes function
; and assigned to all_routes variable
(def all_routes (find_all_routes oregon_trail_short/start_node_name
                                 oregon_trail_short/target_node_name
                                 oregon_trail_short/all_nodes
                                 oregon_trail_short/all_edges
                                 ))

(println "ALL ROUTES TO TARGET:")
(doseq [route all_routes]
  (oregon_trail_short/print_report_for_route route oregon_trail_short/all_nodes true)
  )





