package cc.powind.activiti.core.service;

import cc.powind.activiti.core.model.Participant;

import java.util.List;

public interface ParticipantService {

    List<Participant> findByUserIds(String[] userIds);

    List<Participant> findByGroupIds(String[] groupIds);

    List<Participant> findAll();

    List<String> findAllGroupIds();
}
