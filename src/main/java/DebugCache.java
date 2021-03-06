import com.sun.jdi.Field;
import com.sun.jdi.LocalVariable;

import java.util.*;

/**
 * Cache that holds histories of local variables or fields. Maps the field or variable
 * to a list of VariableInfo object that holds its previous values
 * and the line number at which those values were assigned. Note that
 * values are only cached if they have changed.
 */
public class DebugCache {

    /**
     * Map of local variables to their histories
     */
    private Map<LocalVariable, LinkedList<VariableInfo>> vars;

    /**
     * Map of fields to their histories
     */
    private Map<Field, LinkedList<VariableInfo>> fields;

    /**
     * Creates a DebugCache instance
     */
    public DebugCache() {
        vars = new LinkedHashMap<>();
        fields = new LinkedHashMap<>();
    }

    /**
     * Returns the history of the var
     * @param var field or local variable
     * @return the history of var's values
     */
    public List<VariableInfo> getHistory(Object var) {
        if (var instanceof LocalVariable) {
            return (List<VariableInfo>) vars.get(var).clone();
        } else if (var instanceof Field) {
            return (List<VariableInfo>) fields.get(var).clone();
        }
        return null;
    }

    /**
     * Returns all the variables in the cache
     * @return all the variables in the cache
     */
    public List<LocalVariable> getAllVariables() {
        return new ArrayList<>(vars.keySet());
    }

    /**
     * Returns all the fields in the cache
     * @return all the fields in the cache
     */
    public List<Field> getAllFields() {
        return new ArrayList<>(fields.keySet());
    }

    /**
     * Returns the most recent update that was made to the variable
     * @param var the variable
     * @return the most recent update that was made to the variable
     */
    public VariableInfo getMostRecentUpdate(LocalVariable var) {
        if (vars.containsKey(var)) {
            return vars.get(var).getLast();
        }
        return null;
    }

    /**
     * Adds the given information to the cache.
     * @param var variable
     * @param update updated value
     */
    public void put(LocalVariable var, VariableInfo update) {
        LinkedList<VariableInfo> info = vars.get(var);
        if (info == null) {
            info = new LinkedList<>();
        }
        if (info.size() == 0 || !update.equals(info.getLast())) {
            info.add(update);
            vars.put(var, info);
            pushChangeToUI();
        }
    }

    /**
     * Adds the given information to the cache.
     * @param field field
     * @param update updated value
     */
    public void put(Field field, VariableInfo update) {
        LinkedList<VariableInfo> info = fields.get(field);
        if (info == null) {
            info = new LinkedList<>();
        }
        if (info.size() == 0 || !update.equals(info.getLast())) {
            info.add(update);
            fields.put(field, info);
            pushChangeToUI();
        }
    }

    /**
     * Notify the UI of changes to the cache.
     */
    public void pushChangeToUI() {
        FootPrintToolWindow.getInstance().cacheChanged();
    }

    /**
     * Clears the cache
     */
    public void clear() {
        vars.clear();
        fields.clear();
    }

    @Override
    public String toString() {
        String res = "CACHE:\n\n";
        for(LocalVariable var : vars.keySet()) {
            res += "\t\t\t" + var.name() + "\n";
            res += "\t\t\t" + vars.get(var).toString() + "\n\n";
        }
        for(Field f : fields.keySet()) {
            res += "\t\t\t" + f.name() + "\n";
            res += "\t\t\t" + fields.get(f).toString() + "\n\n";
        }
        return res;
    }

    /**
     * Returns the size of the DebugCache (fields + local vars)
     * @return size of this
     */
    public int size() {
        return vars.size() + fields.size();
    }
}
