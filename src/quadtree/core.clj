(ns quadtree.core
  (:require [quil.core :as q]
            [quadtree.tree :as qt]))


(def screen-w 720)
(def screen-h 480)
(def num-obj 500)

(def rand-dots
  (repeat num-obj [(rand-int 300) (rand-int 300) 5 5]))

(def objs (atom rand-dots))

(defn setup []
  (q/smooth)                                                ;antialiasing
  (q/frame-rate 60)

  (q/background 0)
  (q/stroke-weight 3)
  (q/stroke 0 0 125)

  (q/text-align :right)
  (q/fill 255)
  (q/text-size 25))

rand-dots
(binding [clojure.pprint/*print-right-margin* 200]
  (clojure.pprint/pprint (qt/build-quadtree [235 115 250 250] rand-dots)))


;(qt/build-quadtree [235 115 250 250] rand-dots)

(defn draw []
  (q/text (str "max depth: " 5) 250 22)
  ;(q/text (str "active nodes: " qt/num-nodes) 200 46)
  (q/text (str "total objects: " num-obj) 250 70)
  (q/text (str "obj 0 x pos: " 0) 250 94))

(q/defsketch example
             :title "Quadtree"
             :setup setup
             :draw draw
             :size [screen-w screen-h])
