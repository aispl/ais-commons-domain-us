package pl.ais.commons.domain.ssn;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.annotation.concurrent.ThreadSafe;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import pl.ais.commons.domain.security.CryptographicService;
import pl.ais.commons.domain.stereotype.DomainService;

/**
 * Social Security Number factory.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@DomainService
@ThreadSafe
public final class SocialSecurityNumberFactory {

    @Autowired(required = false)
    private CryptographicService<String> encryptor;

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
    public SocialSecurityNumberFactory(@Nonnull final CryptographicService<String> encryptor) {
        this();
        if (null == encryptor) {
            throw new IllegalArgumentException("Encryptor cannot be null.");
        }
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
        return new SocialSecurityNumber(encryptor.encrypt(areaNumber + groupNumber + serialNumber));
    }

    /**
     * @return the encryptor
     */
    CryptographicService<String> getEncryptor() {
        return encryptor;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("encryptor", encryptor).build();
    }

    @PostConstruct
    private void validate() {
        if (null == encryptor) {
            throw new IllegalStateException("Encryptor not defined.");
        }
    }

}
