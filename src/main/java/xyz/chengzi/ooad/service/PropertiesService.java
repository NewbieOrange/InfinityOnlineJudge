package xyz.chengzi.ooad.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface PropertiesService {
    /**
     * Get the the property of the given key.
     *
     * @param key the key.
     * @return the corresponding value, null if none.
     */
    @Nullable
    String getProperty(@Nonnull String key);

    /**
     * Get the property of the given key as integer.
     *
     * @param key the key.
     * @return the corresponding value, null if none.
     * @throws NumberFormatException if the value is not an integer.
     */
    @Nullable
    default Integer getPropertyAsInt(@Nonnull String key) throws NumberFormatException {
        String property = getProperty(key);
        return property == null ? null : Integer.parseInt(property);
    }

    /**
     * Get the property of the given key.
     *
     * @param key the key.
     * @return the corresponding value, or the fallback value if none.
     */
    @Nonnull
    default String getPropertyOrDefault(@Nonnull String key, @Nonnull String fallbackValue) {
        String property = getProperty(key);
        return property == null ? fallbackValue : property;
    }

    /**
     * Get the property of the given key as integer.
     *
     * @param key the key.
     * @return the corresponding value, or the fallback value if none.
     * @throws NumberFormatException if the value is not an integer.
     */
    default int getPropertyAsIntOrDefault(@Nonnull String key, int fallbackValue) {
        Integer property = getPropertyAsInt(key);
        return property == null ? fallbackValue : property;
    }

    /**
     * Set the value of the given key.
     *
     * @param key   the key.
     * @param value the value.
     */
    void setProperty(@Nonnull String key, @Nonnull Object value);

    /**
     * Remove the key-value set of the given key.
     *
     * @param key the key.
     */
    void removeProperty(@Nonnull String key);

    /**
     * Load the properties from the datasource.
     */
    void loadProperties();

    /**
     * Save the properties to the datasource.
     */
    void saveProperties();
}
