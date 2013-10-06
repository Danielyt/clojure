(ns block'd.gui
  (use [seesaw core color graphics font]
       [block'd.logic :only (board-width board-height)]
       block'd.state))

(def cell-size 25)
(def board-margin 10)

(defn rect-from [[x y]]
  (letfn [(project [n] (+ board-margin (* n cell-size)))]
    (rect (project x) (project y) cell-size cell-size)))

(defn cell-from [point]
  (letfn [(project [n] (int (/ (- n board-margin) cell-size)))]
    (let [x (.x point)
          y (.y point)]
      (if (and (> x board-margin)
               (> y board-margin)
               (< (project x) board-width)
               (< (project y) board-height))
        [(project x) (project y)]
        [-1 -1]))))

(defn draw-board [c g]
  (doseq [y (range board-height)
          x (range board-width)]
    (draw g (rect-from [x y]) (style :foreground :black :background (get-cell-colour [x y])))))
   

(defn make-panel []
  (border-panel
    :north	(flow-panel	:align :center
                        :items [(label :text "Score:" :font (font :name "ARIAL" :style :bold :size 14))
                                (label :text "0"
                                       :class :score
                                       :font (font :name "ARIAL" :style :bold :size 14))])
    :east	(grid-panel :rows 4
                      	:columns 1
                        :size [65 :by 450]
                      	:items [(flow-panel :align :left 
                                           	:items [(label	:text "Level:  "
															:font (font :name "ARIAL" :style :bold :size 14))
                                                    (label	:text "1"
                                                            :class :level
                                                            :font (font :name "ARIAL" :style :bold :size 14))])
                               (flow-panel :align :left
                                           :items [(label	:text "Amount:"
															:font (font :name "ARIAL" :style :bold :size 14))
                                                   (label	:text "0"
                                                            :class :amount
                                                            :font (font :name "ARIAL" :style :bold :size 14))])
                               (flow-panel :align :left
                                           :items [(label	:text "Goal:"
                                                            :font (font :name "ARIAL" :style :bold :size 14))
                                                   (label	:text "3000"
                                                            :class :goal
                                                            :font (font :name "ARIAL" :style :bold :size 14))])
                               (flow-panel :align :left
                                           :items [(label	:text "Time:"
                                                            :font (font :name "ARIAL" :style :bold :size 14))
                                                   (label	:text "00:00"
                                                            :class :time
                                                            :font (font :name "ARIAL" :style :bold :size 14))])])
    :center (canvas :paint draw-board
                  :class :board
                  :background :black)
    :south (flow-panel :align :center
                       :items [(button :text "Pause" :class :pause)])
    :vgap 5
    :hgap 5
    :border 5))


(defn make-frame []
  (frame :title   "Block'd"
         :size    [360 :by 520]
         :content (make-panel)
         :resizable? false
         :on-close :exit))

(defn redisplay [root]
  (config! (select root [:.board])	:paint draw-board)
  (config! (select root [:.amount])	:text (square-of-whites))
  (config! (select root [:.score]) :text (get-score))
  (config! (select root [:.last]) :text (get-last))
  (config! (select root [:.time]) :text (get-formated-time))
  (config! (select root [:.goal]) :text (get-goal))
  (config! (select root [:.level]) :text (get-level)))

(declare ticker)

(defn add-behaviours [root]
  (listen (select root [:.board]) :mouse-moved (fn [e] (hover-over-cell (cell-from (.getPoint e))) (redisplay root)))
  (listen (select root [:.board]) :mouse-clicked (fn [e] (if (.isRunning ticker)
                                                           (do
                                                             (remove-cells)
                                                             (hover-over-cell (cell-from (.getPoint e)))
                                                             (possible-move?))
                                                           (alert root "The game is paused."))
                                                   (redisplay root)))
  (listen (select root [:.pause])  :mouse-clicked (fn [e] (if (.isRunning ticker) (.stop ticker) (.start ticker)))))

(defonce the-frame (make-frame))
(defonce ticker (timer (fn [_]
                         (countdown)
                         (when (and (neg? (get-time))
                                    (pos? (get-goal)))
                           
                           (alert (format "Game Over!\nYour score is %d" (get-score)))
                           (reset-values))
                         (if (<= (get-goal) 0)
                           (next-level))
                         (redisplay the-frame))))

(defn start-game[]
  (native!)
  (config! the-frame :content (make-panel))
  (show! the-frame)
  (add-behaviours the-frame)
  (redisplay the-frame))