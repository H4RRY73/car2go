package com.pe.platform.iam.domain.services;

import com.pe.platform.iam.domain.model.aggregates.User;
import com.pe.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.pe.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.pe.platform.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
}
