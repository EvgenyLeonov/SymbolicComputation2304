(ns clojure-course.how_it_works.demo_002_collections)

(def vect [1 2 3])
(def lst '(1 2 3))
(def sett #{1 2 3})
(def mapp {"a" 1, "b" 2, "c" 3})

(defn demo_vectors
  []
  (println "============")
  (println "VECTOR")
  (println "============")

  (println "after conj=>" (conj vect 5 6 7))
  (println "vect=" vect)
  )

(defn demo_list
  []
  (println "============")
  (println "LIST")
  (println "============")

  (println "after conj=>" (conj lst 5 6 7))
  (println "lst=" lst)
  )

(defn demo_set
  []
  (println "============")
  (println "SET")
  (println "============")

  (println "after conj=>" (conj sett 5 6 7))
  (println "sett=" sett)
  (println "contains 8? >" (contains? sett 8))

  )

(defn demo_map
  []
  (println "============")
  (println "MAP")
  (println "============")

  (println "after assoc=>" (assoc mapp "d" 5 "e" 6, "f" 7))
  (println "mapp=" mapp)
  (println "after dissoc=>" (dissoc mapp "d" "E" "f" "g"))

  )

(defn demo_Map
  []
  (println "Note: names in Clojure are case-sensitive!")
  )

(demo_vectors)
(demo_list)
(demo_set)
(demo_map)
(demo_Map)


