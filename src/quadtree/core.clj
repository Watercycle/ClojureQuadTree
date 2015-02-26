(ns quadtree.core
    (:require [quil.core :as q]))

(def screen-w 720)
(def screen-h 480)

(def max-tree-depth 8)
(def active-nodes (atom 0))
(def active-objects (atom 0))

(defn setup []
  (q/smooth) ;antialiasing
  (q/frame-rate 60)

  (q/stroke-weight 3)
  (q/stroke 0 0 125 )

  (let [x (q/random (q/width))]
    (q/line x 0 x (q/height)))

  (q/text-align :right)
  (q/fill 255)
  (q/text-size 25))

(defn draw []

  (q/background 0)
  (q/text (str "max depth: " max-tree-depth) 200 22)
  (q/text (str "active nodes: " @active-nodes) 200 46)
  (q/text (str "total objects: " @active-objects) 200 70))

(q/defsketch example
  :title "Quadtree"
  :setup setup
  :draw draw
  :size [screen-w screen-h])
