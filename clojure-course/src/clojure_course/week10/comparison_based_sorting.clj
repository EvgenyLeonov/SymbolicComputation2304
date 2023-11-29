(ns clojure-course.week10.comparison_based_sorting
  (:require [clojure-course.week10.sorting_init_data :as data])
  )

(defn selection_sort_routines
  [input_vector]
  (let [min_index (atom 0)
        vect_size (count input_vector)
        current_vector (atom input_vector)
        ]
    (loop [ind1 0]
      (when (< ind1 vect_size)
        (reset! min_index ind1)
        (loop [ind2 (inc ind1)]
          (when (< ind2 vect_size)
            (let [val1 (get @current_vector @min_index)
                  val2 (get @current_vector ind2)
                  ]
              (when (< val2 val1)
                (reset! min_index ind2)
                )
              )
            (recur (inc ind2))
            )
          )
        (reset! current_vector (data/swap @current_vector ind1 @min_index))
        (recur (inc ind1))
        )
      )
    @current_vector
    )
  )

(defn bubble_sort_routines
  [input_vector]
  (let [flipped (atom true)
        vect_size (count input_vector)
        current_vector (atom input_vector)]
    (while (true? @flipped)
      (reset! flipped false)
      (loop [ind 0]
        (when (< ind (dec vect_size))
          (let [v1 (get @current_vector ind)
                v2 (get @current_vector (inc ind))]
            (when (> v1 v2)
              (reset! current_vector (data/swap @current_vector ind (inc ind)))
              (reset! flipped true)
              )
            )
          (recur (inc ind))
          )
        )
      )
    @current_vector
    )
  )

(defn insertion_sort_routines
  [input_vector]
  (let [vect_size (count input_vector)
        current_vector (atom input_vector)
        current_index (atom 0)
        ]
    (loop [ind 1]
      (when (< ind vect_size)
        (reset! current_index ind)
        (while (and (> @current_index 0)
                    (> (get @current_vector (dec @current_index))
                       (get @current_vector @current_index)
                       )
                    )
          (reset! current_vector (data/swap @current_vector @current_index (dec @current_index)))
          (swap! current_index dec)
          )
        (recur (inc ind))
        )
      )

    @current_vector
    )
  )

; uncomment this
;(println "original vector =" data/vector_to_sort)
;(println "Selection Sort =>" (selection_sort_routines data/vector_to_sort))
;(println "Bubble Sort =>" (bubble_sort_routines data/vector_to_sort))
;(println "Insertion Sort =>" (insertion_sort_routines data/vector_to_sort))

