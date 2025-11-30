package ru.astrosoup.geometryservice.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.astrosoup.geometryservice.DAOs.GroupRepository;
import ru.astrosoup.geometryservice.DAOs.HitRepository;
import ru.astrosoup.geometryservice.DAOs.UserRepository;
import ru.astrosoup.geometryservice.DTOs.GroupRequest;
import ru.astrosoup.geometryservice.DTOs.GroupResponse;
import ru.astrosoup.geometryservice.DTOs.JwtDto;
import ru.astrosoup.geometryservice.entities.GroupEntity;
import ru.astrosoup.geometryservice.entities.HitEntity;
import ru.astrosoup.geometryservice.entities.UserEntity;
import ru.astrosoup.geometryservice.exceptions.InvalidGroupRequestException;
import ru.astrosoup.geometryservice.exceptions.UserDoesNotExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;



@ApplicationScoped
public class GroupService {

    @Inject
    private GroupRepository groupRepository;

    @Inject
    private HitRepository hitRepository;

    @Inject
    private UserRepository userRepository;

    public GroupResponse createGroup(GroupRequest request) throws InvalidGroupRequestException, UserDoesNotExistException {

        GroupEntity entity = new GroupEntity();
        if (isNull(request.getName())) {
            throw new InvalidGroupRequestException("Name field is mandatory.");
        }

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());

        Optional<UserEntity> oUser = userRepository.findById(request.getUser().getId());

        if (oUser.isEmpty()) {
            throw new UserDoesNotExistException("User not found.");
        }

        entity.setUser(oUser.get());
        Long userId = oUser.get().getId();
        Set<HitEntity> hits = entity.getHits();
        for (Long id : request.getHits()) {
            Optional<HitEntity> oHit = hitRepository.findById(id);

            if (oHit.isPresent() && oHit.get().getUser().getId().equals(userId)) {
                hits.add(oHit.get());
            }
        }

        groupRepository.save(entity);

        GroupResponse response = new GroupResponse();

        response.setDescription(entity.getDescription());
        response.setName(entity.getName());
        response.setId(entity.getId());
        response.setHits(entity.getHits()
                .stream()
                .map(HitEntity::getId)
                .collect(Collectors.toSet()));

        return response;
    }

    public List<GroupResponse> getGroups(JwtDto user) throws UserDoesNotExistException {

        Optional<UserEntity> oUser = userRepository.findById(user.getId());

        if (oUser.isEmpty()) {
            throw new UserDoesNotExistException("User not found.");
        }


        List<GroupEntity> entities = groupRepository.findByUser(oUser.get());

        List<GroupResponse> groups = new ArrayList<>();
        for (GroupEntity entity : entities) {
            GroupResponse response = new GroupResponse();
            response.setDescription(entity.getDescription());
            response.setName(entity.getName());
            response.setId(entity.getId());
            response.setHits(entity.getHits()
                    .stream()
                    .map(HitEntity::getId)
                    .collect(Collectors.toSet()));

            groups.add(response);
        }
        return groups;
    }

    public GroupResponse updateGroup(GroupRequest request) throws InvalidGroupRequestException {
        if (isNull(request.getId())) {
            throw new InvalidGroupRequestException("Id field is mandatory.");
        }
        Optional<GroupEntity> oEntity = groupRepository.findById(request.getId());
        if (oEntity.isEmpty()) {
            throw new InvalidGroupRequestException("Group not found.");
        }
        GroupEntity entity = oEntity.get();
        if (!isNull(request.getName())) {
            entity.setName(request.getName());
        }
        if (!isNull(request.getDescription())) {
            entity.setDescription(request.getDescription());
        }

        if (!request.getHits().isEmpty()) {
            Long userId = entity.getUser().getId();
            Set<HitEntity> hits = entity.getHits();
            for (Long id : request.getHits()) {
                Optional<HitEntity> oHit = hitRepository.findById(id);

                if (oHit.isPresent() && oHit.get().getUser().getId().equals(userId)) {
                    hits.add(oHit.get());
                }
            }
            entity.setHits(hits);
        }
        groupRepository.update(entity);

        GroupResponse response = new GroupResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setHits(request.getHits());

        return response;
    }

    public void deleteGroup(GroupRequest request) throws InvalidGroupRequestException {
        Optional<GroupEntity> oEntity = groupRepository.findById(request.getId());
        if (oEntity.isEmpty()) {
            return;
        }
        groupRepository.delete(oEntity.get());
    }

}
