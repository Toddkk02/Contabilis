#!/bin/bash

# Crea directory temporanea
rm -rf temp
mkdir -p temp/image

# Compila i file Java
javac -d temp -cp ".:lib/*" src/**/*.java

# Copia le risorse
cp -r main/resources/image/* temp/image/

# Crea il JAR
cd temp
jar cvfe ../contabilis.jar Main *

# Aggiungi le librerie al JAR
cd ..
jar uf contabilis.jar -C lib .

# Pulisci
rm -rf temp

echo "JAR created successfully!"
