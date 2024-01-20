package RBTrees;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;

public class Main {

    private static final String CURRENTPATH = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private static final String PACKAGEPATH = CURRENTPATH + "RBTrees/";
    private static final String SVGPATH = PACKAGEPATH + "SVGs/";
    private static final String DOTPATH = PACKAGEPATH + "DOTs/";
    private static final String PDFPATH = PACKAGEPATH + "PDFs/";

    public static void main(String[] args) {

        RBTree<RBTree.IntComparable> tree = new RBTree<>();
        Random random = new Random();
        Set<Integer> generatedNumbers = new HashSet<>();

        createDirectories(PACKAGEPATH);
        for (int i = 0; i < 15; i++) {
            int number = random.nextInt(100);
            if (generatedNumbers.add(number)) { // Fügt die Zahl hinzu, wenn sie noch nicht vorhanden ist
                tree.insertNode(new RBTree.IntComparable(number));

                tree.printDOT(DOTPATH + "RBTree_" + (i + 1) + ".dot");
                createSVG("RBTree_" + (i + 1));
            } else {
                i--;
            }
        }
        for (int i = 0; i < 15; i++) {
            convertSvgToPdf("RBTree_" + (i + 1));
        }
        mergePdfFiles("RBTree_", "RBTree_Final.pdf");
    }

    /**
     * Erstellt eine SVG-Datei aus einer DOT-Datei.
     * 
     * @param filename Der Name der Datei (ohne Erweiterung), für die eine SVG-Datei
     *                 erstellt werden soll.
     *                 Dieser Name wird sowohl für die DOT- als auch für die
     *                 SVG-Datei verwendet.
     *                 Der Pfad zu den DOT- und SVG-Dateien wird durch die
     *                 Konstanten DOTPATH und SVGPATH bestimmt.
     *                 Die DOT-Datei muss im DOTPATH-Verzeichnis und die SVG-Datei
     *                 wird im SVGPATH-Verzeichnis erstellt.
     */
    public static void createSVG(String filename) {
        try {
            // Erstellen eines neuen Prozesses mit dem Befehl "dot"
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c",
                    "dot -Tsvg " + DOTPATH + filename + ".dot > " + SVGPATH + filename + ".svg");

            // Starten des Prozesses
            processBuilder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Konvertiert eine SVG-Datei in eine PDF-Datei.
     * 
     * @param filename Der Name der Datei (ohne Erweiterung), die konvertiert werden
     *                 soll.
     *                 Der Pfad zu den SVG- und PDF-Dateien wird durch die
     *                 Konstanten SVGPATH und PDFPATH bestimmt.
     *                 Die SVG-Datei muss im SVGPATH-Verzeichnis vorhanden sein und
     *                 die PDF-Datei wird im PDFPATH-Verzeichnis erstellt.
     */
    public static void convertSvgToPdf(String filename) {
        try {
            // Erstellen eines neuen Prozesses mit dem Befehl "rsvg-convert"
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("rsvg-convert", "-f", "pdf", "-o", PDFPATH + filename + ".pdf",
                    SVGPATH + filename + ".svg");
            // Starten des Prozesses
            processBuilder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fügt mehrere PDF-Dateien zu einer einzigen PDF-Datei zusammen.
     * 
     * @param baseFilename   Der Basisname der PDF-Dateien, die zusammengeführt
     *                       werden sollen.
     *                       Die Methode erwartet, dass die Dateien im
     *                       PDFPATH-Verzeichnis vorhanden sind und die Namen
     *                       die Form "baseFilename1.pdf", "baseFilename2.pdf", ...,
     *                       "baseFilename15.pdf" haben.
     * @param mergedFilename Der Name der resultierenden zusammengeführten
     *                       PDF-Datei.
     *                       Die resultierende Datei wird im PDFPATH-Verzeichnis
     *                       erstellt.
     */
    public static void mergePdfFiles(String baseFilename, String mergedFilename) {
        try {
            // Erstellen eines Befehls, um alle PDF-Seiten zu einer Datei zusammenzuführen
            StringJoiner joiner = new StringJoiner(" ");
            for (int i = 1; i <= 15; i++) {
                joiner.add(PDFPATH + baseFilename + i + ".pdf");
            }
            String mergeCommand = "pdftk " + joiner.toString() + " cat output " + PDFPATH + mergedFilename;

            // Ausführen des Befehls zum Zusammenführen der PDF-Seiten
            ProcessBuilder mergePdfBuilder = new ProcessBuilder("bash", "-c", mergeCommand);
            mergePdfBuilder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt die angegebenen Verzeichnisse im Basispfad.
     * 
     * @param basePath Der Basispfad, in dem die Verzeichnisse erstellt werden
     *                 sollen.
     *                 Die Methode erstellt die Verzeichnisse "PDFs", "SVGs" und
     *                 "DOTs" in diesem Pfad.
     *                 Existiert ein Verzeichnis bereits, geschieht nichts.
     */
    public static void createDirectories(String basePath) {
        String[] directories = { "PDFs", "SVGs", "DOTs" };

        for (String dir : directories) {
            Path path = Paths.get(basePath, dir);
            try {
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                    System.out.println("Verzeichnis erstellt: " + path);
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
