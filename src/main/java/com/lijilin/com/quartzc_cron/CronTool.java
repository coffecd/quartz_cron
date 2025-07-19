package com.lijilin.com.quartzc_cron;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CronTool extends Application {
    // 常量定义
    public static final String[] COMMON_EXPRESSIONS = {
            "每隔5秒执行一次: */5 * * * * ?",
            "每隔1分钟执行一次: 0 */1 * * * ?",
            "每小时执行一次: 0 0 * * * ?",
            "每天凌晨1点执行一次: 0 0 1 * * ?",
            "每月1号凌晨1点执行一次: 0 0 1 1 * ?",
            "每周一凌晨1点执行一次: 0 0 1 ? * MON",
            "每年1月1日凌晨1点执行一次: 0 0 1 1 1 ? *"
    };

    @Override
    public void start(Stage primaryStage) {
        try {
            // 加载FXML文件
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lijilin/com/quartzc_cron/main.fxml"));
            Parent root = loader.load();

            // 创建场景
            Scene scene = new Scene(root, 900, 700);

            // 设置舞台
            primaryStage.setTitle("Quartz Cron工具");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
