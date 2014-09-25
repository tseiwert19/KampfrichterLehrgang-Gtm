#!/bin/sh

find -iname '*.class' -delete
javac -encoding UTF-8 -Djava.ext.dirs=src/main/libs src/main/Kampfrichterlehrgang.java
rm ../../"KampfrichterLehrgang Gtm.zip"
7z a -tzip -mx=9 ../../"KampfrichterLehrgang Gtm.zip" ../../"KampfrichterLehrgang Gtm" -xr!*.swp -xr!*~ -xr!*.orig -xr!.gitignore -xr!bin/
#zip -r KampfrichterLehrgang\ Gtm.zip KampfrichterLehrgang\ Gtm
#jar cfm Kampfrichterlehrgang.jar Manifest.txt src img pdf videos
