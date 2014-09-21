#!/bin/sh

cd src
java -Dcom.sun.media.jai.disableMediaLib=true -Dawt.useSystemAAFontSettings=on -Dswing.aatext=true -cp src/main/libs/*:. src.main.Kampfrichterlehrgang
cd ..
