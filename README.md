## Requirements

jdk 8, maven 3+ and git must be installed in order to run the project locally.

## Cloning the repository and running tests

Clone the repository:

`git clone https://github.com/belano/trains-exercise.git trains-exercise`

## Compile and run the program

Navigate to newly created folder `trains-exercise` and run:

`mvn package`

Run the program

`java -jar target/trains-exercise-jar-with-dependencies.jar`

Program runs in interactive mode, first provide the graph definition
```
--Trains exercise--
-------------------
1-Graph definition (use format "AB5")
Enter your graph definition (or "done" to finish graph): AB5
you entered: AB5
Enter your graph definition (or "done" to finish graph): BC4
you entered: BC4
Enter your graph definition (or "done" to finish graph): CD8
you entered: CD8
Enter your graph definition (or "done" to finish graph): DC8
you entered: DC8
Enter your graph definition (or "done" to finish graph): DE6
you entered: DE6
Enter your graph definition (or "done" to finish graph): AD5
you entered: AD5
Enter your graph definition (or "done" to finish graph): CE2
you entered: CE2
Enter your graph definition (or "done" to finish graph): EB3
you entered: EB3
Enter your graph definition (or "done" to finish graph): AE7
you entered: AE7
Enter your graph definition (or "done" to finish graph): DONE
Entered graph definition: [AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7]
```

When you are done setting up the graph definition, calculate distances:
```
2-Calculate distance (use format "A-B-C")
Enter your route (or "done" to exit): A-B-C
distance: 9
Enter your route (or "done" to exit): A-D
distance: 5
Enter your route (or "done" to exit): A-D-C
distance: 13
Enter your route (or "done" to exit): A-E-B-C-D
distance: 22
Enter your route (or "done" to exit): A-E-D
NO SUCH ROUTE
Enter your route (or "done" to exit): DONE
-------------------
--done--
```
