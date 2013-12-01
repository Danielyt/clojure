(def my-bag ["laptop" "knife" "earphones" "phone"])
(def stuff-for-fmi ["clicker" "piece of mind" "Snickers"])

(conj my-bag "ФЗФ file") ; ["laptop" "knife" "earphones" "phone" "ФЗФ file"]
(into my-bag stuff-for-fmi) ; ["laptop" "knife" "earphones" "phone" "clicker" "piece of mind" "Snickers"]
(my-bag 1) ; "knife" 
