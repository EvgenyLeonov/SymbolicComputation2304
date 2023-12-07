(ns clojure-course.week10.counting_sort_anna_olexandra_danchenko)

(def numbers [5 4 5 5 1 1 3])

(defn counting-sort [numbers]
  (let [count-vector (atom (vec (repeat (count numbers) 0)))
        sorted-vector (atom [])
        count-number (atom 0)]
    (doseq [num numbers]
      (swap! count-vector update num inc))
    (doseq [count @count-vector]
      (when (> count 0)
        (dotimes [_ count]
          (swap! sorted-vector conj @count-number)))
      (reset! count-number (inc @count-number)))
    @sorted-vector))

(println (counting-sort numbers))




