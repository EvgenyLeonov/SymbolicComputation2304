(ns clojure-course.week14.zombie_outbreak)

; SETTINGS FOR PLACE
(def place_name "Prague 1")

; Prague 1 core population 23000
; every year 6M tourists in Prague; 90% in Prague 1.
(def population (+ 23000 (* 0.9 (/ 6000000 12))))
; Prague 1 square: 5.53 sq km
(def area 5530000)
(def density_of_population (/ population area))

(println place_name)
(println "Population:" (int population))
(println "Area:" area "m2")
(println "Density:" density_of_population "individuals per m2")

; SETTINGS FOR ZOMBIE
; 4 km/h = 1 m/s
; 10 km/h = 3 m/s
(def zombie_speed 1)
(def conversion_time 10)
(println "Zombie speed:" zombie_speed "m/s")
(println "Time required for zombie turning:" conversion_time "s")

; SETTINGS FOR AUTHORITIES
; 5 police departments in Prague 1
(def combatants_available_instantly (* 5 10))
(def action_delay_time (* 30 60))
(def combatants_reinforcement 100)
(def reinforcement_arrival_time (* 45 60))
(def ammo 80)
(def bullets_per_zombie 4)
(println "Policemen for immediate disposal:" combatants_available_instantly)
(println "(they start to act in" action_delay_time "s)")
(println "Policemen in reinforcements:" combatants_reinforcement)
(println "(they will arrive in" reinforcement_arrival_time "s)")
(println "Each combatants has" ammo "bullets, and" bullets_per_zombie "bullets required to eliminate one zombie")

; ESCAPE
; number of persons leaving Prague 1 per second
(def escaped_persons 80)
(println "People are leaving" place_name "with rate" escaped_persons "individuals per second")

; VARIABLES FOR SIR MODEL

(def zombie_initial_number 100)
; it is convenient to run a loop
(def time_of_turn conversion_time)
;effective contact rate of the disease: 1 zombie infects N individuals
(def infected_per_turn (int (Math/ceil (* time_of_turn density_of_population zombie_speed))))
(println "--------------------------------")
(println "Initial number of zombies:" zombie_initial_number)
(println "Individuals infected per turn by each zombie:" infected_per_turn)
; all population
(def susceptible (atom population))
; current number of zombies
(def infected (atom zombie_initial_number))
; eliminated zombies (removed in SIR model)
(def eliminated (atom 0))
; how much people were able to escape
(def escaped (atom 0))

; SIMULATION
(loop [turn 1]
  (when (> @susceptible 0)



    (recur (inc turn))
    )
  (println "Simulation finished. Time:" (* turn time_of_turn))
  )











