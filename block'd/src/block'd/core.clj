(ns block'd.core
  (:gen-class)
  (:use [block'd.gui :only (start-game)]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (start-game)
  nil)
  
  ; (invoke-later
  ;   (-> (frame :title "Hello",
  ;              :content "Hello, Seesaw",
  ;              :on-close :exit)
  ;       pack!
  ;       show!)))
