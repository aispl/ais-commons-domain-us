package pl.ais.commons.domain.phone;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.annotation.Nonnull;

import pl.ais.commons.domain.stereotype.ValueObject;

import com.google.common.base.Preconditions;

/**
 * Phone number.
 *
 * @see <a href="http://en.wikipedia.org/wiki/North_American_Numbering_Plan">North American Numbering Plan</a>
 * @author Warlock, AIS.PL
 * @since 1.0
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ValueObject
public final class PhoneNumber implements Serializable {

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = 3151171327963441278L;

    private transient String areaCode;

    private transient String exchangeCode;

    private transient String subscriberNumber;

    private String value;

    /**
     * Constructs new instance.
     */
    public PhoneNumber() {
        super();
    }

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

        // Verify constructor requirements, ...
        Preconditions.checkNotNull(areaCode, "Area code is required.");
        Preconditions.checkNotNull(exchangeCode, "Exchange code is required.");
        Preconditions.checkNotNull(subscriberNumber, "Subscriber number is required.");

        // ... and initialize this instance fields.
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
        return value.hashCode();
    }

    private void readObject(final ObjectInputStream objectStream) throws IOException, ClassNotFoundException {

        // Read object, ...
        objectStream.defaultReadObject();

        // ... and restore its state.
        if (10 != value.length()) {
            throw new InvalidObjectException("Deserialized value: '" + value
                + "' is not a valid representation of the phone number.");
        }
        this.areaCode = value.substring(0, 3);
        this.exchangeCode = value.substring(3, 6);
        this.subscriberNumber = value.substring(6);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.areaCode + "-" + this.exchangeCode + "-" + this.subscriberNumber;
    }

}
