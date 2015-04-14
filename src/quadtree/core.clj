(ns quadtree.core
  (:require [clojure.math.numeric-tower :as math]
            [quil.core :as q]
            [quadtree.tree :as qt]
            [criterium.core :as crit]))

(def rand-seed (java.util.Random. 3792))
(defn randfn
  ([n] (.nextInt rand-seed n)))

(def screen-w 720)
(def screen-h 480)
(def num-obj (math/expt 4 6))

(defn rand-dot [] [(randfn 400) (randfn 400) 5 5])
(def rand-dots (vec (repeatedly num-obj rand-dot)))

(println "SEQUNTIAL 4^6")
(crit/bench (qt/build-quadtree [0 0 400 400] rand-dots))

(comment
  (defn setup []
    (q/smooth)                                              ;antialiasing
    (q/frame-rate 60)

    (q/background 0)
    (q/stroke-weight 1.5)
    (q/stroke 0 0 125)

    (q/text-align :right)
    (q/fill 255)
    (q/text-size 25)

    (let [quad (qt/build-quadtree [0 0 400 400] rand-dots)]
      (qt/draw-quadtree quad)))

  (defn draw []
    (q/text (str "max depth: " 5) 600 22)
    (q/text (str "total objects: " num-obj) 600 70)
    (q/text (str "obj 0 x pos: " 0) 600 94))

  (q/defsketch example
               :title "Quadtree"
               :setup setup
               :draw draw
               :size [screen-w screen-h]))