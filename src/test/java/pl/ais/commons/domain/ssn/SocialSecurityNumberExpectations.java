package pl.ais.commons.domain.ssn;

import static junit.framework.Assert.assertTrue;
import static org.springframework.util.SerializationUtils.deserialize;
import static org.springframework.util.SerializationUtils.serialize;

import org.junit.Test;

import pl.ais.commons.domain.security.CryptographicServiceSupport;
import pl.ais.commons.domain.security.PassThroughCryptographicService;

/**
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("static-method")
public class SocialSecurityNumberExpectations {

    private static SocialSecurityNumberFactory ssnFactory() {
        final CryptographicServiceSupport<String> encryptor = new PassThroughCryptographicService();
        return new SocialSecurityNumberFactory(encryptor);
    }

    /**
     * Verifies if {@link SocialSecurityNumber} is serializable.
     */
    @Test
    public void shouldBeSerializable() {

        // Given SSN, ...
        final SocialSecurityNumberFactory factory = ssnFactory();
        final SocialSecurityNumber ssn = factory.createSocialSecurityNumber("987", "65", "4321");

        // ... when we serialize and deserialize it, ...
        final SocialSecurityNumber deserialized = (SocialSecurityNumber) deserialize(serialize(ssn));

        // ... then both instances should be equal and have same hash code.
        assertTrue("Deserialized instance differs from initial one.", (ssn.hashCode() == deserialized.hashCode())
            && ssn.equals(deserialized));
    }

    /**
     * Verifies if each part of SSN is accessible.
     */
    @Test
    public void shouldProvideAccessToEachPart() {

        // Given SSN, ...
        final SocialSecurityNumberFactory factory = ssnFactory();
        final SocialSecurityNumber ssn = factory.createSocialSecurityNumber("987", "65", "4321");

        // ... when we query for specific part of SSN, then appropriate value should be returned.
        assertTrue("Parts of the SSN should match the original value parts.", "987".equals(ssn.getAreaNumber())
            && "65".equals(ssn.getGroupNumber()) &&
            "4321".equals(ssn.getSerialNumber()));
    }

}
