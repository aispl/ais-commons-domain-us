package pl.ais.commons.domain.ein;

import static org.junit.Assert.assertTrue;
import static org.springframework.util.SerializationUtils.deserialize;
import static org.springframework.util.SerializationUtils.serialize;

import org.junit.Test;

/**
 * Verifies {@link EmployerIdentificationNumber} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("static-method")
public class EmployerIdentificationNumberExpectations {

    /**
     * {@link EmployerIdentificationNumber} should be serializable.
     */
    @Test
    public void shouldBeSerializable() {

        // Given EIN, ...
        final EmployerIdentificationNumber ein = new EmployerIdentificationNumber("12", "3456789");

        // ... when we serialize and deserialize it, ...
        final EmployerIdentificationNumber deserialized = (EmployerIdentificationNumber) deserialize(serialize(ein));

        // ... then both instances should be equal and have same hash code.
        assertTrue("Deserialized instance differs from initial one.", (ein.hashCode() == deserialized.hashCode())
            && ein.equals(deserialized));
    }

    /**
     * Should raise {@link IllegalArgumentException} when prefix code is undefined.
     */
    @SuppressWarnings("unused")
    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseExceptionOnUndefinedPrefixCode() {
        new EmployerIdentificationNumber(null, "3456789");
    }

    /**
     * Should raise {@link IllegalArgumentException} when sequence number is undefined.
     */
    @SuppressWarnings("unused")
    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseExceptionOnUndefinedSequenceNumber() {
        new EmployerIdentificationNumber("12", null);
    }

}
