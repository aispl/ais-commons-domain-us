package pl.ais.commons.domain.ssn;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import pl.ais.commons.domain.stereotype.DomainService;

/**
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@DomainService
public class SocialSecurityNumberFormatter implements Formatter<SocialSecurityNumber> {

    private final Pattern pattern = Pattern.compile("^(\\d{3})-(\\d{2})-(\\d{4})$");

    @Autowired(required = false)
    private SocialSecurityNumberFactory ssnFactory;

    /**
     * @see org.springframework.format.Parser#parse(java.lang.String, java.util.Locale)
     */
    @Override
    public SocialSecurityNumber parse(final String text, final Locale locale) throws ParseException {
        SocialSecurityNumber result = null;
        if (null != text) {
            final Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                result = ssnFactory.createSocialSecurityNumber(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        return result;
    }

    /**
     * @see org.springframework.format.Printer#print(java.lang.Object, java.util.Locale)
     */
    @Override
    public String print(final SocialSecurityNumber ssn, final Locale locale) {
        return (null == ssn) ? null : ssn.getAreaNumber() + "-" + ssn.getGroupNumber() + "-" + ssn.getSerialNumber();
    }

    /**
     * @param ssnFactory the Social Security Number factory to set
     */
    public void setSsnFactory(final SocialSecurityNumberFactory ssnFactory) {
        this.ssnFactory = ssnFactory;
    }

}
