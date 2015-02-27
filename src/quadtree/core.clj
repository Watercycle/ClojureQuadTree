(ns quadtree.core
    (:require [quil.core :as q]
              [quadtree.tree :as qt]))


(def screen-w 720)
(def screen-h 480)
(def num-obj 500)

(defrecord dot [x y r])
(def rand-dots (take num-obj (repeatedly #(dot. (rand-int 20) (rand-int 20) 5))))

(def objs (atom (vec rand-dots)))

(defn setup []
  (q/smooth) ;antialiasing
  (q/frame-rate 60)

  (q/stroke-weight 3)
  (q/stroke 0 0 125 )

  (let [x (q/random (q/width))]
    (q/line x 0 x (q/height)))

  (q/text-align :right)
  (q/fill 255)
  (q/text-size 25)

  (qt/build-quadtree @objs))

(defn draw []
  (q/background 0)
  (q/text (str "max depth: " 5) 250 22)
;  (q/text (str "active nodes: " qt/num-nodes) 200 46)
  (q/text (str "total objects: " num-obj) 250 70)
  (q/text (str "obj 0 x pos: " (:x (first @objs))) 250 94))

(q/defsketch example
  :title "Quadtree"
  :setup setup
  :draw draw
  :size [screen-w screen-h])