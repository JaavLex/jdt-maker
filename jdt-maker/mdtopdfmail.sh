#!/bin/bash

for i in "$@"; do
  case $i in
    -f=*|--file=*)
      F_NAME="${i#*=}"
      shift
    ;;
    -m=*|--mail=*)
      M_NAME="${i#*=}"
      shift
    ;;
  esac
done

echo "$F_NAME"

pandoc $F_NAME.md -o $F_NAME.pdf
mailx -s "Rapport de la semaine ($F_NAME) - Javet Alexandre" -A ./$F_NAME.pdf $M_NAME <<< "Hello! Voici le rapport de la semaine en ci-joint Cordialement, Javet Alexandre"
