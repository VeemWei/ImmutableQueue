import java.util.Objects;
import java.util.Optional;

// SampleObject is a demonstration of complex data structure which can be use in the ImmutableQueue
public class SampleObject {

    // property of String data
    private String data;

    // constructor
    public SampleObject(String data) {
        this.data = data;
    }

    // getter for data
    public String getData() {
        return this.data;
    }

    // override hashCode to use data's hashcode, or fallback to super.hashCode
    @Override
    public int hashCode() {
        return Optional.ofNullable(data).map(String::hashCode).orElse(super.hashCode());
    }

    // override equals to use data's equals method
    @Override
    public boolean equals(Object o) {

        if (this == o) { return true; }
        if (o == null) { return false; }
        if (this.getClass() == o.getClass()) {
            // use Objects.equals to avoid null pointer if data is unconfigured
            return Objects.equals(data, ((SampleObject) o).getData());
        }
        // use Objects.equals to avoid null pointer if data is unconfigured
        return Objects.equals(data, o);
    }

    // override toString for printing and debugging
    @Override
    public String toString() {
        return this.data;
    }
}
