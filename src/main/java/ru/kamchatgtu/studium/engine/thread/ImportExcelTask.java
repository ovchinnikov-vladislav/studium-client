package ru.kamchatgtu.studium.engine.thread;

import javafx.concurrent.Task;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import ru.kamchatgtu.studium.engine.SecurityAES;
import ru.kamchatgtu.studium.entity.Answer;
import ru.kamchatgtu.studium.entity.Question;
import ru.kamchatgtu.studium.entity.Theme;
import ru.kamchatgtu.studium.restclient.RestConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class ImportExcelTask extends Task<Boolean> {

    private RestConnection restConnection;
    private HSSFWorkbook workbook;

    public ImportExcelTask(File file) {
        try {
            this.workbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        this.restConnection = new RestConnection();
    }

    @Override
    protected Boolean call() {
        try {
            int count = testExcel() + 2;
            HSSFSheet sheet = workbook.getSheetAt(0);

            Theme theme = new Theme();
            theme.setThemeText(sheet.getRow(0).getCell(0).getStringCellValue());
            theme = restConnection.getRestTheme().add(theme);
            for (int i = 3; i < count; i++) {

                Row row = sheet.getRow(i);
                if (row == null) break;
                Cell cellQuestion = row.getCell(0);
                if (cellQuestion == null) break;
                CellType cellType = cellQuestion.getCellTypeEnum();
                Question question = null;
                switch (cellType) {
                    case _NONE:
                        break;
                    case BOOLEAN:
                        question = initQuestion(cellQuestion.getBooleanCellValue() + "", theme);
                    case BLANK:
                        break;
                    case FORMULA:
                        question = initQuestion(cellQuestion.getCellFormula(), theme);
                        break;
                    case NUMERIC:
                        question = initQuestion(cellQuestion.getNumericCellValue() + "", theme);
                        break;
                    case STRING:
                        question = initQuestion(cellQuestion.getStringCellValue(), theme);
                        break;
                    case ERROR:
                        break;
                }
                if (question != null) {
                    question = restConnection.getRestQuestion().add(question);
                    answerQues(row, question);
                }
                this.updateProgress(i - 3, count - 3);
            }
            return true;
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
    }

    private void answerQues(Row row, Question question) {
        int j = 1;
        int k = 2;
        while (true) {
            Cell cellAnswer = row.getCell(j);
            if (cellAnswer == null) break;
            Cell cellRight;
            CellType cellType = cellAnswer.getCellTypeEnum();
            Answer answer = null;
            switch (cellType) {
                case _NONE:
                    break;
                case BOOLEAN:
                    answer = initAnswer(cellAnswer.getBooleanCellValue() + "", question);
                case BLANK:
                    break;
                case FORMULA:
                    answer = initAnswer(cellAnswer.getCellFormula(), question);
                    break;
                case NUMERIC:
                    answer = initAnswer(cellAnswer.getNumericCellValue() + "", question);
                    break;
                case STRING:
                    answer = initAnswer(cellAnswer.getStringCellValue(), question);
                    break;
                case ERROR:
                    break;
            }
            if (answer != null) {
                cellRight = row.getCell(k);
                if (cellRight != null && cellRight.getStringCellValue() != null && !cellRight.getStringCellValue().equals(""))
                    answer.setCorrect(true);
                restConnection.getRestAnswer().add(answer);
            }
            j += 2;
            k += 2;
        }
    }

    private Question initQuestion(String textQuestion, Theme theme) {
        Question question = new Question();
        question.setQuestionType(2);
        question.setDateReg(new Date());
        question.setTheme(theme);
        question.setUser(SecurityAES.USER_LOGIN);
        question.setQuestionText(textQuestion);
        return question;
    }

    private Answer initAnswer(String textAnswer, Question question) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setDateEdit(new Date());
        answer.setUser(SecurityAES.USER_LOGIN);
        answer.setAnswerText(textAnswer);
        return answer;
    }

    private int testExcel() {
        HSSFSheet sheet = workbook.getSheetAt(0);

        int i = 3;
        while (true) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cellQuestion = row.getCell(0);
                System.out.println(cellQuestion.getStringCellValue());
                if (cellQuestion == null || cellQuestion.getStringCellValue() == null || cellQuestion.getStringCellValue().equals("")) {
                    break;
                }
            } else break;
            i++;
        }
        return i - 2;
    }
}
