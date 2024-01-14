package RBTrees;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        RBTree<IntComparable> tree = new RBTree<>();
        Random random = new Random();
        Set<Integer> generatedNumbers = new HashSet<>();

        String currentPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(currentPath);
        for (int i = 0; i < 15; i++) {
            int number = random.nextInt(100);
            if (generatedNumbers.add(number)) { // Fügt die Zahl hinzu, wenn sie noch nicht vorhanden ist
                tree.insertNode(new IntComparable(number));

                String dotFilename = currentPath + "RBTrees/" + "RBTree_" + (i + 1) + ".dot";
                String svgFilename = currentPath + "RBTrees/" + "RBTree_" + (i + 1) + ".svg";
                String pdfFilename = currentPath + "RBTrees/" + "RBTree_" + (i + 1) + ".pdf";

                tree.printDOT(dotFilename);
//                convertDotToSvg(dotFilename, svgFilename);
//                convertSvgToPdf(svgFilename, pdfFilename);
            } else {
                i--;
            }
        }
        tree.printPreOrder();
        System.out.println("PDF-Dateien wurden erstellt.");
    }

    private static void convertSvgToPdf(String svgFilename, String pdfFilename) {
        try {
            ProcessBuilder pb = new ProcessBuilder("inkscape", svgFilename, "--export-filename=" + pdfFilename);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void convertDotToSvg(String dotFilename, String svgFilename) {
        try {
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tsvg", dotFilename, "-o", svgFilename);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
