package pl.ais.commons.domain.ssn;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import pl.ais.commons.domain.security.DecryptableValue;
import pl.ais.commons.domain.stereotype.ValueObject;

/**
 * Social Security Number.
 *
 * @see <a href="http://www.ssa.gov/history/ssn/geocard.html">The SSN Numbering Scheme</a>
 * @see <a href="http://en.wikipedia.org/wiki/Social_Security_number">Social Security Number</a>
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@ValueObject
public final class SocialSecurityNumber implements Serializable {

    private transient String areaNumber;

    private transient String groupNumber;

    private final DecryptableValue<String> representation;

    private transient volatile String serialNumber;

    /**
     * Constructs new instance.
     *
     * @param representation encrypted SSN representation
     */
    public SocialSecurityNumber(@Nonnull final DecryptableValue<String> representation) {
        super();
        if (null == representation) {
            throw new IllegalArgumentException("Encrypted SSN representation cannot be null.");
        }
        this.representation = representation;
    }

    /**
     * Decrypts and decomposes the SSN representation into area, group and serial numbers.
     */
    private void decomposeIfNeeded() {
        if (null == serialNumber) {
            synchronized (this) {
                if (null == serialNumber) {
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
        return new HashCodeBuilder().append(representation).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("representation", representation).build();
    }

}
