(ns clojure-course.how_it_works.demo_007_filter)

; vector of words. Two of them contains 3 letters.
(def words ["cat" "flower" "dog" "cactus"])

; this function returns only words that pass the filter condition
(defn cut_off_long_words_1
  [words]
  (println "short words only="
           ; there is no loop here
           ; filter function takes a collection and returns another collection,
           ; each element of this new collection must pass the filter condition (here is function (<= (count word) 3))
           ; the filter condition also named as "predicate"
           (filter (fn [word]
                     (<= (count word) 3))
                   words)
           )
  )

(cut_off_long_words_1 words)

(println "========")

(defn cut_off_long_words_2
  [words]
  ; we also can filter values in loops without "if" conditions
  (doseq [word words :when (<= (count word) 3)]
    (println word)
    )
  (println "-----")
  (for [word words :when (<= (count word) 3)]
    (println word)
    )
  )

(cut_off_long_words_2 words)

