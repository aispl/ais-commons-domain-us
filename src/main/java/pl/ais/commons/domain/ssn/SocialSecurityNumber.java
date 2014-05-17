package pl.ais.commons.domain.ssn;

import java.io.Serializable;

import javax.annotation.Nonnull;

import pl.ais.commons.domain.security.DecryptableValue;
import pl.ais.commons.domain.stereotype.ValueObject;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Social Security Number.
 *
 * <p>
 *     Note, that Social Security Number is serializable if and only if {@link DecryptableValue} being its representation
 *     is serializable too.
 * </p>
 *
 * @see <a href="http://www.ssa.gov/history/ssn/geocard.html">The SSN Numbering Scheme</a>
 * @see <a href="http://en.wikipedia.org/wiki/Social_Security_number">Social Security Number</a>
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ValueObject
public final class SocialSecurityNumber implements Serializable {

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = -8790247953875775970L;

    private transient String areaNumber;

    private transient String groupNumber;

    private final DecryptableValue<String> representation;

    @SuppressWarnings("PMD.AvoidUsingVolatile")
    private transient volatile String serialNumber;

    /**
     * Constructs new instance.
     *
     * @param representation encrypted SSN representation
     */
    public SocialSecurityNumber(@Nonnull final DecryptableValue<String> representation) {
        super();

        // Verify constructor requirements, ...
        Preconditions.checkNotNull(representation, "Encrypted SSN representation cannot be null.");

        // ... and initialize this instance fields.
        this.representation = representation;
    }

    /**
     * Decrypts and decomposes the SSN representation into area, group and serial numbers.
     */
    @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
    private void decomposeIfNeeded() {
        String decomposed = serialNumber;
        if (null == decomposed) {
            synchronized (this) {
                decomposed = serialNumber;
                if (null == decomposed) {
                    // Decrypt SSN, ...
                    final String value = representation.decrypt();

                    // ... validate decrypted value, ...
                    if (9 != value.length()) {
                        throw new IllegalArgumentException("Decrypted SSN value has invalid length.");
                    }

                    // ... and decompose it.
                    areaNumber = value.substring(0, 3);
                    groupNumber = value.substring(3, 5);
                    serialNumber = value.substring(5, 9);
                }
            }
        }
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (null != object) && (getClass() == object.getClass())) {
            final SocialSecurityNumber other = (SocialSecurityNumber) object;
            result = representation.equals(other.representation);
        }
        return result;
    }

    /**
     * @return the area number
     */
    @Nonnull
    public String getAreaNumber() {
        decomposeIfNeeded();
        return areaNumber;
    }

    /**
     * @return the group number
     */
    @Nonnull
    public String getGroupNumber() {
        decomposeIfNeeded();
        return groupNumber;
    }

    /**
     * @return encrypted representation of this SSN
     */
    public DecryptableValue<String> getRepresentation() {
        return representation;
    }

    /**
     * @return the serial number
     */
    @Nonnull
    public String getSerialNumber() {
        decomposeIfNeeded();
        return serialNumber;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return representation.hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("representation", representation).toString();
    }

}
