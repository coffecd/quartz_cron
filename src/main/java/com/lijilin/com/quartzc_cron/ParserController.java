package com.lijilin.com.quartzc_cron;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.quartz.CronExpression;

public class ParserController {
    @FXML
    private TextField cronExpressionField;

    @FXML
    private TextArea cronExplanationArea;

    @FXML
    private TableView<FireTimeEntry> nextFireTimesTable;

    @FXML
    private TableColumn<FireTimeEntry, Integer> indexColumn;

    @FXML
    private TableColumn<FireTimeEntry, String> dateTimeColumn;

    @FXML
    private TableColumn<FireTimeEntry, String> formattedColumn;

    private ObservableList<FireTimeEntry> fireTimeEntries = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // 初始化表格列
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        formattedColumn.setCellValueFactory(new PropertyValueFactory<>("formatted"));

        // 设置表格数据源
        nextFireTimesTable.setItems(fireTimeEntries);
    }

    @FXML
    private void onParseButtonClick() {
        String cronExpression = cronExpressionField.getText();
        if (cronExpression == null || cronExpression.trim().isEmpty()) {
            showAlert("错误", "请输入Cron表达式");
            return;
        }

        try {
            // 解析Cron表达式
            parseCronExpression(cronExpression);
        } catch (Exception e) {
            showAlert("错误", "无效的Cron表达式: " + e.getMessage());
        }
    }

    private void parseCronExpression(String cronExpression) throws Exception {
        // 创建Cron表达式对象
        CronExpression cron = new CronExpression(cronExpression);

        // 生成表达式说明
        generateCronExplanation(cronExpression);

        // 计算未来10次执行时间
        calculateNextFireTimes(cron);
    }

    private void generateCronExplanation(String cronExpression) {
        StringBuilder explanation = new StringBuilder();
        String[] parts = cronExpression.split(" ");

        explanation.append("表达式 ").append(cronExpression).append(" 表示：\n");

        if (parts.length >= 6) {
            explanation.append("秒：").append(explainPart(parts[0], 0, 59)).append("\n");
            explanation.append("分：").append(explainPart(parts[1], 0, 59)).append("\n");
            explanation.append("时：").append(explainPart(parts[2], 0, 23)).append("\n");
            explanation.append("日：").append(explainPart(parts[3], 1, 31)).append("\n");
            explanation.append("月：").append(explainPart(parts[4], 1, 12)).append("\n");
            explanation.append("周：").append(explainPart(parts[5], 1, 7)).append("\n");

            if (parts.length >= 7) {
                explanation.append("年：").append(explainPart(parts[6], 1970, 2099)).append("\n");
            }
        }

        cronExplanationArea.setText(explanation.toString());
    }

    private String explainPart(String part, int min, int max) {
        String timeType = getTimeType(min, max);
        
        if (part.equals("*")) {
            if (min == 0 && max == 59) {
                return "每秒";
            } else if (min == 0 && max == 59) {
                return "每分";
            } else if (min == 0 && max == 23) {
                return "每时";
            } else if (min == 1 && max == 31) {
                return "每日";
            } else if (min == 1 && max == 12) {
                return "每月";
            } else if (min == 1 && max == 7) {
                return "每周";
            } else if (min == 1970) {
                return "每年";
            } else {
                return "每" + getTimeType(min, max);
            }
        } else if (part.equals("?")) {
            return "不指定";
        } else if (part.contains("-")) {
            String[] range = part.split("-");
            return "从" + range[0] + "到" + range[1];
        } else if (part.contains("/")) {
            String[] increment = part.split("/");
            return "从" + increment[0] + timeType + "开始，每隔" + increment[1] + timeType;
        } else if (part.contains(",")) {
            return "指定值：" + part;
        } else {
            return "固定值：" + part;
        }
    }
    
    private String getTimeType(int min, int max) {
        if (min == 0 && max == 59) {
            return "秒";
        } else if (min == 0 && max == 59) {
            return "分";
        } else if (min == 0 && max == 23) {
            return "时";
        } else if (min == 1 && max == 31) {
            return "日";
        } else if (min == 1 && max == 12) {
            return "月";
        } else if (min == 1 && max == 7) {
            return "周";
        } else if (min == 1970) {
            return "年";
        } else {
            return "";
        }
    }

    private void calculateNextFireTimes(CronExpression cron) {
        // 清空现有数据
        fireTimeEntries.clear();

        // 获取当前时间
        Date now = new Date();
        Date nextFireTime = now;

        // 创建日期格式化器
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E");

        // 设置时区
        sdf.setTimeZone(TimeZone.getDefault());
        formatter.setTimeZone(TimeZone.getDefault());

        // 计算未来10次执行时间
        List<FireTimeEntry> entries = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            nextFireTime = cron.getNextValidTimeAfter(nextFireTime);
            if (nextFireTime != null) {
                String dateTimeStr = sdf.format(nextFireTime);
                String formattedStr = formatter.format(nextFireTime);
                entries.add(new FireTimeEntry(i, dateTimeStr, formattedStr));

                // 增加1毫秒，以便获取下一次执行时间
                nextFireTime = new Date(nextFireTime.getTime() + 1);
            } else {
                break;
            }
        }

        // 更新表格数据
        fireTimeEntries.addAll(entries);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 表格数据模型
    public static class FireTimeEntry {
        private final Integer index;
        private final String dateTime;
        private final String formatted;

        public FireTimeEntry(Integer index, String dateTime, String formatted) {
            this.index = index;
            this.dateTime = dateTime;
            this.formatted = formatted;
        }

        public Integer getIndex() {
            return index;
        }

        public String getDateTime() {
            return dateTime;
        }

        public String getFormatted() {
            return formatted;
        }
    }
}
