package com.example.demo;

import com.aspose.pdf.*;
import com.aspose.pdf.operators.ConcatenateMatrix;
import com.aspose.pdf.operators.Do;
import com.aspose.pdf.operators.GSave;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/*
Документация
https://blog.aspose.com/ru/pdf/create-pdf-files-in-java/#Insert-an-Image-in-PDF-File
*/
public class CreatePDF {

    private Document documentPDF;
    private Page page;

    public void creatFile(String nameFile, String pathToImage, List<Integer[]> valuesOfModel) {

        // Инициализировать документ
        documentPDF = new Document();

        //Добавить страницу
        page = documentPDF.getPages().add();

        // Создать заголовок/название издения
        TextFragment textFragment = new TextFragment(
                nameFile);
        textFragment.setPosition(new Position(50, 800));

        // Установить свойства текста
        textFragment.getTextState().setFontSize(15);
        textFragment.getTextState().setFont(FontRepository.findFont("TimesNewRoman"));
        textFragment.getTextState().setBackgroundColor(Color.getLightGray());
        textFragment.getTextState().setForegroundColor(Color.getBlack());


        // Создать объект TextBuilder
        TextBuilder textBuilder = new TextBuilder(page);

        // Добавьте текстовые фрагменты на страницу PDF
        textBuilder.appendText(textFragment);

        //Добавить изображение
        addImage(pathToImage);

        //Добавить таблицу с оазмерами
        addTable(valuesOfModel);

        // Сохраните новый PDF
        documentPDF.save("src/main/resources/filePDF/" + nameFile + ".pdf");

    }

    public void addImage(String pathFileImage) {

        // Установить координаты
        int lowerLeftX = 10; //левый часть изображения
        int lowerLeftY = 550; //нижняя часть изображения
        int upperRightX = 270; //правая часть ижображения
        int upperRightY = 800; //верняя часть изображения

        // Получите страницу, на которую вы хотите добавить изображение
        page = documentPDF.getPages().get_Item(1);

        try {
            // Загрузить изображение в поток
            FileInputStream imageStream = new FileInputStream(pathFileImage);

            // Добавьте изображение в коллекцию изображений ресурсов страницы.
            page.getResources().getImages().add(imageStream);

            // Использование оператора GSave: этот оператор сохраняет текущее состояние графики.
            page.getContents().add(new GSave());

            // Создание объектов «Прямоугольник» и «Матрица»
            Rectangle rectangle = new Rectangle(lowerLeftX, lowerLeftY, upperRightX, upperRightY);
            Matrix matrix = new Matrix(new double[]{rectangle.getURX() - rectangle.getLLX(),
                    0,
                    0,
                    rectangle.getURY() - rectangle.getLLY(),
                    rectangle.getLLX(),
                    rectangle.getLLY()});

            // Использование оператора ConcatenateMatrix (сцепление матрицы): определяет, как должно быть размещено изображение.
            page.getContents().add(new ConcatenateMatrix(matrix));
            XImage ximage = page.getResources().getImages().get_Item(page.getResources().getImages().size());

            // Использование оператора Do: этот оператор рисует изображение
            page.getContents().add(new Do(ximage.getName()));

            // Закрыть поток изображений
            imageStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addTable(List<Integer[]> valuesOfModel) {

        // Инициализирует новый экземпляр таблицы
        Table table = new Table();

        // Установите цвет границы таблицы как LightGray
        table.setBorder(new BorderInfo(BorderSide.All, .3f, Color.getLightGray()));

        // установить границу для ячеек таблицы
        table.setDefaultCellBorder(new BorderInfo(BorderSide.All, .3f, Color.getLightGray()));


        // создать цикл для добавления  строк
        valuesOfModel.forEach((value) -> {
            // добавить строку в таблицу
            Row row = table.getRows().add();
            // добавить ячейки таблицы
            row.getCells().add(String.valueOf(value[0]));
            row.getCells().add(String.valueOf(value[1]));
            row.getCells().add(String.valueOf(value[2]));
        });

        // Задайте ширину столбцов таблицы
        table.setColumnWidths("50 50 50");

        //Расположение таблицы
        table.setTop(300);
        table.setLeft(50);

        // Добавить табличный объект на первую страницу входного документа
        documentPDF.getPages().get_Item(1).getParagraphs().add(table);

    }


}
