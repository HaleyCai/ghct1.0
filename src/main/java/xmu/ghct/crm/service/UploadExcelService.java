package xmu.ghct.crm.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xmu.ghct.crm.dao.KlassDao;
import xmu.ghct.crm.dao.StudentDao;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hzm
 */
@Service
public class UploadExcelService {

    @Autowired
    StudentDao studentDao;

    @Autowired
    KlassDao klassDao;

    public boolean addStudentInfo(BigInteger klassId,MultipartFile file) {
        Map<Integer, Map<Integer,Object>> map = new HashMap<>();
        try {
            boolean flag=readExcelContentz(klassId,file);
            return flag;

        } catch (Exception e) {
            e.printStackTrace();
        }
        //excel数据存在map里，map.get(0).get(0)为excel第1行第1列的值，此处可对数据进行处理
        return true;
    }



    public boolean readExcelContentz(BigInteger klassId,MultipartFile file) throws Exception{
        // 上传文件名
        Klass klass =klassDao.getKlassByKlassId(klassId);
        BigInteger courseId=klass.getCourseId();
        Workbook wb = getWb(file);
        if (wb == null) {
            throw new Exception("Workbook对象为空！");
        }
        Sheet sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        Row row;
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            User user=new User();
            String account= getCellFormatValue(row.getCell(0)).toString().replaceAll("\\u00A0", "");
            if(account.length()<=0) {
                continue;
            }
            account=account.replaceAll(" ","");
            user.setAccount(account);
            String name=getCellFormatValue(row.getCell(1)).toString().replaceAll("\\u00A0","");
            if(name.length()<=0) {
                continue;
            }
            name=name.replaceAll(" ","");
            user.setName(name);
            System.out.println(user);
            int flag_1=studentDao.insertStudent(user);
            if(flag_1==0){
                user=studentDao.getStudentByAccount(user.getAccount());
            }
            int flag_2=studentDao.insertKlassStudent(user.getId(),klassId,courseId);
            if(flag_1<0||flag_2<0) {
                return false;
            }
        }
        return true;
    }

    //根据Cell类型设置数据
    private static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                case Cell.CELL_TYPE_FORMULA: {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    } else {
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;


                }
                case Cell.CELL_TYPE_STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    private static Workbook getWb(MultipartFile mf){
        String filepath = mf.getOriginalFilename();
        String ext = filepath.substring(filepath.lastIndexOf("."));
        Workbook wb = null;
        try {
            InputStream is = mf.getInputStream();
            if(".xls".equals(ext)){
                wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext)){
                wb = new XSSFWorkbook(is);
            }else{
                wb=null;
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
        }
        return wb;
    }
}
