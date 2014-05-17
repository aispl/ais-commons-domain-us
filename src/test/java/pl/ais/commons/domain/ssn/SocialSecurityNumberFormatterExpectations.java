package pl.ais.commons.domain.ssn;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

import pl.ais.commons.domain.security.CryptographicServiceSupport;
import pl.ais.commons.domain.security.PassThroughCryptographicService;

/**
 * Verifies {@link SocialSecurityNumberFormatter} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("static-method")
public class SocialSecurityNumberFormatterExpectations {

    private static SocialSecurityNumberFactory ssnFactory() {
        final CryptographicServiceSupport<String> encryptor = new PassThroughCryptographicService();
        return new SocialSecurityNumberFactory(encryptor);
    }

    /**
     * Should be able to convert {@link SocialSecurityNumber} to {@link String}.
     */
    @Test
    public void shouldBeAbleToConvertSSNToString() {
        // Given SSN formatter, ...
        final SocialSecurityNumberFactory factory = ssnFactory();
        final SocialSecurityNumberFormatter formatter = new SocialSecurityNumberFormatter(factory);

        // ... when we use formatter to convert SSN into String, ...
        final SocialSecurityNumber ssn = factory.createSocialSecurityNumber("987", "65", "4321");
        final String convertedValue = formatter.print(ssn, Locale.US);

        // ... then it should return SSN string representation.
        assertEquals("Should be able to format SSN as 00-0000000", "987-65-4321", convertedValue);
    }

    /**
     * Should be able to parse {@link SocialSecurityNumber} from {@link String}.
     *
     * @throws ParseException in case of problems with parsing SSN
     */
    @Test
    public void shouldBeAbleToParseSSNFromString() throws ParseException {

        // Given SSN formatter, ...
        final SocialSecurityNumberFactory factory = ssnFactory();
        final SocialSecurityNumberFormatter formatter = new SocialSecurityNumberFormatter(factory);

        // ... when we parse SSN, ...
        final SocialSecurityNumber ssn = formatter.parse("987-65-4321", Locale.US);

        // ... then it should represent correct value.
        assertTrue("Should be able to parse SSN.",
            "987".equals(ssn.getAreaNumber()) && "65".equals(ssn.getGroupNumber())
            && "4321".equals(ssn.getSerialNumber()));
    }

    /**
     * Should convert {@code null} (SSN) into {@code null} (String).
     */
    @Test
    public void shouldConvertNullIntoNull() {

        // Given SSN formatter, ...
        final SocialSecurityNumberFactory factory = ssnFactory();
        final SocialSecurityNumberFormatter formatter = new SocialSecurityNumberFormatter(factory);

        // ... when we use formatter to convert null into String, ...
        final String convertedValue = formatter.print(null, Locale.US);

        // ... then it should return null.
        assertNull("Should convert null into null", convertedValue);
    }

    /**
     * Should parse {@code null} (String) into {@code null} (SSN).
     *
     * @throws ParseException in case of problems with parsing SSN
     */
    @Test
    public void shouldParseNullIntoNull() throws ParseException {

        // Given SSN formatter, ...
        final SocialSecurityNumberFactory factory = ssnFactory();
        final SocialSecurityNumberFormatter formatter = new SocialSecurityNumberFormatter(factory);

        // ... when we use formatter to parse null String, ...
        final SocialSecurityNumber ssn = formatter.parse(null, Locale.US);

        // ... then it should return null.
        assertNull("Should parse null into null", ssn);
    }

}
