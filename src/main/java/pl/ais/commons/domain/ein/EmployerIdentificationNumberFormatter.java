package pl.ais.commons.domain.ein;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.concurrent.Immutable;

import org.springframework.format.Formatter;

/**
 * {@link Formatter} implementation applicable to {@link EmployerIdentificationNumber}.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Immutable
public class EmployerIdentificationNumberFormatter implements Formatter<EmployerIdentificationNumber> {

    private static final Pattern PATTERN = Pattern.compile("^(\\d{2})-?(\\d{7})$");

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployerIdentificationNumber parse(final String text, final Locale locale) throws ParseException {
        EmployerIdentificationNumber result = null;
        if (null != text) {
            final Matcher matcher = PATTERN.matcher(text);
            if (matcher.matches()) {
                result = new EmployerIdentificationNumber(matcher.group(1), matcher.group(2));
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.NullAssignment")
    public String print(final EmployerIdentificationNumber ein, final Locale locale) {
        return (null == ein) ? null : ein.toString();
    }

}
