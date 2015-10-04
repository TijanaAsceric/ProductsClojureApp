(ns clprojekat.dbbroker
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.conversion :as mconv]
            [monger.operators :refer :all])
  (:import (org.bson.types ObjectId)))

(def connection (mg/connect))

(def db (mg/get-db connection "clprojekat_database"))

(defn auth [username password]
  (let [existing-username (mc/find-maps db "users" {:username username}) existing-password (mc/find-maps db "users" {:password password})]    
    (or (empty? existing-username) (empty? existing-password))))

(defn insert-admin []
  (mc/insert db "users" {:id (inc 1) :username "admin" :password "admin" }))

(defn insert [product_name description year price exrate priceeur]
  (mc/insert db "products" {:name product_name, :description description, :year year, :price price, :exrate exrate, :priceeur priceeur}))

(defn read-all-products []
    (mc/find-maps db "products"))

(defn get-all [table]
  (mc/find-maps db table))

(defn delete-pr [product_id]
  "Delete product from database."
  (mc/remove db "products" {:_id (ObjectId. product_id)}))

(defn update-pr [product_id pr_name description year price exrate priceeur]
  (mc/update db "products" {:_id (ObjectId. product_id)} {:name pr_name} {:description description} {:year year} {:price price} {:exrate exrate} {:priceeur priceeur}))

(defn search-product [prname]
  (mc/find-one-as-map db "products" {:name prname}))



