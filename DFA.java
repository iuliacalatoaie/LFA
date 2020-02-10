import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class DFA {
    private String source;
    private String lastParent;
    private Hashtable<String, Node> transitions;
    private Hashtable<String, Node> transposedTransitions;
    private HashSet<String> finalStates;

    DFA() {
        transitions = new Hashtable<>();
        transposedTransitions = new Hashtable<>();
        finalStates  = new HashSet<>();
    }

    public String getSource() {
        return source;
    }

    public void setLastParent(final String lastParent) {
        this.lastParent = lastParent;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    // Atunci cand adaug o tranzitie in graf, adaug si in graful transpus
    public void addTransition(final String destination) {
        if (transitions.containsKey(lastParent)) {
            transitions.get(lastParent).addChild(destination);
        } else {
            transitions.put(lastParent, new Node(lastParent, destination));
        }

        if (transposedTransitions.containsKey(destination)) {
            transposedTransitions.get(destination).addChild(lastParent);
        } else {
            transposedTransitions.put(destination, new Node(destination, lastParent));
        }
    }

    public void addFinalState(final String finalState) {
        finalStates.add(finalState);
    }

    /* Accepta sirul vid daca sursa e si stare finala */
    public void acceptsE() {
        if (finalStates.contains(source)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    private void bfs(HashSet<String> visited, final Hashtable<String, Node> tree,
                     final String src, final HashSet<String> set, final int condition) {

        visited.add(src);

        Queue<String> queue = new LinkedList<>();
        Iterator<String> iter;
        String top, current;

        queue.add(src);
        while (!queue.isEmpty()) {
            top = queue.remove();

            if (tree.containsKey(top)) {
                iter = tree.get(top).getChildren().listIterator();

                while (iter.hasNext()) {
                    current = iter.next();
                    // daca se cauta stare utila trebuie sa fie deja accesibila
                    if (condition == 1) {
                        if (!visited.contains(current) && set.contains(current)) {
                            queue.add(current);
                            visited.add(current);
                        }
                    } else {
                        // daca se cauta stare accesibila
                        if (!visited.contains(current)) {
                            queue.add(current);
                            visited.add(current);
                        }
                    }
                }
           }
        }
    }

    /* Starile accesibile sunt starile in care se poate ajunge din sursa */
    public HashSet<String> accesibleStates() {
        HashSet<String> aStates = new HashSet<>();
        bfs(aStates, transitions, source, new HashSet<>(), 0);

        return aStates;
    }

    /* Starile utile sunt starile accesibile 
    prin care se trece din starile finale spre sursa */
    public HashSet<String> usefulStates() {
        HashSet<String> aStates = accesibleStates();
        HashSet<String> uStates = new HashSet<>();

        for (String src : finalStates) {
            if (aStates.contains(src)) {
                bfs(uStates, transposedTransitions, src, aStates, 1);
            }
        }

        return uStates;
    }

    /* Accepta limbajul vid daca :
      nicio stare finala nu e accesibila bif
      nu exista stari utile
      starea initiala nu e productiva */
    public void voidLanguage() {
        HashSet<String> aStates = accesibleStates();
        HashSet<String> uStates = usefulStates();

        boolean accesibleFinalStates = true;
        boolean noUsefulStates = uStates.isEmpty();
        boolean sourceNotProductive = !uStates.contains(source);
        for (String state : finalStates) {
            if (aStates.contains(state)) {
                accesibleFinalStates = false;
            }
        }

        boolean hasVoidLanguage = accesibleFinalStates || noUsefulStates || sourceNotProductive;
        if (hasVoidLanguage) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
