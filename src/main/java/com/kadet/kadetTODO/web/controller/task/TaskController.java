package com.kadet.kadetTODO.web.controller.task;

import com.kadet.kadetTODO.service.task.TaskService;
import com.kadet.kadetTODO.util.Strings;
import com.kadet.kadetTODO.util.extjs.ExtJSResponse;
import com.kadet.kadetTODO.web.model.TaskUI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Date: 11.11.2014
 * Time: 10:37
 *
 *  Simple REST API for manipulating with tasks
 *
 * @author Alex Soroka
 */
@Controller
@RequestMapping ("/api")
public class TaskController {

    private Logger logger = Logger.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private ExtJSResponse<TaskUI> extJS;


    /**
     * CREATE
     */

    @RequestMapping (value = "/tasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, ? extends Object> create (TaskUI newTaskUI) {
        try {
            TaskUI created = taskService.create(newTaskUI);
            return (created != null)
                    ? extJS.mapOK(created, Strings.TASK_CREATE_SUCCESS)
                    : extJS.mapError(Strings.TASK_CREATE_ERROR);
        } catch (Exception e) {
            return extJS.mapError(Strings.TASK_CREATE_ERROR);
        }
    }




    /**
     * READ
     */


    /**
     *  Read one
     */
    @RequestMapping (value = "/tasks/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, ? extends Object> read (@PathVariable ("taskId") Long taskId) {
        try {
            TaskUI taskUI = taskService.findById(taskId);
            return extJS.mapOK(taskUI);
        } catch (Exception e) {
            logger.error(e);
            return extJS.mapError(Strings.TASK_RETRIEVE_ERROR);
        }
    }


    /**
     *  Read all
     */
    @RequestMapping (value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, ? extends Object> readAll () {
        try {
            List<TaskUI> taskUIs = taskService.findAll();
            return extJS.mapOK(taskUIs);
        } catch (Exception e) {
            logger.error(e);
            return extJS.mapError(Strings.TASKS_RETRIEVE_ERROR);
        }
    }




    /**
     * UPDATE
     */

    @RequestMapping (value = "/tasks/{taskId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, ? extends Object> update (TaskUI taskUI, @PathVariable("taskId") Long taskId) throws Exception {
        try {
            taskUI.setId(taskId);
            TaskUI task = taskService.update(taskUI);
            return (task != null)
                    ? extJS.mapOK(task, Strings.TASK_UPDATE_SUCCESS)
                    : extJS.mapError(Strings.TASK_UPDATE_ERROR);
        } catch (Exception e) {
            logger.error(e);
            return extJS.mapError(Strings.TASK_UPDATE_ERROR);
        }
    }




    /**
     * DELETE
     */


    /**
     *  Delete one
     */
    @RequestMapping (value = "/tasks/{taskId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, ? extends Object> delete (@PathVariable("taskId") Long id) throws Exception {
        try {
            TaskUI taskUI = taskService.findById(id);
            taskService.delete(taskUI);
            return extJS.mapOK(taskUI);
        } catch (Exception e) {
            logger.error(e);
            return extJS.mapError(Strings.TASK_DELETE_ERROR);
        }
    }


    /**
     *
     *  Delete many tasks
     *
     * @param taskIds   String contains list of task ids. Format example:  '[1, 2, 3]'
     */
    @RequestMapping (value = "/tasks/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, ? extends Object> deleteTasks (@RequestParam(value = "tasks") String taskIds) throws Exception {
        try {
            List<TaskUI> deletedTasks = new ArrayList<TaskUI>();
            for (String stringTaskId : taskIds.substring(1, taskIds.length() - 1).replaceAll("\"", "").split(",")) {
                TaskUI taskUI = taskService.findById(Long.parseLong(stringTaskId));
                if (taskUI != null) {
                    taskService.delete(taskUI);
                    deletedTasks.add(taskUI);
                }
            }
            return extJS.mapOK(deletedTasks, Strings.TASK_DELETE_SUCCESS);
        } catch (Exception e) {
            logger.error(e);
            return extJS.mapError(Strings.TASK_DELETE_ERROR);
        }
    }


}