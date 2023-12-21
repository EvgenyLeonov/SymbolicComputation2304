(ns clojure-course.datasets.broker.sales_routines
  (:require [clojure-course.datasets.broker.broker :as broker])
  ; TODO replace this link with your engine
  (:require [clojure-course.datasets.broker.demo_search_engine :as your_engine])
  )

; TODO SET YOUR TEAM NUMBER: 1-7
(def team_number 1)
(def search_ticket_function your_engine/prepare_travel_plan)
(broker/run team_number search_ticket_function)




