#!/bin/bash

base=/mnt/c/Users/h-yosiok/Lab/aggregateSpecSusp
opt=/opt/apr-data/example
mkdir -p $base/spectrum

for i in {1..9};
do
    mkdir -p $base/route/math00$i
    cp $opt/math00$i/TR.txt $base/spectrum/math00$i
done

for i in {1..9};
do
    for j in {0..9};
    do
        mkdir -p $base/route/math0$i$j
        cp $opt/math0$i$j/TR.txt $base/spectrum/math0$i$j
    done
done

for i in {0..4};
do
    mkdir -p $base/route/math10$i
    cp $opt/math10$i/TR.txt $base/spectrum/math10$i
done