package pl.ais.commons.domain.ein;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.Locale;

import org.junit.Test;

/**
 * Verifies {@link EmployerIdentificationNumberFormatter} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("static-method")
public class EmployerIdentificationNumberFormatterExpectations {

    /**
     * Should be able to convert {@link EmployerIdentificationNumber} to {@link String}.
     */
    @Test
    public void shouldBeAbleToConvertEINToString() {

        // Given EIN formatter, ...
        final EmployerIdentificationNumberFormatter formatter = EmployerIdentificationNumberFormatter.getInstance();

        // ... when we use formatter to convert EIN into String, ...
        final EmployerIdentificationNumber ein = new EmployerIdentificationNumber("12", "3456789");
        final String convertedValue = formatter.print(ein, Locale.US);

        // ... then it should return EIN string representation.
        assertEquals("Should be able to format EIN as 00-0000000", "12-3456789", convertedValue);
    }

    /**
     * Should be able to parse {@link EmployerIdentificationNumber} from {@link String}.
     *
     * @throws ParseException in case of problems with parsing EIN
     */
    @Test
    public void shouldBeAbleToParseEINFromString() throws ParseException {

        // Given EIN formatter, ...
        final EmployerIdentificationNumberFormatter formatter = EmployerIdentificationNumberFormatter.getInstance();

        // ... when we use formatter to parse EIN from string, ...
        final EmployerIdentificationNumber ein = formatter.parse("12-3456789", Locale.US);

        // ... then it should return appropriate EIN.
        assertEquals("Should be able to parse EIN.", new EmployerIdentificationNumber("12", "3456789"), ein);
    }

    /**
     * Should convert {@code null} (EIN) into {@code null} (String).
     */
    @Test
    public void shouldConvertNullIntoNull() {

        // Given EIN formatter, ...
        final EmployerIdentificationNumberFormatter formatter = EmployerIdentificationNumberFormatter.getInstance();

        // ... when we use formatter to convert null into String, ...
        final String convertedValue = formatter.print(null, Locale.US);

        // ... then it should return null.
        assertNull("Should convert null into null", convertedValue);
    }

    /**
     * Should parse {@code null} (String) into {@code null} (EIN).
     *
     * @throws ParseException in case of problems with parsing EIN
     */
    @Test
    public void shouldParseNullIntoNull() throws ParseException {

        // Given EIN formatter, ...
        final EmployerIdentificationNumberFormatter formatter = EmployerIdentificationNumberFormatter.getInstance();

        // ... when we use formatter to parse null String, ...
        final EmployerIdentificationNumber ein = formatter.parse(null, Locale.US);

        // ... then it should return null.
        assertNull("Should parse null into null", ein);
    }

    /**
     * @throws ParseException
     */
    @SuppressWarnings("unused")
    @Test(expected = ParseException.class)
    public void shouldRaiseExceptionWhenParsingInvalidEIN() throws ParseException {

        // Given EIN formatter, ...
        final EmployerIdentificationNumberFormatter formatter = EmployerIdentificationNumberFormatter.getInstance();

        // ... when we use formatter to parse invalid String, ...
        final EmployerIdentificationNumber ein = formatter.parse("1234", Locale.US);

        // ... exception should be raised.
    }

}
