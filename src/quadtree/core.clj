(ns quadtree.core
    (:require [quil.core :as q]))

(defn setup []
  (q/smooth)                          ;; Turn on anti-aliasing
  (q/frame-rate 60)                    ;; Set framerate to 1 FPS
  (q/background 200))                 ;; Set the background colour to
                                      ;; a nice shade of grey.
(defn draw []
  (q/stroke-weight 3)       ;; Set the stroke thickness randomly
  (q/stroke 0 (q/random 255) (q/random 255) )               ;; Set the fill colour to a random grey

  (let [x (q/random (q/width))]     ;; Set the y coord randomly within the sketch
    (q/line x 0 x (q/height))))         ;; Draw a circle at x y with the correct diameter

(q/defsketch example                  ;; Define a new sketch named example
  :title "Oh so many grey circles"    ;; Set the title of the sketch
  :setup setup                        ;; Specify the setup fn
  :draw draw                          ;; Specify the draw fn
  :size [720 480])                    ;; You struggle to beat the golden ratio
