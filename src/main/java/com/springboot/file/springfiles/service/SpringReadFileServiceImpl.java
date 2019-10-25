package com.springboot.file.springfiles.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.springboot.file.springfiles.model.Entity;
import com.springboot.file.springfiles.repository.SpringReadFileRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class SpringReadFileServiceImpl  implements SpringReadFileService {
    @Autowired
    private SpringReadFileRepository springReadFileRepository;


    @Override
    public List<Entity> findAll() {
        return (List<Entity>) springReadFileRepository.findAll();
    }




    @Override
    public void saveEntity(Entity entity) {
        springReadFileRepository.save(entity);
    }
    @Override
    public boolean saveDataFromUploadfile(MultipartFile file) {
        boolean isFlag = false;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension.equalsIgnoreCase("json")){
            isFlag = readDataFromJson(file);
        }else if(extension.equalsIgnoreCase("csv")){
            isFlag = readDataFromCsv(file);
        }else if(extension.equalsIgnoreCase("xls")||extension.equalsIgnoreCase("xlsx")){
            isFlag = readDataFromExcel(file);
        }

        return isFlag;
    }





    private boolean readDataFromExcel(MultipartFile file) {
        Workbook workbook = getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows= sheet.iterator();
        rows.next();
        while(rows.hasNext()){
            Row row = rows.next();
            Entity entity = new Entity();
            if(row.getCell(0).getCellType()== Cell.CELL_TYPE_STRING){
                entity.setField1(row.getCell(0).getStringCellValue()+" "+row.getCell(1).getStringCellValue());
            }if(row.getCell(2).getCellType()== Cell.CELL_TYPE_STRING){
                entity.setField2(row.getCell(2).getStringCellValue());
            }if(row.getCell(5).getCellType()== Cell.CELL_TYPE_STRING){
                entity.setField3(row.getCell(5).getStringCellValue());
            }if(row.getCell(8).getCellType()== Cell.CELL_TYPE_STRING) {
                entity.setField4(row.getCell(8).getStringCellValue());
            }
            springReadFileRepository.save(entity);


        }
        return true;
    }

    private Workbook getWorkBook(MultipartFile file) {
        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try{
            if(extension.equalsIgnoreCase("xlsx")){
                workbook= new XSSFWorkbook(file.getInputStream());
            }else if(extension.equalsIgnoreCase("xls")){
                workbook = new HSSFWorkbook(file.getInputStream());

            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return workbook;

    }

    private boolean readDataFromCsv(MultipartFile file) {
        try{
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            List<String[]> rows = csvReader.readAll();
            for (String [] row: rows){
                springReadFileRepository.save(new Entity(row[0]+" "+row[1],row[2],row[5],row[8]));
            }
            return true;

        }catch (Exception e){
            return false;
        }

    }

    private boolean readDataFromJson(MultipartFile file) {
        try{
            InputStream inputStream= file.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<Entity> entities = Arrays.asList(mapper.readValue(inputStream, Entity[].class));
            if (entities !=null&& entities.size()>0){
                for (Entity entity : entities){
                    springReadFileRepository.save(entity);
                }
            }
            return true;

        }catch (Exception e){
            return false;
        }

    }
}
