(ns clojure-course.week7.islands-roman-yepanchenko
  (:require [clojure-course.week7.islands-init-data :as data])
  )


(defn checkNeighbors
  [columnIndex, rowIndex]

  (let [response (atom {})]

    (if (>= (- columnIndex 1) 0)
      (do
        (reset! response (assoc @response "top" {"coordinates" {"cI" (- columnIndex 1) "rI" rowIndex} "value" (get (get data/islands_chart (- columnIndex 1)) rowIndex)}))
        )
      (do
        (reset! response (assoc @response "top" {"coordinates" {"cI" (- columnIndex 1) "rI" rowIndex} "value" -1}))
        )
      )

    (if (>= (- (count data/islands_chart) 1) (+ columnIndex 1))
      (do
        (reset! response (assoc @response "bottom" {"coordinates" {"cI" (+ columnIndex 1) "rI" rowIndex} "value" (get (get data/islands_chart (+ columnIndex 1)) rowIndex)}))
        )
      (do
        (reset! response (assoc @response "bottom" {"coordinates" {"cI" (+ columnIndex 1) "rI" rowIndex} "value" -1}))
        )
      )

    (if (>= (- (count (get data/islands_chart columnIndex)) 1) (+ rowIndex 1))
      (do
        (reset! response (assoc @response "right" {"coordinates" {"cI" columnIndex "rI" (+ rowIndex 1)} "value" (get (get data/islands_chart columnIndex) (+ rowIndex 1))}))
        )
      (do
        (reset! response (assoc @response "right" {"coordinates" {"cI" columnIndex "rI" (+ rowIndex 1)} "value" -1}))
        )
      )

    (if (>= (- rowIndex 1) 0)
      (do
        (reset! response (assoc @response "left" {"coordinates" {"cI" columnIndex "rI" (- rowIndex 1)} "value" (get (get data/islands_chart columnIndex) (- rowIndex 1))}))
        )
      (do
        (reset! response (assoc @response "left" {"coordinates" {"cI" columnIndex "rI" (- rowIndex 1)} "value" -1}))
        )
      )
    @response
    )
  )

(let
  [
   connectedIslandsArray (atom [])
   trueIslandsArray (atom [])
   hashMap (atom {})
   ]

  (let [
        columnIndex (atom 0)
        rowIndex (atom 0)
        ]
    (doseq [rowValues data/islands_chart]
      (reset! rowIndex 0)

      (doseq [rowValue rowValues]
        (if (= rowValue 1)
          (reset! trueIslandsArray (conj @trueIslandsArray {"cI" @columnIndex "rI" @rowIndex}))
          )
        (reset! rowIndex (+ @rowIndex 1))
        )

      (reset! columnIndex (+ @columnIndex 1))
      )
    )

  (doseq [coordinates @trueIslandsArray]
    ; (println coordinates ":")
    (let [
          arrayOfConnectedIslandsToCurrentIsland (atom [])
          currentIslandStringCoordinates (str (get coordinates "cI") (get coordinates "rI"))
          connectionStringCoordinates (atom "")
          ]

      (reset! arrayOfConnectedIslandsToCurrentIsland (into [] (filter #(= (get (get % 1) "value") 1) (checkNeighbors (get coordinates "cI") (get coordinates "rI")))))

      (if (empty? @connectedIslandsArray)
        (do
          (reset! connectedIslandsArray (conj @connectedIslandsArray [currentIslandStringCoordinates]))
          (reset! hashMap (assoc @hashMap currentIslandStringCoordinates 0))
          )
        (do
          (let [refToIndexOfConnectedIslandsArray (atom -1)]
            (doseq [neighbor @arrayOfConnectedIslandsToCurrentIsland]
              (reset! connectionStringCoordinates (str (get (get (get neighbor 1) "coordinates") "cI") (get (get (get neighbor 1) "coordinates") "rI")))

              ;(println "connection string coordinates" @connectionStringCoordinates)

              (if (not (= (get @hashMap @connectionStringCoordinates) nil))
                (do
                  (reset! refToIndexOfConnectedIslandsArray (get @hashMap @connectionStringCoordinates))
                  )
                )
              )

            (if (not (= @refToIndexOfConnectedIslandsArray -1))
              (do
                (reset! connectedIslandsArray (assoc @connectedIslandsArray @refToIndexOfConnectedIslandsArray (conj (get @connectedIslandsArray @refToIndexOfConnectedIslandsArray) currentIslandStringCoordinates)))
                (reset! hashMap (assoc @hashMap currentIslandStringCoordinates @refToIndexOfConnectedIslandsArray))
                )
              (do
                (reset! connectedIslandsArray (conj @connectedIslandsArray [currentIslandStringCoordinates]))
                (reset! hashMap (assoc @hashMap currentIslandStringCoordinates (- (count @connectedIslandsArray) 1)))
                )
              )
            )
          )
        )
      )
    ;(println "===========")
    )

  ;(println @hashMap)
  (println @connectedIslandsArray)
  (println "Number of islands:" (count @connectedIslandsArray))
  )

