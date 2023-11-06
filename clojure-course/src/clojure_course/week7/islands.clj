(ns clojure-course.week7.islands
  (:require [clojure-course.week7.islands-init-data :as data])
  )

(def number_of_islands (atom 0))
(def visited_cells (atom '()))
(def chart_width (count (get data/islands_chart 0)))
(def chart_height (count data/islands_chart))

; row, column are 0-based
(defn get_cell
  [row column]
  (get (get data/islands_chart row) column)
  )

(defn is_soil
  [row column]
  (= 1 (get_cell row column))
  )

(defn get_key [row column]
  (+ (str row) "_" (str column))
  )

(defn is_valid_cell
  [way]
  (let [row (first way)
        col (second way)
        result (and
                 (> row 0)
                 (> col 0)
                 (< row chart_height)
                 (< col chart_width)
                 )
        ]
    result
    )
  )

(defn explore_island
  [current_row current_col]
  (let [possible_ways [
                       ; North
                       [(dec current_row) current_col]
                       ; West
                       [current_row (dec current_col)]
                       ; East
                       [current_row (inc current_col)]
                       ; South
                       [(inc current_row) current_col]
                       ]
        k (get_key current_row current_col)
        ]
    (when (and
            (is_valid_cell [current_row current_col])
            (is_soil current_row current_col)
            )
      (swap! visited_cells conj k)
      (doseq [way possible_ways
              :when (is_valid_cell way)
              ]
        (explore_island (first way) (second way))
        )
      )
    )
  )

(defn is_valid_row [row]
  (< row chart_height)
  )
(defn is_valid_col [col]
  (< col chart_width)
  )

; main loop
(loop [r_index 0]
  (when (is_valid_row r_index)
    (loop [c_index 0]
      (when (is_valid_col c_index)
        (when (and
                (is_soil r_index c_index)
                (not (.contains visited_cells (get_key r_index c_index)))
                )
          ; a new island found
          (swap! number_of_islands inc)
          ; run its exploring
          (explore_island r_index c_index)
          )

        (recur (inc c_index))
        )
      )
    (recur (inc r_index))
    )
  )

(println "Number of islands:" @number_of_islands)


