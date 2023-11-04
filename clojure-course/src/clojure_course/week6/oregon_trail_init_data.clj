(ns clojure-course.week6.oregon_trail_init_data)

(defrecord Node [name])

; days -- how many days takes this piece of route
; supplies -- how much supplies requires this piece of route
(defrecord Edge [id node1 node2 supplies days])

(def all_nodes
  [
   (Node. "Independence City")
   (Node. "Chimney Rock")
   (Node. "Fort Laramie")
   (Node. "Pine Canyon")
   (Node. "Ambush!")
   (Node. "Trade Post")
   (Node. "Colorado River")
   (Node. "Donner Pass")
   (Node. "Salt Desert")
   (Node. "Oregon City")
   ]
  )

(def all_edges
  [
   (Edge. 1 "Independence City" "Chimney Rock" -30 7)
   (Edge. 2 "Independence City" "Fort Laramie" -20 10)
   (Edge. 3 "Chimney Rock" "Pine Canyon" -20 4)
   (Edge. 4 "Fort Laramie" "Pine Canyon" -10 5)
   (Edge. 5 "Pine Canyon" "Ambush!" -40 4)
   (Edge. 6 "Pine Canyon" "Trade Post" 40 10)
   (Edge. 7 "Ambush!" "Colorado River" -20 8)
   (Edge. 8 "Trade Post" "Colorado River" -30 10)
   (Edge. 9 "Colorado River" "Donner Pass" -25 12)
   (Edge. 10 "Colorado River" "Salt Desert" -20 8)
   (Edge. 11 "Donner Pass" "Oregon City" -10 6)
   (Edge. 12 "Salt Desert" "Oregon City" -20 4)
   ]
  )

(def affected_by_winter [9 10 11 12])

(defn get_edge_by_id
  [id
   all_edges]
  (first (filter #(= id (:id %)) all_edges))
  )

(defn get_amount_of_days
  [edge_id]
  (:days (get_edge_by_id edge_id all_edges))
  )

(defn get_amount_of_supplies
  [edge_id total_num_of_days]
  (let [edge (get_edge_by_id edge_id all_edges)]
    (cond
      (and (.contains affected_by_winter edge_id) (>= total_num_of_days 30)) (* 2 (:supplies edge))
      :else (:supplies edge)
      )
    )
  )

; DEBUG
(println "days =" (get_amount_of_days 4))
(println "supplies 1 =" (get_amount_of_supplies 4 35))
(println "supplies 2 =" (get_amount_of_supplies 10 20))
(println "supplies 2 =" (get_amount_of_supplies 10 35))





