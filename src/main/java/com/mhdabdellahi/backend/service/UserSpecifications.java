package com.mhdabdellahi.backend.service;

import com.mhdabdellahi.backend.model.Profile;
import com.mhdabdellahi.backend.model.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecifications {
    public static Specification<User> containsSearch(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isEmpty()) return null;
            String likeSearch = "%" + search.toLowerCase() + "%";

            Join<User, Profile> profile = root.join("profile", JoinType.LEFT);
            return cb.or(
                    cb.like(cb.lower(root.get("username")), likeSearch),
                    cb.like(cb.lower(profile.get("firstName")), likeSearch),
                    cb.like(cb.lower(profile.get("lastName")), likeSearch),
                    cb.like(cb.lower(profile.get("email")), likeSearch)
            );
        };
    }

    public static Specification<User> hasRole(String role) {
        return (root, query, cb) ->
                (role == null || role.isEmpty()) ? null : cb.equal(root.get("role"), role);
    }
}
