package pl.ais.commons.domain.ssn;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import pl.ais.commons.domain.stereotype.DomainService;

/**
 * {@link Formatter} implementation applicable to {@link SocialSecurityNumber}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@DomainService
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class SocialSecurityNumberFormatter implements Formatter<SocialSecurityNumber> {

    private final Pattern pattern = Pattern.compile("^(\\d{3})-(\\d{2})-(\\d{4})$");

    private final SocialSecurityNumberFactory ssnFactory;

    /**
     * Constructs new instance.
     *
     * @param ssnFactory SSN factory to use
     */
    @Autowired
    public SocialSecurityNumberFormatter(final SocialSecurityNumberFactory ssnFactory) {
        super();
        this.ssnFactory = ssnFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SocialSecurityNumber parse(final String text, final Locale locale) throws ParseException {
        SocialSecurityNumber result = null;
        if (null != text) {
            final Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                result = ssnFactory.createSocialSecurityNumber(matcher.group(1), matcher.group(2), matcher.group(3));
            } else {
                throw new ParseException("Unable to parse provided text as SSN.", 0);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.NullAssignment")
    public String print(final SocialSecurityNumber ssn, final Locale locale) {
        return (null == ssn) ? null : ssn.getAreaNumber() + "-" + ssn.getGroupNumber() + "-" + ssn.getSerialNumber();
    }

}
