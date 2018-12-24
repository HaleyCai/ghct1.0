package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.service.RequestService;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.VO.TeamApplicationVO;

import java.math.BigInteger;
import java.util.Map;
import java.util.List;

@RestController
public class RequestController {
    @Autowired RequestService requestService;

    /**
     * 获得队伍共享申请信息列表
     * @return
     */
    @RequestMapping(value="/request/teamshare",method = RequestMethod.GET)
    public List<Share> getAllTeamShare(@RequestBody Map<String,Object> inMap){
        return requestService.getAllShare(1,(int)inMap.get("status"));
    }

    /**
     * 获得讨论课共享申请信息列表
     * @return
     */
    @RequestMapping(value="/request/seminarshare",method = RequestMethod.GET)
    public List<Share> getAllSeminarShare(@RequestBody Map<String,Object> inMap){
        return requestService.getAllShare(0,(int)inMap.get("status"));
    }

    /**
     * 获得某一个队伍共享申请信息
     * @return
     */
    @RequestMapping(value="/request/teamshare/{teamshareId}",method = RequestMethod.GET)
    public Share getTeamShare(@RequestBody Map<String,Object> inMap){
        return requestService.getShare(
                1,
                new BigInteger(inMap.get("shareId").toString()),
                (int)inMap.get("status"));
    }

    /**
     * 获得某一个讨论课共享申请信息
     * @return
     */
    @RequestMapping(value="/request/seminarshare/{seminarshareId}",method = RequestMethod.GET)
    public Share getSeminarShare(@RequestBody Map<String,Object> inMap){
        return requestService.getShare(
                0,
                new BigInteger(inMap.get("shareId").toString()),
                (int)inMap.get("status"));
    }

    /**
     * 修改共享队伍请求状态
     * @return
     */
    @RequestMapping(value="/request/teamshare/{teamshareId}",method = RequestMethod.PUT)
    public boolean updateTeamShareStatus(@RequestBody Map<String,Object> inMap){
        return requestService.updateShareStatus(
                1,
                new BigInteger(inMap.get("shareId").toString()),
                (int)inMap.get("status"));
    }

    /**
     * 修改共享讨论课请求状态
     * @return
     */
    @RequestMapping(value="/request/seminarshare/{seminarshareId}",method = RequestMethod.PUT)
    public boolean updateSeminarShareStatus(@RequestBody Map<String,Object> inMap){
        return requestService.updateShareStatus(
                0,
                new BigInteger(inMap.get("shareId").toString()),
                (int)inMap.get("status"));
    }

    /**
     * 获得组队申请信息列表
     * @return
     */
    @RequestMapping(value="/request/teamvalid",method = RequestMethod.GET)
    public List<TeamApplicationVO> getAllTeamApplication(@RequestBody Map<String,Object> inMap){
        return requestService.getAllTeamApplication((int)inMap.get("status"));
    }

    /**
     * 获得某一个组队申请信息
     * @return
     */
    @RequestMapping(value="/request/teamvalid/{teamvalidId}",method = RequestMethod.GET)
    public TeamApplicationVO getTeamApplication(@RequestBody Map<String,Object> inMap){
        return requestService.getTeamApplication(
                new BigInteger(inMap.get("teamValidId").toString()),
                (int)inMap.get("status"));
    }

    /**
     * 修改组队请求状态
     * @return
     */
    @RequestMapping(value="/request/teamvalid/{teamvalidId}",method = RequestMethod.PUT)
    public boolean updateTeamApplicationStatus(@RequestBody Map<String,Object> inMap){
        return requestService.updateTeamApplicationStatus(
                new BigInteger(inMap.get("teamValidId").toString()),
                (int)inMap.get("status"));
    }
}
