package com.dexwin.taskflow.controller;

import com.dexwin.taskflow.entity.Project;
import com.dexwin.taskflow.entity.Task;
import com.dexwin.taskflow.entity.TaskStatus;
import com.dexwin.taskflow.repository.ProjectRepository;
import com.dexwin.taskflow.repository.TaskRepository;
import com.dexwin.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class TaskController {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    public TaskController(TaskRepository taskRepository,
                          ProjectRepository projectRepository,
                          TaskService taskService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskService = taskService;
    }

    @GetMapping("/projects/{projectId}/tasks")
    public List<Task> getTasks(@PathVariable Long projectId,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be positive");
        }
        return taskRepository.findByProjectId(projectId);
    }

    @GetMapping("/projects/{projectId}/task-summaries")
    public List<Map<String, Object>> getTaskSummaries(@PathVariable Long projectId) {
        return taskService.getTaskSummaries(projectId);
    }

    @PostMapping("/projects/{projectId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@PathVariable Long projectId, @Valid @RequestBody Task task) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        task.setProject(project);
        return taskRepository.save(task);
    }

    @PutMapping("/tasks/{id}/status")
    public Task updateStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        return taskService.updateStatus(id, status);
    }

    @GetMapping("/tasks/search")
    public List<Task> search(@RequestParam String q) {
        if (q == null || q.isBlank()) {
            return List.of();
        }
        return taskService.search(q.trim());
    }
}
