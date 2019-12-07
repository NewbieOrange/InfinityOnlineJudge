package xyz.chengzi.ooad.repository.user;

import com.google.common.collect.ImmutableMap;
import xyz.chengzi.ooad.entity.User;
import xyz.chengzi.ooad.repository.JpqlSpecification;

import javax.annotation.Nonnull;
import java.util.Map;

public class PermissionSpecification implements JpqlSpecification<User> {
    private String permission;

    public PermissionSpecification(String permission) {
        this.permission = permission;
    }

    @Nonnull
    @Override
    public String toJpqlQuery() {
        return ":permission" + hashCode() + " MEMBER OF permissions";
    }

    @Nonnull
    @Override
    public Map<String, Object> getJpqlParameters() {
        return ImmutableMap.of("permission" + hashCode(), permission);
    }
}
