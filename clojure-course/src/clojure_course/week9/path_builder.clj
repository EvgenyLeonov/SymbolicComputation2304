(ns clojure-course.week9.path_builder
  (:require [clojure.string :as string])
  )

(defn read_path_dataset []
  (with-open [rdr (clojure.java.io/reader "src/clojure_course/week9/path_dataset.txt")]
    (reduce conj [] (line-seq rdr)))
  )

(defn find_segment_started_with
  [letter dataset]
  (filterv #(= letter (first (string/split % #"\ "))) dataset)
  )

(defn process_segment
  [input_vector search_for_letter dataset]
  (let [path_on_this_step (conj input_vector search_for_letter)
        segments_from_here (find_segment_started_with search_for_letter dataset)
        ]
    (if (empty? segments_from_here)
      (println "path evaluated =" path_on_this_step)
      (do
        (doseq [segment segments_from_here
                :let [destination (last segment)]
                ]
          (process_segment path_on_this_step (str destination) dataset)
          )
        )
      )
    )
  )

(process_segment [] "A" (read_path_dataset))

; DEBUG
;(println "find_segment_started_with=" (find_segment_started_with "A" dataset))


