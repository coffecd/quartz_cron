package com.lijilin.com.quartzc_cron;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.quartz.CronExpression;

import java.text.SimpleDateFormat;
import java.util.*;

public class ParserController {
    @FXML
    private TextField cronExpressionField;

    @FXML
    private TextArea cronExplanationArea;

    // 表格相关字段
    @FXML
    private TableView<FireTimeEntry> nextFireTimesTable;
    
    @FXML
    private TableColumn<FireTimeEntry, Integer> indexColumn;
    
    @FXML
    private TableColumn<FireTimeEntry, String> dateTimeColumn;
    
    @FXML
    private TableColumn<FireTimeEntry, String> formattedColumn;
    
    @FXML
    private Button parseButton;

    private ObservableList<FireTimeEntry> fireTimeEntries = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // 初始化表格
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        formattedColumn.setCellValueFactory(new PropertyValueFactory<>("formatted"));
        
        // 设置表格数据源
        nextFireTimesTable.setItems(fireTimeEntries);
        
        // 设置列宽自适应
        nextFireTimesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        // 设置按钮初始样式和悬停效果
        parseButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        parseButton.setOnMouseEntered(e -> parseButton.setStyle("-fx-background-color: #0069d9; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        parseButton.setOnMouseExited(e -> parseButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;"));
        
        // 设置表达式输入框样式和提示文本
        cronExpressionField.setPromptText("输入Cron表达式，例如: 0 0 12 * * ?");
        cronExpressionField.setStyle("-fx-font-size: 14px; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #ced4da; -fx-border-width: 1;");
        
        // 设置表达式说明区域样式
        cronExplanationArea.setStyle("-fx-font-family: 'Consolas', 'Courier New', monospace; -fx-font-size: 14px; -fx-background-color: #f1f8ff; -fx-border-color: #b8daff; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        // 添加输入框获取焦点时的效果
        cronExpressionField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                cronExpressionField.setStyle("-fx-border-color: #80bdff; -fx-background-color: #fff; -fx-border-radius: 5; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,123,255,0.25), 5, 0, 0, 0);");
            } else {
                cronExpressionField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #ced4da; -fx-border-width: 1;");
            }
        });
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
        try {
            // 创建Cron表达式对象
            CronExpression cron = new CronExpression(cronExpression);

            // 生成表达式说明
            generateCronExplanation(cronExpression);

            // 计算未来10次执行时间（虽然不再显示，但保留计算功能）
            calculateNextFireTimes(cron);
            
            // 添加成功解析的视觉反馈
            cronExpressionField.setStyle("-fx-border-color: #28a745; -fx-background-color: #f0fff0; -fx-border-radius: 5; -fx-background-radius: 5;");
            
            // 2秒后恢复正常样式
            Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> cronExpressionField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #ced4da; -fx-border-width: 1;")
            ));
            timeline.play();
        } catch (Exception e) {
            // 添加错误的视觉反馈
            cronExpressionField.setStyle("-fx-border-color: #dc3545; -fx-background-color: #fff0f0; -fx-border-radius: 5; -fx-background-radius: 5;");
            
            // 2秒后恢复正常样式
            Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> cronExpressionField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #ced4da; -fx-border-width: 1;")
            ));
            timeline.play();
            
            // 重新抛出异常以便上层处理
            throw e;
        }
    }

    private void generateCronExplanation(String cronExpression) {
        StringBuilder explanation = new StringBuilder();
        String[] parts = cronExpression.split(" ");
        String[] partNames = {"秒", "分", "时", "日", "月", "周", "年"};
        String[] colors = {"#007bff", "#28a745", "#6f42c1", "#fd7e14", "#20c997", "#e83e8c", "#17a2b8"};

        // 添加表达式标题
        explanation.append("表达式: ").append(cronExpression).append("\n\n");
        
        // 添加各部分详细解释
        explanation.append("各部分含义:\n");
        for (int i = 0; i < Math.min(parts.length, partNames.length); i++) {
            explanation.append("• ")
                      .append(partNames[i])
                      .append(": ")
                      .append(parts[i])
                      .append(" → ")
                      .append(explainPart(parts[i], getMinForPart(i), getMaxForPart(i)))
                      .append("\n");
        }
        
        // 添加完整描述
        try {
            // 创建Quartz Cron解析器
            CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
            Cron cron = parser.parse(cronExpression);
            
            // 创建描述器(中文)
            CronDescriptor descriptor = CronDescriptor.instance(Locale.CHINESE);
            
            explanation.append("\n完整描述:\n")
                      .append(descriptor.describe(cron));
        } catch (Exception e) {
            explanation.append("\n无法生成完整描述: ").append(e.getMessage());
        }

        cronExplanationArea.setText(explanation.toString());
    }
    
    private int getMinForPart(int partIndex) {
        switch (partIndex) {
            case 0: return 0;    // 秒
            case 1: return 0;    // 分
            case 2: return 0;    // 时
            case 3: return 1;    // 日
            case 4: return 1;    // 月
            case 5: return 1;    // 周
            case 6: return 1970; // 年
            default: return 0;
        }
    }
    
    private int getMaxForPart(int partIndex) {
        switch (partIndex) {
            case 0: return 59;   // 秒
            case 1: return 59;   // 分
            case 2: return 23;   // 时
            case 3: return 31;   // 日
            case 4: return 12;   // 月
            case 5: return 7;    // 周
            case 6: return 2099; // 年
            default: return 0;
        }
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
        nextFireTimesTable.refresh();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        // 设置对话框样式
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStyleClass().add("alert-dialog");
        dialogPane.setStyle("-fx-background-color: #fff0f0; -fx-border-color: #dc3545; -fx-border-width: 2px;");
        
        // 设置按钮样式
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        
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
        
        // 添加一个别名方法，以便兼容可能存在的其他代码
        public String getFormattedDateTime() {
            return formatted;
        }
    }
}
