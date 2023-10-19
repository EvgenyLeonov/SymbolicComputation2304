(ns clojure-course.week4.decameron-tales-vladyslav-zolotarevskyi-02
  (:require [clojure.string :as str])
  )

(defn Narrator [text]
  (let [sentences (str/split text #"\.\s+")
        last-sentence (last sentences)]
    (doseq [sentence (butlast sentences)]
      (doseq [char (str/split sentence #"")]
        (flush)
        (print char)
        (Thread/sleep 100))
      (print ".\n\n"))
    (Thread/sleep 5000)
    (doseq [char (str/split last-sentence #"")]
      (flush)
      (print char)
      (Thread/sleep 100))))

(Narrator "A Sicilian woman cunningly conveys from a merchant that which he has brought to Palermo. He made a show of being come back with far greater store of goods than before. The merchant borrows money of her, and leaves her in lieu thereof water and tow.\n\nSimona loves Pasquino. They are together in a garden. Pasquino rubs a leaf of sage against his teeth, and dies. Simona is arrested. With intent to show the judge how Pasquino died, rubs one of the leaves of the same plant against her teeth, and likewise dies.\n\nFederigo, who loves but is not loved in return, spends all the money he has in courtship. Eventually, he left with only a falcon. Since he has nothing else to give her, he offers the falcon to his lady to eat when she visits his home. She, learning of this, changes her mind, takes him for her husband, and makes him rich.")

