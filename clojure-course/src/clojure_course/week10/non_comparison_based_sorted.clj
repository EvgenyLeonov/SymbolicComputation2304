(ns clojure-course.week10.non_comparison_based_sorted
  (:require [clojure-course.week10.sorting_init_data :as data])
  (:require [clojure-course.week10.comparison_based_sorting :as comparison_based_sorting])
  )

(defn counting_sort
  [input_vector]
  (let [vect_size (count input_vector)
        max_value (apply max input_vector)
        counts (atom (into [] (repeat (inc max_value) 0)))
        sorted_vector (atom (into [] (repeat vect_size 0)))
        ]
    (loop [ind 0]
      (when (< ind vect_size)
        (let [elem (get input_vector ind)
              current_value (get @counts elem)]
          (reset! counts (data/replace_item @counts elem (inc current_value)))
          )
        (recur (inc ind))
        )
      )
    ;(println "filled counts1=" @counts)

    ; overwrite original counts with the starting index of each element in the final sorted array
    (let [starting_index (atom 0)]
      (loop [ind 0]
        (when (< ind (inc max_value))
          (let [cnt (get @counts ind)]
            (reset! counts (data/replace_item @counts ind @starting_index))
            (swap! starting_index + cnt)
            )
          (recur (inc ind))
          )
        )
      )
    ;(println "filled counts2=" @counts)

    (loop [ind 0]
      (when (< ind vect_size)
        (let [elem (get input_vector ind)
              counts_elem (get @counts elem)]
          (reset! sorted_vector (data/replace_item @sorted_vector counts_elem elem))
          (reset! counts (data/replace_item @counts elem (inc counts_elem)))
          )
        (recur (inc ind))
        )
      )
    @sorted_vector
    )
  )

(defn print_buckets_content
  [buckets]
  (loop [ind 0]
    (when (< ind (count @buckets))
      (println @(get @buckets ind))
      (recur (inc ind))
      )
    )
  )

(defn bucket_sort
  [input_vector]
  (let [vect_size (count input_vector)
        min_value (apply min input_vector)
        max_value (- (apply max input_vector) min_value)
        num_of_buckets 5
        buckets (atom [])
        buckets_size (int (if (< (float (/ max_value num_of_buckets)) 1)
                            1.0
                            (float (/ max_value num_of_buckets))
                            ))
        sorted_vector (atom [])
        ]
    ; doesn't work: refer to the same atom
    ; buckets (into [] (repeat num_of_buckets (atom [])))
    (loop [ind 0]
      (when (< ind num_of_buckets)
        (swap! buckets conj (atom []))
        (recur (inc ind))
        )
      )

    ; put values to buckets
    (loop [ind 0]
      (when (< ind vect_size)
        (let [elem (get input_vector ind)
              bucket_index_preliminary (int (/ (- elem min_value) buckets_size))
              bucket_index (if (= bucket_index_preliminary num_of_buckets)
                                  (dec bucket_index_preliminary)
                                  bucket_index_preliminary
                                  )
              ]
          (swap! (get @buckets bucket_index) conj elem)
          )
        (recur (inc ind))
        )
      )
    ;(println "buckets before sort:")
    ;(print_buckets_content buckets)

    ; sort values in each bucket separately
    (loop [ind 0]
      (when (< ind (count @buckets))
        (let [bucket (get @buckets ind)]
          (when (> (count @bucket) 1)
            (reset! bucket (comparison_based_sorting/insertion_sort_routines @bucket))
            )
          )
        (recur (inc ind))
        )
      )

    ;(println "buckets after sort:")
    ;(print_buckets_content buckets)

    ; combine all buckets together
    (loop [ind_bucket 0]
      (when (< ind_bucket (count @buckets))
        (swap! sorted_vector concat @(get @buckets ind_bucket))
        (recur (inc ind_bucket))
        )
      )
    (into [] @sorted_vector)
    )
  )

(println "original vector for Counting Sort =" data/vector_to_sort2)
(println "Counting Sort =>" (counting_sort data/vector_to_sort2))
(println "original vector for Bucket Sort =" data/vector_to_sort3)
(println "Bucket Sort =>" (bucket_sort data/vector_to_sort3))


