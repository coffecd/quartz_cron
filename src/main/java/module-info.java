module com.lijilin.com.quartzc_cron {
    requires javafx.controls;
    requires javafx.fxml;
    requires quartz;
    requires com.cronutils;


    opens com.lijilin.com.quartzc_cron to javafx.fxml;
    exports com.lijilin.com.quartzc_cron;
}