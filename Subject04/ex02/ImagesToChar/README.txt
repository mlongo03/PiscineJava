PROJECT DESCRIPTION:

this project has to convert a bmp two-colored image (only black and white) in a rappresentation of its own on the standard output where a choosen color will rappresent
black pixels and another one white pixels, the color for white pixel and the other one for the black pixel are get from the input parameters and the path of the file
aswell in particular the form of the the excecution will be explained.

COMPILATION:

mkdir lib
curl https://repo1.maven.org/maven2/com/diogonunes/JColor/5.5.1/JColor-5.5.1.jar --output lib/JColor-5.5.1.jar
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar --output lib/jcommander-1.82.jar
javac -cp "lib/JColor-5.5.1.jar:lib/jcommander-1.82.jar" -d target src/java/edu.Roma42.printer/*/*.java

EXCECUTION:

cp -r src/resources target
cd target; jar -xf ../lib/JColor-5.5.1.jar com; jar -xf ../lib/jcommander-1.82.jar com; cd ..
java -cp ./target edu.Roma42.printer.logic.Program --black=<color> --white=<color>

JAR ASSEMBLY:

mkdir lib
curl https://repo1.maven.org/maven2/com/diogonunes/JColor/5.5.1/JColor-5.5.1.jar --output lib/JColor-5.5.1.jar
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar --output lib/jcommander-1.82.jar
javac -cp "lib/JColor-5.5.1.jar:lib/jcommander-1.82.jar" -d target src/java/edu.Roma42.printer/*/*.java
cp -r src/resources target
cd target; jar -xf ../lib/JColor-5.5.1.jar com; jar -xf ../lib/jcommander-1.82.jar com; cd ..
jar -cfmv target/images-to-chars-printer.jar ./src/manifest.txt -C target edu -C . target/resources -C target/com

JAR STARTUP:

java -jar target/images-to-chars-printer.jar --black=<color> --white<color>


