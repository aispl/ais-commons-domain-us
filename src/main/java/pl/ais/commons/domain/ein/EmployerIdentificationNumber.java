package pl.ais.commons.domain.ein;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import pl.ais.commons.domain.stereotype.ValueObject;

/**
 * Employer Identification Number.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Employer_Identification_Number">Employer Identification Number</a>
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ValueObject
public final class EmployerIdentificationNumber implements Serializable {

    private static final Pattern PREFIX_PATTERN = Pattern.compile("^\\d{2}$");

    private static final Pattern SEQUENCE_PATTERN = Pattern.compile("^\\d{7}$");

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = -420949124853131997L;

    private final String prefixCode;

    private final String sequenceNumber;

    /**
     * Constructs new instance.
     *
     * @param prefixCode prefix code (first two digits of EIN)
     * @param sequenceNumber sequence number (last 7 digits of EIN)
     */
    public EmployerIdentificationNumber(@Nonnull final String prefixCode, @Nonnull final String sequenceNumber) {
        super();
        this.prefixCode = prefixCode;
        this.sequenceNumber = sequenceNumber;
        validateState();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (null != object) && (getClass() == object.getClass())) {
            final EmployerIdentificationNumber other = (EmployerIdentificationNumber) object;
            result = prefixCode.equals(other.prefixCode) && sequenceNumber.equals(other.sequenceNumber);
        }
        return result;
    }

    /**
     * @return the prefix code (first 2 digits of EIN)
     */
    public String getPrefixCode() {
        return prefixCode;
    }

    /**
     * @return the sequence number (last 7 digits of EIN)
     */
    public String getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(prefixCode, sequenceNumber);
    }

    @SuppressWarnings("PMD.PreserveStackTrace")
    private void readObject(final ObjectInputStream objectStream) throws IOException, ClassNotFoundException {
        objectStream.defaultReadObject();
        try {
            validateState();
        } catch (IllegalArgumentException exception) {
            throw new InvalidObjectException(exception.getMessage());
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return prefixCode + "-" + sequenceNumber;
    }

    private void validateState() {
        if ((null == prefixCode) || !PREFIX_PATTERN.matcher(prefixCode).matches()) {
            throw new IllegalArgumentException("Prefix code cannot be null, and should have exactly 2 digits.");
        }
        if ((null == sequenceNumber) || !SEQUENCE_PATTERN.matcher(sequenceNumber).matches()) {
            throw new IllegalArgumentException("Sequence number cannot be null, and should have exactly 7 digits.");
        }
    }
}
