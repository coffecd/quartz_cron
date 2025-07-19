package com.lijilin.com.quartzc_cron;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.util.HashMap;
import java.util.Map;

public class GeneratorController {
    @FXML
    private ComboBox<String> secondComboBox;
    @FXML
    private ComboBox<String> minuteComboBox;
    @FXML
    private ComboBox<String> hourComboBox;
    @FXML
    private ComboBox<String> dayComboBox;
    @FXML
    private ComboBox<String> monthComboBox;
    @FXML
    private ComboBox<String> weekComboBox;
    @FXML
    private ComboBox<String> yearComboBox;

    @FXML
    private TextField secondTextField;
    @FXML
    private TextField minuteTextField;
    @FXML
    private TextField hourTextField;
    @FXML
    private TextField dayTextField;
    @FXML
    private TextField monthTextField;
    @FXML
    private TextField weekTextField;
    @FXML
    private TextField yearTextField;

    @FXML
    private TextField cronExpressionField;
    @FXML
    private TextArea cronExplanationArea;

    private final Map<String, String> cronExpressionMap = new HashMap<>();
    private final ObservableList<String> secondOptions = FXCollections.observableArrayList(
            "每秒",
            "周期",
            "从某秒开始，每隔",
            "指定"
    );
    
    private final ObservableList<String> minuteOptions = FXCollections.observableArrayList(
            "每分",
            "周期",
            "从某分开始，每隔",
            "指定"
    );
    
    private final ObservableList<String> hourOptions = FXCollections.observableArrayList(
            "每时",
            "周期",
            "从某时开始，每隔",
            "指定"
    );
    
    private final ObservableList<String> dayOptions = FXCollections.observableArrayList(
            "每日",
            "周期",
            "从某日开始，每隔",
            "指定",
            "不指定"
    );
    
    private final ObservableList<String> monthOptions = FXCollections.observableArrayList(
            "每月",
            "周期",
            "从某月开始，每隔",
            "指定"
    );
    
    private final ObservableList<String> weekOptions = FXCollections.observableArrayList(
            "每周",
            "周期",
            "从某周开始，每隔",
            "指定",
            "不指定"
    );
    
    private final ObservableList<String> yearOptions = FXCollections.observableArrayList(
            "每年",
            "周期",
            "从某年开始，每隔",
            "指定"
    );

    @FXML
    public void initialize() {
        // 初始化所有ComboBox
        initializeComboBoxes();

        // 添加ComboBox选择监听器
        addComboBoxListeners();

        // 初始化默认值
        initializeDefaultValues();
    }

    private void initializeComboBoxes() {
        secondComboBox.setItems(secondOptions);
        minuteComboBox.setItems(minuteOptions);
        hourComboBox.setItems(hourOptions);
        dayComboBox.setItems(dayOptions);
        monthComboBox.setItems(monthOptions);
        weekComboBox.setItems(weekOptions);
        yearComboBox.setItems(yearOptions);
        
        // 为秒选项添加工具提示
        setTooltip(secondComboBox, "每秒", "表示每秒都执行，对应Cron表达式中的'*'");
        setTooltip(secondComboBox, "周期", "表示一个时间范围，如1-5表示第1到5秒，对应Cron表达式中的'1-5'");
        setTooltip(secondComboBox, "从某秒开始，每隔", "表示从某个秒数开始每隔几秒执行，如0/5表示从第0秒开始每隔5秒，对应Cron表达式中的'0/5'");
        setTooltip(secondComboBox, "指定", "表示指定具体的秒数，如1,3,5表示第1、3、5秒，对应Cron表达式中的'1,3,5'");
        
        // 为分钟选项添加工具提示
        setTooltip(minuteComboBox, "每分", "表示每分钟都执行，对应Cron表达式中的'*'");
        setTooltip(minuteComboBox, "周期", "表示一个时间范围，如10-20表示第10到20分钟，对应Cron表达式中的'10-20'");
        setTooltip(minuteComboBox, "从某分开始，每隔", "表示从某个分钟数开始每隔几分钟执行，如15/10表示从第15分钟开始每隔10分钟，对应Cron表达式中的'15/10'");
        setTooltip(minuteComboBox, "指定", "表示指定具体的分钟数，如5,10,15表示第5、10、15分钟，对应Cron表达式中的'5,10,15'");
        
        // 为小时选项添加工具提示
        setTooltip(hourComboBox, "每时", "表示每小时都执行，对应Cron表达式中的'*'");
        setTooltip(hourComboBox, "周期", "表示一个时间范围，如8-17表示上午8点到下午5点，对应Cron表达式中的'8-17'");
        setTooltip(hourComboBox, "从某时开始，每隔", "表示从某个小时数开始每隔几小时执行，如6/3表示从6点开始每隔3小时，对应Cron表达式中的'6/3'");
        setTooltip(hourComboBox, "指定", "表示指定具体的小时数，如9,12,18表示上午9点、中午12点、下午6点，对应Cron表达式中的'9,12,18'");
        
        // 为日期选项添加工具提示
        setTooltip(dayComboBox, "每日", "表示每天都执行，对应Cron表达式中的'*'");
        setTooltip(dayComboBox, "周期", "表示一个日期范围，如5-10表示每月5号到10号，对应Cron表达式中的'5-10'");
        setTooltip(dayComboBox, "从某日开始，每隔", "表示从某个日期开始每隔几天执行，如1/5表示从1号开始每隔5天，对应Cron表达式中的'1/5'");
        setTooltip(dayComboBox, "指定", "表示指定具体的日期，如1,15,31表示每月1号、15号、31号，对应Cron表达式中的'1,15,31'");
        setTooltip(dayComboBox, "不指定", "表示不指定具体日期(通常与周一起使用)，对应Cron表达式中的'?'");
        
        // 为月份选项添加工具提示
        setTooltip(monthComboBox, "每月", "表示每个月都执行，对应Cron表达式中的'*'");
        setTooltip(monthComboBox, "周期", "表示一个月份范围，如3-6表示3月到6月，对应Cron表达式中的'3-6'");
        setTooltip(monthComboBox, "从某月开始，每隔", "表示从某个月份开始每隔几个月执行，如2/3表示从2月开始每隔3个月，对应Cron表达式中的'2/3'");
        setTooltip(monthComboBox, "指定", "表示指定具体的月份，如1,4,7表示1月、4月、7月，对应Cron表达式中的'1,4,7'");
        
        // 为周选项添加工具提示
        setTooltip(weekComboBox, "每周", "表示每周都执行，对应Cron表达式中的'*'");
        setTooltip(weekComboBox, "周期", "表示一个周范围，如1-5表示周一到周五，对应Cron表达式中的'1-5'");
        setTooltip(weekComboBox, "从某周开始，每隔", "表示从某个星期几开始每隔几周执行，如2/2表示从周二开始每隔2周，对应Cron表达式中的'2/2'");
        setTooltip(weekComboBox, "指定", "表示指定具体的星期几，如1,3,5表示周一、周三、周五，对应Cron表达式中的'1,3,5'");
        setTooltip(weekComboBox, "不指定", "表示不指定具体星期几(通常与日期一起使用)，对应Cron表达式中的'?'");
        
        // 为年选项添加工具提示
        setTooltip(yearComboBox, "每年", "表示每年都执行，对应Cron表达式中的'*'");
        setTooltip(yearComboBox, "周期", "表示一个年份范围，如2020-2025表示2020年到2025年，对应Cron表达式中的'2020-2025'");
        setTooltip(yearComboBox, "从某年开始，每隔", "表示从某个年份开始每隔几年执行，如2020/2表示从2020年开始每隔2年，对应Cron表达式中的'2020/2'");
        setTooltip(yearComboBox, "指定", "表示指定具体的年份，如2023,2025表示2023年和2025年，对应Cron表达式中的'2023,2025'");
    }
    
    private void setTooltip(ComboBox<String> comboBox, String item, String tooltipText) {
        comboBox.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                if (item != null && !empty) {
                    setTooltip(new Tooltip(tooltipText));
                }
            }
        });
        
        comboBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                if (item != null && !empty) {
                    setTooltip(new Tooltip(tooltipText));
                }
            }
        });
    }

    private void addComboBoxListeners() {
        // ComboBox选择监听器
        secondComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTextField(secondTextField, newValue));
        minuteComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTextField(minuteTextField, newValue));
        hourComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTextField(hourTextField, newValue));
        dayComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTextField(dayTextField, newValue));
        monthComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTextField(monthTextField, newValue));
        weekComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTextField(weekTextField, newValue));
        yearComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTextField(yearTextField, newValue));
        
        // 文本字段焦点监听器 - 当用户完成编辑时自动验证和修正
        secondTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // 失去焦点时
                secondTextField.setText(validateAndCorrectField(secondTextField.getText(), 0, 59, "second", secondComboBox));
                updateCronExpressionMap();
                generateCronExpression();
            }
        });
        
        minuteTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                minuteTextField.setText(validateAndCorrectField(minuteTextField.getText(), 0, 59, "minute", minuteComboBox));
                updateCronExpressionMap();
                generateCronExpression();
            }
        });
        
        hourTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                hourTextField.setText(validateAndCorrectField(hourTextField.getText(), 0, 23, "hour", hourComboBox));
                updateCronExpressionMap();
                generateCronExpression();
            }
        });
        
        dayTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                dayTextField.setText(validateAndCorrectField(dayTextField.getText(), 1, 31, "day", dayComboBox));
                updateCronExpressionMap();
                generateCronExpression();
            }
        });
        
        monthTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                monthTextField.setText(validateAndCorrectField(monthTextField.getText(), 1, 12, "month", monthComboBox));
                updateCronExpressionMap();
                generateCronExpression();
            }
        });
        
        weekTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                weekTextField.setText(validateAndCorrectField(weekTextField.getText(), 1, 7, "week", weekComboBox));
                updateCronExpressionMap();
                generateCronExpression();
            }
        });
        
        yearTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                yearTextField.setText(validateAndCorrectField(yearTextField.getText(), 1970, 2099, "year", yearComboBox));
                updateCronExpressionMap();
                generateCronExpression();
            }
        });
    }
    
    /**
     * 更新Cron表达式映射
     */
    private void updateCronExpressionMap() {
        cronExpressionMap.put("second", secondTextField.getText());
        cronExpressionMap.put("minute", minuteTextField.getText());
        cronExpressionMap.put("hour", hourTextField.getText());
        cronExpressionMap.put("day", dayTextField.getText());
        cronExpressionMap.put("month", monthTextField.getText());
        cronExpressionMap.put("week", weekTextField.getText());
        cronExpressionMap.put("year", yearTextField.getText());
        
        // 确保日期和星期字段不能同时为"?"
        if ("?".equals(dayTextField.getText()) && "?".equals(weekTextField.getText())) {
            weekTextField.setText("*");
            cronExpressionMap.put("week", "*");
        }
    }

    private void initializeDefaultValues() {
        // 设置默认选择
        secondComboBox.getSelectionModel().select("每秒");
        minuteComboBox.getSelectionModel().select("每分");
        hourComboBox.getSelectionModel().select("每时");
        dayComboBox.getSelectionModel().select("每日");
        monthComboBox.getSelectionModel().select("每月");
        weekComboBox.getSelectionModel().select("每周");
        yearComboBox.getSelectionModel().select("每年");

        // 设置默认值
        secondTextField.setText("*");
        minuteTextField.setText("*");
        hourTextField.setText("*");
        dayTextField.setText("*");
        monthTextField.setText("*");
        weekTextField.setText("?");
        yearTextField.setText("");

        // 初始化表达式映射
        cronExpressionMap.put("second", "*");
        cronExpressionMap.put("minute", "*");
        cronExpressionMap.put("hour", "*");
        cronExpressionMap.put("day", "*");
        cronExpressionMap.put("month", "*");
        cronExpressionMap.put("week", "?");
        cronExpressionMap.put("year", "");

        // 生成初始表达式
        generateCronExpression();
    }

    private void updateTextField(TextField textField, String selectedOption) {
        switch (selectedOption) {
            case "每秒":
            case "每分":
            case "每时":
            case "每日":
            case "每月":
            case "每周":
            case "每年":
                textField.setText("*");
                break;
            case "周期":
                textField.setText("1-2");
                break;
            case "从某时刻开始，每隔":
                textField.setText("0/1");
                break;
            case "指定":
                textField.setText("1,2,3");
                break;
            case "不指定":
                textField.setText("?");
                
                // 确保日期和星期字段不能同时为"不指定"
                if (textField == dayTextField && "?".equals(weekTextField.getText())) {
                    weekTextField.setText("*");
                    weekComboBox.getSelectionModel().select("每周");
                } else if (textField == weekTextField && "?".equals(dayTextField.getText())) {
                    dayTextField.setText("*");
                    dayComboBox.getSelectionModel().select("每日");
                }
                break;
        }
    }

    @FXML
    private void onGenerateButtonClick() {
        // 验证并自动修正每个字段的值
        secondTextField.setText(validateAndCorrectField(secondTextField.getText(), 0, 59, "second", secondComboBox));
        minuteTextField.setText(validateAndCorrectField(minuteTextField.getText(), 0, 59, "minute", minuteComboBox));
        hourTextField.setText(validateAndCorrectField(hourTextField.getText(), 0, 23, "hour", hourComboBox));
        dayTextField.setText(validateAndCorrectField(dayTextField.getText(), 1, 31, "day", dayComboBox));
        monthTextField.setText(validateAndCorrectField(monthTextField.getText(), 1, 12, "month", monthComboBox));
        weekTextField.setText(validateAndCorrectField(weekTextField.getText(), 1, 7, "week", weekComboBox));
        yearTextField.setText(validateAndCorrectField(yearTextField.getText(), 1970, 2099, "year", yearComboBox));

        // 更新表达式映射
        cronExpressionMap.put("second", secondTextField.getText());
        cronExpressionMap.put("minute", minuteTextField.getText());
        cronExpressionMap.put("hour", hourTextField.getText());
        cronExpressionMap.put("day", dayTextField.getText());
        cronExpressionMap.put("month", monthTextField.getText());
        cronExpressionMap.put("week", weekTextField.getText());
        cronExpressionMap.put("year", yearTextField.getText());

        // 检查日期和星期字段是否为"?"，并更新ComboBox的选择
        if ("?".equals(dayTextField.getText())) {
            dayComboBox.getSelectionModel().select("不指定");
            // 确保星期字段不是"?"
            if ("?".equals(weekTextField.getText())) {
                weekTextField.setText("*");
                weekComboBox.getSelectionModel().select("每周");
                cronExpressionMap.put("week", "*");
            }
        }
        
        if ("?".equals(weekTextField.getText())) {
            weekComboBox.getSelectionModel().select("不指定");
            // 确保日期字段不是"?"
            if ("?".equals(dayTextField.getText())) {
                dayTextField.setText("*");
                dayComboBox.getSelectionModel().select("每日");
                cronExpressionMap.put("day", "*");
            }
        }

        // 生成表达式
        generateCronExpression();
    }
    
    /**
     * 验证并自动修正字段值
     * @param value 字段值
     * @param min 最小有效值
     * @param max 最大有效值
     * @param fieldName 字段名称
     * @param comboBox 对应的ComboBox
     * @return 修正后的值
     */
    private String validateAndCorrectField(String value, int min, int max, String fieldName, ComboBox<String> comboBox) {
        if (value == null || value.trim().isEmpty()) {
            // 如果是年字段，允许为空
            if (fieldName.equals("year")) {
                return "";
            }
            // 其他字段为空时，设置为默认值
            if (fieldName.equals("day") || fieldName.equals("week")) {
                return fieldName.equals("week") ? "?" : "*";
            }
            return "*";
        }
        
        // 处理特殊值
        if (value.equals("*") || value.equals("?")) {
            // 确保只有日期和星期字段可以使用"?"
            if (value.equals("?") && !fieldName.equals("day") && !fieldName.equals("week")) {
                return "*";
            }
            return value;
        }
        
        try {
            // 处理周期表达式 (例如: 1-5)
            if (value.contains("-")) {
                return validateAndCorrectRangeExpression(value, min, max);
            }
            
            // 处理间隔表达式 (例如: 0/5)
            if (value.contains("/")) {
                return validateAndCorrectIncrementExpression(value, min, max);
            }
            
            // 处理列表表达式 (例如: 1,3,5)
            if (value.contains(",")) {
                return validateAndCorrectListExpression(value, min, max);
            }
            
            // 处理单个数值
            int singleValue = Integer.parseInt(value.trim());
            if (singleValue < min || singleValue > max) {
                return String.valueOf(Math.max(min, Math.min(max, singleValue)));
            }
            
            return value.trim();
        } catch (NumberFormatException e) {
            // 如果无法解析，返回默认值
            if (fieldName.equals("day") || fieldName.equals("week")) {
                return fieldName.equals("week") ? "?" : "*";
            }
            return "*";
        }
    }
    
    /**
     * 验证并修正范围表达式 (例如: 1-5)
     */
    private String validateAndCorrectRangeExpression(String value, int min, int max) {
        String[] parts = value.split("-");
        if (parts.length != 2) {
            return min + "-" + max;
        }
        
        try {
            int start = Integer.parseInt(parts[0].trim());
            int end = Integer.parseInt(parts[1].trim());
            
            // 确保值在有效范围内
            start = Math.max(min, Math.min(max, start));
            end = Math.max(min, Math.min(max, end));
            
            // 确保开始值小于结束值
            if (start >= end) {
                end = Math.min(start + 1, max);
            }
            
            return start + "-" + end;
        } catch (NumberFormatException e) {
            return min + "-" + max;
        }
    }
    
    /**
     * 验证并修正间隔表达式 (例如: 0/5)
     */
    private String validateAndCorrectIncrementExpression(String value, int min, int max) {
        String[] parts = value.split("/");
        if (parts.length != 2) {
            return min + "/" + 1;
        }
        
        try {
            String startStr = parts[0].trim();
            int increment = Integer.parseInt(parts[1].trim());
            
            // 处理起始值
            int start;
            if (startStr.equals("*")) {
                start = min;
            } else {
                start = Integer.parseInt(startStr);
                start = Math.max(min, Math.min(max, start));
            }
            
            // 确保增量至少为1
            increment = Math.max(1, increment);
            
            return start + "/" + increment;
        } catch (NumberFormatException e) {
            return min + "/" + 1;
        }
    }
    
    /**
     * 验证并修正列表表达式 (例如: 1,3,5)
     */
    private String validateAndCorrectListExpression(String value, int min, int max) {
        String[] parts = value.split(",");
        StringBuilder correctedList = new StringBuilder();
        
        boolean isFirst = true;
        for (String part : parts) {
            try {
                int val = Integer.parseInt(part.trim());
                val = Math.max(min, Math.min(max, val));
                
                if (!isFirst) {
                    correctedList.append(",");
                }
                correctedList.append(val);
                isFirst = false;
            } catch (NumberFormatException e) {
                // 忽略无效值
            }
        }
        
        // 如果所有值都无效，返回默认值
        if (correctedList.length() == 0) {
            return String.valueOf(min);
        }
        
        return correctedList.toString();
    }

    @FXML
    private void onCopyButtonClick() {
        String cronExpression = cronExpressionField.getText();
        if (cronExpression != null && !cronExpression.isEmpty()) {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(cronExpression);
            clipboard.setContent(content);

            // 显示复制成功提示
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("复制成功");
            alert.setHeaderText(null);
            alert.setContentText("Cron表达式已复制到剪贴板！");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void onParseButtonClick() {
        String expression = cronExpressionField.getText();
        if (expression != null && !expression.isEmpty()) {
            parseExpression(expression);
        }
    }
    
    private void parseExpression(String expression) {
        String[] parts = expression.split("\\s+");
        if (parts.length < 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("无效的Cron表达式");
            alert.setHeaderText(null);
            alert.setContentText("Cron表达式至少需要6个部分");
            alert.showAndWait();
            return;
        }

        // 解析各个部分
        secondTextField.setText(parts[0]);
        minuteTextField.setText(parts[1]);
        hourTextField.setText(parts[2]);
        dayTextField.setText(parts[3]);
        monthTextField.setText(parts[4]);
        weekTextField.setText(parts[5]);
        
        if (parts.length > 6) {
            yearTextField.setText(parts[6]);
        } else {
            yearTextField.setText("");
        }

        // 更新ComboBox的选择
        updateComboBoxSelectionFromValue(secondTextField.getText(), secondComboBox);
        updateComboBoxSelectionFromValue(minuteTextField.getText(), minuteComboBox);
        updateComboBoxSelectionFromValue(hourTextField.getText(), hourComboBox);
        updateComboBoxSelectionFromValue(dayTextField.getText(), dayComboBox);
        updateComboBoxSelectionFromValue(monthTextField.getText(), monthComboBox);
        updateComboBoxSelectionFromValue(weekTextField.getText(), weekComboBox);
        updateComboBoxSelectionFromValue(yearTextField.getText(), yearComboBox);

        // 更新表达式映射
        cronExpressionMap.put("second", secondTextField.getText());
        cronExpressionMap.put("minute", minuteTextField.getText());
        cronExpressionMap.put("hour", hourTextField.getText());
        cronExpressionMap.put("day", dayTextField.getText());
        cronExpressionMap.put("month", monthTextField.getText());
        cronExpressionMap.put("week", weekTextField.getText());
        cronExpressionMap.put("year", yearTextField.getText());

        // 生成表达式
        generateCronExpression();
    }
    
    private void updateComboBoxSelectionFromValue(String value, ComboBox<String> comboBox) {
        if (value.equals("*")) {
            if (comboBox == secondComboBox) {
                comboBox.getSelectionModel().select("每秒");
            } else if (comboBox == minuteComboBox) {
                comboBox.getSelectionModel().select("每分");
            } else if (comboBox == hourComboBox) {
                comboBox.getSelectionModel().select("每时");
            } else if (comboBox == dayComboBox) {
                comboBox.getSelectionModel().select("每日");
            } else if (comboBox == monthComboBox) {
                comboBox.getSelectionModel().select("每月");
            } else if (comboBox == weekComboBox) {
                comboBox.getSelectionModel().select("每周");
            } else if (comboBox == yearComboBox) {
                comboBox.getSelectionModel().select("每年");
            }
        } else if (value.equals("?")) {
            comboBox.getSelectionModel().select("不指定");
        } else if (value.contains("-")) {
            comboBox.getSelectionModel().select("周期");
        } else if (value.contains("/")) {
            comboBox.getSelectionModel().select("从某时刻开始，每隔");
        } else if (value.contains(",")) {
            comboBox.getSelectionModel().select("指定");
        } else {
            // 如果是单个数字，默认选择"指定"
            comboBox.getSelectionModel().select("指定");
        }
    }

    private void generateCronExpression() {
        StringBuilder sb = new StringBuilder();
        sb.append(cronExpressionMap.get("second")).append(" ");
        sb.append(cronExpressionMap.get("minute")).append(" ");
        sb.append(cronExpressionMap.get("hour")).append(" ");
        sb.append(cronExpressionMap.get("day")).append(" ");
        sb.append(cronExpressionMap.get("month")).append(" ");
        sb.append(cronExpressionMap.get("week"));

        String year = cronExpressionMap.get("year");
        if (year != null && !year.isEmpty()) {
            sb.append(" ").append(year);
        }

        String cronExpression = sb.toString();
        cronExpressionField.setText(cronExpression);

        // 生成表达式说明
        generateCronExplanation(cronExpression);
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
            return "所有" + (min == 1 ? "个" : "");
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
}
