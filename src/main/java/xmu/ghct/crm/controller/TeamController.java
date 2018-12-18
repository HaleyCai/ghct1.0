package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.service.TeamService;

import java.math.BigInteger;
import java.util.List;

@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    @RequestMapping(value="/course/{courseId}/team",method = RequestMethod.GET)
    @ResponseBody
    public List<Team> getTeamInfoByCourseId(@PathVariable("courseId") BigInteger courseId){
        return teamService.getTeamInfoByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}/noTeam",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getNoTeamStudentByCourseId(@PathVariable("courseId")BigInteger courseId){
        return teamService.getNoTeamStudentByCourseId(courseId);
    }
}
