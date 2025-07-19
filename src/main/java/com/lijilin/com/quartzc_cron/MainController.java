package com.lijilin.com.quartzc_cron;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab generatorTab;

    @FXML
    private Tab parserTab;

    @FXML
    public void initialize() {
        // 初始化主控制器
        System.out.println("MainController initialized");
    }
}
