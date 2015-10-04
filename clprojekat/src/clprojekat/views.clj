(ns clprojekat.views
(:use clostache.parser
        [hiccup core page])
  (:require [clojure.java.io]
            [noir.session :as session]))
(defn login-page []
  (render-resource "templates/login.html"))
(defn index-page []
  (render-resource "templates/index.html"))
(defn products-page [products]
  (render-resource "templates/products.html" products))




;(defn not-found [] 
;  (html5
;    [:div#not-found.red "Page Not Found!"]))