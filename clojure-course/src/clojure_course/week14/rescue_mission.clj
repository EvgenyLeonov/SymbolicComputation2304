(ns clojure-course.week14.rescue_mission
  (:require [clojure-course.week14.rescue_mission_data :as data])
  )

(def airplane_x 50)
(def airplane_y 57)
(def max_distance_of_transmission 10)
(def airplane_x_min (- airplane_x max_distance_of_transmission))
(def airplane_x_max (+ airplane_x max_distance_of_transmission))
(def airplane_y_min (- airplane_y max_distance_of_transmission))
(def airplane_y_max (+ airplane_y max_distance_of_transmission))
(def suitable_points (atom []))

(defn linear_function [point1 point2]
  "calculates k and b in y = k * x + b"
  ; x1 must not be equal x2
  (let [x1 (int (first point1))
        y1 (int (last point1))
        x2 (int (first point2))
        y2 (int (last point2))
        ;_ (println "x1=" x1 "; x2=" x2 "; y1=" y1 "; y2=" y2)
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
        x_from (cond
                 (> xi xa) xa
                 :else xi
                 )
        x_to (cond
               (> xi xa) xi
               :else xa
               )
        lin_f (linear_function [xa ya] [xi yi])
        k (first lin_f)
        b (second lin_f)
        heights (atom [])
        result (atom [])
        ]
    ;(println "x_from=" x_from "; x_to=" x_to)
    (loop [x x_from]
      (when (<= x x_to)
        (let [y (int (Math/ceil (+ (* k x) b)))
              z (get_height x y)
              ;_ (println "x=" x "; y=" y "; z=" z "; k=" k "; b=" b)
              ]
          (when (> z 0)
            (swap! heights conj z)
            ;(reset! x_last x)
            ;(reset! y_last y)
            ;(println "xx x_last=" @x_last "; y_last=" @y_last)
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
            ;_ (println "max_v=" max_v "; max_ind=" max_ind)
            valid? (= max_ind 0)
            ]
        (when (true? valid?)
          ;(println "x_last=" @x_last "; y_last=" @y_last)
          ;(reset! result (vec [@x_last @y_last]))
          (reset! result (vec [xi yi]))
          )
        )
      )

    @result
    )
  )

(defn find_radio_signal []
  (loop [x airplane_x_min]
    (when (<= x airplane_x_max)
      (loop [y airplane_y_min]
        (when (<= y airplane_y_max)
          (let [airplane_position (vec [airplane_x airplane_y])
                point_of_interest (vec [x y])
                ;_ (println "point_of_interest=" point_of_interest)
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
  (doseq [p @suitable_points]
    (println (first p) "," (last p))
    )
  )

(defn find_potential_lzs []
  ; works as stack
  (let [
        ; landing zones
        lzs (atom [])
        point_of_interest (atom nil)
        ]
    (loop [index 1]
      (when (< index (count data/data))
        (let [v1 (get data/data index)
              v0 (get data/data (dec index))
              x_1 (int (first v1))
              y_1 (int (second v1))
              z_1 (int (last v1))
              x_0 (int (first v0))
              y_0 (int (second v0))
              z_0 (int (last v0))
              y_poi (cond
                      (nil? @point_of_interest) -1
                      :else (int (second @point_of_interest))
                      )
              ]
          (if (nil? @point_of_interest)
            (when (and
                    (= x_0 x_1)
                    (= y_0 (dec y_1))
                    (= (dec z_0) z_1)
                    )
              (reset! point_of_interest (vec [x_0 y_0 z_0]))
              ;(println "point_of_interest set=" @point_of_interest)
              )
            (do
              ;(println "z_0=" z_0 "; z_1=" z_1 "; y_point=" y_poi "; y_1=" y_1)
              (cond
                (and
                  (= z_1 (int (last @point_of_interest)))
                  ; min length of a landing zone is 3
                  (>= (- y_1 y_poi) 3)
                  )
                (do
                  (swap! lzs conj @point_of_interest)
                  (reset! point_of_interest nil)
                  )
                ;;;;
                (< z_0 z_1) (reset! point_of_interest nil)
                )
              )

            )
          (recur (inc index))
          )
        )
      )
    (println "points of interest:")
    (doseq [p @lzs]
      (println (first p) "," (second p))
      )
    )
  )


(find_radio_signal )

;(find_potential_lzs)



; DEBUG
;(def point1 [0 0])
;(def point2 [-2 1])
;(linear_function point1 point2)

;(println (get_height 99 2))

; [60 30] [70 85]
; [60 30] [66 63]
;(println (is_point_suits? [60 30] [70 85]))





