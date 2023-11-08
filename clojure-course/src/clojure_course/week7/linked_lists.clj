(ns clojure-course.week7.linked_lists)

(defrecord SinglyNode [name next_node])
(defrecord DoublyNode [name prev_node next_node])

(defn create_singly_list []
  (let [
        node6  (SinglyNode. "6"  nil)
        node5  (SinglyNode. "5"  node6)
        node4  (SinglyNode. "4"  node5)
        node3  (SinglyNode. "3"  node4)
        node2  (SinglyNode. "2"  node3)
        node1  (SinglyNode. "1"  node2)
        node0  (SinglyNode. "0"  node1)
       ]
    node0
    )
  )
