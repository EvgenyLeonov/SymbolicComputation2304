(ns clojure-course.week6.graph_common_funcs

  )

(defn get_node
  [node_name
   all_nodes
   ]
  (first (filter #(= node_name (:name %)) all_nodes))
  )

(defn get_edges_for_node
  [node
   all_edges]
  (filter #(or (= (:name node) (:node1 %)) (= (:name node) (:node2 %))) all_edges)
  )

; each edge based on two nodes. if we give the function "this_node", it returns us the second node.
(defn get_another_node_for_edge
  [this_node
   edge
   all_nodes
   ]
  (cond
    (= (:name this_node) (:node1 edge)) (get_node (:node2 edge) all_nodes)
    :else (get_node (:node1 edge) all_nodes)
    )
  )

; returns nodes that are children for the given node
(defn get_children_for_node
  [node_name all_edges all_nodes]
  (let [this_node (get_node node_name all_nodes)
        edges_for_this_node (get_edges_for_node this_node all_edges)
        children (atom [])]
    (when-not (nil? edges_for_this_node)
      (doseq [edge edges_for_this_node]
        (swap! children conj (get_another_node_for_edge this_node edge all_nodes))
        )
      )
    @children
    )
  )

; find an edge based on two given nodes
(defn get_edge_by_nodes
  [node1_name node2_name all_edges]
  (first (filter #(or (and (= (:node1 %) node1_name) (= (:node2 %) node2_name))
                      (and (= (:node2 %) node1_name) (= (:node1 %) node2_name))
                      ) all_edges)
         )
  )

