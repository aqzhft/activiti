package cc.powind.activiti.impl.service;

import cc.powind.activiti.core.model.Participant;
import cc.powind.activiti.core.service.ParticipantService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    public static final Map<String, Participant> cache = new ConcurrentHashMap<>();

    static {
        cache.put("zhangsan", new Participant("zhangsan", "张三", new String[] {"zhuguan"}));
        cache.put("lisi", new Participant("lisi", "李四", new String[] {"zhuguan"}));
        cache.put("wangwu", new Participant("wangwu", "王五"));
        cache.put("zhaoliu", new Participant("zhaoliu", "赵六"));
        cache.put("qianqi", new Participant("qianqi", "钱七"));
    }

    @Override
    public List<Participant> findByUserIds(String[] userIds) {
        return cache.entrySet().stream().filter(entry -> ArrayUtils.contains(userIds, entry.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public List<Participant> findByGroupIds(String[] groupIds) {
        return cache.values().stream().filter(user -> {

            String[] labelIds = user.getGroupIds();

            for (String groupId : groupIds) {
                if (ArrayUtils.contains(labelIds, groupId)) {
                    return true;
                }
            }

            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Participant> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public List<String> findAllGroupIds() {
        return Collections.singletonList("zhuguan");
    }
}
