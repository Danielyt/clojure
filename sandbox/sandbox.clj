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

(def people #{"Clayton Shantelle" "Pauleen Linton" "Aniyah Deeann"})
(conj people "Rosamund Maxwell")		; #{“Clayton Shantelle" “Pauleen Linton" "Aniyah Deeann“ “Rosamund Maxwell”}

(conj people "Pauleen Linton")		; #{“Clayton Shantelle" “Pauleen Linton" "Aniyah Deeann"}
(disj people "Pauleen Linton")		; #{“Clayton Shantelle“ “Aniyah Deeann"})
