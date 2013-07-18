package pl.ais.commons.domain.phone;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import pl.ais.commons.domain.stereotype.ValueObject;

/**
 * Phone number.
 *
 * @see <a href="http://en.wikipedia.org/wiki/North_American_Numbering_Plan">North American Numbering Plan</a>
 * @author Warlock, AIS.PL
 * @since 1.0
 */
@ValueObject
public final class PhoneNumber implements Serializable {

    private final String areaCode;

    private final String exchangeCode;

    private final String subscriberNumber;

    private final String value;

    /**
     * Constructs new instance.
     *
     * @param areaCode the area code
     * @param exchangeCode the exchange code
     * @param subscriberNumber the subscriber number
     */
    public PhoneNumber(@Nonnull final String areaCode, @Nonnull final String exchangeCode,
        @Nonnull final String subscriberNumber) {
        super();
        if ((null == areaCode) || (null == exchangeCode) || (null == subscriberNumber)) {
            throw new IllegalArgumentException(
                "All of the area code, exchange code and subscriber number cannot be null.");
        }
        this.value = areaCode + exchangeCode + subscriberNumber;
        if (10 != value.length()) {
            throw new IllegalArgumentException("Provided value: '" + value
                + "' is not a valid representation of the phone number.");
        }
        this.areaCode = areaCode;
        this.exchangeCode = exchangeCode;
        this.subscriberNumber = subscriberNumber;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (null != object) && (getClass() == object.getClass())) {
            final PhoneNumber other = (PhoneNumber) object;
            result = value.equals(other.value);
        }
        return result;
    }

    /**
     * @return the area code
     */
    @Nonnull
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * @return the exchange code
     */
    @Nonnull
    public String getExchangeCode() {
        return exchangeCode;
    }

    /**
     * @return the subscriber number
     */
    @Nonnull
    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.areaCode + "-" + this.exchangeCode + "-" + this.subscriberNumber;
    }

}
