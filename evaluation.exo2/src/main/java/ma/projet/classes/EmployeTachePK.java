// EmployeTachePK.java (clé composite)
package ma.projet.classes;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeTachePK implements Serializable {
    private int employeId;
    private int tacheId;

    public EmployeTachePK() {}

    public EmployeTachePK(int employeId, int tacheId) {
        this.employeId = employeId;
        this.tacheId = tacheId;
    }

    // equals + hashCode obligatoires
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeTachePK that = (EmployeTachePK) o;
        return employeId == that.employeId && tacheId == that.tacheId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeId, tacheId);
    }
}