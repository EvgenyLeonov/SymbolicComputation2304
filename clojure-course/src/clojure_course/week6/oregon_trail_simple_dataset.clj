(ns clojure-course.week6.oregon-trail-simple-dataset)

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
   (Node. "E")
   (Node. "F")
   (Node. "G")
   ]
  )

(def all_edges
  [
   (Edge. 1 "A" "B" -30 7)
   (Edge. 2 "A" "C" -20 10)
   (Edge. 3 "B" "D" -20 4)
   (Edge. 3 "C" "D" -20 4)
   (Edge. 3 "D" "E" -20 4)
   (Edge. 3 "D" "F" -20 4)
   (Edge. 3 "E" "G" -20 4)
   (Edge. 3 "F" "G" -20 4)
   ]
  )
