(ns quadtree.core
  (:require [quil.core :as q]
            [quadtree.tree :as qt]
            [criterium.core :as crit]))


(def screen-w 720)
(def screen-h 480)
(def num-obj 5000)

(defn rand-dot []
  [(rand-int 400) (rand-int 400) 5 5])

(def rand-dots (vec (repeatedly num-obj rand-dot)))

(defn setup []
  (q/smooth)                                                ;antialiasing
  (q/frame-rate 60)

  (q/background 0)
  (q/stroke-weight 1.5)
  (q/stroke 0 0 125)

  (q/text-align :right)
  (q/fill 255)
  (q/text-size 25)

  (let [quad (qt/build-quadtree [0 0 400 400] rand-dots)]
    (qt/draw-quadtree quad)))

;(crit/bench (qt/build-quadtree [0 0 400 400] rand-dots))

(defn draw []
  (q/text (str "max depth: " 5) 600 22)
  (q/text (str "total objects: " num-obj) 600 70)
  (q/text (str "obj 0 x pos: " 0) 600 94))

(q/defsketch example
             :title "Quadtree"
             :setup setup
             :draw draw
             :size [screen-w screen-h])