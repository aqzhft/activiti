package cc.powind.activiti.impl.service;

import cc.powind.activiti.core.model.TaskLog;
import cc.powind.activiti.core.service.LogService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private HistoryService service;

    @Autowired
    private TaskService taskService;

    public List<TaskLog> findByInstanceId(String instanceId) {

        Map<String, List<Comment>> commentGroup = taskService.getProcessInstanceComments(instanceId).stream().collect(Collectors.groupingBy(Comment::getTaskId));

        List<HistoricTaskInstance> list = service.createHistoricTaskInstanceQuery().processInstanceId(instanceId).orderByHistoricTaskInstanceStartTime().asc().list();
        List<TaskLog> logList = list.stream().map(this::convert).filter(Objects::nonNull).collect(Collectors.toList());

        logList.forEach(log -> {
            List<Comment> commentList = commentGroup.get(log.getId());
            if (commentList != null && !commentList.isEmpty()) {
                log.setComments(commentList.stream().map(Comment::getFullMessage).toArray(String[]::new));
            }
        });

        return logList;
    }

    private TaskLog convert(HistoricTaskInstance entity) {

        if (entity == null) {
            return null;
        }

        TaskLog bean = new TaskLog();
        bean.setId(entity.getId());
        bean.setName(entity.getName());
        bean.setStartTime(entity.getStartTime() == null ? null : entity.getStartTime().toInstant());
        bean.setEndTime(entity.getEndTime() == null ? null : entity.getEndTime().toInstant());
        bean.setExecutorId(entity.getAssignee());

        return bean;
    }
}
