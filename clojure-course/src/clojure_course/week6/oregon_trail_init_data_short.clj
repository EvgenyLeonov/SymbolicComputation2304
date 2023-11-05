(ns clojure-course.week6.oregon_trail_init_data_short
  (:require [clojure-course.week6.graph_common_funcs :as funcs])
  )

(defrecord Node [name full_name])

; days -- how many days takes this piece of route
; supplies -- how much supplies requires this piece of route
(defrecord Edge [node1 node2 supplies days winter_affected])

(def all_nodes
  [
   (Node. "A" "Independence City")
   (Node. "B" "Chimney Rock")
   (Node. "C" "Fort Laramie")
   (Node. "D" "Colorado River")
   (Node. "E" "Donner Pass")
   (Node. "F" "Salt Desert")
   (Node. "G" "Oregon City")
   ]
  )

(def all_edges
  [
   (Edge. "A" "B" -30 10 false)
   (Edge. "A" "C" -20 6 false)
   (Edge. "B" "D" -20 8 false)
   (Edge. "C" "D" -10 9 false)
   (Edge. "D" "E" -40 7 true)
   (Edge. "D" "F" -10 8 true)
   (Edge. "E" "G" -20 12 true)
   (Edge. "F" "G" -20 15 true)
   ]
  )

(def start_node_name "A")
(def target_node_name "G")

(defn get_amount_of_supplies
  [edge total_num_of_days]
  (cond
    (and (true? (:winter_affected edge)) (>= total_num_of_days 30)) (* 2 (:supplies edge))
    :else (:supplies edge)
    )
  )

; calculates and prints total amount of supplies and days for each route
; a route contains several edges
(defn evaluate_route
  [edges]
  (let [supply (atom 0)
        days (atom 0)
        ]
    (doseq [edge edges]
      (swap! supply + (get_amount_of_supplies edge @days))
      (swap! days + (:days edge))
      )
    (print "supplies:" @supply "; days:" @days "| ")
    )
  )

(defn print_report_for_route
  [edges
   all_nodes
   show_full_names
   ]
  (evaluate_route edges)
  (doseq [edge edges
          ]
    (let [node1 (funcs/get_node (:node1 edge) all_nodes)
          node2 (funcs/get_node (:node2 edge) all_nodes)
          ]
      (if (true? show_full_names)
        (print (:full_name node1) "->" (:full_name node2))
        (print (:name node1) "->" (:name node2))
        )
      (when (not (= edge (last edges)))
        (print ", ")
        )
      )
    )
  (println)
  )

; DEBUG
;(println "days =" (get_amount_of_days 4))
;(println "supplies 1 =" (get_amount_of_supplies 4 35))
;(println "supplies 2 =" (get_amount_of_supplies 10 20))
;(println "supplies 2 =" (get_amount_of_supplies 10 35))





