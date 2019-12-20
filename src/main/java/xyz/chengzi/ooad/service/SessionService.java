package xyz.chengzi.ooad.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.chengzi.ooad.entity.User;
import xyz.chengzi.ooad.repository.Repository;

public interface SessionService {
    /**
     * Hash the plain password.
     *
     * @param plainPassword the password to store.
     * @return the hashed password.
     */
    @NotNull
    String hashPassword(@NotNull String plainPassword);

    /**
     * Check if the given password is correct for the user.
     *
     * @param user          the user.
     * @param plainPassword the password to test.
     * @return whether the password matches.
     */
    boolean checkPassword(@NotNull User user, @NotNull String plainPassword);

    /**
     * Find the corresponding user by the token.
     *
     * @param token the token to check.
     * @return the user corresponding to the token, null if none.
     */
    @Nullable
    User findTokenOwner(@NotNull Repository<User> userRepository, @NotNull byte[] token);

    /**
     * Get the token of the given user if present.
     *
     * @param user the user.
     * @return the token if present, null otherwise.
     */
    @Nullable
    byte[] getTokenIfPresent(@NotNull User user);

    /**
     * Generate and return a token for the user.
     * <p>
     * The older token (if present) will be invalid after this method.
     *
     * @param user the user.
     * @return the generated token.
     */
    @NotNull
    byte[] generateToken(@NotNull User user);

    /**
     * Invalidate the token (if present) of the given user.
     *
     * @param user the user.
     */
    void invalidateToken(@Nullable User user);

    /**
     * Close the underlying data backend.
     */
    void close();
}
