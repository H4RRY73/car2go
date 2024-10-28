package com.pe.platform.profiles.domain.services;

import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.pe.platform.profiles.domain.model.queries.GetProfileByIdQuery;

import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByIdQuery query);
    Optional<Profile> handle(GetAllProfilesQuery query);

}
