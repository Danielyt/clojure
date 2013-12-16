(def l '(:lettuce :tomato :cheese))
(def v [:lettuce :tomato :cheese])

(conj l :salami)	; (:salami :lettuce :tomato :cheese)
(conj v :salami)	; [:lettuce :tomato :cheese :salami]

(peek l)		; :lettuce
(peek v)		; :cheese

(pop l)		; (:tomato :cheese)
(pop v)		; [:lettuce :tomato]

(defn fib [n]
  (let [fib-seq (lazy-cat [1 1] (map + (rest fib-seq) fib-seq))]
    (nth fib-seq n)))

(def number (atom 0))
(defn slow-inc [x]
  (printf "Incrementing %s slowly...\n" x)
  (Thread/sleep 100)
  (inc x))
(defn very-slow-inc [x]
  (printf "Incrementing %s very slowly...\n" x)
  (Thread/sleep 500) (inc x))
(future (swap! number very-slow-inc))
(future (swap! number slow-inc))


; Incrementing 0 very slowly…
; Incrementing 0 slowly…
; Incrementing 1 very slowly... 

