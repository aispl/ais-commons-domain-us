package pl.ais.commons.domain.ssn;

import javax.annotation.Nonnull;

import pl.ais.commons.domain.security.CryptographicServiceSupport;
import pl.ais.commons.domain.stereotype.DomainService;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Factory capable of creating {@link SocialSecurityNumber}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@DomainService
public final class SocialSecurityNumberFactory {

    private transient CryptographicServiceSupport<String> encryptor;

    /**
     * Constructs new instance.
     */
    SocialSecurityNumberFactory() {
        super();
    }

    /**
     * Constructs new instance.
     *
     * @param encryptor the encryptor which will be used for encrypting SSN
     */
    public SocialSecurityNumberFactory(@Nonnull final CryptographicServiceSupport<String> encryptor) {
        this();

        // Verify constructor requirements, ...
        Preconditions.checkNotNull(encryptor, "Encryptor cannot be null.");

        // ... and intialize this instance fields.
        this.encryptor = encryptor;
    }

    /**
     * Creates new Social Security Number.
     *
     * @param areaNumber the area number
     * @param groupNumber the group number
     * @param serialNumber the serial number
     * @return newly created Social Security Number
     */
    public SocialSecurityNumber createSocialSecurityNumber(
        final String areaNumber, final String groupNumber, final String serialNumber) {
        final String value = Strings.nullToEmpty(areaNumber) + Strings.nullToEmpty(groupNumber)
            + Strings.nullToEmpty(serialNumber);
        if (9 != value.length()) {
            throw new IllegalArgumentException("Invalid SSN components provided.");
        }
        return new SocialSecurityNumber(encryptor.encrypt(value));
    }

}
