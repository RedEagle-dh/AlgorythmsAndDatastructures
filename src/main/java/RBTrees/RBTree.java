package RBTrees;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RBTree<T extends Comparable<T>> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node<T> root;
    private static final String DOT_CODE = "[label=\"NIL\", shape=box, color=black, fontcolor=white, style=filled];\n";
    private static final String SPACE4 = "    ";

    public static class IntComparable implements Comparable<IntComparable> {
        private final int value;

        public IntComparable(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(IntComparable other) {
            return Integer.compare(this.value, other.value);
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    /**
     * Erzeugt eine DOT-Datei (Graphviz-Format) zur grafischen Darstellung des RBTrees.
     * Diese Methode öffnet eine Datei mit dem angegebenen Dateinamen und schreibt die
     * Baumstruktur in das DOT-Format. Anschließend kann diese Datei mit einem Graphviz-kompatiblen
     * Tool visualisiert werden.
     * <p>
     * Die Methode führt folgende Schritte aus:
     * <p>
     * - Öffnet einen BufferedWriter für die angegebene Datei.
     * <p>
     * - Schreibt den Header und die initialen Einstellungen für eine Graphviz-Datei.
     * <p>
     * - Ruft die Methode printDOTRecursive auf, um die Knoten und Kanten des Baums zu erzeugen.
     * <p>
     * - Schließt die DOT-Datei mit einer schließenden Klammer.
     *
     * @param filename Der Name der Datei, in die die DOT-Repräsentation des Baumes geschrieben wird.
     */
    public void printDOT(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("digraph RBTree {\n");
            bw.write(SPACE4 + "graph [ratio=.48];\n");
            bw.write(SPACE4 + "node [style=filled, color=black, shape=circle, width=.6, fontname=Helvetica, fontweight=bold, fontcolor=white, fontsize=24, fixedsize=true];");

            if (root != null) {
                printDOTRecursive(root, bw);
            }

            bw.write("}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt rekursiv eine DOT-Repräsentation (Graphviz) des RBTrees
     * für die grafische Darstellung des Baums. Diese Methode wird für jeden Knoten
     * des Baums aufgerufen und generiert die DOT-Anweisungen, um die Knoten und
     * ihre Beziehungen darzustellen. Sie wird typischerweise von einer Wrapper-Methode
     * aufgerufen, die den gesamten Baum durchläuft.
     * <p>
     * Diese Methode führt folgende Aktionen durch:
     * <p>
     * - Erzeugt eine Knotendarstellung im DOT-Format mit der Farbe des Knotens.
     * <p>
     * - Für jeden Kindknoten (links und rechts) erzeugt sie eine Verbindungslinie
     *   zwischen dem aktuellen Knoten und dem Kindknoten und ruft sich selbst
     *   rekursiv für diese Kindknoten auf.
     * <p>
     * - Erzeugt zusätzlich "nil" Knoten für fehlende Kinder, um die Blätter des
     *   Baumes in der grafischen Darstellung zu verdeutlichen.
     *
     * @param node Der aktuelle Knoten, der in DOT-Format umgewandelt wird.
     * @param bw Ein BufferedWriter, der verwendet wird, um die DOT-Anweisungen
     *           zu schreiben.
     * @throws IOException Wenn ein I/O-Fehler beim Schreiben in den BufferedWriter auftritt.
     */
    private void printDOTRecursive(Node<T> node, BufferedWriter bw) throws IOException {
        if (node == null) {
            return;
        }

        // Darstellung des aktuellen Knotens
        String nodeColor = node.color ? "red" : "black";
        bw.write(SPACE4 + "\"" + node.data + "\" [color=" + nodeColor + ", fontcolor=white, style=filled];\n");

        // Linker Kindknoten
        if (node.left != null) {
            bw.write(SPACE4 + "\"" + node.data + "\" -> \"" + node.left.data + "\";\n");
            printDOTRecursive(node.left, bw);
        } else {
            // Darstellung eines "nil" Blattknotens für den linken Teilbaum
            bw.write(SPACE4 + "\"nilL" + node.data + "\"" + DOT_CODE);
            bw.write(SPACE4 + "\"" + node.data + "\" -> \"nilL" + node.data + "\";\n");
        }

        // Rechter Kindknoten
        if (node.right != null) {
            bw.write(SPACE4 + "\"" + node.data + "\" -> \"" + node.right.data + "\";\n");
            printDOTRecursive(node.right, bw);
        } else {
            // Darstellung eines "nil" Blattknotens für den rechten Teilbaum
            bw.write(SPACE4 + "\"nilR" + node.data + "\"" + DOT_CODE);
            bw.write(SPACE4 + "\"" + node.data + "\" -> \"nilR" + node.data + "\";\n");
        }
    }


    /**
     * Ermittelt den Onkelknoten eines gegebenen Knotens im RBTree.
     * Der Onkel eines Knotens ist der Bruder seines Elternteils, also das andere Kind
     * des Großelternteils des gegebenen Knotens. Diese Methode wird in der Regel
     * während der Korrektur der Rot-Schwarz-Eigenschaften nach dem Einfügen oder
     * Löschen eines Knotens verwendet.
     * <p>
     * Die Methode funktioniert wie folgt:
     * <p>
     * - Ermittelt den Großelternteil des gegebenen Knotens.
     * <p>
     * - Gibt den Bruder des Elternteils zurück, je nachdem, ob der Elternteil
     *   das linke oder das rechte Kind des Großelternteils ist.
     * <p>
     * - Löst eine IllegalStateException aus, wenn der Elternteil nicht tatsächlich
     *   ein Kind des Großelternteils ist, was auf einen inkonsistenten Baumzustand
     *   hinweisen würde.
     *
     * @param parent Der Elternteil des Knotens, dessen Onkel gefunden werden soll.
     * @return Der Onkelknoten, falls vorhanden.
     * @throws IllegalStateException wenn der übergebene Knoten kein gültiger
     *         Elternteil im Baum ist.
     */
    private Node<T> getUncle(Node<T> parent) {
        Node<T> grandparent = parent.parent;
        if (grandparent.left == parent) {
            return grandparent.right;
        } else if (grandparent.right == parent) {
            return grandparent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    /**
     * Diese Methode wird verwendet, um die Rot-Schwarz-Eigenschaften eines RBTrees (RBTree) nach dem Einfügen eines Knotens zu korrigieren. Ein RBTree ist eine spezielle Art von binärem Suchbaum, der bestimmte Eigenschaften erfüllen muss, um die Balance und Effizienz der Operationen aufrechtzuerhalten. Diese Methode sorgt dafür, dass die Rot-Schwarz-Eigenschaften nach dem Einfügen eines Knotens erhalten bleiben.
     * <p>
     * Der Algorithmus verwendet rekursive Schritte, um sicherzustellen, dass die folgenden Rot-Schwarz-Eigenschaften erfüllt sind:
     * <p>
     * 1. Die Wurzel des Baums ist immer SCHWARZ.
     * <p>
     * 2. Rote Knoten haben immer schwarze Eltern.
     * <p>
     * 3. Jeder Pfad von einem Knoten zu seinen Blattknoten enthält die gleiche Anzahl schwarzer Knoten (sog. Schwarzhöhe).
     * <p>
     * 4. Es gibt keine aufeinanderfolgenden roten Knoten entlang eines Pfades (keine rote Eltern-Kind-Kombination).
     * <p>
     * <p>
     * Die Methode beginnt mit dem neu eingefügten Knoten und arbeitet sich rekursiv zu dessen Eltern- und Großelternknoten vor, um die oben genannten Eigenschaften wiederherzustellen. Dabei werden verschiedene Fälle berücksichtigt, wie z.B. der Onkel des Knotens (der Bruder des Elternknotens), um die Farben und die Struktur des Baums anzupassen.
     * <p>
     * Dieser Algorithmus stellt sicher, dass nach dem Einfügen eines Knotens in den RBTree die Rot-Schwarz-Eigenschaften wiederhergestellt werden und der Baum weiterhin korrekt ausbalanciert bleibt, was wichtig für die Effizienz von Operationen wie dem Suchen und Einfügen im Baum ist.
     *
     * @param node Der eingefügte Knoten, dessen Rot-Schwarz-Eigenschaften korrigiert werden sollen.
     */
    private void fixRedBlackPropertiesAfterInsert(Node<T> node) {
        Node<T> parent = node.parent;

        // Case 1: Parent is null, we've reached the root, the end of the recursion
        if (parent == null) {
            node.color = BLACK;
            return;
        }

        // Parent is black --> nothing to do
        if (parent.color == BLACK) {
            return;
        }

        // From here on, parent is red
        Node<T> grandparent = parent.parent;


        // Get the uncle (may be null/nil, in which case its color is BLACK)
        Node<T> uncle = getUncle(parent);

        // Case 3: Uncle is red -> recolor parent, grandparent and uncle
        if (uncle != null && uncle.color == RED) {
            parent.color = BLACK;
            grandparent.color = RED;
            uncle.color = BLACK;

            // Call recursively for grandparent, which is now red.
            // It might be root or have a red parent, in which case we need to fix more...
            fixRedBlackPropertiesAfterInsert(grandparent);
        }

        // Parent is left child of grandparent
        else if (parent == grandparent.left) {
            // Case 4a: Uncle is black and node is left->right "inner child" of its grandparent
            if (node == parent.right) {
                rotateLeft(parent);

                // Let "parent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                parent = node;
            }

            // Case 5a: Uncle is black and node is left->left "outer child" of its grandparent
            rotateRight(grandparent);

            // Recolor original parent and grandparent
            parent.color = BLACK;
            grandparent.color = RED;
        }

        // Parent is right child of grandparent
        else {
            // Case 4b: Uncle is black and node is right->left "inner child" of its grandparent
            if (node == parent.left) {
                rotateRight(parent);

                // Let "parent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                parent = node;
            }

            // Case 5b: Uncle is black and node is right->right "outer child" of its grandparent
            rotateLeft(grandparent);

            // Recolor original parent and grandparent
            parent.color = BLACK;
            grandparent.color = RED;
        }
    }

    /**
     * Fügt einen neuen Knoten mit dem gegebenen Schlüssel in den RBTree ein.
     * Diese Methode sucht zunächst den korrekten Einfügeort im Baum, basierend auf
     * dem Wert des Schlüssels. Sie fügt dann einen neuen Knoten an dieser Stelle ein
     * und stellt sicher, dass die Rot-Schwarz-Eigenschaften des Baums erhalten bleiben.
     * <p>
     * Der Einfügevorgang folgt diesen Schritten:
     * <p>
     * 1. Traversiert den Baum, um den korrekten Einfügeort
     *    für den neuen Schlüssel zu finden. Dabei wird nach links navigiert, wenn
     *    der Schlüssel kleiner als der Schlüssel des aktuellen Knotens ist, und
     *    nach rechts, wenn er größer ist.
     * <p>
     * 2. Wenn ein Knoten mit dem gleichen Schlüssel bereits im Baum vorhanden ist,
     *    wird eine IllegalArgumentException ausgelöst, da Duplikate im RBTree nicht
     *    erlaubt sind.
     *    <p>
     * 3. Der neue Knoten wird dann als roter Knoten erstellt und an der gefundenen Position eingefügt.
     * <p>
     * 4. Nach dem Einfügen des neuen Knotens wird eine Hilfsmethode aufgerufen,
     *    um eventuelle Verletzungen der Rot-Schwarz-Eigenschaften zu beheben.
     *
     * @param key Der Schlüssel des neuen Knotens, der eingefügt werden soll.
     * @throws IllegalArgumentException wenn ein Knoten mit demselben Schlüssel bereits existiert.
     */
     public void insertNode(T key) {
        Node<T> node = root;
        Node<T> parent = null;
        // Traversiere den Baum nach links oder nach rechts je nach dem Wert des keys
        while (node != null) {
            parent = node;
            int cmp = key.compareTo(node.data);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                throw new IllegalArgumentException("RBTree already contains a node with key " + key);
            }
        }

        // Füge neuen Node ein
        Node<T> newNode = new Node<>(key);
        newNode.color = RED;
        if (parent == null) {
            root = newNode;
        } else if (key.compareTo(parent.data) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        newNode.parent = parent;

        fixRedBlackPropertiesAfterInsert(newNode);
    }

    /**
     * Ersetzt ein Kind eines Elternknotens durch ein neues Kind im RBTree.
     * Diese Methode wird nach einer Baumrotation aufgerufen, um die Eltern-Kind-Beziehung
     * im Baum zu aktualisieren.
     * <p>
     * Die Methode funktioniert wie folgt:
     * <p>
     * - Wenn der übergebene Elternknoten null ist, wird das neue Kind zur Wurzel des Baumes.
     * <p>
     * - Wenn das alte Kind der linke oder
     *   rechte Kindknoten des Elternknotens ist, wird es durch das neue Kind ersetzt.
     *   <p>
     * - Wenn das alte Kind nicht mit dem übergebenen Elternknoten übereinstimmt,
     *   wird eine IllegalStateException ausgelöst, da dies auf einen inkonsistenten
     *   Baumzustand hinweist.
     *   <p>
     * - Schließlich wird, falls das neue Kind nicht null ist, dessen Elternbeziehung
     *   auf den übergebenen Elternknoten aktualisiert.
     *
     * @param parent Der Elternknoten, dessen Kind ersetzt werden soll.
     * @param oldChild Das alte Kind, das ersetzt werden soll.
     * @param newChild Das neue Kind, das das alte Kind ersetzen soll.
     * @throws IllegalStateException wenn das alte Kind nicht tatsächlich ein Kind
     *         des Elternknotens ist.
     */
     private void replaceParentsChild(Node<T> parent, Node<T> oldChild, Node<T> newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.parent = parent;
        }
    }

    /**
     * Führt eine Linksrotation um den angegebenen Knoten im RBTree aus.
     * Diese Methode wird verwendet, um die Eigenschaften des RBTrees
     * nach dem Einfügen oder Löschen von Knoten zu erhalten.
     * <p>
     * Die Linksrotation wird folgendermaßen durchgeführt:
     * <p>
     * - Der rechte Kindknoten des übergebenen Knotens wird zum neuen Elternknoten
     *   dieses Knotens, während der ursprüngliche Knoten zum linken Kind des neuen
     *   Elternknotens wird.
     *   <p>
     * - Die ursprüngliche linke Seite des rechten Kindes wird zum rechten Kind
     *   des ursprünglichen Knotens.
     *   <p>
     * - Die Eltern-Kind-Beziehung wird entsprechend aktualisiert, um die
     *   Baumstruktur konsistent zu halten.
     *
     * @param node Der Knoten, um den die Linksrotation durchgeführt wird.
     *             Dieser Knoten wird zum linken Kind seines rechten Kindes.
     */

     private void rotateLeft(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        replaceParentsChild(parent, node, rightChild);
    }

/**
 * Führt eine Rechtsrotation um den angegebenen Knoten im RBTree aus.
 * Diese Methode wird verwendet, um die Eigenschaften des RBTrees
 * nach dem Einfügen oder Löschen von Knoten zu erhalten.
 * <p>
 * Die Rechtsrotation wird wie folgt durchgeführt:
 * <p>
 * - Der linke Kindknoten des gegebenen Knotens wird zum neuen Elternknoten
 *   des gegebenen Knotens, während der gegebene Knoten zum rechten Kind
 *   des neuen Elternknotens wird.
 *   <p>
 * - Die ursprüngliche rechte Seite des linken Kindes wird zum linken Kind
 *   des gegebenen Knotens.
 *   <p>
 * - Die Eltern-Kind-Beziehung wird entsprechend aktualisiert, um die
 *   Baumstruktur beizubehalten.
 *
 * @param node Der Knoten, um den die Rechtsrotation durchgeführt wird.
 *             Dieser Knoten wird zum rechten Kind seines linken Kindes.
 */
 private void rotateRight(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        replaceParentsChild(parent, node, leftChild);
    }


    public static class Node<T extends Comparable<T>> {
        T data;

        Node<T> left, right, parent;

        boolean color;

        public Node(T data) {
            this.data = data;
        }
    }

}
