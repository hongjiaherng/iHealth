
![](../iHealth/src/res/images/patient.png)

[iHealth](https://github.com/hongjiaherng/iHealth) is a clinic appointment system written in Java using JavaFX

## Contributors
    - Hong Jia Herng
    - Chai Nam Chi
    - Desmond Tee Yu Wei
    - Boo Zhan Yi
    - Chong Jun Yi

## Libraries used
- [Java SE 15](https://www.oracle.com/java/technologies/javase-downloads.html)
- [JavaFX 15.0.1](https://gluonhq.com/products/javafx/)
- [mongodb-java-driver 3.11.0](https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver/3.11.0) - for connection to MongoDB with Java
- [Argon2-jvm](https://github.com/phxql/argon2-jvm) - for password hashing

## To set up the project in IDE
we use Intellij IDEA as the example
1. Download and import the external libraries
   * Download
       * Make sure you have the JDK installed (Java 8 onwards will be compatible)
       * download [JavaFX SDK version 15.0.1](https://gluonhq.com/products/javafx/)
    
   * Include external libraries into IDE
       1) File > Project Structure > Libraries
       2) Project Settings > Libraries > + 
       - ![](https://www.jetbrains.com/help/img/idea/2020.3/javafx-install-sdk.png)
       3) Add the .jar file of the external libraries
       - Add the lib folder of JavaFX
       - Add the Argon2-jvm jar (contains 2 jar files, go to the folder we provided for you)
       - Add the mongodb java driver (contains 3 jar files, go to the folder we provided for you)
       4) Run the program once, and you will see an error indicates JavaFX runtime component are missing
       5) Select Add configuration
       - ![](https://i.stack.imgur.com/eOaYu.png)
       6) From More options list, select Add VM options (include JavaFX runtime component)
       - ![](https://www.jetbrains.com/help/img/idea/2020.3/javafx-vm-options-add-field.png)
         * --module-path </your/path/to/javafx/sdk/lib> --add-modules javafx.controls,javafx.fxml 
         * e.g.:
            - --module-path "C:\Program Files\Java\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml
       7) There you go! You can run the program now 
