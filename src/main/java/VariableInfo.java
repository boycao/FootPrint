import com.sun.jdi.Field;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.Value;
import com.sun.tools.jdi.ArrayReferenceImpl;
import com.sun.tools.jdi.ObjectReferenceImpl;
import com.sun.tools.jdi.StringReferenceImpl;

import java.util.List;
import java.util.Map;

/**
 * Variable info holds a value and the line number at which it was assigned.
 */
public class VariableInfo {
    private int line;
    private String value;

    public VariableInfo(int line, String value) {
        this.line = line;
        this.value = value;
    }

    /**
     * Returns the line and value pair in string format
     * @return line value pair as string
     */
    public String toString() {
        return "line: " + line + ", value: " + value;
    }

    /**
     * Returns the value in string format
     * @return the value
     */
    public String getValue() {
        return value;
    }
  
    /**
     * Returns the line number this change was made on
     * @return the line number
     */
    public int getLine() {
        return line;
    }

    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof VariableInfo)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        VariableInfo c = (VariableInfo) o;

        // Compare the data members and return accordingly
        return this.value.equals(c.value);
    }
}
