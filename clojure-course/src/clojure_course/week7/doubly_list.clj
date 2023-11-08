(ns clojure-course.week7.doubly_list)

(defrecord DList [head tail])
(defrecord DListNode [prev data next])

(defn make-dlist [] (DList. (ref nil) (ref nil)))
(defn dlist-empty? [lst] (nil? (deref (:head lst))))
(defn dlist-ok? [lst]
  (= (nil? (deref (:head lst)))
     (nil? (deref (:tail lst)))))
(defn dlist? [lst] (= (class lst) DList))
(defn dlist-prepend! [lst val]
  (let [new-node (DListNode. (ref nil) val (ref (deref (:head lst))))]
    (if (dlist-empty? lst)
      (dosync (ref-set (:head lst) new-node)
              (ref-set (:tail lst) new-node))
      (dosync (ref-set (:prev (deref (:head lst)))new-node)
              (ref-set (:head lst)new-node))))val)
(defn dlist-iter [lst func]
  (loop [node (deref (:head lst))]
    (if (not (nil? node))
      (do
        (func (:data node))
        (recur (deref (:next node)))))))


(def lst1 (make-dlist))
(dlist-prepend! lst1 1)
(dlist-prepend! lst1 2)
(dlist-prepend! lst1 3)
(dlist-iter lst1 println)
