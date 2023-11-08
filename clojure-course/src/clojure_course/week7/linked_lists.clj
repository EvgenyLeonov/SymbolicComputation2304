(ns clojure-course.week7.linked_lists)

(defrecord SinglyNode [name next_node])
(defrecord DoublyNode [name prev_node next_node])

(defn create_singly_list []
  (let [
        node2  (SinglyNode. "2"  nil)
        node1  (SinglyNode. "1"  node2)
        node0  (SinglyNode. "0"  node1)
       ]
    node0
    )
  )

