(ns clojure-course.week14.zombie_outbreak)

; SETTINGS FOR PLACE
(def place_name "Prague 1")

; Prague 1 core population 23000
; 6M tourists in Prague every year; 90% in Prague 1.
(def population (+ 23000 (* 0.9 (/ 6000000 12))))
; Prague 1 square: 5.53 sq km
(def area 5530000)
(def density_of_population (atom (/ population area)))

(println place_name)
(println "Population:" (int population))
(println "Area:" area "m2")
(println "Density:" @density_of_population "individuals per m2")

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
(def action_delay_time (* 2 60))
(def combatants_reinforcement 100)
(def reinforcement_arrival_time (* 4 60))
(def ammo 80)
(def bullets_per_zombie 4)
; TODO this variable should be calculable
(def killed_zombies_per_turn 0.5)
(println "Policemen for immediate disposal:" combatants_available_instantly)
(println "(they start to act in" action_delay_time "s)")
(println "Policemen in reinforcements:" combatants_reinforcement)
(println "(they will arrive in" reinforcement_arrival_time "s)")
(println "Each combatants has" ammo "bullets, and" bullets_per_zombie "bullets required to eliminate one zombie")

; ESCAPE
; number of persons leaving Prague 1 per second
(def escaped_persons_rate 80)
(println "People are leaving" place_name "with rate" escaped_persons_rate "individuals per second")

; VARIABLES FOR SIR MODEL

(def zombie_initial_number 1)
; it is convenient to run a loop
(def time_of_turn conversion_time)

;effective contact rate of the disease: 1 zombie infects N individuals
(defn infected_per_turn [time_of_turn density_of_population zombie_speed]
  (let [obstacles_k 2]
    (/ (* time_of_turn density_of_population zombie_speed) obstacles_k)
    )
  )

(println "--------------------------------")
(println "Initial number of zombies:" zombie_initial_number)

; all population
(def susceptible (atom population))
; current number of zombies
(def infected (atom zombie_initial_number))
; eliminated zombies (removed in SIR model)
(def eliminated (atom 0))
; how much people were able to escape
(def escaped (atom 0))
(def turn_total (atom 0))

; SIMULATION
(loop [turn 1]
  (reset! turn_total turn)
  (when (and (> @susceptible 0)
             (> @infected 0)
             )
    (let [infected_delta (int (Math/ceil (* @infected (infected_per_turn time_of_turn @density_of_population zombie_speed))))
          to_be_infected (cond
                           (> infected_delta @susceptible) @susceptible
                           :else infected_delta
                           )
          to_be_escaped (* escaped_persons_rate time_of_turn)
          time_passed (* time_of_turn turn)
          zombies_to_be_killed_1 (* killed_zombies_per_turn combatants_available_instantly)
          zombies_to_be_killed_2 (* killed_zombies_per_turn combatants_reinforcement)
          ]
      ; infestation
      (swap! infected + to_be_infected)
      (swap! susceptible - to_be_infected)
      (println "to_be_infected=" to_be_infected "; infected=" (int @infected) "; susceptible=" (int @susceptible) "; escaped so far=" (int @escaped))

      ; people are escaping
      (when (> @susceptible 0)
        (swap! escaped + (cond
                           (> to_be_escaped @susceptible) @susceptible
                           :else to_be_escaped
                           ))
        (swap! susceptible - to_be_escaped)
        )

      ; police takes action
      (when
        (and (>= time_passed action_delay_time) (> @infected 0))
        (swap! infected - zombies_to_be_killed_1)
        (swap! eliminated + zombies_to_be_killed_1)
        (println "Police kills:" (int zombies_to_be_killed_1))
        )

      ;reinforcements
      (when
        (and (>= time_passed reinforcement_arrival_time) (> @infected 0))
        (swap! infected - zombies_to_be_killed_2)
        (swap! eliminated + zombies_to_be_killed_2)
        (println "SWAT kills:" (int zombies_to_be_killed_2))
        )

      (when (< @infected 0)
        (reset! infected 0)
        )
      ; fewer people in the area
      (reset! density_of_population (/ @susceptible area))
      )

    (recur (inc turn))
    )
  )
(let [time_of_simulation (* @turn_total time_of_turn)
      tos_hours (float (/ time_of_simulation 60))
      ]
  (when (< @susceptible 0)
    (reset! susceptible 0)
    )
  (println "--------------------------------")
  (println "Simulation finished. Time:" time_of_simulation "s /" tos_hours "min")
  (println "Zombies in action:" (int @infected))
  (println "Zombies eliminated:" (int @eliminated))
  (println "People escaped:" (int @escaped) )
  (println "People stays in" place_name ":" (int @susceptible))
  (println "Survivors:" (* 100 (/ (+ @susceptible @escaped) population)) "%")
  )












