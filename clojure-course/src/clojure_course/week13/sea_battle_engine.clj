(ns clojure-course.week13.sea_battle_engine)

; IMPORTANT: GAME FIELD COORDINATES ARE 1-BASED
; game field: 10 x 10 squares
; symbols on map: <empty>=nothing, .=miss,X=hit

(defn get_address [x y]
  (let [letters ["a" "b" "c" "d" "e" "f" "g" "h" "i" "j"]]
    (str (get letters (inc x)) y)
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
            alive? (if_ship_alive? ship hits)
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
              (let [hit? (is_hit_there? x y hits)
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

(defn set_ships []
  ;(def test_ship1 (Ship. "Ship1" [(Coord. 1 2) (Coord. 1 3)]))
  ;(def test_ship2 (Ship. "Ship2" [(Coord. 5 5) (Coord. 6 5) (Coord. 7 5)]))
  ;(def hits [(Coord. 5 4) (Coord. 1 2) (Coord. 1 3) (Coord. 1 4) (Coord. 5 8) (Coord. 6 5) (Coord. 7 5)])
  ;(def ships [test_ship1 test_ship2])
  [
   (Ship. "Ship1" [(Coord. 1 2) (Coord. 1 3)])
   (Ship. "Ship2" [(Coord. 5 5) (Coord. 6 5) (Coord. 7 5)])
   ]
  )

(defn shot! [x y hits]
  (swap! hits conj (Coord. x y))

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