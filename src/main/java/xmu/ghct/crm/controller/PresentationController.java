package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.dao.DownloadFileDao;
import xmu.ghct.crm.dao.SeminarDao;
import xmu.ghct.crm.entity.Attendance;
import xmu.ghct.crm.service.PresentationService;
import xmu.ghct.crm.service.SeminarService;
import xmu.ghct.crm.service.UploadFileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class PresentationController {

    @Autowired
    PresentationService presentationService;

    @Autowired
    UploadFileService uploadFileService;

    @Autowired
    SeminarService seminarService;

    @Autowired
    DownloadFileDao downloadFileDao;

    @Autowired
    SeminarDao seminarDao;

    /**
     * @author hzm
     * 修改讨论课报名次序
     * @param attendanceId
     * @param orderMap
     * @return
     */
    @PutMapping("attendance/{attendanceId}")
    public boolean updateAttendanceOrderByAttendanceId(@PathVariable("attendanceId") BigInteger attendanceId, @RequestBody Map<String,Object> orderMap){
        int flag=presentationService.updateAttendanceOrderByAttendanceId(attendanceId,orderMap);
        if(flag>0) return true;
        else return false;
    }

    /**
     * @author hzm
     * 取消报名
     * @param attendanceId
     * @return
     */
    @DeleteMapping("attendance/{attendanceId}")
    public boolean deleteAttendanceByAttendance(@PathVariable("attendanceId") BigInteger attendanceId){
        int flag= presentationService.deleteAttendanceByAttendance(attendanceId);
        if(flag>0) return true;
        else return false;
    }

    /**
     * @author hzm
     * 根据attendanceId上传报告
     * @param attendanceId
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/attendance/{attendanceId}/report",method = RequestMethod.POST)
    public boolean reportUpload(@PathVariable("attendanceId")BigInteger attendanceId,@RequestParam("file") MultipartFile file) throws IOException {
        Attendance attendance=presentationService.getAttendanceByAttendanceId(attendanceId);
        BigInteger klassSeminarId=attendance.getKlassSeminarId();
        SeminarVO seminarVO =seminarService.getKlassSeminarByKlassSeminarId(klassSeminarId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//修改日期格式
        Date reportDDL=seminarVO.getReportDDL();
        String reportDdl=dateFormat.format(reportDDL);
        Date date=new Date();
        String nowTime = dateFormat.format( date );
        int judge=reportDdl.compareTo(nowTime);
        if(judge<=0) {
            //throw new OutTimeScopeException;
            return false;
        }
        else {
            Map<String, String> reportMap = uploadFileService.uploadFile(file);
            String reportName = reportMap.get("name");
            String reportUrl = reportMap.get("path");
            int flag = presentationService.updateReportByAttendanceId(attendanceId, reportUrl, reportName);
            if (flag > 0)
                return true;
            else return false;
        }
    }


    /**
     * @author hzm
     * 根据attendanceId下载讨论课报告
     * @param response
     * @param attendanceId
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/attendance/{attendanceId}/report")
    public void reportDownload (HttpServletResponse  response,@PathVariable("attendanceId")BigInteger attendanceId) throws UnsupportedEncodingException {
        Attendance attendance=presentationService.getAttendanceByAttendanceId(attendanceId);
        String filePath=attendance.getReportUrl();
        downloadFileDao.downloadFile(response,filePath);
    }



    /**
     * @author hzm
     * 根据attendanceId上传报告
     * @param attendanceId
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/attendance/{attendanceId}/powerPoint",method = RequestMethod.POST)
    public boolean pptUpload(@PathVariable("attendanceId")BigInteger attendanceId,@RequestParam("file") MultipartFile file) throws IOException {
            Map<String, String> pptMap = uploadFileService.uploadFile(file);
            String pptName = pptMap.get("name");
            String pptUrl = pptMap.get("path");
            int flag = presentationService.updatePPTByAttendanceId(attendanceId, pptUrl, pptName);
            if (flag > 0)
                return true;
            else return false;
    }


    /**
     * @author hzm
     * 根据attendanceId下载ppt
     * @param response
     * @param attendanceId
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/attendance/{attendanceId}/powerPoint")
    public void pptDownload (HttpServletResponse  response,@PathVariable("attendanceId")BigInteger attendanceId) throws UnsupportedEncodingException {
        Attendance attendance=presentationService.getAttendanceByAttendanceId(attendanceId);
        String filePath=attendance.getPptUrl();
        downloadFileDao.downloadFile(response,filePath);
    }

}
