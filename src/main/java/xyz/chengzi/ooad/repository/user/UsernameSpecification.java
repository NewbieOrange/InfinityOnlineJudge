package xyz.chengzi.ooad.repository.user;

import com.google.common.collect.ImmutableMap;
import xyz.chengzi.ooad.entity.User;
import xyz.chengzi.ooad.repository.JpqlSpecification;

import javax.annotation.Nonnull;
import java.util.Map;

public class UsernameSpecification implements JpqlSpecification<User>
{
    private String username;

    public UsernameSpecification(@Nonnull String username)
    {
        this.username = username;
    }

    @Override public boolean isSatisfiedBy(@Nonnull User user)
    {
        return username.equals(user.getUsername());
    }

    @Nonnull
    @Override public String toJpqlQuery()
    {
        return "username = :username" + hashCode();
    }

    @Nonnull
    @Override public Map<String, Object> getJpqlParameters()
    {
        return new ImmutableMap.Builder<String, Object>().put("username" + hashCode(), username).build();
    }
}
