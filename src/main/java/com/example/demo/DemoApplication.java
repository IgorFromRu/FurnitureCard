package com.example.demo;

import com.aspose.pdf.*;
import com.aspose.pdf.operators.ConcatenateMatrix;
import com.aspose.pdf.operators.Do;
import com.aspose.pdf.operators.GRestore;
import com.aspose.pdf.operators.GSave;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class, args);

        // Полные примеры и файлы данных см. на странице https://github.com/aspose-pdf/Aspose.Pdf-for-Java.
        // Загрузить исходный PDF-документ
        Document doc = new Document("src/main/resources/filePDF/Енисей.pdf");
        // Инициализирует новый экземпляр таблицы
        Table table = new Table();
        // Установите цвет границы таблицы как LightGray
        table.setBorder(new BorderInfo(BorderSide.All, .5f, Color.getLightGray()));
        // установить границу для ячеек таблицы
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .5f, Color.getLightGray()));
        // создать цикл для добавления 10 строк
        for (int row_count = 1; row_count < 10; row_count++) {
            // добавить строку в таблицу
            Row row = table.getRows().add();
            // добавить ячейки таблицы
            row.getCells().add("Column (" + row_count + ", 1)");
            row.getCells().add("Column (" + row_count + ", 2)");
            row.getCells().add("Column (" + row_count + ", 3)");
        }
        // Добавить табличный объект на первую страницу входного документа
        doc.getPages().get_Item(1).getParagraphs().add(table);
        // Сохранить обновленный документ, содержащий табличный объект
        doc.save("src/main/resources/filePDF/Енисей.pdf");
    }

}
