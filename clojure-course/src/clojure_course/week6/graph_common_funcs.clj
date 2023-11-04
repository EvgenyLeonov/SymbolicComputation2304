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

(defn get_children_for_node
  [node_name all_edges all_nodes]
  (let [this_node (get_node node_name all_nodes)
        ;_ (println "this_node" this_node)
        ;_ (println "all_edges" all_edges)
        edges_for_this_node (get_edges_for_node this_node all_edges)
        ;_ (println "edges_for_this_node"edges_for_this_node)
        children (atom [])]
    (when-not (nil? edges_for_this_node)
      (doseq [edge edges_for_this_node]
        (swap! children conj (get_another_node_for_edge this_node edge all_nodes))
        )
      )
    @children
    )
  )

(defn get_edge_by_nodes
  [node1_name node2_name all_edges]
  (first (filter #(or (and (= (:node1 %) node1_name) (= (:node2 %) node2_name))
                      (and (= (:node2 %) node1_name) (= (:node1 %) node2_name))
                      ) all_edges)
         )
  )

