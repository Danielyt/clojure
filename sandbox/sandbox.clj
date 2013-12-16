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

(defn safe-withdraw
  [account amount]
  (dosync
    (when (>= (+ (ensure account-1) (ensure account-2)) amount)
      (Thread/sleep 100)
      (alter account - amount))))

(dorun (pvalues (safe-withdraw account-1 200)
                (safe-withdraw account-2 200)))

(println "Using ensure:")
(printf "- Account 1: %s\n" @account-1)	; - Account 1: 100
(printf "- Account 2: %s\n" @account-2)	; - Account 2: -100
