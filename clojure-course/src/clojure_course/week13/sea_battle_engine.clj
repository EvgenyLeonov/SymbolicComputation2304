(ns clojure-course.week13.sea_battle_engine)

; IMPORTANT: GAME FIELD COORDINATES ARE 1-BASED
; game field: 10 x 10 squares
; symbols on map: <empty>=nothing, .=miss,X=hit

(defn get_address [x y]
  "x is 1-based"
  (let [letters ["a" "b" "c" "d" "e" "f" "g" "h" "i" "j"]]
    (str (get letters (dec x)) y)
    )
  )

(defn report_attack [x y hit?]
  (println (get_address x y)
           (if (true? hit?)
             "... hit!"
             "... miss!"
             )
           )
  )

(defrecord Coord [x y])
(defrecord Ship [name decks])

(defn is_deck_there? [x_hit y_hit ships]
  (loop [ship_index 0]
    (if (< ship_index (count ships))
      (if (nil? (some->> (:decks (get ships ship_index))
                         (filter #(and (= (:x %) x_hit) (= (:y %) y_hit)))
                         first
                         ))
        (recur (inc ship_index))
        true
        )
      false
      )
    )
  )

(defn is_hit_there? [x_hit y_hit hits]
  (not (nil? (some->> hits
                      (filter #(and (= (:x %) x_hit) (= (:y %) y_hit)))
                      first
                      )))
  )

(defn if_ship_alive? [ship hits]
  (loop [deck_index 0]
    (if (< deck_index (count (:decks ship)))
      (let [deck (get (:decks ship) deck_index)
            x_dec (:x deck)
            y_dec (:y deck)
            ]
        (if (some->> hits
                     (filter #(and (= (:x %) x_dec) (= (:y %) y_dec)))
                     first
                     )
          (recur (inc deck_index))
          true
          )
        )
      false
      )
    )
  )

(defn all_ships_sunken?
  [ships hits]
  (loop [ship_index 0]
    (if (< ship_index (count ships))
      (let [ship (get ships ship_index)
            alive? (if_ship_alive? ship @hits)
            ]
        (if (false? alive?)
          (recur (inc ship_index))
          false
          )
        )
      true
      )
    )
  )

(defn create_map [ships hits]
  "returns vector of vectors (matrix) of the game field"
  (let [title1 "   abcdefghij"
        ;title2 "   __________"
        result (atom [title1])
        ]
    (loop [y 1]
      (when (<= y 10)
        (let [
              y_value (cond
                        (= y 10) "10"
                        :else (str "0" y)
                        )
              row_data (atom [y_value "|"])
              ]
          (loop [x 1]
            (when (<= x 10)
              (let [hit? (is_hit_there? x y @hits)
                    deck? (is_deck_there? x y ships)
                    symbol (cond
                             (and (true? hit?) (true? deck?)) "X"
                             (true? deck?) "O"
                             (true? hit?) "."
                             :else " "
                             )
                    ]
                ;(println "symbol =" symbol)
                (swap! row_data conj symbol)
                )
              (recur (inc x))
              )
            )
          (swap! row_data concat ["|" y_value])
          (swap! result conj (apply str @row_data))
          )
        (recur (inc y))
        )
      )
    (swap! result concat [title1])
    (into [] @result)
    )
  )

(defn draw_map [sea_chart]
  (doseq [row sea_chart]
    (println row)
    )
  )

; direction = 0 horizontal; 1 vertical
(defn place_ship [x_start y_start direction length]
  (let [x_mod (if (= direction 0) 1 0)
        y_mod (if (= direction 0) 0 1)
        decks (atom [])
        ]
    (loop [i 0]
      (when (< i length)
        (swap! decks conj (Coord. (+ x_start (* x_mod i)) (+ y_start (* y_mod i))))
        (recur (inc i))
        )
      )
    @decks
    )
  )

(defn create_submarine
  [index carrier_side]
  (let [opposite_side (cond
                        (= carrier_side 0) 2
                        (= carrier_side 1) 3
                        (= carrier_side 2) 0
                        (= carrier_side 3) 1
                        )
        is_top (= opposite_side 0)
        is_right (= opposite_side 1)
        is_bottom (= opposite_side 2)
        is_left (= opposite_side 3)
        crd [2 5 8]
        submarine_x (cond
                      (true? is_left) 2
                      (true? is_right) 9
                      :else (get crd index)
                      )
        submarine_y (cond
                      (true? is_top) 2
                      (true? is_bottom) 9
                      :else (get crd index)
                      )
        submarine_direction (cond
                              (or (true? is_left) (true? is_right)) 1
                              :else 0
                              )
        ]
    (Ship. (str "Submarine" (inc index)) (place_ship submarine_x submarine_y submarine_direction 2))
    )
  )

(defn create_destroyer
  [index cruiser_direction]
  (let [hor [[4 2] [6 2] [4 8] [6 8]]
        ver [[1 4] [1 6] [10 4] [10 6]]
        crds (if (= 0 cruiser_direction)
               hor
               ver
               )
        x (first (get crds index))
        y (second (get crds index))
        ]
    (Ship. (str "Destroyer" (inc index)) (place_ship x y 0 1))
    )
  )

(defn set_ships []
  (let [
        ; clock-wise N-E-S-W
        carrier_side (rand-int 4)
        carrier_pos (inc (rand-int 7))
        is_top (= carrier_side 0)
        is_bottom (= carrier_side 2)
        is_left (= carrier_side 3)
        is_horizontal (or (true? is_top) (true? is_bottom))
        carrier_direction (cond
                            (true? is_horizontal) 0
                            :else 1
                            )
        carrier_x (cond
                    (true? is_horizontal) carrier_pos
                    (true? is_left) 1
                    :else 10
                    )
        carrier_y (cond
                    (true? is_top) 1
                    (true? is_bottom) 10
                    :else carrier_pos
                    )
        cruiser_direction (cond
                            (true? is_horizontal) 1
                            :else 0
                            )
        cruiser_x (cond
                    (true? is_horizontal) 6
                    :else 4
                    )
        cruiser_y (cond
                    (true? is_horizontal) 4
                    :else 6
                    )


        ships [
               (Ship. "Carrier" (place_ship carrier_x carrier_y carrier_direction 4))
               (Ship. "Cruiser 1" (place_ship 4 4 cruiser_direction 3))
               (Ship. "Cruiser 2" (place_ship cruiser_x cruiser_y cruiser_direction 3))
               (create_submarine 0 carrier_side)
               (create_submarine 1 carrier_side)
               (create_submarine 2 carrier_side)
               (create_destroyer 0 cruiser_direction)
               (create_destroyer 1 cruiser_direction)
               (create_destroyer 2 cruiser_direction)
               (create_destroyer 3 cruiser_direction)
               ]
        ]
    ships
    )
  )

(defn shot! [x y ships hits show_report?]
  (swap! hits conj (Coord. x y))
  (when (true? show_report?)
    (report_attack x y (is_deck_there? x y ships))
    )
  )

; DEBUG
;(report_attack 8 3 false)

;(def test_ship1 (Ship. "Ship1" [(Coord. 1 2) (Coord. 1 3)]))
;(def test_ship2 (Ship. "Ship2" [(Coord. 5 5) (Coord. 6 5) (Coord. 7 5)]))
;(def hits [(Coord. 5 4) (Coord. 1 2) (Coord. 1 3) (Coord. 1 4) (Coord. 5 8) (Coord. 6 5) (Coord. 7 5)])
;(def ships [test_ship1 test_ship2])
;(println (is_deck_there? 1 4 ships))
;(println "Ship1 alive =" (if_ship_alive? test_ship1 hits))
;(println "Ship2 alive =" (if_ship_alive? test_ship2 hits))
;(println "all ships are sunken =" (all_ships_sunken? ships hits))

;(def sea_chart (create_map ships hits))
;(println sea_chart)
;(draw_map sea_chart)