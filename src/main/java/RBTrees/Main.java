package RBTrees;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        RBTree<RBTree.IntComparable> tree = new RBTree<>();
        Random random = new Random();
        Set<Integer> generatedNumbers = new HashSet<>();

        String currentPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        for (int i = 0; i < 15; i++) {
            int number = random.nextInt(100);
            if (generatedNumbers.add(number)) { // Fügt die Zahl hinzu, wenn sie noch nicht vorhanden ist
                tree.insertNode(new RBTree.IntComparable(number));

                String dotFilename = currentPath + "RBTrees/" + "RBTree_" + (i + 1) + ".dot";

                tree.printDOT(dotFilename);
            } else {
                i--;
            }
        }
        System.out.println("DOT-Dateien wurden erstellt.");
    }


}
