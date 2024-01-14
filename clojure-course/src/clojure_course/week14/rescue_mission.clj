(ns clojure-course.week14.rescue_mission
  (:require [clojure-course.week14.rescue_mission_data :as data])
  )

(def airplane_x 60)
(def airplane_y_min 30)
(def airplane_y_max 60)
(def max_distance_of_transmission 2)
(def airplane_x_min (- airplane_x max_distance_of_transmission))
(def airplane_x_max (+ airplane_x max_distance_of_transmission))
(def suitable_points (atom []))

(defn linear_function [point1 point2]
  "calculates k and b in y = k * x + b"
  ; x1 must not be equal x2
  (let [x1 (int (first point1))
        y1 (int (last point1))
        x2 (int (first point2))
        y2 (int (last point2))
        k (cond
            (= x1 x2) 0
            :else (/ (- y2 y1) (- x2 x1))
            )
        b (cond
            (= x1 x2) 0
            :else (/ (- (* x2 y1) (* x1 y2)) (- x2 x1))
            )
        ]
    ;(println "k=" k "; b=" b)
    (vec [k b])
    )
  )

(defn get_height [x y]
  (some->> data/data
           (filter #(and (= (first %) x) (= (second %) y)))
           last
           last
           int
           )
  )

(defn is_point_suits? [point_airplane point_of_interest]
  ; check from point_of_interest to point_airplane (direction is important)
  (let [xa (int (first point_airplane))
        ya (int (last point_airplane))
        xi (int (first point_of_interest))
        yi (int (last point_of_interest))
        lin_f (linear_function [xa ya] [xi yi])
        k (first lin_f)
        b (second lin_f)
        heights (atom [])
        x_last (atom 0)
        y_last (atom 0)
        result (atom [])
        ]
    (loop [x (inc xa)]
      (when (<= x xi)
        (let [y (int (Math/ceil (+ (* k x) b)))
              z (get_height x y)
              _ (println "x=" x "; y=" y "; z=" z "; k=" k "; b=" b)
              ]
          (when (some? z)
            (swap! heights conj z)
            (reset! x_last x)
            (reset! y_last y)
            )
          )
        (recur (inc x))
        )
      )
    (when (> (count @heights) 0)
      (reset! heights (into [] (reverse @heights)))
      ; radio signal can't pass through an obstacle on its way
      (let [max_v (apply max @heights)
            max_ind (.indexOf @heights max_v)
            _ (println "max_v=" max_v "; max_ind=" max_ind)
            valid? (= max_ind 0)
            ]
        (when (true? valid?)
          (reset! result (vec [@x_last @y_last]))
          )
        )
      )

    @result
    )
  )

(loop [x airplane_x_min]
  (when (<= x airplane_x_max)
    (loop [y airplane_y_min]
      (when (<= y airplane_y_max)
        (let [airplane_position (vec [airplane_x y])
              ; we scan horizontally only
              point_of_interest (vec [x y])
              result (is_point_suits? airplane_position point_of_interest)]
          (when (> (count result) 0)
            (swap! suitable_points conj result)
            )
          )
        (recur (inc y))
        )
      )
    (recur (inc x))
    )
  )
(println @suitable_points)

; DEBUG
;(def point1 [0 0])
;(def point2 [-2 1])
;(linear_function point1 point2)

;(println (get_height 99 2))

; [60 30] [70 85]
; [60 30] [66 63]
;(println (is_point_suits? [60 30] [70 85]))





