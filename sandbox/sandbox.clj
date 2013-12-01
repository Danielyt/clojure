(def my-stack ["lettuce" "cheese" "marmite"])

(conj my-stack "salami")	; ["lettuce" "cheese" "marmite" "salami"]
(peek my-stack)		; "marmite"
(pop my-stack)		; ["lettuce" "cheese"]
(pop [])			; java.lang.IllegalStateException: Can't pop empty vector
