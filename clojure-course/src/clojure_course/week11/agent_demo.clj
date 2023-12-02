(ns clojure-course.week11.agent_demo)

(defn incrementation [current_value new_value]
  (Thread/sleep 1000)
  (println "this is current value ="  current_value)
  (+ current_value new_value)
  )

(defn watch_handler
  [key ref old_val new_val]
  (println "key =" key)
  (println "ref =" ref)
  (println "old_val =" old_val)
  (println "new_val =" new_val)
  )

(def agent1 (agent 400))
;(add-watch agent1 :agentWatcher watch_handler)
(send agent1 incrementation 300)
(await-for 2000 agent1 )
(println @agent1)




